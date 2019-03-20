package com.example.mrzhang.smarttraffic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzhang.smarttraffic.R;
import com.example.mrzhang.smarttraffic.bean.MenuBean;

import java.util.List;

public class LeftMenuAdapter extends RecyclerView.Adapter<LeftMenuAdapter.MyViewHolder> {

    Context mContext;
    List<MenuBean> mlist;
    private ClickListen mClicklisten;

    public LeftMenuAdapter(Context mContext , List<MenuBean> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false);
        return new MyViewHolder(inflate);
    }

    public ClickListen getmClicklisten() {
        return mClicklisten;
    }

    public void setmClicklisten(ClickListen mClicklisten) {
        this.mClicklisten = mClicklisten;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.iv.setBackgroundResource(mlist.get(position).getIv());
        holder.tv.setText(mlist.get(position).getTv());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClicklisten.click(position,mlist.get(position).getTv());
//                Toast.makeText(mContext,"点击粒"+mlist.get(position).getTv(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl;
        ImageView iv;
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            rl = itemView.findViewById(R.id.rl);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);

        }
    }

    public interface ClickListen{
        void click(int position,String menuName);
    }
}
