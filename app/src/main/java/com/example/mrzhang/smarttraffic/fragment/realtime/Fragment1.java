package com.example.mrzhang.smarttraffic.fragment.realtime;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.bean.SenseBean;
import com.example.mrzhang.smarttraffic.utils.BaseUrl;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment1 extends Fragment implements View.OnClickListener {

    private TextView tv_title;
    private LineChart mChart;
    private RequestQueue requestQueue;
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.item_real_time, container, false);
        initView(inflate);
        tv_title.setText("温度");
        requestQueue = Volley.newRequestQueue(getActivity());

        initChat();

        setChat();

        return inflate;
    }

    private void setChat() {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                JSONObject object = new JSONObject();
                long l = System.currentTimeMillis();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yy$MM&dd HH:mm:ss",Locale.CHINA);
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
                final String format = dateFormat.format(l);
                Log.e("zz", "当前时间==》" + format);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BaseUrl.AllURL + "get_all_sense", object.toString(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Gson gson = new Gson();
                                SenseBean senseBean = gson.fromJson(jsonObject.toString(), SenseBean.class);
                                if ("S".equals(senseBean.getRESULT())) {
                                    int temperature = senseBean.getTemperature();
                                    LineData lineData = mChart.getLineData();
                                    LineDataSet dataSet = lineData.getDataSetByIndex(0);
                                    lineData.addEntry(new Entry(temperature, dataSet.getValueCount()), 0);
                                    lineData.addXValue(format);
                                    mChart.notifyDataSetChanged();
                                    mChart.invalidate();
                                    mChart.setVisibleXRange(20, 20);
                                    mChart.moveViewToX(dataSet.getValueCount() - 20);
                                } else {
                                    Toast.makeText(getActivity(), senseBean.getERRMSG(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, null);
                requestQueue.add(request);
            }
        }, 1000, 3000);

    }

    private void initChat() {

        mChart.setTouchEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        mChart.setDescription("");

        YAxis axisLeft = mChart.getAxisLeft();
        YAxis axisRight = mChart.getAxisRight();
        axisRight.setEnabled(false);

        List<String> mxv = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mxv.add("X轴" + i);
        }

        //一个坐标数组
        ArrayList<Entry> mList = new ArrayList<Entry>();
        for (int i = 0; i < 20; i++) {
            mList.add(new Entry((float) (Math.random() * 10), i));
        }

        //
        LineDataSet lineDataSet = new LineDataSet(mList, "zz");

        lineDataSet.setCircleColor(0xff999999);
        lineDataSet.setColor(0xff999999);
//        lineData.addDataSet(lineDataSet);
        //LineData是这条线的数据，包括X轴的数据和lineDataSet
        LineData lineData = new LineData(mxv, lineDataSet);
        mChart.setData(lineData);
    }

    private void initView(View inflate) {
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        mChart = (LineChart) inflate.findViewById(R.id.chart1);
    }


    @Override
    public void onClick(View view) {

    }
}
