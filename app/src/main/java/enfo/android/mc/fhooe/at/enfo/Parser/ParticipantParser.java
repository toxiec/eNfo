package enfo.android.mc.fhooe.at.enfo.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Objects.ParticipantLogo;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;

/**
 * Created by David on 28.06.2017.
 */

public class ParticipantParser implements JSONTask.AsyncResponse{
    private List<Participant> mParticipantList = new ArrayList<>();
    private final OnParseFinished mParseFinished;

    public ParticipantParser(OnParseFinished _parseFinished){
        mParseFinished = _parseFinished;
    }

    public interface OnParseFinished{
        void notifyParseFinished(List<Participant> _tournamentList);
    }

    @Override
    public void processFinish(String jsonResult) {
        mParticipantList.clear();
        try {
            JSONArray jsonarray = new JSONArray(jsonResult);
            for(int i = 0; i<jsonarray.length(); i++){
                JSONObject jsonObject = jsonarray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");

                JSONObject jsonLogoObject = jsonObject.getJSONObject("logo");
                String icon_large = jsonLogoObject.getString("icon_large_square");
                String extra_small = jsonLogoObject.getString("extra_small_square");
                String medium_small = jsonLogoObject.getString("medium_small_square");

                ParticipantLogo participantLogo = new ParticipantLogo(icon_large, extra_small, medium_small);

                String country = jsonObject.getString("country");
                List<Player> playerList = new ArrayList<>();
                try {
                    JSONArray jsonArrayLineup = jsonObject.getJSONArray("lineup");
                    for(int j = 0; j<jsonArrayLineup.length(); j++){
                        String playerName = jsonArrayLineup.getJSONObject(i).getString("name");
                        String playerCountry = jsonArrayLineup.getJSONObject(i).getString("country");

                        JSONObject jsonObjectCustomField = new JSONObject("custom_fields");
                        List<String> playercustomField = new ArrayList<>();
                        String fieldType = jsonObjectCustomField.getString("type");
                        String fieldLabel = jsonObjectCustomField.getString("label");
                        String fieldValue = jsonObjectCustomField.getString("value");
                        playercustomField.add(fieldType);
                        playercustomField.add(fieldLabel);
                        playercustomField.add(fieldValue);

                        Player player = new Player(playerName, playerCountry, playercustomField);
                        playerList.add(player);
                    }
                }catch(JSONException _e){

                }
                List<String> customField = new ArrayList<>();
                try{
                    String type = jsonObject.getString("type");
                    String label = jsonObject.getString("label");
                    String value = jsonObject.getString("value");
                    customField.add(type);
                    customField.add(label);
                    customField.add(value);
                }catch(JSONException _e){

                }
                Participant participant = new Participant(id, name, participantLogo, country,  playerList, customField);
                mParticipantList.add(participant);
            }
            mParseFinished.notifyParseFinished(mParticipantList);
            //System.out.println(mTournamentDetail.toString());
        } catch (JSONException e) {
            mParseFinished.notifyParseFinished(mParticipantList);
            e.printStackTrace();
        }
    }
}
