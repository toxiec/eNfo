package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Activities.TournamentActivity;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.TournamentsAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;

public class RunningFragment extends Fragment implements JSONTask.AsyncResponse {

    private static final String TAG = "FTF";
    private final String mRunningTournamentsURL = "https://api.toornament.com/v1/tournaments?";
    private String mJSONResult;
    /**Key which is used to receive the passed Discipline Object from DisciplineActivity*/
    private static final String DISCIPLINE_KEY = "discipline_key";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private Discipline mDiscipline;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TournamentsAdapter mTournamentsAdapter;
    private RecyclerView mRunningTournamentsRecycleView;
    private List<Tournament> mRunningTournamentsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_tournaments, container, false);

        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.containsKey(DISCIPLINE_KEY)){
                mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                Log.i(TAG, mDiscipline.getmFullname());
            }
        }

        //Log.i(TAG, mDiscipline.getmId());
        //mRunningTournamentsListView = (ListView) view.findViewById(R.id.lv_runningTournaments);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_running_tournaments);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRunningTournaments();
            }
        });
        mRunningTournamentsRecycleView = (RecyclerView) view.findViewById(R.id.rv_runningTournaments);
        getRunningTournaments();
        mTournamentsAdapter = new TournamentsAdapter(getActivity(), R.layout.item_featured_tournament_layout, mDiscipline, mRunningTournamentsList);
        mRunningTournamentsRecycleView.setAdapter(mTournamentsAdapter);
        mRunningTournamentsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemClickSupport.addTo(mRunningTournamentsRecycleView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Tournament tournament = mRunningTournamentsList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(TOURNAMENT_KEY, tournament);
                bundle.putSerializable(DISCIPLINE_KEY, mDiscipline);
                Intent i = new Intent(getActivity(), TournamentActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                //Toast.makeText(getActivity(), "Clicked on " + mRunningTournamentsList.get(position).getmName(), Toast.LENGTH_SHORT ).show();
            }
        });
        return view;
    }

    private void getRunningTournaments(){
        StringBuilder urlbuilder = new StringBuilder();
        urlbuilder.append(mRunningTournamentsURL);
        //get Tournaments of specified Discipline
        //urlbuilder.append("featured=1");
        urlbuilder.append("&discipline="+mDiscipline.getmId());
        urlbuilder.append(("&status=running"));
        String url = urlbuilder.toString();
        JSONTask jsonTask = new JSONTask(getActivity(), mSwipeRefreshLayout, this);
        jsonTask.execute(url);
    }

    private void parseRunningTnmt() {
        if(mJSONResult == null){
            Toast.makeText(getContext(), "No Running Tournaments Found", Toast.LENGTH_SHORT).show();
        }else{
            mRunningTournamentsList.clear();
            try {
                JSONArray jsonarray = new JSONArray(mJSONResult);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String id = jsonobject.getString("id");
                    String discipline = jsonobject.getString("discipline");
                    String name = jsonobject.getString("name");
                    String fullname = jsonobject.getString("full_name");
                    String status = jsonobject.getString("full_name");

                    String dateStart = jsonobject.getString("date_start");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date_start = sdf.parse(dateStart);

                    String dateEnd = jsonobject.getString("date_start");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date_end = sdf2.parse(dateEnd);

                    boolean online = jsonobject.getBoolean("online");
                    boolean publicT = jsonobject.getBoolean("public");
                    String location = jsonobject.getString("location");
                    String country = jsonobject.getString("country");
                    int size = jsonobject.getInt("size");

                    Tournament tournament = new Tournament(id, discipline, name,fullname,status,date_start,date_end,
                            online,publicT,location,country,size);
                    //mDisciplineList.add(discipline);
                    mRunningTournamentsList.add(tournament);
                }
                mTournamentsAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processFinish(String output) {
        mJSONResult = output;
        parseRunningTnmt();
    }
}
