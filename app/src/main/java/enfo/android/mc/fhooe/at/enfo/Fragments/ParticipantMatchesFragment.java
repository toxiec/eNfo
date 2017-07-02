package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.content.Intent;
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

import enfo.android.mc.fhooe.at.enfo.Activities.GameActivity;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.MatchAdapter;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.ParticipantLineupAdapter;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.ParticipantMatchAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Objects.MatchType;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

/**
 * Created by David on 30.06.2017.
 */

public class ParticipantMatchesFragment extends Fragment implements ModelChangeListener {
    private RecyclerView mParticipantMatchesRecylcerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ParticipantMatchAdapter mParticipantMatchesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participant_matches, container, false);

        if (NetworkCheck.isNetworkAvailable(getActivity())) {
            EntityManager.getInstance().addModelChangeListener(this);

            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_participant_matches);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    EntityManager.getInstance().requestMatches(MatchType.participantMatches);
                }
            });
            mParticipantMatchesRecylcerView = (RecyclerView) view.findViewById(R.id.rv_participant_matches);

            mParticipantMatchesAdapter = new ParticipantMatchAdapter(getActivity(), R.layout.item_match);
            EntityManager.getInstance().requestMatches(MatchType.participantMatches);
            mParticipantMatchesRecylcerView.setAdapter(mParticipantMatchesAdapter);


            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mParticipantMatchesRecylcerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mParticipantMatchesRecylcerView.getContext(),
                    layoutManager.getOrientation());
            mParticipantMatchesRecylcerView.addItemDecoration(dividerItemDecoration);

            ItemClickSupport.addTo(mParticipantMatchesRecylcerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Match match = EntityManager.getInstance().getParticipantMatchesList().get(position);
                    EntityManager.getInstance().setCurrentMatch(match);
                    Intent i = new Intent(getActivity(), GameActivity.class);
                    startActivity(i);
                }
            });

        }else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantMatchDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantMatchDownloadRunning());
                mParticipantMatchesAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantMatchDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


