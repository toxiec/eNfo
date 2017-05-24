package enfo.android.mc.fhooe.at.enfo;

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

import enfo.android.mc.fhooe.at.enfo.Adapter.DiscipleAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "JK5nCbHtb9yEGHDYNCdYgCvHGXRD7r-3HwVOJDjSMME";
    private final String API_KEY_HTTP_HEADER = "X-Api-Key";
    private final String mDisciplesURL = "https://api.toornament.com/v1/disciplines";
    private String mJSONResult;
    private DiscipleAdapter mDisciplineAdapter;
    private ListView mDisciplineListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisciplineListView = (ListView) findViewById(R.id.lv_discipline);
        mDisciplineAdapter = new DiscipleAdapter(this, R.layout.disciple_row_layout);
        getDisciplineJSON();
        mDisciplineListView.setAdapter(mDisciplineAdapter);
        mDisciplineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _adapter, View _view, int _position, long _id) {
                Discipline discipline = (Discipline) _adapter.getItemAtPosition(_position);
                System.out.println(discipline.getmFullname()+" has been selected");
                Intent i = new Intent(MainActivity.this, DisciplineActivity.class);
                startActivity(i);
            }
        });
    }

    public void getDisciplineJSON(){
        new JSONTask().execute(mDisciplesURL);
    }

    public void parseDisciplineJSON(){
        String[] games = {"counterstrike_go","dota2", "hearthstone", "leagueoflegends"};
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
    
    public class JSONTask extends AsyncTask<String, String, String>{
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
}
