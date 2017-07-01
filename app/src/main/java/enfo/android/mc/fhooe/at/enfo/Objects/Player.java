package enfo.android.mc.fhooe.at.enfo.Objects;

import java.util.List;

/**
 * Created by David on 08.06.2017.
 */

public class Player {
    private String mName;
    private CountryID mCountry;
    private List<String> mCustomField;

    public Player(String _name, CountryID _country, List<String> _customField){
        mName = _name;
        mCountry = _country;
        mCustomField = _customField;
    }

    public String getmName() {
        return mName;
    }

    public CountryID getmCountry() {
        return mCountry;
    }

    public List<String> getmCustomField() {
        return mCustomField;
    }

}
