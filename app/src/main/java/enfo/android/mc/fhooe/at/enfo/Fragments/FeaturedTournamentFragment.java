package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.R;

public class FeaturedTournamentFragment extends Fragment implements JSONTask.AsyncResponse {

    private static final String TAG = "FTF";
    private final String mFeaturedTournamentsURL = "https://api.toornament.com/v1/tournaments?featured=1";
    private String mJSONResult;
    /**Key which is used to receive the passed Discipline Object from DisciplineActivity*/
    private static final String DISCIPLINE_KEY = "discipline_key";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private Discipline mDiscipline;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Tournament> mTournamentList = new ArrayList<>();
    private RecyclerView mFeaturedTournamentsRecyclerView;
    private TournamentsAdapter mTournamentsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured_tournament, container, false);

        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.containsKey(DISCIPLINE_KEY)){
                mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                Log.i(TAG, mDiscipline.getmFullname());
            }
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_featured_tournaments);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFeaturedTournaments();
            }
        });

        mFeaturedTournamentsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_featuredMatches);
        getFeaturedTournaments();
        mTournamentsAdapter = new TournamentsAdapter(getActivity(), R.layout.item_featured_tournament_layout, mDiscipline, mTournamentList);
        mFeaturedTournamentsRecyclerView.setAdapter(mTournamentsAdapter);
        mFeaturedTournamentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemClickSupport.addTo(mFeaturedTournamentsRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Tournament tournament = mTournamentList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(TOURNAMENT_KEY, tournament);
                bundle.putSerializable(DISCIPLINE_KEY, mDiscipline);
                Intent i = new Intent(getActivity(), TournamentActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                //Toast.makeText(getActivity(), "Clicked on " + mTournamentList.get(position).getmName(), Toast.LENGTH_SHORT ).show();
            }
        });
        return view;
    }

    private void getFeaturedTournaments(){
        StringBuilder urlbuilder = new StringBuilder();
        urlbuilder.append(mFeaturedTournamentsURL);
        //get Tournaments of specified Discipline
        urlbuilder.append("&discipline="+mDiscipline.getmId());
        String url = urlbuilder.toString();
        //JSONTask jsonTask = new JSONTask(getActivity());
        //jsonTask.execute(url);
    }

    private void parseFeaturedTnmt() {
        if(mJSONResult == null){
            Toast.makeText(getContext(), "No Featured Tournaments Found", Toast.LENGTH_SHORT).show();
        }else{
            mTournamentList.clear();
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
                    Date date_end = sdf.parse(dateEnd);

                    boolean online = jsonobject.getBoolean("online");
                    boolean publicT = jsonobject.getBoolean("public");
                    String location = jsonobject.getString("location");
                    String country = jsonobject.getString("country");
                    int size = jsonobject.getInt("size");


                    Tournament tournament = new Tournament(id, discipline, name,fullname,status,date_start,date_end,
                                online,publicT,location,country,size);
                        //mDisciplineList.add(discipline);

                    //mTournamentAdapter.add(tournament);
                    //mTournamentAdapter.notifyDataSetChanged();
                    mTournamentList.add(tournament);
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
        parseFeaturedTnmt();
    }
}
