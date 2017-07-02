package enfo.android.mc.fhooe.at.enfo.Parser;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Game;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Objects.Opponent;

/**
 * Created by David on 02.07.2017.
 */

public class GameParser implements JSONTask.AsyncResponse {
    private final OnParseFinished mParseFinished;
    List<Game> mGameList = new ArrayList<>();
    List<Opponent> mOpponentList = new ArrayList<>();

    public GameParser(OnParseFinished _parseFinished) {
        mParseFinished = _parseFinished;
    }

    public interface OnParseFinished {
        void notifyParseFinished(List<Game> _gameList);
    }


    @Override
    public void processFinish(String jsonResult) {
        mGameList.clear();
        try {
            JSONArray jsonArray = new JSONArray(jsonResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int number = jsonObject.getInt("number");
                String status = jsonObject.getString("status");
                JSONArray jsonArrayOpponents = jsonObject.getJSONArray("opponents");

                mOpponentList = new ArrayList<>();
                for (int j = 0; j < jsonArrayOpponents.length(); j++) {

                    int opponentNumber = jsonArrayOpponents.getJSONObject(j).getInt("number");

                    JSONObject jsonObjectParticipant = jsonArrayOpponents.getJSONObject(j).getJSONObject("participant");

                    String partId = jsonObjectParticipant.getString("id");
                    String partName = jsonObjectParticipant.getString("name");
                    String partCountry = "";
                    try {
                        partCountry = jsonObjectParticipant.getString("country");
                    } catch (JSONException e) {

                    }
                    Participant participant = new Participant(partId, partName, null, partCountry, null, null);

                    int result = 0;
                    int rank = 0;
                    int score = 0;
                    try {
                        result = jsonArrayOpponents.getJSONObject(j).getInt("result");
                    } catch (JSONException e) {
                    }
                    try {
                        rank = jsonArrayOpponents.getJSONObject(j).getInt("rank");
                    } catch (JSONException e) {
                    }
                    try {
                        score = jsonArrayOpponents.getJSONObject(j).getInt("score");
                    } catch (JSONException e) {
                    }
                    boolean forfeit = jsonArrayOpponents.getJSONObject(j).getBoolean("forfeit");
                    Opponent opponent = new Opponent(opponentNumber, participant, result, rank, score, forfeit);
                    mOpponentList.add(opponent);
                }

                String map = "";
                try{
                    map = jsonObject.getString("map");

                }catch (JSONException e ){

                }
                Game game = new Game(number, status, mOpponentList, map);
                mGameList.add(game);
            }
            mParseFinished.notifyParseFinished(mGameList);
        } catch (JSONException e) {
            e.printStackTrace();
            mParseFinished.notifyParseFinished(mGameList);
        }
    }
}
