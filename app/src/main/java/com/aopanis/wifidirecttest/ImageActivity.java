package com.aopanis.wifidirecttest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        if(getIntent().hasExtra("image_path")) {
            ImageView imageView = findViewById(R.id.imageDisplayer);
            Bundle bundle = getIntent().getExtras();
            String path = bundle.getString("image_path");
            Uri uri = Uri.fromFile(new File(path));
            imageView.setImageURI(uri);
        }
    }
}
