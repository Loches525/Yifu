package com.bishe.yifu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.widget.ImageView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowImageActivity extends Activity {

    ImageView iv;
    String path;
    PhotoViewAttacher mAttacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        path = intent.getStringExtra("path");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_image);
        iv = (ImageView) findViewById(R.id.showimage);
        Picasso.with(this).load(new File(path)).into(iv, new Callback() {
            @Override
            public void onSuccess() {
                mAttacher = new PhotoViewAttacher(iv);
            }

            @Override
            public void onError() {
            }
        });


    }

}
