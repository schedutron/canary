package com.kartik.canary.networkTest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kartik on Sat, 17/3/18 in android_app.
 */

public class ConnectionTest {

    Context mContext;

    public ConnectionTest(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isNetworkFunctional() {
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null) {
            return false;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
