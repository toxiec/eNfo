package enfo.android.mc.fhooe.at.enfo.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.TournamentDetail;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Objects.Stream;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;

/**
 * Created by David on 28.06.2017.
 */

public class TournamentInformationParser implements JSONTask.AsyncResponse {
    private List<TournamentDetail> mTournamentDetailList = new ArrayList<>();
    private List<TournamentInformationItem> mTournamentInfoList = new ArrayList<>();
    private List<Stream> mStreamList = new ArrayList<>();
    private TournamentDetail mTournamentDetail;
    private final OnParseFinished mParseFinished;

    public TournamentInformationParser(OnParseFinished _parseFinished) {
        mParseFinished = _parseFinished;
    }

    public interface OnParseFinished {
        void notifyParseFinished(List<TournamentInformationItem> _tournamentList);
    }

    @Override
    public void processFinish(String jsonResult) {
        mTournamentDetailList.clear();
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);

            String id = jsonObject.getString("id");
            String discipline = jsonObject.getString("discipline");
            String name = jsonObject.getString("name");
            String fullname = jsonObject.getString("full_name");
            String status = jsonObject.getString("full_name");

            String dateStart = jsonObject.getString("date_start");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date_start = sdf.parse(dateStart);

            String dateEnd = jsonObject.getString("date_end");
            Date date_end = sdf.parse(dateEnd);

            String timeZone = jsonObject.getString("timezone");
            boolean online = jsonObject.getBoolean("online");
            boolean publicT = jsonObject.getBoolean("public");
            String location = jsonObject.getString("location");
            String country = jsonObject.getString("country");
            int size = jsonObject.getInt("size");

            String participantDetail = jsonObject.getString("participant_type");
            String matchType = jsonObject.getString("match_type");
            String organzation = jsonObject.getString("organization");
            String website = jsonObject.getString("website");
            String description = jsonObject.getString("description");
            String rules = jsonObject.getString("rules");
            String prize = jsonObject.getString("prize");
            int teamSizeMin;
            int teamSizeMax;
            if (participantDetail.equals("team")) {
                teamSizeMin = jsonObject.getInt("team_min_size");
                teamSizeMax = jsonObject.getInt("team_max_size");
            } else {
                teamSizeMin = 0;
                teamSizeMax = 0;
            }
            JSONArray jsonArrayStream = jsonObject.getJSONArray("streams");
            for (int j = 0; j < jsonArrayStream.length(); j++) {
                String stream_id = jsonArrayStream.getJSONObject(j).getString("id");
                String stream_name = jsonArrayStream.getJSONObject(j).getString("name");
                String stream_url = jsonArrayStream.getJSONObject(j).getString("url");
                String stream_language = jsonArrayStream.getJSONObject(j).getString("language");
                Stream stream = new Stream(stream_id, stream_name, stream_url, stream_language);
                mStreamList.add(stream);
            }
            TournamentDetail tournamentDetail = new TournamentDetail(id, discipline, name, fullname, status, date_start, date_end,
                    online, publicT, location, country, size, timeZone, participantDetail, matchType, organzation,
                    website, description, rules, prize, teamSizeMin, teamSizeMax, mStreamList);

            mTournamentDetail = tournamentDetail;
            fill_with_data();
            mParseFinished.notifyParseFinished(mTournamentInfoList);
        } catch (JSONException e) {
            mParseFinished.notifyParseFinished(mTournamentInfoList);
            e.printStackTrace();
        } catch (ParseException e) {
            mParseFinished.notifyParseFinished(mTournamentInfoList);
            e.printStackTrace();
        }
    }

    public void fill_with_data() {
        mTournamentInfoList.clear();
        mTournamentInfoList.add(new TournamentInformationItem("Discipline", EntityManager.getInstance().getCurrentDiscipline().getmName()));
        if (mTournamentDetail.getmParticipantType().equals("team")) {
            mTournamentInfoList.add(new TournamentInformationItem("Participants", Integer.toString(mTournamentDetail.getmSize()) + " Teams"));
        } else {
            mTournamentInfoList.add(new TournamentInformationItem("Participants", Integer.toString(mTournamentDetail.getmSize()) + " Players"));
        }
        mTournamentInfoList.add(new TournamentInformationItem("Organizer", mTournamentDetail.getmOrganization()));

        SimpleDateFormat fromDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        SimpleDateFormat toDate = new SimpleDateFormat("dd.MM.yyyy");
        String startDate = mTournamentDetail.getmDateStart().toString();
        String endDate = mTournamentDetail.getmDateEnd().toString();
        try {
            String reformattedDateStart = toDate.format(fromDate.parse(startDate));
            String reformattedDateEnd = toDate.format(fromDate.parse(endDate));
            mTournamentInfoList.add(new TournamentInformationItem("Start Date", reformattedDateStart));
            mTournamentInfoList.add(new TournamentInformationItem("End Date", reformattedDateEnd));
            mTournamentInfoList.add(new TournamentInformationItem("Description", mTournamentDetail.getmDescription()));
            mTournamentInfoList.add(new TournamentInformationItem("Price", mTournamentDetail.getmPrize()));
            mTournamentInfoList.add(new TournamentInformationItem("Rules", mTournamentDetail.getmRules()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
