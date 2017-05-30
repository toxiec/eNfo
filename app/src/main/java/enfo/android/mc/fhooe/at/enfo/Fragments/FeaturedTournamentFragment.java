package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.TournamentsAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.R;

public class FeaturedTournamentFragment extends Fragment {

    private static final String TAG = "FTF";
    private final String API_KEY = "JK5nCbHtb9yEGHDYNCdYgCvHGXRD7r-3HwVOJDjSMME";
    private final String API_KEY_HTTP_HEADER = "X-Api-Key";
    private final String mFeaturedTournamentsURL = "https://api.toornament.com/v1/tournaments?featured=1";
    private String mJSONResult;
    /**Key which is used to receive the passed Discipline Object from DisciplineActivity*/
    private static final String DISCIPLINE_KEY = "discipline_key";
    private Discipline mDiscipline;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Tournament> mTournamentList = new ArrayList<>();
    private RecyclerView mFeaturedTournamentsRecyclerView;
    private TournamentsAdapter mTournamentsAdapter;
    private Toolbar mToolbar;

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

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_featured_tournaments);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFeaturedTournaments();
            }
        });

        mFeaturedTournamentsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_featuredMatches);
        getFeaturedTournaments();
        mTournamentsAdapter = new TournamentsAdapter(getActivity(), R.layout.featured_tournament_row_layout, mDiscipline, mTournamentList);
        mFeaturedTournamentsRecyclerView.setAdapter(mTournamentsAdapter);
        mFeaturedTournamentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void getFeaturedTournaments(){
        new JSONTask().execute(mFeaturedTournamentsURL);
    }

    private class JSONTask extends AsyncTask<String, String, String> {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                StringBuilder urlbuilder = new StringBuilder();
                urlbuilder.append(params[0]);
                //get Tournaments of specified Discipline
                urlbuilder.append("&discipline="+mDiscipline.getmId());
                URL url = new URL(urlbuilder.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty(API_KEY_HTTP_HEADER ,API_KEY);
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer result = new StringBuffer();
                String line = "";

                while((line = reader.readLine())!=null){
                    result.append(line+"\n");
                }
                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection != null){
                    connection.disconnect();
                }if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mJSONResult = result;
            //mTournamentsAdapter.notifyDataSetChanged();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            parseFeaturedTnmt();
        }
    }

    private void parseFeaturedTnmt() {
        if(mJSONResult == null){
            Toast.makeText(getContext(), "No Featured Tournaments Found", Toast.LENGTH_SHORT).show();
        }else{
            mTournamentList.clear();
            try {
                JSONArray jsonarray = new JSONArray(mJSONResult);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String id = jsonobject.getString("id");
                    String discipline = jsonobject.getString("discipline");
                    String name = jsonobject.getString("name");
                    String fullname = jsonobject.getString("full_name");
                    String status = jsonobject.getString("full_name");

                    String dateStart = jsonobject.getString("date_start");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date_start = sdf.parse(dateStart);

                    String dateEnd = jsonobject.getString("date_start");
                    Date date_end = sdf.parse(dateEnd);

                    boolean online = jsonobject.getBoolean("online");
                    boolean publicT = jsonobject.getBoolean("public");
                    String location = jsonobject.getString("location");
                    String country = jsonobject.getString("country");
                    int size = jsonobject.getInt("size");


                    Tournament tournament = new Tournament(id, discipline, name,fullname,status,date_start,date_end,
                                online,publicT,location,country,size);
                        //mDisciplineList.add(discipline);

                    //mTournamentAdapter.add(tournament);
                    //mTournamentAdapter.notifyDataSetChanged();
                    mTournamentList.add(tournament);
                }
                mTournamentsAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
