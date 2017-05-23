package enfo.android.mc.fhooe.at.enfo.Entities;

/**
 * Created by David on 22.05.2017.
 */

public class Discipline {
    private String mId;
    private String mName;
    private String mShortname;
    private String mFullname;
    private String mCopyrights;

    public Discipline(String _id, String _name, String _shortname, String _fullname, String _copyrights){
        mId = _id;
        mName = _name;
        mShortname = _shortname;
        mFullname = _fullname;
        mCopyrights = _copyrights;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmShortname() {
        return mShortname;
    }

    public void setmShortname(String mShortname) {
        this.mShortname = mShortname;
    }

    public String getmFullname() {
        return mFullname;
    }

    public void setmFullname(String mFullname) {
        this.mFullname = mFullname;
    }

    public String getmCopyrights() {
        return mCopyrights;
    }

    public void setmCopyrights(String mCopyrights) {
        this.mCopyrights = mCopyrights;
    }

}
