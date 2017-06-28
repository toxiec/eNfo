package enfo.android.mc.fhooe.at.enfo.Entities;

import java.util.Date;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Objects.DisciplineID;
import enfo.android.mc.fhooe.at.enfo.Objects.Stream;

/**
 * Object which contains detailed information about a Tournament
 */
public class TournamentDetail extends Tournament {
    /**Time zone of the tournament. This value is represented using the IANA tz database.*/
    private String mTimeZone;
    /**Type of participants who plays in the tournament.*/
    private String mParticipantType;
    /**Type of matches played in the tournament.*/
    private String mMatchType;
    /**Tournament organizer: individual, group, association or company.*/
    private String mOrganization;
    /**URL of the website*/
    private String mWebsite;
    /**User-defined description of the tournament (maximum 1,500 characters).*/
    private String mDescription;
    /**User-defined rules of the tournament (maximum 10,000 characters).*/
    private String mRules;
    /**User-defined description of the tournament prizes (maximum 1,500 characters).*/
    private String mPrize;
    /**If the "participant type" value in this tournament is 'team', specify the smallest possible team size.*/
    private int mTeamSizeMin;
    /**If the "participant type" value in this tournament is 'team', specify the largest possible team size.*/
    private int mTeamSizeMax;
    /**Stream of the Tournament*/
    private List<Stream> mStreamList;

    public TournamentDetail(String _ID, String _discipline, String _name, String _fullName, String _status,
                            Date _dateStart, Date _dateEnd, boolean _online, boolean _public, String _location,
                            String _country, int _size, String _timeZone, String _participantType,
                            String _matchType, String _organization, String _website, String _description,
                            String _rules, String _prize, int _teamSizeMin, int _teamSizeMax, List<Stream> _streamList) {
        super(_ID, _discipline, _name, _fullName, _status, _dateStart, _dateEnd, _online, _public, _location, _country, _size);
        mTimeZone = _timeZone;
        mParticipantType = _participantType;
        mMatchType = _matchType;
        mOrganization = _organization;
        mWebsite = _website;
        mDescription = _description;
        mRules = _rules;
        mPrize = _prize;
        mTeamSizeMin = _teamSizeMin;
        mTeamSizeMax = _teamSizeMax;
        mStreamList = _streamList;
    }

    public String getmTimeZone() {
        return mTimeZone;
    }

    public String getmParticipantType() {
        return mParticipantType;
    }

    public String getmMatchType() {
        return mMatchType;
    }

    public String getmOrganization() {
        return mOrganization;
    }

    public String getmWebsite() {
        return mWebsite;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmRules() {
        return mRules;
    }

    public String getmPrize() {
        return mPrize;
    }

    public int getmTeamSizeMin() {
        return mTeamSizeMin;
    }

    public int getmTeamSizeMax() {
        return mTeamSizeMax;
    }

    public List<Stream> getmStream() {
        return mStreamList;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("Disziplin: "+getmDiscipline()+"\n"+"Teilnehmer "+getmTeamSizeMax()+" Teams"+ "\n"+
        "Turnierinhaber "+getmOrganization()+"\n"+"Start Datum :"+getmDateStart()+"\n"+
        "Enddatum :"+getmDateEnd()+"\n"+"Beschreibung : "+getmDescription()+"\n"+
        "Preis : "+getmPrize()+"\n"+"Rules : "+getmRules()+"\n");
        return buffer.toString();
    }


}
