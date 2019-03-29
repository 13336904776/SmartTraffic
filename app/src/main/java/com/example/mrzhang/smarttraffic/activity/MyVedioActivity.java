package com.example.mrzhang.smarttraffic.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.bean.CarViolaBean;

public class MyVedioActivity extends BaseActivity {

    private VideoView mMyvideoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vedio);
        initView();
        initData();


    }

    private void initData() {
        String path2 = getIntent().getStringExtra("path");
        Log.e("zz","path2==>"+path2);

        CarViolaBean bean = (CarViolaBean) getIntent().getSerializableExtra("bean");
        Log.e("zz",bean.toString());

        String path = Environment.getExternalStorageDirectory().getPath() + "/Download/paomo.mp4";
        String path1 = "android.resource://" + getPackageName() + "/raw/paomo";
        Uri parse = Uri.parse(path);
        mMyvideoview.setVideoPath(path2);
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
