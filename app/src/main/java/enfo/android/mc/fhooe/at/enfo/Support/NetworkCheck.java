package enfo.android.mc.fhooe.at.enfo.Support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by David on 29.05.2017.
 */

/**
 * Check if Network Connection is Available
 */
public class NetworkCheck {
    /**
     * Check if Network Connection is Available
     * @param context
     * @return true if network is available, else false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
