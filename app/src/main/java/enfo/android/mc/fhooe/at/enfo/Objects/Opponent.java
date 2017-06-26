package enfo.android.mc.fhooe.at.enfo.Objects;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Participant;

/**
 * Created by David on 26.06.2017.
 */

public class Opponent {
    private int mNumber;
    private Participant mParticipant;
    private int mResult;
    private int mRank;
    private int mScore;
    private boolean mForfeit;
    public Opponent(int _number, Participant _participant, int _result,
                    int _rank, int _score, boolean _forfeit){
        mNumber = _number;
        mParticipant = _participant;
        mResult = _result;
        mRank = _rank;
        mScore = _score;
        mForfeit = _forfeit;
    }


    public int getmNumber() {
        return mNumber;
    }

    public Participant getmParticipant() {
        return mParticipant;
    }

    public int getmResult() {
        return mResult;
    }

    public int getmRank() {
        return mRank;
    }

    public int getmScore() {
        return mScore;
    }

    public boolean ismForfeit() {
        return mForfeit;
    }

}
