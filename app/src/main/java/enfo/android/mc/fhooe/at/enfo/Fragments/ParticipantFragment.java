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
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Objects.ParticipantLogo;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;
import enfo.android.mc.fhooe.at.enfo.Objects.Stream;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

public class ParticipantFragment extends Fragment implements ModelChangeListener {
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
            EntityManager.getInstance().addModelChangeListener(this);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_participant);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    EntityManager.getInstance().requestParticipants(mTournament);
                }
            });
            mParticipantRecylcerView = (RecyclerView) view.findViewById(R.id.rv_participant);
            EntityManager.getInstance().requestParticipants(mTournament);
            mParticipantAdapter = new ParticipantAdapter(getActivity(), R.layout.item_participant);
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

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantDownloadRunning());
                mParticipantAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isParticipantDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
