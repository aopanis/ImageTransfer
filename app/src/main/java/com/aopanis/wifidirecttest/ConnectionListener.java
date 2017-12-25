package com.aopanis.wifidirecttest;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

/**
 * Created by aopan on 12/24/2017.
 */

public class ConnectionListener implements WifiP2pManager.ConnectionInfoListener {

    private MainActivity activity;

    public ConnectionListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        Toast.makeText(activity, "Connection Changed", Toast.LENGTH_SHORT).show();
    }

}
