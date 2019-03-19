package com.example.mrzhang.smarttraffic.activity;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrzhang.smarttraffic.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入账号
     */
    private EditText mAccountEdt;
    /**
     * 清输入密码
     */
    private EditText mPwdEdt;
    /**
     * 记住密码
     */
    private CheckBox mRememberPwdCb;
    /**
     * 自动登录
     */
    private CheckBox mSelfLoginCb;
    /**
     * 登录
     */
    private Button mLoginBtn;
    /**
     * 注册
     */
    private Button mRegistBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }


    private void initView() {
        mAccountEdt = (EditText) findViewById(R.id.account_edt);
        mPwdEdt = (EditText) findViewById(R.id.pwd_edt);
        mRememberPwdCb = (CheckBox) findViewById(R.id.remember_pwd_cb);
        mSelfLoginCb = (CheckBox) findViewById(R.id.self_login_cb);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
        mRegistBtn = (Button) findViewById(R.id.regist_btn);
        mRegistBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_btn:
                String mAccount = mAccountEdt.getText().toString().trim();
                String mPwd = mPwdEdt.getText().toString().trim();
                if(TextUtils.isEmpty(mAccount) || mAccount.length() < 4){
                    Toast.makeText(this,"账号不能为空且不能少于四位",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(mPwd)  || mPwd.length() < 6){
                    Toast.makeText(this,"密码不能为空且不能少于六位",Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JSONObject object = new JSONObject();
                try {
                    object.put("UserName",mAccount);
                    object.put("UserPwd",mPwd);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://60.205.182.162:8080/transportservice/api/user_login",
                        object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Thread currentThread = Thread.currentThread();
                        Log.e("zfy","currentThread==>"+currentThread.toString());
                        if(currentThread == Looper.getMainLooper().getThread()){
                            Log.e("zfy","currentThread==>是主线程");
                        }
                        String s = jsonObject.toString();
                        Log.e("zfy","请求结果==>"+s+"==="+jsonObject);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Thread currentThread = Thread.currentThread();
                        Log.e("zfy","currentThread=Err=>"+currentThread.toString());
                        if(currentThread == Looper.getMainLooper().getThread()){
                            Log.e("zfy","currentThread=Err=>是主线程");
                        }
                    }
                });
                requestQueue.add(jsonObjectRequest);

                break;
            case R.id.regist_btn:

                break;
        }
    }
}

