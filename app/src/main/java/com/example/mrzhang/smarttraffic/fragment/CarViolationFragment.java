package com.example.mrzhang.smarttraffic.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.activity.MyVedioActivity;
import com.example.mrzhang.smarttraffic.adapter.CarViolaAdapter;
import com.example.mrzhang.smarttraffic.bean.CarViolaBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarViolationFragment extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * 违章视频
     */
    private RadioButton mRb1;
    /**
     * 违章图片
     */
    private RadioButton mRb2;
    /**
     * 视频
     */
    private Button mBtn1;
    /**
     * 音频
     */
    private Button mBtn2;
    private RecyclerView mRcv;
    private int lastclick = 1;
    private RadioGroup mRg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_violation_query, container, false);
//        inflater.inflate(R.layout.fragment_violation_query,null);

        initView(view);
        initData();
        return view;
    }

    private void initData() {
        mRb1.setChecked(true);
        GridLayoutManager gridLayoutManager  = new GridLayoutManager(getActivity(),4);
//        gridLayoutManager.setOrientation();
        mRcv.setLayoutManager(gridLayoutManager);
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        List<CarViolaBean> mlist = new ArrayList<>();
        mlist.add(new CarViolaBean(R.mipmap.menu_book,"超速",sdPath+"/Download/paomo.mp4"));
        mlist.add(new CarViolaBean(R.mipmap.menu_slideshow,"违章停车",sdPath+"/Download/冰雨.mp4"));
        mlist.add(new CarViolaBean(R.mipmap.menu_download,"非法鸣笛",sdPath+"/Download/大人的片想.mp4"));
        mlist.add(new CarViolaBean(R.mipmap.menu_star,"闯红灯",sdPath+"/Download/进阶.mkv"));
        mlist.add(new CarViolaBean(R.mipmap.menu_target,"苍鹭等",sdPath+"/Download/paomo.mp4"));
        mlist.add(new CarViolaBean(R.mipmap.menu_book,"超速",sdPath+"/Download/paomo.mp4"));
        mlist.add(new CarViolaBean(R.mipmap.menu_slideshow,"速度太慢",sdPath+"/Download/paomo.mp4"));
        CarViolaAdapter carViolaAdapter = new CarViolaAdapter(getActivity(), mlist);
        mRcv.setAdapter(carViolaAdapter);
    }

    private void initView(View view) {

        mBtn1 = (Button) view.findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) view.findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mRcv = (RecyclerView) view.findViewById(R.id.rcv);
        mRb1 = (RadioButton) view.findViewById(R.id.rb1);
        mRb2 = (RadioButton) view.findViewById(R.id.rb2);
        mRg = (RadioGroup) view.findViewById(R.id.rg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
//            case R.id.rb1://违章视频
//                if(lastclick == 1){
//                    return;
//                }
//                lastclick = 1;
//                mRb1.setBackgroundResource(R.drawable.bg_btn_white);
//                mRb2.setBackgroundResource(R.drawable.bg_btn_grey);
//                Toast.makeText(getActivity(),"点击粒违章视频",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.rb2://违章图片
//                if(lastclick == 2){
//                    return;
//                }
//                lastclick = 2;
//                mRb1.setBackgroundResource(R.drawable.bg_btn_grey);
//                mRb2.setBackgroundResource(R.drawable.bg_btn_white);
//                Toast.makeText(getActivity(),"点击粒违章图片",Toast.LENGTH_SHORT).show();
//                break;
            case R.id.btn1://视频
                Intent intent = new Intent(getActivity(),MyVedioActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2://音频

                MediaPlayer player = new MediaPlayer();
                String path = Environment.getExternalStorageDirectory().getPath() + "/Download/Oops.MP3";
                try {
                    player.setDataSource(path);
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    Log.e("zz","zzzzz");
                    e.printStackTrace();
                }
//                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mediaPlayer) {
//                        mediaPlayer.start();
//                    }
//                });
                break;
        }
    }
}
