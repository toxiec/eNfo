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
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;
import enfo.android.mc.fhooe.at.enfo.R;

public class MainActivity extends AppCompatActivity implements DisciplineAdapter.ClickListener, JSONTask.AsyncResponse{
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
    /**List which contains the fetched Disciples*/
    private List<Discipline> mDisciplineList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(NetworkCheck.isNetworkAvailable(this)){
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_activity_main);
            mDisciplineRecycleView = (RecyclerView) findViewById(R.id.rv_discipline);
            getDisciplines();
            mDisciplineAdapter = new DisciplineAdapter(this, R.layout.item_disciple_layout, mDisciplineList);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            mDisciplineRecycleView.setLayoutManager(mLayoutManager);
            mDisciplineRecycleView.setItemAnimator(new DefaultItemAnimator());
            mDisciplineRecycleView.setAdapter(mDisciplineAdapter);
            mDisciplineRecycleView.setHasFixedSize(true);
            mDisciplineAdapter.setClickListener(this);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getDisciplines();
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Fetch Discipline from the API
     */
    private void getDisciplines() {
        JSONTask jsonTask = new JSONTask(this, mSwipeRefreshLayout, this);
        jsonTask.execute(mDisciplesURL);
        //parseDisciplineJSON();
        //new JSONTask().execute(mDisciplesURL);
    }

    /**
     * Onclick Method for the RecyclerView
     * @param view
     * @param position of the item which has been clicked
     */
    @Override
    public void itemClicked(View view, int position) {
        Discipline discipline = mDisciplineList.get(position);
        Log.i(TAG, discipline.getmFullname()+" Item clicked");
        Toast.makeText(getApplicationContext(), discipline.getmName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DISCIPLINE_KEY, discipline);
        Intent i = new Intent(MainActivity.this, DisciplineActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * Creates Tournament Objects from the received Discipline JSON and adds it to the Listview.
     */
    private void parseDisciplineJSON(){
        //Only Adds Disciplines which contain the ID from this Array (Filtering)
        String[] games = {"counterstrike_go","dota2", "hearthstone", "leagueoflegends","starcraft2_lotv","overwatch", "heroesofthestorm"};
        if(mJSONResult == null){
            Toast.makeText(getApplicationContext(), "No Games Found", Toast.LENGTH_SHORT).show();
        }else{
            mDisciplineList.clear();
            try {
                JSONArray jsonarray = new JSONArray(mJSONResult);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String id = jsonobject.getString("id");
                    String name = jsonobject.getString("name");
                    String shortname = jsonobject.getString("shortname");
                    String fullname = jsonobject.getString("fullname");
                    String copyrights = jsonobject.getString("copyrights");
                    System.out.println(id);
                    if(Arrays.asList(games).contains(id)){
                        Discipline discipline = new Discipline(id,name,shortname,fullname,copyrights);
                        //mDisciplineList.add(discipline);

                        //mDisciplineAdapter.add(discipline);
                        //mDisciplineAdapter.notifyDataSetChanged();
                        mDisciplineList.add(discipline);
                    }
                }
                mDisciplineAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processFinish(String output) {
        mJSONResult = output;
        parseDisciplineJSON();
    }
}
