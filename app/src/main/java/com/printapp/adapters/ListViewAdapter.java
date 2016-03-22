package com.printapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.printapp.R;

import retrofit2.Response;


public abstract class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder>{

    public abstract void updateItems(Response<?> rawResponse, HorizontalViewAdapter horizontal_data);


    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView list_txt;
        ImageView list_img;
        View wrapper;
        public ListViewHolder(View itemView) {
            super(itemView);
            wrapper = itemView;
            list_txt = (TextView) itemView.findViewById(R.id.list_txt);
            list_img = (ImageView) itemView.findViewById(R.id.list_img);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ListViewHolder lvh = new ListViewHolder(v);
        return lvh;
    }

    @Override
    public abstract void onBindViewHolder(ListViewHolder holder, int position);


    @Override
    public abstract int getItemCount();
}
