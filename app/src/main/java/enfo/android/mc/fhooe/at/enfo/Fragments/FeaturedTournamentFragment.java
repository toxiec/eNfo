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
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentType;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.R;

/**Displays the Featured Tournaments*/
public class FeaturedTournamentFragment extends Fragment implements ModelChangeListener {

    private static final String TAG = "FTF";
    /**Key which is used to receive the passed Discipline Object from DisciplineActivity*/
    private static final String DISCIPLINE_KEY = "discipline_key";
    /**Key which is used to receive the passed Tournament Object from DisciplineActivity*/
    private static final String TOURNAMENT_KEY = "tournament_key";
    /**Current selected Discipline*/
    private Discipline mDiscipline;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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

        EntityManager.getInstance().addModelChangeListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_featured_tournaments);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                EntityManager.getInstance().requestTournaments(mDiscipline, true);
            }
        });

        mFeaturedTournamentsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_featuredMatches);
        EntityManager.getInstance().requestTournaments(mDiscipline, true);
        mTournamentsAdapter = new TournamentsAdapter(getActivity(), R.layout.item_featured_tournament_layout, mDiscipline, TournamentType.featured);
        mFeaturedTournamentsRecyclerView.setAdapter(mTournamentsAdapter);
        mFeaturedTournamentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemClickSupport.addTo(mFeaturedTournamentsRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Tournament tournament = EntityManager.getInstance().getTournamentList(TournamentType.featured).get(position);
                EntityManager.getInstance().setCurrentTournament(tournament);
                EntityManager.getInstance().setCurrentDiscipline(mDiscipline);
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

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentDownloadRunning());
                mTournamentsAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
