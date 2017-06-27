package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.TournamentInformationAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Objects.Stream;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Entities.TournamentDetail;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;


public class TournamentInformationFragment extends Fragment implements JSONTask.AsyncResponse{
    private static final String TAG = "TIFragemtn";
    private Tournament mTournament;
    private Discipline mDiscipline;
    private static final String DISCIPLINE_KEY = "discipline_key";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mTournamentInformationRecyclerView;
    private final String mTournamentInformationURL = "https://api.toornament.com/v1/tournaments/";
    private String mJSONResult;
    private TournamentDetail mTournamentDetail;
    private List<Stream> mStreamList = new ArrayList<>();
    private List<TournamentInformationItem> mTInfoList = new ArrayList<>();
    private TournamentInformationAdapter mTInformationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tournament_information, container, false);

        Bundle bundle = getArguments();
        if(NetworkCheck.isNetworkAvailable(getActivity())){
            if(bundle!=null){
                if (bundle.containsKey(TOURNAMENT_KEY)) {
                    mTournament = (Tournament) bundle.getSerializable(TOURNAMENT_KEY);
                    //Log.i(TAG, mTournament.getmName());

                }if(bundle.containsKey(DISCIPLINE_KEY)){
                    mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                }
            }
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_tournament_information);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getTournamentInformation();
                }
            });
            mTournamentInformationRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tournament_information);
            getTournamentInformation();
            mTInformationAdapter = new TournamentInformationAdapter(getActivity(), R.layout.item_tournament_information, mDiscipline, mTInfoList);
            mTournamentInformationRecyclerView.setAdapter(mTInformationAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mTournamentInformationRecyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mTournamentInformationRecyclerView.getContext(),
                    layoutManager.getOrientation());
            mTournamentInformationRecyclerView.addItemDecoration(dividerItemDecoration);

        }else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void getTournamentInformation(){
        StringBuilder builder = new StringBuilder();
        builder.append(mTournamentInformationURL);
        builder.append(mTournament.getmID());
        String url = builder.toString();
        //JSONTask jsonTask = new JSONTask(getActivity(), mSwipeRefreshLayout, this);
        //jsonTask.execute(url);
    }

    private void parseTournamentInformation() {
        if(mJSONResult == null){
            Toast.makeText(getContext(), "No Information Found", Toast.LENGTH_SHORT).show();
        }else{
            mTInfoList.clear();
            try {
                JSONObject jsonObject = new JSONObject(mJSONResult);

                String id = jsonObject.getString("id");
                String discipline = jsonObject.getString("discipline");
                String name = jsonObject.getString("name");
                String fullname = jsonObject.getString("full_name");
                String status = jsonObject.getString("full_name");

                String dateStart = jsonObject.getString("date_start");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date_start = sdf.parse(dateStart);

                String dateEnd = jsonObject.getString("date_end");
                Date date_end = sdf.parse(dateEnd);

                String timeZone = jsonObject.getString("timezone");
                boolean online = jsonObject.getBoolean("online");
                boolean publicT = jsonObject.getBoolean("public");
                String location = jsonObject.getString("location");
                String country = jsonObject.getString("country");
                int size = jsonObject.getInt("size");

                String participantDetail = jsonObject.getString("participant_type");
                String matchType = jsonObject.getString("match_type");
                String organzation = jsonObject.getString("organization");
                String website = jsonObject.getString("website");
                String description = jsonObject.getString("description");
                String rules = jsonObject.getString("rules");
                String prize = jsonObject.getString("prize");
                int teamSizeMin;
                int teamSizeMax;
                if(participantDetail.equals("team")){
                    teamSizeMin = jsonObject.getInt("team_min_size");
                    teamSizeMax = jsonObject.getInt("team_max_size");
                }else{
                    teamSizeMin = 0;
                    teamSizeMax = 0;
                }
                JSONArray jsonArrayStream = jsonObject.getJSONArray("streams");
                for(int j = 0; j< jsonArrayStream.length(); j++){
                    String stream_id = jsonArrayStream.getJSONObject(j).getString("id");
                    String stream_name = jsonArrayStream.getJSONObject(j).getString("name");
                    String stream_url =jsonArrayStream.getJSONObject(j).getString("url");
                    String stream_language = jsonArrayStream.getJSONObject(j).getString("language");
                    Stream stream = new Stream(stream_id, stream_name, stream_url, stream_language);
                    mStreamList.add(stream);
                }
                TournamentDetail tournamentDetail = new TournamentDetail(id, discipline, name, fullname, status, date_start, date_end,
                        online, publicT, location,country, size, timeZone, participantDetail, matchType, organzation,
                        website, description, rules, prize, teamSizeMin, teamSizeMax, mStreamList);

                mTournamentDetail = tournamentDetail;

                fill_with_data();
                mTInformationAdapter.notifyDataSetChanged();
                //System.out.println(mTournamentDetail.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void fill_with_data(){
            List<TournamentInformationItem> list = new ArrayList<>();
            mTInfoList.add(new TournamentInformationItem("Discipline", mDiscipline.getmName()));
            if(mTournamentDetail.getmParticipantType().equals("team")){
                mTInfoList.add(new TournamentInformationItem("Participants", Integer.toString(mTournamentDetail.getmSize())+" Teams"));
            }else{
                mTInfoList.add(new TournamentInformationItem("Participants", Integer.toString(mTournamentDetail.getmSize())+" Players"));
            }
            mTInfoList.add(new TournamentInformationItem("Organizer", mTournamentDetail.getmOrganization()));
            mTInfoList.add(new TournamentInformationItem("Start Date", mTournamentDetail.getmDateStart().toString()));
            mTInfoList.add(new TournamentInformationItem("End Date", mTournamentDetail.getmDateEnd().toString()));
            mTInfoList.add(new TournamentInformationItem("Description", mTournamentDetail.getmDescription()));
            mTInfoList.add(new TournamentInformationItem("Price", mTournamentDetail.getmPrize()));
            mTInfoList.add(new TournamentInformationItem("Rules", mTournamentDetail.getmRules()));
    }

    @Override
    public void processFinish(String output) {
        mJSONResult = output;
        parseTournamentInformation();
    }
}

