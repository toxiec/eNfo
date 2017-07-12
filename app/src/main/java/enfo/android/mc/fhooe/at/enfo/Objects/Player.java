package enfo.android.mc.fhooe.at.enfo.Objects;

import java.util.List;



public class Player {
    /**Player name*/
    private String mName;
    /**Players Country */
    private CountryID mCountry;
    /**A list full with custom fields (not used in the project)*/
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
