package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.TournamentInformationAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Objects.DisciplineID;
import enfo.android.mc.fhooe.at.enfo.Objects.Stream;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Entities.TournamentDetail;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;


public class TournamentInformationFragment extends Fragment implements ModelChangeListener{
    private static final String TAG = "TIFragemtn";
    private Tournament mTournament;
    private Discipline mDiscipline;
    private static final String DISCIPLINE_KEY = "discipline_key";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mTournamentInformationRecyclerView;
    private final String mTournamentInformationURL = "https://api.toornament.com/v1/tournaments/";
    private String mJSONResult;
    private TournamentDetail mTournamentDetail;
    private List<Stream> mStreamList = new ArrayList<>();
    private List<TournamentInformationItem> mTInfoList = new ArrayList<>();
    private TournamentInformationAdapter mTInformationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tournament_information, container, false);

        Bundle bundle = getArguments();
        if(NetworkCheck.isNetworkAvailable(getActivity())){
            if(bundle!=null){
                if (bundle.containsKey(TOURNAMENT_KEY)) {
                    mTournament = (Tournament) bundle.getSerializable(TOURNAMENT_KEY);
                    //Log.i(TAG, mTournament.getmName());

                }if(bundle.containsKey(DISCIPLINE_KEY)){
                    mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                }
            }

            EntityManager.getInstance().addModelChangeListener(this);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_tournament_information);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    EntityManager.getInstance().requestTournamentInformation(mTournament);
                }
            });
            mTournamentInformationRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tournament_information);
            EntityManager.getInstance().requestTournamentInformation(mTournament);
            mTInformationAdapter = new TournamentInformationAdapter(getActivity(), R.layout.item_tournament_information, mDiscipline);
            mTournamentInformationRecyclerView.setAdapter(mTInformationAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mTournamentInformationRecyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mTournamentInformationRecyclerView.getContext(),
                    layoutManager.getOrientation());
            mTournamentInformationRecyclerView.addItemDecoration(dividerItemDecoration);

        }else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentInformationDownloadRunning());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentInformationDownloadRunning());
                mTInformationAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isTournamentInformationDownloadRunning());
                Toast.makeText(getActivity(), "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

