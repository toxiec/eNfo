package enfo.android.mc.fhooe.at.enfo.Objects;


public class ParticipantLogo {
    private String mIcon_large_square;
    private String mExtra_small_square;
    private String mMedium_small_square;
    private String mMedium_large_square;
    public ParticipantLogo(String _icon_large_square, String _extra_small_square,
                          String _medium_small_square){
       mIcon_large_square = _icon_large_square;
       mExtra_small_square = _extra_small_square;
       mMedium_small_square = _medium_small_square;

    }

    public String getmIcon_large_square() {
        return mIcon_large_square;
    }

    public String getmExtra_small_square() {
        return mExtra_small_square;
    }

    public String getmMedium_small_square() {
        return mMedium_small_square;
    }

}
