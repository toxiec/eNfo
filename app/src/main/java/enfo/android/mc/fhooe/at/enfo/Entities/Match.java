package enfo.android.mc.fhooe.at.enfo.Entities;

import android.graphics.Path;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Objects.Opponent;

/**
 * Created by David on 26.06.2017.
 */

public class Match {
    private String mID;
    private String mType;
    private String mDiscipline;
    private String mStatus;
    private String mTournamentID;
    private int mMatchNumber;
    private int mStageNumber;
    private int mGroupNumber;
    private int mRoundNumber;
    private String mDate;
    private String mTimeZone;
    private String mMatchFormat;
    private List<Opponent> mOpponentList;

    public Match(String _id,String _type,String _discipline,String _status, String _tournamentID, int _matchNumber,
                 int _stageNumber,int _groupNumber,int _roundNumber,String _date,String _timezone,
                 String _matchFormat,List<Opponent> _opponentList){
        mID = _id;
        mType = _type;
        mDiscipline = _discipline;
        mStatus = _status;
        mTournamentID = _tournamentID;
        mMatchNumber = _matchNumber;
        mStageNumber = _stageNumber;
        mGroupNumber = _groupNumber;
        mRoundNumber = _roundNumber;
        mDate = _date;
        mTimeZone = _timezone;
        mMatchFormat = _matchFormat;
        mOpponentList = _opponentList;

    }
    public String getmID() {
        return mID;
    }

    public String getmType() {
        return mType;
    }

    public String getmDiscipline() {
        return mDiscipline;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmTournamentID() {
        return mTournamentID;
    }

    public int getmMatchNumber() {
        return mMatchNumber;
    }

    public int getmStageNumber() {
        return mStageNumber;
    }

    public int getmGroupNumber() {
        return mGroupNumber;
    }

    public int getmRoundNumber() {
        return mRoundNumber;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTimeZone() {
        return mTimeZone;
    }

    public String getmMatchFormat() {
        return mMatchFormat;
    }

    public List<Opponent> getmOpponentList() {
        return mOpponentList;
    }


}
