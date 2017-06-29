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

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.ParticipantAdapter;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.ParticipantLineupAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

/**
 * Created by David on 29.06.2017.
 */

public class ParticipantLineUpFragment extends Fragment implements ModelChangeListener {
    private RecyclerView mParticipantLineupRecylcerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ParticipantLineupAdapter mParticipantLineupAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participant_lineup, container, false);

        if (NetworkCheck.isNetworkAvailable(getActivity())) {
            EntityManager.getInstance().addModelChangeListener(this);

            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_participant_lineup);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    EntityManager.getInstance().requestPlayers();
                }
            });
            mParticipantLineupRecylcerView = (RecyclerView) view.findViewById(R.id.rv_participant_lineup);
            mParticipantLineupAdapter = new ParticipantLineupAdapter(getActivity(), R.layout.item_player);
            mParticipantLineupRecylcerView.setAdapter(mParticipantLineupAdapter);
            EntityManager.getInstance().requestPlayers();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mParticipantLineupRecylcerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mParticipantLineupRecylcerView.getContext(),
                    layoutManager.getOrientation());
            mParticipantLineupRecylcerView.addItemDecoration(dividerItemDecoration);

        }else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantDownloadRunning());
                mParticipantLineupAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
