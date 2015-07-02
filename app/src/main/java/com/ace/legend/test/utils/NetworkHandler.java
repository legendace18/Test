package com.ace.legend.test.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rohan on 6/25/15.
 */
public class NetworkHandler {

    private Context context;

    public NetworkHandler(Context context){
        this.context = context;
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }
}
