package enfo.android.mc.fhooe.at.enfo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Arrays;

import enfo.android.mc.fhooe.at.enfo.Adapter.DiscipleAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "JK5nCbHtb9yEGHDYNCdYgCvHGXRD7r-3HwVOJDjSMME";
    private final String API_KEY_HTTP_HEADER = "X-Api-Key";
    private Button reqeustBtn;
    private String mJSONResult;
    private String url;
    private DiscipleAdapter disciplineAdapter;
    ListView disciplineListView;
    //private ArrayList<Discipline> mDisciplineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mDisciplineList = new ArrayList<>();
        reqeustBtn = (Button) findViewById(R.id.btn_request);
        url = "https://api.toornament.com/v1/disciplines";

        disciplineListView = (ListView) findViewById(R.id.lv_discipline);
        //disciplineAdapter = new DiscipleAdapter(this, R.layout.disciple_row_layout, mDisciplineList);
        disciplineAdapter = new DiscipleAdapter(this, R.layout.disciple_row_layout);
        getDisciplineJSON();
        disciplineListView.setAdapter(disciplineAdapter);
    }

    public void getDisciplineJSON(){
        new JSONTask().execute(url);
    }

    public void parseDisciplineJSON(){
        String[] games = {"counterstrike_go","dota2", "hearthstone", "leagueoflegends"};
        if(mJSONResult == null){
            Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_SHORT).show();
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
                        disciplineAdapter.add(discipline);
                        disciplineAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    
    public class JSONTask extends AsyncTask<String, String, String>{
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            parseDisciplineJSON();
        }
    }
}
