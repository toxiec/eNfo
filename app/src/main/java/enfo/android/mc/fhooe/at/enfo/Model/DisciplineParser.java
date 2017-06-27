package enfo.android.mc.fhooe.at.enfo.Model;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Objects.DisciplineID;

/**
 * Created by David on 27.06.2017.
 */

public class DisciplineParser implements JSONTask.AsyncResponse {
    private final OnParseFinished mParseFinished;

    public DisciplineParser(OnParseFinished _parseFinished){
        mParseFinished = _parseFinished;
    }

    public interface OnParseFinished{
        void notifyParseFinished(List<Discipline> _disciplineList);
    }

    List<Discipline> mDisciplineList = new ArrayList<>();

    @Override
    public void processFinish(String jsonResult) {
        //Only Adds Disciplines which contain the ID from this Array (Filtering)
        //String[] games = {"counterstrike_go", "dota2", "hearthstone", "leagueoflegends", "starcraft2_lotv", "overwatch", "heroesofthestorm"};
        mDisciplineList.clear();
        try {
            JSONArray jsonarray = new JSONArray(jsonResult);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                DisciplineID id;
                try{
                    id = DisciplineID.valueOf(jsonobject.getString("id"));
                }catch (IllegalArgumentException e){
                    continue;
                }
                if(id == null){
                    continue;
                }
                String name = jsonobject.getString("name");
                String shortname = jsonobject.getString("shortname");
                String fullname = jsonobject.getString("fullname");
                String copyrights = jsonobject.getString("copyrights");
                System.out.println(id);


                Discipline discipline = new Discipline(id, name, shortname, fullname, copyrights);
                mDisciplineList.add(discipline);

            }
            mParseFinished.notifyParseFinished(mDisciplineList);

        } catch (JSONException e) {
            //todo, Event wenn parsen fehlschlägt (Spinner ausschalten)
            e.printStackTrace();
        }
    }
}
