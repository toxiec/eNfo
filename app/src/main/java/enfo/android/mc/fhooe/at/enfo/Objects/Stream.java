package enfo.android.mc.fhooe.at.enfo.Objects;



public class Stream {

    /**An unique identifier for this stream.*/
    private String mID;
    /**Title of the stream.*/
    private String mName;
    /**Url of the stream.*/
    private String mURL;
    /**Language code of the stream content.
     * This value is represented as an ISO 639-1 code.*/
    private String mLanguage;

    public Stream(String _id, String _name, String _url, String _language){
        mID = _id;
        mName = _name;
        mURL = _url;
        mLanguage = _language;
    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public String getmURL() {
        return mURL;
    }

    public String getmLanguage() {
        return mLanguage;
    }
}
