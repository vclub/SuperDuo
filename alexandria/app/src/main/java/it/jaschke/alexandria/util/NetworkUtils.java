package it.jaschke.alexandria.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Bin Li on 2015/11/21.
 */
public class NetworkUtils {

    public static boolean isNetworkAvailable(Context ctx){
        ConnectivityManager connectivityManager
                = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
