package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.MatchAdapter;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.TournamentsAdapter;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Objects.Opponent;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;

/**
 * Created by David on 26.06.2017.
 */

public class MatchFragment extends Fragment implements JSONTask.AsyncResponse {
    private static final String TAG = "MatchFragment";
    private final String mMatchesURL = "https://api.toornament.com/v1/tournaments/";
    private String mJSONResult;
    /**Key which is used to receive the passed Discipline Object from DisciplineActivity*/
    private static final String DISCIPLINE_KEY = "discipline_key";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private Discipline mDiscipline;
    private Tournament mTournament;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MatchAdapter mMatchesAdapter;
    private RecyclerView mMatchesRecycleView;
    private List<Match> mMatchList = new ArrayList<>();
    private List<Participant> mParticipantList = new ArrayList<>();
    private List<Opponent> mOpponentList = new ArrayList<>();
    private Match mMatch;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(TOURNAMENT_KEY)) {
                mTournament = (Tournament) bundle.getSerializable(TOURNAMENT_KEY);
                //Log.i(TAG, mTournament.getmName());

            }if(bundle.containsKey(DISCIPLINE_KEY)){
                mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
            }
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_matches);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMatches();
            }
        });
        mMatchesRecycleView = (RecyclerView) view.findViewById(R.id.rv_matches);
        getMatches();
        mMatchesAdapter = new MatchAdapter(getActivity(), R.layout.item_match, mMatchList);
        mMatchesRecycleView.setAdapter(mMatchesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mMatchesRecycleView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMatchesRecycleView.getContext(),
                layoutManager.getOrientation());
        mMatchesRecycleView.addItemDecoration(dividerItemDecoration);
        ItemClickSupport.addTo(mMatchesRecycleView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity(), "Clicked on Match "+ mMatchList.get(position).getmID(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void getMatches(){
        StringBuilder urlbuilder = new StringBuilder();
        urlbuilder.append(mMatchesURL);
        urlbuilder.append(mTournament.getmID()+"/matches");
        String url = urlbuilder.toString();
        //JSONTask jsonTask = new JSONTask(getActivity(), mSwipeRefreshLayout, this);
        //jsonTask.execute(url);
    }

    public void parseMatches(){
        if(mJSONResult == null){
            Toast.makeText(getContext(), "No Matches Found", Toast.LENGTH_SHORT).show();
        }else{
            mMatchList.clear();
            try {
                JSONArray jsonArray = new JSONArray(mJSONResult);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String type = jsonObject.getString("type");
                    String discipline = jsonObject.getString("discipline");
                    String status = jsonObject.getString("status");
                    String tournamentID = jsonObject.getString("tournament_id");
                    int matchNumber = jsonObject.getInt("number");
                    int stageNumber = jsonObject.getInt("stage_number");
                    int groupNumber = jsonObject.getInt("group_number");
                    int roundNumber = jsonObject.getInt("round_number");
                    String date = jsonObject.getString("date");
                    String timezone = jsonObject.getString("timezone");
                    String matchFormat = jsonObject.getString("match_format");

                    JSONArray jsonArrayOpponents = jsonObject.getJSONArray("opponents");
                    mOpponentList = new ArrayList<>();
                    for(int j = 0; j < jsonArrayOpponents.length(); j++){

                        int number = jsonArrayOpponents.getJSONObject(j).getInt("number");

                        JSONObject jsonObjectParticipant = jsonArrayOpponents.getJSONObject(j).getJSONObject("participant");

                        String partId = jsonObjectParticipant.getString("id");
                        String partName = jsonObjectParticipant.getString("name");
                        String partCountry = jsonObjectParticipant.getString("country");
                        Participant participant = new Participant(partId, partName, null, partCountry, null, null);

                        int result = jsonArrayOpponents.getJSONObject(j).getInt("result");
                        int rank = 0;
                        try{
                            rank = jsonArrayOpponents.getJSONObject(j).getInt("rank");
                        }catch(JSONException e){

                        }
                        int score = jsonArrayOpponents.getJSONObject(j).getInt("score");
                        boolean forfeit = jsonArrayOpponents.getJSONObject(j).getBoolean("forfeit");
                        Opponent opponent = new Opponent(number, participant, result, rank, score, forfeit);
                        mOpponentList.add(opponent);
                    }

                    Match match = new Match(id, type, discipline, status, tournamentID, matchNumber, stageNumber,
                            groupNumber, roundNumber, date, timezone, matchFormat, mOpponentList);
                    mMatchList.add(match);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void processFinish(String output) {
        mJSONResult = output;
        parseMatches();
    }
}
