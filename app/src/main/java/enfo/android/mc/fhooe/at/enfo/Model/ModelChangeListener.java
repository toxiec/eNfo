package enfo.android.mc.fhooe.at.enfo.Model;

import java.util.EventListener;

/**
 * Created by David on 27.06.2017.
 */

public interface ModelChangeListener extends EventListener {
    public void onChangeOccured(ChangeEvent e);
}
