package com.example.mrzhang.smarttraffic.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.adapter.LeftMenuAdapter;
import com.example.mrzhang.smarttraffic.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTitleIv;
    /**
     * 标题
     */
    private TextView mTitleTv;
    /**
     * 副标题
     */
    private TextView mTitleRightTv;
    private RelativeLayout mRlHome;
    private RecyclerView mRcvLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        List<MenuBean> menuBeans = new ArrayList<>();
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"账户管理"));
        menuBeans.add(new MenuBean(R.mipmap.menu_book,"公交查询"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"账户管理"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"红绿灯管理"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"违章查询"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"道路状况"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"生活助手"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"数据分析"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"个人中心"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"创新题"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"意见反馈"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star,"退出"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvLeft.setLayoutManager(linearLayoutManager);
        LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(MainActivity.this, menuBeans);
        mRcvLeft.setAdapter(leftMenuAdapter);
        leftMenuAdapter.setmClicklisten(new LeftMenuAdapter.ClickListen() {
            @Override
            public void click(int position, String menuName) {
                Toast.makeText(MainActivity.this,menuName+"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        mTitleIv = (ImageView) findViewById(R.id.title_iv);
        mTitleIv.setOnClickListener(this);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mTitleRightTv.setOnClickListener(this);
        mRlHome = (RelativeLayout) findViewById(R.id.rl_home);
        mRcvLeft = (RecyclerView) findViewById(R.id.rcv_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.title_iv:
                break;
            case R.id.title_right_tv:
                break;
        }
    }
}
