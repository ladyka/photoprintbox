package com.printapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.printapp.R;

import java.util.ArrayList;


public class HorizontalViewAdapter extends RecyclerView.Adapter<HorizontalViewAdapter.HorizontalViewHolder> {

    ArrayList<String> strings = new ArrayList<>();

    public void add(){
        strings.add("");
        notifyItemInserted(strings.size());
    }
    public void remove(int pos){
        strings.remove(pos);
        notifyItemRemoved(pos);
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder{

        public TextView txt;
        public ImageView img;
        public HorizontalViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txt);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    @Override
    public HorizontalViewAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item,parent,false);
        HorizontalViewHolder hvh = new HorizontalViewHolder(v);
        return hvh;
    }

    @Override
    public void onBindViewHolder(HorizontalViewAdapter.HorizontalViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        holder.img.setImageResource(R.mipmap.ic_launcher);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(pos);
            }
        });
    }


    @Override
    public int getItemCount() {
        return strings.size();
    }
}
