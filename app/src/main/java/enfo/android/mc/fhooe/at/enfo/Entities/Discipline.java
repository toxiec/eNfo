package enfo.android.mc.fhooe.at.enfo.Entities;

import android.media.Image;
import android.os.Parcelable;

import java.io.Serializable;

import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Objects.DisciplineID;

public class Discipline implements Serializable {
    /**An identifier for a discipline*/
    private DisciplineID mId;
    /**The official name of the discipline.*/
    private String mName;
    /**The short name of the discipline.*/
    private String mShortname;
    /**The complete name of the discipline*/
    private String mFullname;
    /**The name of the publisher of the discipline or any other right related information about the
     * owner of the discipline.*/
    private String mCopyrights;
    /**The Logo off the Discipline*/
    private Image mLogo;

    public Discipline(DisciplineID _id, String _name, String _shortname, String _fullname, String _copyrights){
        mId = _id;
        mName = _name;
        mShortname = _shortname;
        mFullname = _fullname;
        mCopyrights = _copyrights;
    }

    public DisciplineID getmId() {
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
