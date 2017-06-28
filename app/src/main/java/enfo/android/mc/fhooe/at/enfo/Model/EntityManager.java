package enfo.android.mc.fhooe.at.enfo.Model;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentType;
import enfo.android.mc.fhooe.at.enfo.Parser.DisciplineParser;
import enfo.android.mc.fhooe.at.enfo.Parser.MatchParser;
import enfo.android.mc.fhooe.at.enfo.Parser.ParticipantParser;
import enfo.android.mc.fhooe.at.enfo.Parser.TournamentInformationParser;
import enfo.android.mc.fhooe.at.enfo.Parser.TournamentParser;

/**
 * Created by David on 27.06.2017.
 */

public class EntityManager {

    private static EntityManager instance;
    private boolean mDisciplineDownloadRunning = false;
    private boolean mTournamentDownloadRunning = false;
    private boolean mParticipantDownloadRunning = false;
    private boolean mTournamentInformationDownloadRunning = false;
    private boolean mMatchDownloadRunning = false;

    private final String mDisciplesURL = "https://api.toornament.com/v1/disciplines";
    private final String mFeaturedTournamentsURL = "https://api.toornament.com/v1/tournaments?featured=1&discipline=";
    private final String mRunningTournamentsURL = "https://api.toornament.com/v1/tournaments?&status=running&discipline=";
    private final String mParticipantURL = "https://api.toornament.com/v1/tournaments/";
    private final String mTournamentInformationURL = "https://api.toornament.com/v1/tournaments/";
    private final String mMatchURL = "https://api.toornament.com/v1/tournaments/";

    private Tournament mCurrentTournament;
    private Discipline mCurrentDiscipline;

    private List<Discipline> mDisciplineList = new ArrayList<>();
    private List<Tournament> mRunningTournamentList = new ArrayList<>();
    private List<Tournament> mFeaturedTournamentList = new ArrayList<>();
    private List<Participant> mParticipantList = new ArrayList<>();
    private List<TournamentInformationItem> mTournamentInformationList = new ArrayList<>();
    private List<Match> mMatchList = new ArrayList<>();

    private List<ModelChangeListener> mListenerList = new ArrayList<>();

    private EntityManager() {

    }

    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    public void addModelChangeListener(ModelChangeListener _listener) {
        mListenerList.add(_listener);
    }

    public void removeModelChangeListener(ModelChangeListener _lister) {
        mListenerList.remove(_lister);
    }

    private void fireChangeOccured(ChangeEvent _e) {
        for (ModelChangeListener l : mListenerList) {
            l.onChangeOccured(_e);
        }
    }

