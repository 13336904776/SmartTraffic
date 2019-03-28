package com.example.mrzhang.smarttraffic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.bean.TrafficLightBean;

import java.util.List;

public class TrafficLightAdapter extends RecyclerView.Adapter<TrafficLightAdapter.MyViewHold> {

    Context mContext;
    List<TrafficLightBean> mList;

    public TrafficLightAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_traffic_light, parent, false);
        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {
        holder.tv_road_id.setText(mList.get(position).getRoadId()+"");
        holder.tv_red_time.setText(mList.get(position).getRedTime()+"");
        holder.tv_green_time.setText(mList.get(position).getGreenTime()+"");
        holder.tv_yellow_time.setText(mList.get(position).getYellowTime()+"");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHold extends RecyclerView.ViewHolder{
        TextView tv_road_id;
        TextView tv_red_time;
        TextView tv_yellow_time;
        TextView tv_green_time;
        public MyViewHold(View itemView) {
            super(itemView);
            tv_road_id = itemView.findViewById(R.id.tv_road_id);
            tv_red_time = itemView.findViewById(R.id.tv_red_time);
            tv_yellow_time = itemView.findViewById(R.id.tv_yellow_time);
            tv_green_time = itemView.findViewById(R.id.tv_green_time);
        }
    }
}
