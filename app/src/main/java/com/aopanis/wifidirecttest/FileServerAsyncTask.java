package com.aopanis.wifidirecttest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aopan on 12/26/2017.
 */

public class FileServerAsyncTask extends AsyncTask<Void, Void, String> {
    private final static String TAG = "ServerTask";
    private Context context;
//    private TextView statusText;
    public FileServerAsyncTask(Context context){
        this.context = context;
//        this.statusText = (TextView) statusText;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Log.d(TAG, "doInBackground: Server socket created");
            Socket client = serverSocket.accept();
            Log.d(TAG, "doInBackground: accepted client socket");

            final File f = new File(Environment.getExternalStorageDirectory() + "/"
                    + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis()
                    + ".jpg");

            File dirs = new File(f.getParent());
            if (!dirs.exists()) {
                Log.d(TAG, "doInBackground: Make directories: " + dirs.mkdirs());
            }
            f.createNewFile();
            InputStream inputstream = client.getInputStream();
            ByteStreams.copy(inputstream, new FileOutputStream(f));
            serverSocket.close();
            return f.getAbsolutePath();
        } catch(IOException e) {
            Log.e(MainActivity.TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
//        if(result != null) {
//            statusText.setText(String.format("File copied - %s", result));
//        }
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("image_path", result);
        context.startActivity(intent);
    }
}
