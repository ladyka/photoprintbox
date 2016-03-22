package com.printapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.printapp.R;
import com.printapp.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HorizontalViewAdapter extends RecyclerView.Adapter<HorizontalViewAdapter.HorizontalViewHolder> {

    ArrayList<Photo> photos = new ArrayList<>();

    public void add(Photo p){
        photos.add(p);
        notifyItemInserted(photos.size());
    }
    public void remove(int pos){
        photos.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setData(ArrayList<Photo> horizontal_data){
        this.photos =horizontal_data;
        notifyDataSetChanged();
    }
    public ArrayList<Photo> getData(){
        return this.photos;
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
        Picasso.with(holder.img.getContext()).load(photos.get(position).photo_75).placeholder(R.drawable.image_placeholder).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(pos);
            }
        });
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

}
