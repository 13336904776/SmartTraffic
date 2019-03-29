package com.example.mrzhang.smarttraffic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity {

    private TextView mTimeTv;
    private SharedPreferences setting;
    private String mUserName;
    private String mPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.e("zzz","我进入APP粒");
        initView();
        setting = getSharedPreferences("setting", 0);
        mUserName = setting.getString(Constant.SP_USERNAME, "");
        mPsw = setting.getString(Constant.SP_PASSWORD, "");
        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeTv.setText(millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {

                boolean isFrist = setting.getBoolean(Constant.SP_ISFRAIST, true);
                boolean isAutoLogin = setting.getBoolean(Constant.SP_ISAUTOLOGIN, true);
                if (isFrist) {//是第一次进入APP
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (isAutoLogin) {

                        Log.e("zz","zzzz1");
                        if (TextUtils.isEmpty(mUserName) || TextUtils.isEmpty(mPsw)) {//
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            login(mUserName, mPsw);
                        }

                    }else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }

        }.start();
    }

    private void login(String mUserName, String mPsw) {
        Log.e("zz","zzzz2");
        RequestQueue requestQueue = Volley.newRequestQueue(SplashActivity.this);
        //传过去的参数
        JSONObject object = new JSONObject();
        try {
            object.put("UserName", mUserName);
            object.put("UserPwd", mPsw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://60.205.182.162:8080/transportservice/api/user_login"
                , object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //在这处理请求成功
                Log.e("zzcg", jsonObject.toString());
                String result = jsonObject.optString("RESULT");

                if ("S".equals(result)) {//请求成功且登录成功
                    String userRole = jsonObject.optString("UserRole");
                    setting.edit().putString(Constant.SP_USERROLE, userRole);

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {//请求成功但是登录失败

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void initView() {
        mTimeTv = (TextView) findViewById(R.id.time_tv);
    }
}
