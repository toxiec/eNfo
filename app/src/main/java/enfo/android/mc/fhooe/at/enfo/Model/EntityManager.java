package enfo.android.mc.fhooe.at.enfo.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Database.DatabaseHandler;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Game;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Entities.TournamentDetail;
import enfo.android.mc.fhooe.at.enfo.Objects.MatchType;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentType;
import enfo.android.mc.fhooe.at.enfo.Parser.DisciplineParser;
import enfo.android.mc.fhooe.at.enfo.Parser.GameParser;
import enfo.android.mc.fhooe.at.enfo.Parser.MatchParser;
import enfo.android.mc.fhooe.at.enfo.Parser.ParticipantParser;
import enfo.android.mc.fhooe.at.enfo.Parser.TournamentInformationParser;
import enfo.android.mc.fhooe.at.enfo.Parser.TournamentParser;

/**
 * The Model of the MVC Concept
 */
public class EntityManager {

    /**Instance of the Model*/
    private static EntityManager instance;

    /**Is the Discipline JSON Download Running*/
    private boolean mDisciplineDownloadRunning = false;
    /**Is the Tournament JSON Download Running*/
    private boolean mTournamentDownloadRunning = false;
    /**Is the Participant JSON Download Running*/
    private boolean mParticipantDownloadRunning = false;
    /**Is the TournamentInformation JSON Download Running*/
    private boolean mTournamentInformationDownloadRunning = false;
    /**Is the Match JSON Download Running*/
    private boolean mMatchDownloadRunning = false;
    /**Is the ParticipantMatch JSON Download Running*/
    private boolean mParticipantMatchDownloadRunning = false;
    /**Is the Game JSON Download Running*/
    private boolean mGameDownloadRunning = false;

    /**URL to get the Discipline JSON*/
    private final String mDisciplesURL = "https://api.toornament.com/v1/disciplines";
    /**URL to get the Featured Tournaments JSON*/
    private final String mFeaturedTournamentsURL = "https://api.toornament.com/v1/tournaments?featured=1&discipline=";
    /**URL to get the Running Tournaments JSON*/
    private final String mRunningTournamentsURL = "https://api.toornament.com/v1/tournaments?&status=running&discipline=";
    /**URL to get the Participant JSON*/
    private final String mParticipantURL = "https://api.toornament.com/v1/tournaments/";
    /**URL to get the Tournament Information JSON*/
    private final String mTournamentInformationURL = "https://api.toornament.com/v1/tournaments/";
    /**URL to get the Match JSON*/
    private final String mMatchURL = "https://api.toornament.com/v1/tournaments/";

    /**Current selected Tournament*/
    private Tournament mCurrentTournament;
    /**Current selected Discipline */
    private Discipline mCurrentDiscipline;
    /**Current selected Participant*/
    private Participant mCurrentParticipant;
    /**Current selected TournamentDetail*/
    private TournamentDetail mCurrentTournamentDetail;
    /**Current selected Match*/
    private Match mCurrentMatch;

    /**List of Discipline which are displayed in RecyclerView*/
    private List<Discipline> mDisciplineList = new ArrayList<>();
    /**List of Running Tournaments which are displayed in RecyclerView*/
    private List<Tournament> mRunningTournamentList = new ArrayList<>();
    /**List of Featured Tournaments which are displayed in RecyclerView*/
    private List<Tournament> mFeaturedTournamentList = new ArrayList<>();
    /**List of Participants which are displayed in RecyclerView*/
    private List<Participant> mParticipantList = new ArrayList<>();
    /**List of TournamentInformationItem which are displayed in RecyclerView*/
    private List<TournamentInformationItem> mTournamentInformationList = new ArrayList<>();
    /**List of Matches which are displayed in RecyclerView*/
    private List<Match> mMatchList = new ArrayList<>();
    /**List of ParticipantMatches which are displayed in RecyclerView*/
    private List<Match> mParticipantMatchList = new ArrayList<>();
    /**List of Favorites which are displayed in RecyclerView*/
    private List<Tournament> mFavoriteList = new ArrayList<>();
    /**List of Games which are displayed in RecyclerView*/
    private List<Game> mGameList = new ArrayList<>();
    /**List of all registered Listeners*/
    private List<ModelChangeListener> mListenerList = new ArrayList<>();

    private DatabaseHandler db_local;

    private EntityManager() {

    }

    /**
     * Return the Model instance
     * @return instance of the Model
     */
    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    /**
     * Adds a Listener to the mListenerList
     * @param _listener which should be added
     */
    public void addModelChangeListener(ModelChangeListener _listener) {
        mListenerList.add(_listener);
    }

    /**
     * Remove a listener from the mListenerList
     * @param _lister which should be removed
     */
    public void removeModelChangeListener(ModelChangeListener _lister) {
        mListenerList.remove(_lister);
    }

    /**
     * Notifies Listener when model changed
     * @param _e Event which occured
     */
    private void fireChangeOccured(ChangeEvent _e) {
        for (ModelChangeListener l : mListenerList) {
            l.onChangeOccured(_e);
        }
    }

    //###### Discipline #####

