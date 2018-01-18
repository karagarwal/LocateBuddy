package skinfonaut.locatebuddy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AGARWAL-PC on 07-11-2017.
 */

public class NetworkCheck {
    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            android.net.NetworkInfo bt = cm.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()) || (bt != null && bt.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;
    }
}
