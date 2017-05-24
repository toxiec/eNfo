package enfo.android.mc.fhooe.at.enfo.Entities;

import android.media.Image;

public class Discipline {
    private String mId;
    private String mName;
    private String mShortname;
    private String mFullname;
    private String mCopyrights;
    private Image mLogo;

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

    public String getmName() {
        return mName;
    }

    public String getmShortname() {
        return mShortname;
    }

    public String getmFullname() {
        return mFullname;
    }

    public String getmCopyrights() {
        return mCopyrights;
    }

    public Image getmLogo() {
        return mLogo;
    }

    public void setmLogo(Image mLogo) {
        this.mLogo = mLogo;
    }
}
