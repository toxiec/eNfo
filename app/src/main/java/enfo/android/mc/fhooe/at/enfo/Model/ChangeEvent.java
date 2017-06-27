package enfo.android.mc.fhooe.at.enfo.Model;

import java.util.EventObject;

/**
 * Created by David on 27.06.2017.
 */

public class ChangeEvent extends EventObject {

    public final EventType mEventType;

    public enum EventType{
        reload,
        startDownload,
        finishDownload,
        errorOnDownload,
    };

    /**
     * Constructs a prototypical Event.
     *
     * @param eventType The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ChangeEvent(EventType eventType) {
        super(eventType);
        mEventType = eventType;
    }
}
