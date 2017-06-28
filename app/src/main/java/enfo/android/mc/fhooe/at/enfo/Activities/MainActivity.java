package enfo.android.mc.fhooe.at.enfo.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.DisciplineAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;
import enfo.android.mc.fhooe.at.enfo.R;

public class MainActivity extends AppCompatActivity implements ModelChangeListener{
    private static final String TAG = "MainActivity";
    /**Key to get Discipline Data from the Bundle */
    private static final String DISCIPLINE_KEY = "discipline_key";
    /**Key will be used to authenticate your application on the Toornament API*/
    private final String API_KEY = "JK5nCbHtb9yEGHDYNCdYgCvHGXRD7r-3HwVOJDjSMME";
    /**adds the API KEY to the HTTP Header to authenticate the App*/
    private final String API_KEY_HTTP_HEADER = "X-Api-Key";
    /**URL to receive all disciplines available in the API*/
    private final String mDisciplesURL = "https://api.toornament.com/v1/disciplines";
    /**Contains the Discipline JSON Result*/
    private String mJSONResult;
    /**List which displays the fetched Disciplines*/
    private ListView mDisciplineListView;
    /**Contains the Discipline Items*/
    private RecyclerView mDisciplineRecycleView;
    /**Adapter to populate the mDisciplineRecycleView*/
    private DisciplineAdapter mDisciplineAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(NetworkCheck.isNetworkAvailable(this)){
            //View bei Model anmelden
            EntityManager.getInstance().addModelChangeListener(this);

            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_activity_main);
            mDisciplineRecycleView = (RecyclerView) findViewById(R.id.rv_discipline);
            EntityManager.getInstance().requestDiscipline();

            mDisciplineAdapter = new DisciplineAdapter(this, R.layout.item_disciple_layout);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
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
                    Log.i(TAG, discipline.getmFullname()+" Item clicked");
                    Toast.makeText(getApplicationContext(), discipline.getmName(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DISCIPLINE_KEY, discipline);
                    Intent i = new Intent(MainActivity.this, DisciplineActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(this, "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
