package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.util.Date;

import enfo.android.mc.fhooe.at.enfo.Adapter.FeaturedTournamentAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.FeaturedTournament;
import enfo.android.mc.fhooe.at.enfo.R;

public class RunningFragment extends Fragment {

    private static final String TAG = "FTF";
    private final String API_KEY = "JK5nCbHtb9yEGHDYNCdYgCvHGXRD7r-3HwVOJDjSMME";
    private final String API_KEY_HTTP_HEADER = "X-Api-Key";
    private final String mRunningTournamentsURL = "https://api.toornament.com/v1/tournaments?";
    private String mJSONResult;
    /**Key which is used to receive the passed Discipline Object from DisciplineActivity*/
    private static final String DISCIPLINE_KEY = "discipline_key";
    private Discipline mDiscipline;
    private ListView mRunningTournamentsListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeaturedTournamentAdapter mFeaturedTournamentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_tournaments, container, false);

        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.containsKey(DISCIPLINE_KEY)){
                mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                Log.i(TAG, mDiscipline.getmFullname());
            }
        }

        //Log.i(TAG, mDiscipline.getmId());
        mRunningTournamentsListView = (ListView) view.findViewById(R.id.lv_runningTournaments);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_running_tournaments);

        mFeaturedTournamentAdapter = new FeaturedTournamentAdapter(getActivity(), R.layout.featured_tournament_row_layout, mDiscipline);
        getRunningTournaments();
        mRunningTournamentsListView.setAdapter(mFeaturedTournamentAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRunningTournaments();
            }
        });
        return view;
    }

    private void getRunningTournaments(){
        new JSONTask().execute(mRunningTournamentsURL);
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
                //urlbuilder.append("featured=1");
                urlbuilder.append("&discipline="+mDiscipline.getmId());
                urlbuilder.append(("&status=running"));
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
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            parseRunningTnmt();
        }
    }

    private void parseRunningTnmt() {
        if(mJSONResult == null){
            Toast.makeText(getContext(), "No Running Tournaments Found", Toast.LENGTH_SHORT).show();
        }else{
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

                    FeaturedTournament tournament = new FeaturedTournament(id, discipline, name,fullname,status,date_start,date_end,
                            online,publicT,location,country,size);
                    //mDisciplineList.add(discipline);

                    mFeaturedTournamentAdapter.add(tournament);
                    mFeaturedTournamentAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
