package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.content.Intent;
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

import enfo.android.mc.fhooe.at.enfo.Activities.GameActivity;
import enfo.android.mc.fhooe.at.enfo.Activities.ParticipantActivity;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.MatchAdapter;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.TournamentsAdapter;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Objects.MatchType;
import enfo.android.mc.fhooe.at.enfo.Objects.Opponent;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;

/**Displays the Matches of a Tournament */
public class MatchFragment extends Fragment implements ModelChangeListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MatchAdapter mMatchesAdapter;
    private RecyclerView mMatchesRecycleView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        EntityManager.getInstance().addModelChangeListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_matches);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                EntityManager.getInstance().requestMatches(MatchType.allTournamentMatches);
            }
        });
        mMatchesRecycleView = (RecyclerView) view.findViewById(R.id.rv_matches);

        mMatchesAdapter = new MatchAdapter(getActivity(), R.layout.item_match);
        mMatchesRecycleView.setAdapter(mMatchesAdapter);
        EntityManager.getInstance().requestMatches(MatchType.allTournamentMatches);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mMatchesRecycleView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMatchesRecycleView.getContext(),
                layoutManager.getOrientation());
        mMatchesRecycleView.addItemDecoration(dividerItemDecoration);
        ItemClickSupport.addTo(mMatchesRecycleView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Match match = EntityManager.getInstance().getMatchesList().get(position);
                EntityManager.getInstance().setCurrentMatch(match);
                Intent i = new Intent(getActivity(), GameActivity.class);
                startActivity(i);
                //Toast.makeText(getActivity(), "Clicked on Match "+ mMatchList.get(position).getmID(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isMatchDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isMatchDownloadRunning());
                mMatchesAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isMatchDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
