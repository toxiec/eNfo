package enfo.android.mc.fhooe.at.enfo.Model;

import java.util.EventObject;


/**
 * Event Types which can occure
 */
public class ChangeEvent extends EventObject {

    public final EventType mEventType;

    public enum EventType{
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
