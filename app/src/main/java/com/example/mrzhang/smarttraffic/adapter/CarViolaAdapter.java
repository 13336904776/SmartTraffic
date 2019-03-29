package com.example.mrzhang.smarttraffic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.activity.MyVedioActivity;
import com.example.mrzhang.smarttraffic.bean.CarViolaBean;

import java.util.List;

public class CarViolaAdapter extends RecyclerView.Adapter<CarViolaAdapter.MyyViewholder> {

    Context mContext;
    List<CarViolaBean> mList;

    public CarViolaAdapter(Context mContext, List<CarViolaBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_car_viola, parent, false);
        return new MyyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyyViewholder holder, final int position) {
        holder.img.setImageResource(mList.get(position).getImgId());
        holder.tv.setText(mList.get(position).getName());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MyVedioActivity.class);
                intent.putExtra("path",mList.get(position).getPath());
                intent.putExtra("bean",mList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyyViewholder extends RecyclerView.ViewHolder{
        LinearLayout ll;
        ImageView img;
        TextView tv;
        public MyyViewholder(View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll);
            img = itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
