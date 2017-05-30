package enfo.android.mc.fhooe.at.enfo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Arrays;

import enfo.android.mc.fhooe.at.enfo.Adapter.DisciplineAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.NetworkCheck;
import enfo.android.mc.fhooe.at.enfo.R;

public class MainActivity extends AppCompatActivity {
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
    /**ListAdapter for the mDisciplineListView*/
    private DisciplineAdapter mDisciplineAdapter;
    /**List which displays the fetched Disciplines*/
    private ListView mDisciplineListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(NetworkCheck.isNetworkAvailable(this)){
            mDisciplineListView = (ListView) findViewById(R.id.lv_discipline);
            mDisciplineAdapter = new DisciplineAdapter(this, R.layout.disciple_row_layout);
            getDisciplines();
            mDisciplineListView.setAdapter(mDisciplineAdapter);
            mDisciplineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> _adapter, View _view, int _position, long _id) {
                    Discipline discipline = (Discipline) _adapter.getItemAtPosition(_position);
                    System.out.println(discipline.getmFullname()+" has been selected");
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

    /**
     * Fetch Discipline from the API
     */
    private void getDisciplines(){
        new JSONTask().execute(mDisciplesURL);
    }

    /**
     *Async Task to Receive Discipline JSON Data
     */
    private class JSONTask extends AsyncTask<String, String, String>{
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please Wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            parseDisciplineJSON();
        }
    }

    /**
     * Creates Tournament Objects from the received Discipline JSON and adds it to the Listview.
     */
    private void parseDisciplineJSON(){
        //Only Adds Disciplines which contain the ID from this Array (Filtering)
        String[] games = {"counterstrike_go","dota2", "hearthstone", "leagueoflegends","starcraft2_lotv"};
        if(mJSONResult == null){
            Toast.makeText(getApplicationContext(), "No Games Found", Toast.LENGTH_SHORT).show();
        }else{
            try {
                JSONArray jsonarray = new JSONArray(mJSONResult);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String id = jsonobject.getString("id");
                    String name = jsonobject.getString("name");
                    String shortname = jsonobject.getString("shortname");
                    String fullname = jsonobject.getString("fullname");
                    String copyrights = jsonobject.getString("copyrights");

                    if(Arrays.asList(games).contains(id)){
                        Discipline discipline = new Discipline(id,name,shortname,fullname,copyrights);
                        //mDisciplineList.add(discipline);

                        mDisciplineAdapter.add(discipline);
                        mDisciplineAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
