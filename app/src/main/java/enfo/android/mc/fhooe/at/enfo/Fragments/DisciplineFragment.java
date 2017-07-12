package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import enfo.android.mc.fhooe.at.enfo.Activities.DisciplineActivity;
import enfo.android.mc.fhooe.at.enfo.Activities.MainActivity;
import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.DisciplineAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

/**Displays the Disciplines in a Grid Layout*/
public class DisciplineFragment extends Fragment implements ModelChangeListener {
    /**Key to get Discipline Data from the Bundle */
    private static final String DISCIPLINE_KEY = "discipline_key";
    /**Contains the Discipline Items*/
    private RecyclerView mDisciplineRecycleView;
    /**Adapter to populate the mDisciplineRecycleView*/
    private DisciplineAdapter mDisciplineAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discipline, container, false);
        if (NetworkCheck.isNetworkAvailable(getActivity())) {

            EntityManager.getInstance().addModelChangeListener(this);

            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_discipline);
            mDisciplineRecycleView = (RecyclerView) view.findViewById(R.id.rv_discipline);
            EntityManager.getInstance().requestDiscipline();

            mDisciplineAdapter = new DisciplineAdapter(getActivity(), R.layout.item_disciple_layout);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mDisciplineRecycleView.setLayoutManager(mLayoutManager);
            mDisciplineRecycleView.setItemAnimator(new DefaultItemAnimator());
            mDisciplineRecycleView.setAdapter(mDisciplineAdapter);
            mDisciplineRecycleView.setHasFixedSize(true);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    EntityManager.getInstance().requestDiscipline();
                }
            });

            ItemClickSupport.addTo(mDisciplineRecycleView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Discipline discipline = EntityManager.getInstance().getDisciplineList().get(position);
                    EntityManager.getInstance().setCurrentDiscipline(discipline);
                    Toast.makeText(getActivity(), discipline.getmName(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DISCIPLINE_KEY, discipline);
                    Intent i = new Intent(getActivity(), DisciplineActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            });
        }
        return view;
    }

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isDisciplineDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isDisciplineDownloadRunning());
                mDisciplineAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isDisciplineDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
