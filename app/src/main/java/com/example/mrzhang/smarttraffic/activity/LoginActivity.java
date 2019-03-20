package com.example.mrzhang.smarttraffic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.mrzhang.smarttraffic.utils.Constant;

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
    private String mAccount;
    private String mPsw;
    private SharedPreferences setting;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();

    }

    private void initData() {
        setting = getSharedPreferences("setting", 0);
        edit = setting.edit();
        boolean pwdIsCheck = setting.getBoolean(Constant.SP_ISREMEMBERPWD, false);
        mRememberPwdCb.setChecked(pwdIsCheck);
        if(pwdIsCheck){//如果上次保存的是记住密码的话
            mAccount = setting.getString(Constant.SP_USERNAME, "");
            mPsw = setting.getString(Constant.SP_PASSWORD, "");
            mAccountEdt.setText(mAccount);
            mPwdEdt.setText(mPsw);
        }
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
                mAccount = mAccountEdt.getText().toString().trim();
                mPsw = mPwdEdt.getText().toString().trim();

                if(TextUtils.isEmpty(mAccount)){
                    Toast.makeText(LoginActivity.this,"请输入您的账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if( mAccount.length() <= 4){
                    Toast.makeText(LoginActivity.this,"您输入完整的账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mPsw)){
                    Toast.makeText(LoginActivity.this,"请输入您的密码",Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                //传过去的参数
                JSONObject object  = new JSONObject();
                try {
                    object.put("UserName", mAccount);
                    object.put("UserPwd", mPsw);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://60.205.182.162:8080/transportservice/api/user_login"
                        , object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //在这处理请求成功
                        Log.e("zzcg",jsonObject.toString());
                        String result = jsonObject.optString("RESULT");

                        if("S".equals(result)){//请求成功且登录成功
                            String userRole = jsonObject.optString("UserRole");

                            edit.putString(Constant.SP_USERROLE,userRole).commit();
                            edit.putBoolean(Constant.SP_ISAUTOLOGIN,mSelfLoginCb.isChecked());
                            boolean checked = mRememberPwdCb.isChecked();
                            if(checked){
                                edit.putString(Constant.SP_USERNAME,mAccount).commit();
                                edit.putString(Constant.SP_PASSWORD,mPsw).commit();
                                edit.putBoolean(Constant.SP_ISREMEMBERPWD,true).commit();
                            }else {
                                edit.remove(Constant.SP_USERNAME).commit();
//                                edit.putString(Constant.SP_USERNAME,"").commit();
                                edit.remove(Constant.SP_PASSWORD).commit();
                                edit.putBoolean(Constant.SP_ISREMEMBERPWD,false).commit();
                            }

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {//请求成功但是登录失败
                            String errmsg = jsonObject.optString("ERRMSG");
                            Toast.makeText(LoginActivity.this,errmsg,Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //接口请求失败
                        Log.e("zzsb",volleyError.getMessage());
                        Toast.makeText(LoginActivity.this,volleyError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);

                break;
            case R.id.regist_btn:
                break;
        }
    }
}

