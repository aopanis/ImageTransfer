package com.aopanis.wifidirecttest;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by aopan on 1/4/2018.
 */

public class FileClientAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "FileClient";

    private static final int PORT = 8888;
    private Context context;
    private InetAddress device;
    private Uri uri;

    public FileClientAsyncTask(Context context, InetAddress device, Uri uri) {
        this.context = context;
        this.device = device;
        this.uri = uri;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Socket socket = new Socket();
        boolean success = false;
        try {
            Log.d(TAG, "doInBackground: Something but nothing happened");
            socket.bind(null);
            socket.connect((new InetSocketAddress(device, PORT)), 5000);

            OutputStream outputStream = socket.getOutputStream();
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            Log.d(TAG, "doInBackground: URI: " + uri);
            inputStream = cr.openInputStream(uri);
            Log.d(TAG, "doInBackground: input stream created");
            ByteStreams.copy(inputStream, outputStream);

            outputStream.close();
            inputStream.close();
            success = true;
        } catch(FileNotFoundException e) {
            Log.e(TAG, "doInBackground: File Not Found");
        } catch(IOException e){
            Log.e(TAG, "doInBackground: File could not be read");
        } finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        //catch logic
                    }
                }
            }
        }
        return success;
    }

    @Override
    protected void onPostExecute(Boolean results) {
        Toast.makeText(context, "Successful: " + results, Toast.LENGTH_SHORT).show();

    }
}
