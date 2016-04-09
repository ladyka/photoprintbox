package com.printapp.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.printapp.PhotoSelectDialogFragment;
import com.printapp.R;
import com.printapp.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HorizontalViewAdapter extends RecyclerView.Adapter<HorizontalViewAdapter.HorizontalViewHolder> {

    ArrayList<Photo> photos = new ArrayList<>();
    FragmentManager fragmentManager;
    static HorizontalViewAdapter instance;

    public static HorizontalViewAdapter getHorizontalViewAdapter(){
        if(instance == null){
            instance = new HorizontalViewAdapter();
        }
        return instance;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void add(Photo photo){
        photos.add(photo);
        notifyItemInserted(photos.size());
        notifyDataSetChanged();
    }

    public void remove(Photo photo){
        if(photos.contains(photo)){
            photos.remove(photo);
        }
        notifyDataSetChanged();
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
            txt = (TextView) itemView.findViewById(R.id.count);
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
        final Photo photo = photos.get(holder.getAdapterPosition());
        holder.txt.setText(String.valueOf(photo.count));
        Picasso.with(holder.img.getContext()).load(photo.photo_75).placeholder(R.drawable.image_placeholder).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoSelectDialogFragment photoSelectDialogFragment = PhotoSelectDialogFragment.newInstance(photo,"UPDATE");
                photoSelectDialogFragment.show(fragmentManager,"TAG");
            }
        });
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

}
