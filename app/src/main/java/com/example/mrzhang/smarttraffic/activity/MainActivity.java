package com.example.mrzhang.smarttraffic.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.adapter.LeftMenuAdapter;
import com.example.mrzhang.smarttraffic.bean.MenuBean;
import com.example.mrzhang.smarttraffic.fragment.AccountManageFragment;
import com.example.mrzhang.smarttraffic.fragment.BusFragment;
import com.example.mrzhang.smarttraffic.fragment.TrafficLightFragment;
import com.example.mrzhang.smarttraffic.utils.Constant;

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
    private FragmentManager fragmentManager;
    private DrawerLayout mDraw;
    private LinearLayout mLlLeftMenu;
    private SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();
    }

    private void initData() {

        mTitleIv.setBackgroundResource(R.mipmap.ic_launcher);

        fragmentManager = getFragmentManager();
        List<MenuBean> menuBeans = new ArrayList<>();
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "账户管理"));
        menuBeans.add(new MenuBean(R.mipmap.menu_book, "公交查询"));
        setting = getSharedPreferences("setting", 0);
        String userRole = setting.getString(Constant.SP_USERROLE, "");
        if (!userRole.equals("nor_user")) {
            menuBeans.add(new MenuBean(R.mipmap.menu_star, "红绿灯管理"));
        }
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "违章查询"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "道路状况"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "生活助手"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "数据分析"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "个人中心"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "创新题"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "意见反馈"));
        menuBeans.add(new MenuBean(R.mipmap.menu_star, "退出"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvLeft.setLayoutManager(linearLayoutManager);
        LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(MainActivity.this, menuBeans);
        mRcvLeft.setAdapter(leftMenuAdapter);
        leftMenuAdapter.setmClicklisten(new LeftMenuAdapter.ClickListen() {
            @Override
            public void click(int position, String menuName) {
//                mDraw.closeDrawers();
                mDraw.closeDrawer(mLlLeftMenu);
                mTitleTv.setText(menuName);
//                Toast.makeText(MainActivity.this, menuName + "点击了" + position, Toast.LENGTH_SHORT).show();
                if ("账户管理".equals(menuName)) {
//                getSupportFragmentManager();//v4
                    fragmentManager.beginTransaction().replace(R.id.rl_home, new AccountManageFragment()).commit();

                } else if ("公交查询".equals(menuName)) {
                    fragmentManager.beginTransaction().replace(R.id.rl_home, new BusFragment()).commit();
                } else if ("账户管理".equals(menuName)) {

                } else if ("红绿灯管理".equals(menuName)) {
                    fragmentManager.beginTransaction().replace(R.id.rl_home, new TrafficLightFragment()).commit();
                } else if ("违章查询".equals(menuName)) {

                } else if ("道路状况".equals(menuName)) {

                } else if ("生活助手".equals(menuName)) {

                } else if ("数据分析".equals(menuName)) {

                } else if ("个人中心".equals(menuName)) {

                } else if ("创新管理".equals(menuName)) {

                } else if ("意见反馈".equals(menuName)) {

                } else if ("退出".equals(menuName)) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    setting.edit().putBoolean(Constant.SP_ISAUTOLOGIN,false).commit();
                    finish();
                }

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
        mDraw = (DrawerLayout) findViewById(R.id.draw);
        mLlLeftMenu = (LinearLayout) findViewById(R.id.ll_left_menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.title_iv:
                mDraw.openDrawer(mLlLeftMenu);
                break;
            case R.id.title_right_tv:
                break;
        }
    }
}
