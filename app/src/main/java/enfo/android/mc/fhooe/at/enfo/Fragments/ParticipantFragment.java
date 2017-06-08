package enfo.android.mc.fhooe.at.enfo.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.ParticipantAdapter;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Entities.TournamentDetail;
import enfo.android.mc.fhooe.at.enfo.Objects.ParticipantLogo;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;
import enfo.android.mc.fhooe.at.enfo.Objects.Stream;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

public class ParticipantFragment extends Fragment implements JSONTask.AsyncResponse {
    private static final String DISCIPLINE_KEY = "discipline_key";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private String mURL = "https://api.toornament.com/v1/tournaments/";
    private Tournament mTournament;
    private Discipline mDiscipline;
    private List<Participant> mParticipantList = new ArrayList<>();
    private String mJSONResult;

    private RecyclerView mParticipantRecylcerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ParticipantAdapter mParticipantAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_participant, container, false);

        Bundle bundle = getArguments();
        if (NetworkCheck.isNetworkAvailable(getActivity())) {
            if (bundle != null) {
                if (bundle.containsKey(TOURNAMENT_KEY)) {
                    mTournament = (Tournament) bundle.getSerializable(TOURNAMENT_KEY);
                    //Log.i(TAG, mTournament.getmName());

                }
                if (bundle.containsKey(DISCIPLINE_KEY)) {
                    mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                }
            }
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_participant);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getParticipants();
                }
            });
            mParticipantRecylcerView = (RecyclerView) view.findViewById(R.id.rv_participant);
            getParticipants();
            mParticipantAdapter = new ParticipantAdapter(getActivity(), R.layout.item_participant, mParticipantList);
            mParticipantRecylcerView.setAdapter(mParticipantAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mParticipantRecylcerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mParticipantRecylcerView.getContext(),
                    layoutManager.getOrientation());
            mParticipantRecylcerView.addItemDecoration(dividerItemDecoration);

        }else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void getParticipants(){
        StringBuilder builder = new StringBuilder();
        builder.append(mURL);
        builder.append(mTournament.getmID()+"/participants");
        String url = builder.toString();
        JSONTask jsonTask = new JSONTask(getActivity(), mSwipeRefreshLayout, this);
        jsonTask.execute(url);
    }

    @Override
    public void processFinish(String output) {
        mJSONResult = output;
        parseParticipants();
    }

    private void parseParticipants(){
        if(mJSONResult == null){
            Toast.makeText(getContext(), "No Information Found", Toast.LENGTH_SHORT).show();
        }else{
            mParticipantList.clear();
            try {
                JSONArray jsonarray = new JSONArray(mJSONResult);
                for(int i = 0; i<jsonarray.length(); i++){
                    JSONObject jsonObject = jsonarray.getJSONObject(i);

                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");

                    JSONObject jsonLogoObject = jsonObject.getJSONObject("logo");
                    String icon_large = jsonLogoObject.getString("icon_large_square");
                    String extra_small = jsonLogoObject.getString("extra_small_square");
                    String medium_small = jsonLogoObject.getString("medium_small_square");

                    ParticipantLogo participantLogo = new ParticipantLogo(icon_large, extra_small, medium_small);

                    String country = jsonObject.getString("country");

                    JSONArray jsonArrayLineup = jsonObject.getJSONArray("lineup");
                    List<Player> playerList = new ArrayList<>();
                    for(int j = 0; j<jsonArrayLineup.length(); j++){
                        String playerName = jsonArrayLineup.getJSONObject(i).getString("name");
                        String playerCountry = jsonArrayLineup.getJSONObject(i).getString("country");

                        JSONObject jsonObjectCustomField = new JSONObject("custom_fields");
                        List<String> playercustomField = new ArrayList<>();
                        String fieldType = jsonObjectCustomField.getString("type");
                        String fieldLabel = jsonObjectCustomField.getString("label");
                        String fieldValue = jsonObjectCustomField.getString("value");
                        playercustomField.add(fieldType);
                        playercustomField.add(fieldLabel);
                        playercustomField.add(fieldValue);

                        Player player = new Player(playerName, playerCountry, playercustomField);
                        playerList.add(player);
                    }

                    List<String> customField = new ArrayList<>();

                    String type = jsonObject.getString("type");
                    String label = jsonObject.getString("label");
                    String value = jsonObject.getString("value");
                    customField.add(type);
                    customField.add(label);
                    customField.add(value);

                    Participant participant = new Participant(id, name, participantLogo, country,  playerList, customField);
                    mParticipantList.add(participant);
                    mParticipantAdapter.notifyDataSetChanged();
                }

                //System.out.println(mTournamentDetail.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
