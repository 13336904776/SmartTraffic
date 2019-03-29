package com.example.mrzhang.smarttraffic.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.mrzhang.smarttraffic.R;

public class MyVedioActivity extends BaseActivity {

    private VideoView mMyvideoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vedio);
        initView();
        String path = Environment.getExternalStorageDirectory().getPath() + "/Download/paomo.mp4";
        Uri parse = Uri.parse(path);
        mMyvideoview.setVideoPath(path);
//        mMyvideoview.setVideoURI(parse);
        mMyvideoview.setMediaController(new MediaController(this));
        mMyvideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    private void initView() {
        mMyvideoview = (VideoView) findViewById(R.id.myvideoview);
    }
}
