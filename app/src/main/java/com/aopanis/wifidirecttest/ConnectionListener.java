package com.aopanis.wifidirecttest;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;

import static com.aopanis.wifidirecttest.MainActivity.TAG;

/**
 * Created by aopan on 12/24/2017.
 */

public class ConnectionListener implements WifiP2pManager.ConnectionInfoListener {

    private MainActivity activity;
    private WifiP2pGroup group;

    public ConnectionListener(MainActivity activity, WifiP2pGroup group) {
        this.activity = activity;
        this.group = group;
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        Toast.makeText(activity, "Connection Success: " + info.groupFormed,
                Toast.LENGTH_SHORT).show();
        InetAddress device = info.groupOwnerAddress;
        Log.d(TAG, "onConnectionInfoAvailable: does it get here?");
        if(info.groupFormed) {
            if(info.isGroupOwner) {
                Log.d(TAG, "onConnectionInfoAvailable: maybe this works");
                FileServerAsyncTask serverAsyncTask = new FileServerAsyncTask(activity);
                serverAsyncTask.execute();
            } else {
                Intent intent = new Intent(activity, ImageSelectorActivity.class);
                intent.putExtra("device_name", device);
                activity.startActivity(intent);
            }
        }
    }

}
