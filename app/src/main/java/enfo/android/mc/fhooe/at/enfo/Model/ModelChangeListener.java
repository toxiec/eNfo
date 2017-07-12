package enfo.android.mc.fhooe.at.enfo.Model;

import java.util.EventListener;


/**
 * Listener which gets fired when an event occurs
 */
public interface ModelChangeListener extends EventListener {
    /**
     * Gets fired when Model gets changed
     * @param e Event which got fired
     */
    public void onChangeOccured(ChangeEvent e);
}
