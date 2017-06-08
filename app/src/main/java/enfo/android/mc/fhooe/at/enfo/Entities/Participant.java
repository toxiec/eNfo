package enfo.android.mc.fhooe.at.enfo.Entities;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Objects.ParticipantLogo;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;

public class Participant {
    /**Unique identifier for this participant.*/
    private String mID;
    /**Participant name (maximum 40 characters).*/
    private String mName;
    /**Logo of the participant.*/
    private ParticipantLogo mParticipantLogo;
    /**Country of the participant.*/
    private String mCountry;
    /**Team Lineup*/
    private List<Player> mLineup;
    /**List of custom fields*/
    private List<String> mCustomField;

    public Participant(String _id, String _name, ParticipantLogo _logo, String _country,
                       List<Player> _lineup, List<String> _customField){
        mID = _id;
        mName = _name;
        mParticipantLogo = _logo;
        mCountry = _country;
        mLineup = _lineup;
        mCustomField = _customField;
    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public ParticipantLogo getmParticipantLogo() {
        return mParticipantLogo;
    }

    public String getmCountry() {
        return mCountry;
    }

    public List<Player> getmLineup() {
        return mLineup;
    }

    public List<String> getmCustomField() {
        return mCustomField;
    }
}