    //###### Discipline #####
    public void requestDiscipline() {
        JSONTask jsonTask = new JSONTask(new DisciplineParser(new DisciplineParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<Discipline> _disciplineList) {
                if (_disciplineList == null) {
                    mDisciplineDownloadRunning = false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                } else {
                    updateDisciplineList(_disciplineList);
                }
            }
        }));

        jsonTask.execute(mDisciplesURL);
        mDisciplineDownloadRunning = true;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    void updateDisciplineList(List<Discipline> _disciplineList) {
        mDisciplineList = _disciplineList;
        mDisciplineDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    public List<Discipline> getDisciplineList() {
        return mDisciplineList;
    }

    public boolean isDisciplineDownloadRunning() {
        return mDisciplineDownloadRunning;
    }

    //############################# Tournaments #################################
    public void requestTournaments(Discipline _discipline, final boolean _featured) {
        JSONTask jsonTask = new JSONTask(new TournamentParser(new TournamentParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<Tournament> _tournamentList) {
                if (_tournamentList == null) {
                    mTournamentDownloadRunning = false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                } else {
                    if (_featured) {
                        updateFeaturedTournamentList(_tournamentList);
                    } else {
                        updateRunningTournamentList(_tournamentList);
                    }
                }
            }
        }));
        StringBuilder urlbuilder = new StringBuilder();
        String url = "";

        if (_featured) {
            urlbuilder.append(mFeaturedTournamentsURL);
            if (_discipline != null) {
                urlbuilder.append(_discipline.getmId());
            }
            url = urlbuilder.toString();
        } else {
            urlbuilder.append(mRunningTournamentsURL);
            if (_discipline != null) {
                urlbuilder.append(_discipline.getmId());
            }
            url = urlbuilder.toString();
        }
        jsonTask.execute(url);
        mTournamentDownloadRunning = true;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    void updateFeaturedTournamentList(List<Tournament> _featuredTournaments) {
        mFeaturedTournamentList = _featuredTournaments;
        mTournamentDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    void updateRunningTournamentList(List<Tournament> _runningTournamentList) {
        mRunningTournamentList = _runningTournamentList;
        mTournamentDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    public List<Tournament> getTournamentList(TournamentType _type) {
        switch (_type) {
            case featured: {
                return mFeaturedTournamentList;
            }
            case running: {
                return mRunningTournamentList;
            }
        }
        return mRunningTournamentList;
    }

    public boolean isTournamentDownloadRunning() {
        return mTournamentDownloadRunning;
    }

    //################# Participant #####################
    public void requestParticipants(Tournament _tournament) {
        JSONTask jsonTask = new JSONTask(new ParticipantParser(new ParticipantParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<Participant> _participantList) {
                if (_participantList == null) {
                    mParticipantDownloadRunning = false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                } else {
                    updateParticipantTournamentList(_participantList);
                }
            }
        }));
        StringBuilder urlbuilder = new StringBuilder();
        String url = "";
        urlbuilder.append(mParticipantURL);
        if (_tournament != null) {
            urlbuilder.append(_tournament.getmID() + "/participants");
        }
        url = urlbuilder.toString();

        jsonTask.execute(url);
        mParticipantDownloadRunning = true;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    private void updateParticipantTournamentList(List<Participant> _participantList) {
        mParticipantList = _participantList;
        mParticipantDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    public List<Participant> getParticipantList() {
        return mParticipantList;
    }

    public boolean isParticipantDownloadRunning() {
        return mParticipantDownloadRunning;
    }

    //########## TournamentInformation ###############
    public void requestTournamentInformation(Tournament _tournament) {
        JSONTask jsonTask = new JSONTask(new TournamentInformationParser(new TournamentInformationParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<TournamentInformationItem> _tournamentDetailList) {
                if (_tournamentDetailList == null) {
                    mParticipantDownloadRunning = false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                } else {
                    updateTournamentInformationList(_tournamentDetailList);
                }
            }
        }));
        StringBuilder urlbuilder = new StringBuilder();
        String url = "";
        urlbuilder.append(mTournamentInformationURL);
        if (_tournament != null){
            urlbuilder.append(_tournament.getmID());
        }
        url = urlbuilder.toString();
        jsonTask.execute(url);
        mTournamentInformationDownloadRunning = true;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    public void updateTournamentInformationList(List<TournamentInformationItem> _tournamentDetailList){
        mTournamentInformationList = _tournamentDetailList;
        mTournamentInformationDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    };

    public List<TournamentInformationItem> getTournamentInformationList(){
        return mTournamentInformationList;
    }

    public boolean isTournamentInformationDownloadRunning(){
        return mTournamentInformationDownloadRunning;
    }


    // ############### Match #######################
    public void requestMatches(Tournament _tournament) {
        JSONTask jsonTask = new JSONTask(new MatchParser(new MatchParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<Match> _matchList) {
                if (_matchList == null) {
                    mMatchDownloadRunning = false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                } else {
                    updateMatches(_matchList);
                }
            }
        }));
        StringBuilder urlbuilder = new StringBuilder();
        String url = "";
        urlbuilder.append(mMatchURL);
        if (_tournament != null){
            urlbuilder.append(_tournament.getmID()+"/matches");
            urlbuilder.append("?sort=structure");
        }

        url = urlbuilder.toString();

        jsonTask.execute(url);
        mMatchDownloadRunning = true;

        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    public void updateMatches(List<Match> _matchList){
        mMatchList = _matchList;
        mMatchDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    };

    public List<Match> getMatchesList(){
        return mMatchList;
    }

    public boolean isMatchDownloadRunning(){
        return mMatchDownloadRunning;
    }

    public void setCurrentTournament(Tournament _tournament){
        mCurrentTournament = _tournament;
    }

    public Tournament getCurrentTournament(){
        return mCurrentTournament;
    }

    public void setCurrentDiscipline(Discipline _discipline){
        mCurrentDiscipline = _discipline;
    }

    public Discipline getCurrentDiscipline(){
        return mCurrentDiscipline;
    }
}