    /**
     * Fetches Disciplines from the API, parses and saves them in mDisciplineList
     */
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

    /**
     * Updates the mDisciplineList with the new Fetched Disciplines
     * @param _disciplineList the new fetched DisciplineList
     */
    void updateDisciplineList(List<Discipline> _disciplineList) {
        mDisciplineList = _disciplineList;
        mDisciplineDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Returns the DisciplineList
     * @return DisciplineList
     */
    public List<Discipline> getDisciplineList() {
        return mDisciplineList;
    }

    /**
     * Check if the Discipline fetch Process is still running
     * @return true if running, false if finished
     */
    public boolean isDisciplineDownloadRunning() {
        return mDisciplineDownloadRunning;
    }

    //############################# Tournaments #################################

    /**
     * Fetches Tournaments from the API, parses and saves them in List
     * @param _discipline from which the Tournaments should be downloaded
     * @param _featured true if featured tournaments should be downloaded, false if running tournaments
     *                 should be downloaded
     */
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

    /**
     * Update the mFeaturedTournaments with the new Fetched Featured Tournaments
     * @param _featuredTournaments the new fetched FeaturedTournament List
     */
    void updateFeaturedTournamentList(List<Tournament> _featuredTournaments) {
        mFeaturedTournamentList = _featuredTournaments;
        mTournamentDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Update the mRunningTournamentList with the new Fetched Running Tournaments
     * @param _runningTournamentList the new fetched RunningTournament List
     */
    void updateRunningTournamentList(List<Tournament> _runningTournamentList) {
        mRunningTournamentList = _runningTournamentList;
        mTournamentDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Returns whether Running, Featured or Favorited Tournament List
     * @param _type which Tournament Type list should be returned.
     *              possible Types: featured, running, favorites
     * @return Tournament List of the given type
     */
    public List<Tournament> getTournamentList(TournamentType _type) {
        switch (_type) {
            case featured: {
                return mFeaturedTournamentList;
            }
            case running: {
                return mRunningTournamentList;
            }
            case favorites: {
                return mFavoriteList;
            }
        }
        return mRunningTournamentList;
    }

    /**
     * Check if the Tournament fetch Process is still running
     * @return true if running, false if finished
     */
    public boolean isTournamentDownloadRunning() {
        return mTournamentDownloadRunning;
    }

    //################# Participant #####################

    /**
     * Fetches Participants from the API, parses and saves them in List
     * @param _tournament from which the Participants should be downloaded
     */
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
            urlbuilder.append("?with_lineup=1");
            /*if(getCurrentTournamentDetail()!=null){
                if(getCurrentTournamentDetail().getmParticipantType().equals("team")){
                    urlbuilder.append("?with_lineup=1");
                }
            }*/
        }
        url = urlbuilder.toString();

        jsonTask.execute(url);
        mParticipantDownloadRunning = true;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    /**
     * Update the mParticipantTournament List with the new Fetched Participants
     * @param _participantList the new fetched Participant List
     */
    private void updateParticipantTournamentList(List<Participant> _participantList) {
        mParticipantList = _participantList;
        mParticipantDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Fetches Games from the API, parses and saves them in List
     */
    public void requestPlayers() {
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Return PlayerList
     * @return playerlist
     */
    public List<Player> getPlayerList() {
        return getCurrentParticipant().getmLineup();
    }

    /**
     * Return ParticipantList
     * @return participantlist
     */
    public List<Participant> getParticipantList() {
        return mParticipantList;
    }

    /**
     * Check if the Participant fetch Process is still running
     * @return true if running, false if finished
     */
    public boolean isParticipantDownloadRunning() {
        return mParticipantDownloadRunning;
    }

    //########## TournamentInformation ###############

    /**
     * Fetches TournamentInformation from the API, parses and saves them in List
     */
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
        if (_tournament != null) {
            urlbuilder.append(_tournament.getmID());
        }
        url = urlbuilder.toString();
        jsonTask.execute(url);
        mTournamentInformationDownloadRunning = true;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    /**
     * Update the TournamentInformation List with the new Fetched Tournament Information
     * @param _tournamentDetailList the new fetched TournamentInformation List
     */
    public void updateTournamentInformationList(List<TournamentInformationItem> _tournamentDetailList) {
        mTournamentInformationList = _tournamentDetailList;
        mTournamentInformationDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }


    /**
     * Return the TournamentInformation List
     * @return tournamentInformation List
     */
    public List<TournamentInformationItem> getTournamentInformationList() {
        return mTournamentInformationList;
    }

    /**
     * Check if the TournamentInformation fetch Process is still running
     * @return true if running, false if finished
     */
    public boolean isTournamentInformationDownloadRunning() {
        return mTournamentInformationDownloadRunning;
    }

    // ############### Game ########################

    /**
     * Fetches Games from the API, parses and saves them in List
     */
    public void requestGame(){
        JSONTask jsonTask = new JSONTask(new GameParser(new GameParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<Game> _gameList) {
                if(_gameList == null){
                    mGameDownloadRunning = false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                }else{
                    updateGames(_gameList);
                }
            }
        }));

        StringBuilder urlbuilder = new StringBuilder();
        String url = "";
        urlbuilder.append(mMatchURL);
        urlbuilder.append(getCurrentTournament().getmID());
        urlbuilder.append("/matches/"+getCurrentMatch().getmID());
        urlbuilder.append("/games");
        mGameDownloadRunning = true;
        url = urlbuilder.toString();
        jsonTask.execute(url);
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    /**
     * Update the Games List with the new Fetched Games
     * @param _gameList the new fetched Game List
     */
    private void updateGames(List<Game> _gameList){
        mGameList = _gameList;
        mGameDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Return GameList
     * @return list of games
     */
    public List<Game> getGameList(){
        return mGameList;
    }

    /**
     * Check if the Game fetch Process is still running
     * @return true if running, false if finished
     */
    public boolean isGameDownloading(){
        return mGameDownloadRunning;
    }
    // ############### Match #######################

    /**
     * Fetches Matches from the API, parses and saves them in List
     */
    public void requestMatches(final MatchType _type) {
        JSONTask jsonTask = new JSONTask(new MatchParser(new MatchParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<Match> _matchList) {
                if (_matchList == null) {
                    mMatchDownloadRunning = false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                } else {
                    switch (_type){
                        case allTournamentMatches: {
                            if(_matchList == null){
                                mMatchDownloadRunning = false;
                                fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                            }else{
                                updateMatches(_matchList);
                            }
                            break;
                        }case participantMatches: {
                            if (_matchList == null) {
                                mParticipantMatchDownloadRunning = false;
                                fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                            } else {
                                updateParticipantMatches(_matchList);
                            }
                            break;
                        }
                    }
                }
            }
        }));
        StringBuilder urlbuilder = new StringBuilder();
        String url = "";
        urlbuilder.append(mMatchURL);

        switch (_type){
            case allTournamentMatches: {
                if(getCurrentTournament() != null){
                    urlbuilder.append(getCurrentTournament().getmID() + "/matches");
                    urlbuilder.append("?sort=structure");
                    mMatchDownloadRunning = true;
                }
                break;
            }
            case participantMatches: {
                if(getCurrentTournament() != null){
                    urlbuilder.append(getCurrentTournament().getmID() + "/matches");
                    urlbuilder.append("?sort=structure");
                    urlbuilder.append("&participant_id="+getCurrentParticipant().getmID());
                    mParticipantMatchDownloadRunning = true;
                }
                break;
            }
        }

        url = urlbuilder.toString();
        jsonTask.execute(url);
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    /**
     * Update the Match List with the new Fetched Matches
     * @param _matchList the new fetched Match List
     */
    public void updateMatches(List<Match> _matchList) {
        mMatchList = _matchList;
        mMatchDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Return the Match List
     * @return List with Matches
     */
    public List<Match> getMatchesList() {
        return mMatchList;
    }


    /**
     * Check if the Match fetch Process is still running
     * @return true if running, false if finished
     */
    public boolean isMatchDownloadRunning() {
        return mMatchDownloadRunning;
    }

    //############ Participant Matches ######################

    /**
     * Update the ParticipantMatch List with the new Fetched Matches
     * @param _matchList the new fetched Participant Match List
     */
    public void updateParticipantMatches(List<Match> _matchList) {
        mParticipantMatchList = _matchList;
        mParticipantMatchDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Return the Participant Match List
     * @return list of Matches from the particular Participant
     */
    public List<Match> getParticipantMatchesList() {
        return mParticipantMatchList;
    }

    /**
     * Check if the ParticipantMatch fetch Process is still running
     * @return true if running, false if finished
     */
    public boolean isParticipantMatchDownloadRunning() {
        return mParticipantMatchDownloadRunning;
    }

    //##############################################################

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

    public Participant getCurrentParticipant() {
        return mCurrentParticipant;
    }

    public void setCurrentParticipant(Participant mCurrentParticipant) {
        this.mCurrentParticipant = mCurrentParticipant;
    }

    public TournamentDetail getCurrentTournamentDetail() {
        return mCurrentTournamentDetail;
    }

    public void setCurrentTournamentDetail(TournamentDetail _currentTournamentDetail) {
        mCurrentTournamentDetail = _currentTournamentDetail;
    }

    public void setCurrentMatch(Match _match){
        mCurrentMatch = _match;
    }

    public Match getCurrentMatch(){
        return mCurrentMatch;
    }

    /**
     * Get the Favorite List from the Database
     * @param _context
     */
    public void requestFavoriteList(Context _context){
        DatabaseHandler db_local = new DatabaseHandler(_context);
        List<Tournament> favoriteList = db_local.getAllEntries();
        updateFavoriteList(favoriteList);
    }

    /**
     * Update the Favorite List
     * @param _favoriteList the new Favorite List
     */
    public void updateFavoriteList(List<Tournament> _favoriteList){
        mFavoriteList = _favoriteList;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    /**
     * Return Favorited Tournaments List
     * @return List of Tournaments which have been favorited
     */
    public List<Tournament> getFavoriteList(){
        return mFavoriteList;
    }

}




