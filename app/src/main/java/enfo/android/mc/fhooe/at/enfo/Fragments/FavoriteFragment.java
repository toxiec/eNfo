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

import enfo.android.mc.fhooe.at.enfo.Activities.TournamentActivity;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.TournamentsAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentType;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

/**
 * Created by David on 01.07.2017.
 */

public class FavoriteFragment extends Fragment implements ModelChangeListener {
    private RecyclerView mFavoriteRecyclerView;
    private TournamentsAdapter mTournamentAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        if (NetworkCheck.isNetworkAvailable(getActivity())) {

            EntityManager.getInstance().addModelChangeListener(this);

            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_favorites);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    EntityManager.getInstance().requestFavoriteList(getActivity());
                }
            });

            mFavoriteRecyclerView = (RecyclerView) view.findViewById(R.id.rv_favorites);
            mTournamentAdapter = new TournamentsAdapter(getActivity(), R.layout.item_featured_tournament_layout, null, TournamentType.favorites);
            EntityManager.getInstance().requestFavoriteList(getActivity());

            mFavoriteRecyclerView.setAdapter(mTournamentAdapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mFavoriteRecyclerView.getContext(),
                    layoutManager.getOrientation());
            mFavoriteRecyclerView.addItemDecoration(dividerItemDecoration);
            mFavoriteRecyclerView.setLayoutManager(layoutManager);

            ItemClickSupport.addTo(mFavoriteRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Tournament tournament = EntityManager.getInstance().getFavoriteList().get(position);
                    EntityManager.getInstance().setCurrentTournament(tournament);
                    Intent i = new Intent(getActivity(), TournamentActivity.class);
                    startActivity(i);
                    //Toast.makeText(getActivity(), "Clicked on " + mTournamentList.get(position).getmName(), Toast.LENGTH_SHORT ).show();
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
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentDownloadRunning());
                mTournamentAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
