package enfo.android.mc.fhooe.at.enfo.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Objects.Opponent;

/**
 * Parses JSON to Match Object
 */
public class MatchParser  implements JSONTask.AsyncResponse{
    private final OnParseFinished mParseFinished;
    List<Match> mMatchList = new ArrayList<>();
    List<Opponent> mOpponentList = new ArrayList<>();

    public MatchParser(OnParseFinished _parseFinished){
        mParseFinished = _parseFinished;
    }

    public interface OnParseFinished{
        void notifyParseFinished(List<Match> _matchList);
    }

    @Override
    public void processFinish(String jsonResult) {
        mMatchList.clear();
        try {
            JSONArray jsonArray = new JSONArray(jsonResult);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String type = jsonObject.getString("type");
                String discipline = jsonObject.getString("discipline");
                String status = jsonObject.getString("status");
                String tournamentID = jsonObject.getString("tournament_id");
                int matchNumber = jsonObject.getInt("number");
                int stageNumber = jsonObject.getInt("stage_number");
                int groupNumber = jsonObject.getInt("group_number");
                int roundNumber = jsonObject.getInt("round_number");
                String date = jsonObject.getString("date");
                String timezone = jsonObject.getString("timezone");
                String matchFormat = jsonObject.getString("match_format");

                JSONArray jsonArrayOpponents = jsonObject.getJSONArray("opponents");
                mOpponentList = new ArrayList<>();
                for(int j = 0; j < jsonArrayOpponents.length(); j++){

                    int number = jsonArrayOpponents.getJSONObject(j).getInt("number");

                    JSONObject jsonObjectParticipant = jsonArrayOpponents.getJSONObject(j).getJSONObject("participant");

                    String partId = jsonObjectParticipant.getString("id");
                    String partName = jsonObjectParticipant.getString("name");
                    String partCountry="";
                    try{
                        partCountry = jsonObjectParticipant.getString("country");
                    }catch (JSONException e){

                    }
                    Participant participant = new Participant(partId, partName, null, partCountry, null, null);

                    int result = 0;
                    int rank = 0;
                    int score = 0;
                    try{
                        result = jsonArrayOpponents.getJSONObject(j).getInt("result");
                    }catch (JSONException e){}
                    try{
                        rank = jsonArrayOpponents.getJSONObject(j).getInt("rank");
                    }catch(JSONException e){}
                    try{
                        score = jsonArrayOpponents.getJSONObject(j).getInt("score");
                    } catch (JSONException e){}

                    boolean forfeit = jsonArrayOpponents.getJSONObject(j).getBoolean("forfeit");
                    Opponent opponent = new Opponent(number, participant, result, rank, score, forfeit);
                    mOpponentList.add(opponent);
                }

                Match match = new Match(id, type, discipline, status, tournamentID, matchNumber, stageNumber,
                        groupNumber, roundNumber, date, timezone, matchFormat, mOpponentList);
                mMatchList.add(match);
            }

            mParseFinished.notifyParseFinished(mMatchList);
            } catch (JSONException e) {
                e.printStackTrace();
                mParseFinished.notifyParseFinished(mMatchList);
            }
    }
}
