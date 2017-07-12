package enfo.android.mc.fhooe.at.enfo.Entities;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Objects.Opponent;

public class Game {
    /**Game's number.*/
    private int mNumber;
    /**Game's status: "pending" means it hasn’t started yet; "running" means it has started
     * but not ended yet; "completed" indicates the game is finished.*/
    private String mStatus;
    /**List of Opponents who play in the Game*/
    private List<Opponent> mOpponentList;
    /**The Map on which the Game takes place*/
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
