package wayt.com.whatareyourthoughts.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
/**
 * Created by Pragya on 12/2/2016.
 */

public class NetworkAvailabilityChecker {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
