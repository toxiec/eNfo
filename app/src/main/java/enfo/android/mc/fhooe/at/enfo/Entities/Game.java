package enfo.android.mc.fhooe.at.enfo.Entities;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Objects.Opponent;

public class Game {
    private int mNumber;
    private String mStatus;
    private List<Opponent> mOpponentList;
    private String mMap;

    public Game(int _number, String _status, List<Opponent> _oppentList, String _map){
        mNumber = _number;
        mStatus = _status;
        mOpponentList = _oppentList;
        mMap = _map;
    }


    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public List<Opponent> getmOpponentList() {
        return mOpponentList;
    }

    public void setmOpponentList(List<Opponent> mOpponentList) {
        this.mOpponentList = mOpponentList;
    }

    public String getmMap() {
        return mMap;
    }

    public void setmMap(String mMap) {
        this.mMap = mMap;
    }


}
