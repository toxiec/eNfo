package enfo.android.mc.fhooe.at.enfo.Model;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;

/**
 * Created by David on 27.06.2017.
 */

public class EntityManager implements JSONTask.AsyncResponse {

    private static EntityManager instance;
    private boolean mDisciplineDownloadRunning = false;

    private final String mDisciplesURL = "https://api.toornament.com/v1/disciplines";
    private List<Discipline> mDisciplineList = new ArrayList<>();
    private List<ModelChangeListener> mListenerList = new ArrayList<>();

    private EntityManager(){

    }

    public boolean isDisciplineDownloadRunning(){
        return mDisciplineDownloadRunning;
    }

    public void addModelChangeListener(ModelChangeListener _listener){
        mListenerList.add(_listener);
    }

    public void removeModelChangeListener(ModelChangeListener _lister){
        mListenerList.remove(_lister);
    }

    private void fireChangeOccured(ChangeEvent _e){
        for(ModelChangeListener l: mListenerList){
            l.onChangeOccured(_e);
        }
    }

    public static EntityManager getInstance(){
        if(instance == null){
            instance = new EntityManager();
        }
        return instance;
    }

    public void requestDiscipline(){
        JSONTask jsonTask = new JSONTask(new DisciplineParser(new DisciplineParser.OnParseFinished() {
            @Override
            public void notifyParseFinished(List<Discipline> _disciplineList) {
                if(_disciplineList == null){
                    mDisciplineDownloadRunning=false;
                    fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.errorOnDownload));
                }else{
                    updateDisciplineList(_disciplineList);
                }
            }
        }));

        jsonTask.execute(mDisciplesURL);
        mDisciplineDownloadRunning = true;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.startDownload));
    }

    void updateDisciplineList(List<Discipline> _disciplineList){
        mDisciplineList = _disciplineList;
        mDisciplineDownloadRunning = false;
        fireChangeOccured(new ChangeEvent(ChangeEvent.EventType.finishDownload));
    }

    public List<Discipline> getDisciplineList(){
        return mDisciplineList;
    }

    void getMatches(){

    }

    @Override
    public void processFinish(String jsonResult) {

    }
}




