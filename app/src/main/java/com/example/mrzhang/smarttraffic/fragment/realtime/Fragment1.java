package com.example.mrzhang.smarttraffic.fragment.realtime;

import android.graphics.Color;
import android.os.Bundle;
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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Fragment1 extends Fragment {

    private TextView tv_title;
    private LineChart mChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.item_real_time, container, false);
        initView(inflate);
        tv_title.setText("温度");
        setData(20, 30);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject object = new JSONObject();
        long l = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy$MM&dd HH:mm:ss",Locale.CHINA);
        String format = dateFormat.format(l);
        Log.e("zz","当前时间==》"+format);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,BaseUrl.AllURL + "get_all_sense", object.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        SenseBean senseBean = gson.fromJson(jsonObject.toString(), SenseBean.class);
                        if("S".equals(senseBean.getRESULT())){
                            int temperature = senseBean.getTemperature();

                        }else {
                            Toast.makeText(getActivity(),senseBean.getERRMSG(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },null);

        return inflate;
    }

    private void initView(View inflate) {
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        mChart = (LineChart) inflate.findViewById(R.id.chart1);
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            int val =  (int)(Math.random() * mult) + 50;
            yVals1.add(new Entry(i, val));
            strings.add(i+5+"");
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            // TODO: 2019/4/1
//            set1.setValues(yVals1);
//            set1.setValueFormatter(new ValueFormatter() {
//                @Override
//                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
//                    return null;
//                }
//            });
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(getResources().getColor(R.color.gray));
            set1.setCircleColor(getResources().getColor(R.color.gray));
            set1.setLineWidth(2f);
//            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a data object with the datasets
            LineData data = new LineData(strings,set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }
}
