package com.example.mrzhang.smarttraffic.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.adapter.TrafficLightAdapter;
import com.example.mrzhang.smarttraffic.bean.TrafficLightBean;
import com.example.mrzhang.smarttraffic.utils.BaseUrl;
import com.example.mrzhang.smarttraffic.utils.Constant;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrafficLightFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Spinner mSpinner;
    /**
     * 查询
     */
    private Button mBtnQuertLight;
    private RecyclerView mRcv;
    private String userName;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traffic_light_manage, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        SharedPreferences setting = getActivity().getSharedPreferences("setting", 0);
        userName = setting.getString(Constant.SP_USERNAME, "");
        requestQueue = Volley.newRequestQueue(getActivity());
        mRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        getData();
    }

    private void initView(View view) {
        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        mBtnQuertLight = (Button) view.findViewById(R.id.btn_quert_light);
        mBtnQuertLight.setOnClickListener(this);
        mRcv = (RecyclerView) view.findViewById(R.id.rcv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_quert_light:
                getData();

                break;
        }
    }

    private void getData() {
        final List<TrafficLightBean> mlist = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
           JSONObject object = new JSONObject();
           try {
               object.put("TrafficLightId", i);
               object.put("UserName", userName);
           } catch (JSONException e) {
               e.printStackTrace();
           }
            final int finalI = i;
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BaseUrl.AllURL + "get_trafficlight_config",
                   object, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject jsonObject) {
                   String result = jsonObject.optString("RESULT");
                   String errmsg = jsonObject.optString("ERRMSG");
                   if("S".equals(result)) {
                       int greenTime = jsonObject.optInt("GreenTime");
                       int yellowTime = jsonObject.optInt("YellowTime");
                       int redTime = jsonObject.optInt("RedTime");
                       TrafficLightBean bean = new TrafficLightBean(greenTime, yellowTime, redTime, finalI);
                       mlist.add(bean);
                       if(mlist.size() == 5){
                           String s = mSpinner.getSelectedItem().toString();
                           if("路口升序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return trafficLightBean.getRoadId() - t1.getRoadId();
                                   }
                               });
                           }else if("路口降序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return  t1.getRoadId() - trafficLightBean.getRoadId() ;
                                   }
                               });
                           }else if("红灯升序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return trafficLightBean.getRedTime() - t1.getRedTime();
                                   }
                               });
                           }else if("红灯降序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return  t1.getRedTime()-trafficLightBean.getRedTime();
                                   }
                               });
                           }else if("绿灯升序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return trafficLightBean.getGreenTime() - t1.getGreenTime();
                                   }
                               });
                           }else if("绿灯降序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return -trafficLightBean.getGreenTime() + t1.getGreenTime();
                                   }
                               });
                           }else if("黄灯升序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return trafficLightBean.getYellowTime() - t1.getYellowTime();
                                   }
                               });
                           }else if("黄灯降序".equals(s)){
                               Collections.sort(mlist, new Comparator<TrafficLightBean>() {
                                   @Override
                                   public int compare(TrafficLightBean trafficLightBean, TrafficLightBean t1) {
                                       return -trafficLightBean.getYellowTime() + t1.getYellowTime();
                                   }
                               });
                           }
                           TrafficLightAdapter trafficLightAdapter = new TrafficLightAdapter(getActivity(),mlist);
                           mRcv.setAdapter(trafficLightAdapter);
                       }
                   }else {
                       Toast.makeText(getActivity(),errmsg,Toast.LENGTH_SHORT).show();
                   }

               }
           }, null);
           requestQueue.add(request);
       }
    }
}
