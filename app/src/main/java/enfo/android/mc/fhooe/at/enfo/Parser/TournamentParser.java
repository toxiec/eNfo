package enfo.android.mc.fhooe.at.enfo.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Objects.DisciplineID;

/**
 * Created by David on 27.06.2017.
 */

public class TournamentParser implements JSONTask.AsyncResponse {
    private final OnParseFinished mParseFinished;
    List<Tournament> mTournamentList = new ArrayList<>();

    public TournamentParser(OnParseFinished _parseFinished){
        mParseFinished = _parseFinished;
    }

    public interface OnParseFinished{
        void notifyParseFinished(List<Tournament> _tournamentList);
    }

    @Override
    public void processFinish(String jsonResult) {
        if(jsonResult == null){

        }
        mTournamentList.clear();
        try {
            JSONArray jsonarray = new JSONArray(jsonResult);
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

                mTournamentList.add(tournament);
            }
            mParseFinished.notifyParseFinished(mTournamentList);

        } catch (JSONException e) {
            mParseFinished.notifyParseFinished(mTournamentList);
            e.printStackTrace();
        } catch (ParseException e) {
            mParseFinished.notifyParseFinished(mTournamentList);
            e.printStackTrace();
        }
    }
}
