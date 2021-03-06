package enfo.android.mc.fhooe.at.enfo.Entities;

import java.io.Serializable;
import java.util.Date;

import enfo.android.mc.fhooe.at.enfo.Objects.DisciplineID;

/**
 * Object which contains information about the Tournaments of various eSport Tournaments
 */
public class Tournament implements Serializable{
    /**An hexadecimal unique identifier for this tournament.*/
    private String mID;

    /**This string is a unique identifier of a discipline.*/
    private String mDiscipline;

    /**Name of a tournament (maximum 30 characeters).*/
    private String mName;

    /**Complete name of this tournament (maximum 80 characters). */
    private String mFullName;

    /**Status of the tournament. "setup" implies it has not started yet; "running" means it has at
     * least one match result; "completed" indicates all matches have a result.*/
    private String mStatus;

    /**Starting date of the tournament. This value uses the ISO 8601 date containing only the
     * date section.*/
    private Date mDateStart;

    /**Ending date of the tournament. This value uses the ISO 8601 date containing only the date section.*/
    private Date mDateEnd;

    /**Whether the tournament is played on internet or not.*/
    private boolean mOnline;

    /**Whether the tournament is public or private.*/
    private boolean mPublic;

    /**Location (city, address, place of interest) of the tournament.*/
    private String mLocation;

    /**Country of the tournament. This value uses the ISO 3166-1 alpha-2 country code.*/
    private String mCountry;

    /**Size of a tournament. Represents the expected number of participants it'll be able to manage.*/
    private int mSize;

    public Tournament(){

    }

    public Tournament(String _ID, String _discipline, String _name, String _fullName,
                      String _status, Date _dateStart, Date _dateEnd, boolean _online,
                      boolean _public, String _location, String _country, int _size){

        mID = _ID;
        mDiscipline = _discipline;
        mName = _name;
        mFullName = _fullName;
        mStatus = _status;
        mDateStart = _dateStart;
        mDateEnd = _dateEnd;
        mOnline = _online;
        mPublic = _public;
        mLocation = _location;
        mCountry = _country;
        mSize = _size;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public void setmDiscipline(String mDiscipline) {
        this.mDiscipline = mDiscipline;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public void setmDateStart(Date mDateStart) {
        this.mDateStart = mDateStart;
    }

    public void setmDateEnd(Date mDateEnd) {
        this.mDateEnd = mDateEnd;
    }

    public void setmOnline(boolean mOnline) {
        this.mOnline = mOnline;
    }

    public void setmPublic(boolean mPublic) {
        this.mPublic = mPublic;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
    }

    public String getmID() {
        return mID;
    }

    public String getmDiscipline() {
        return mDiscipline;
    }

    public String getmName() {
        return mName;
    }

    public String getmFullName() {
        return mFullName;
    }

    public String getmStatus() {
        return mStatus;
    }

    public Date getmDateStart() {
        return mDateStart;
    }

    public Date getmDateEnd() {
        return mDateEnd;
    }

    public boolean ismOnline() {
        return mOnline;
    }

    public boolean ismPublic() {
        return mPublic;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmCountry() {
        return mCountry;
    }

    public int getmSize() {
        return mSize;
    }


}
