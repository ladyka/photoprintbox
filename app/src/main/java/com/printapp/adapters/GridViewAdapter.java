package com.printapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.printapp.R;
import com.printapp.models.Photo;
import com.printapp.models.SearchPhotos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Response;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridViewHolder>{

    ArrayList<Photo> photos;
    public GridViewAdapter(Response<SearchPhotos> response) {
        photos = new ArrayList<Photo>();
        for(Photo photo :response.body().response.items){
            photos.add(photo);
        }
        notifyDataSetChanged();
        Log.d("GridViewA: PHOTO SIZE", String.valueOf(photos.size()));
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView grid_image;
        public GridViewHolder(View itemView) {
            super(itemView);
            grid_image = (ImageView) itemView.findViewById(R.id.grid_image);
        }
    }
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent,false);
        GridViewHolder gvh = new GridViewHolder(layoutView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        String currentUrl = photos.get(position).photo_75;
        Log.d("onBindViewHolder: ",currentUrl);
        Context currentContext = holder.grid_image.getContext();
        Picasso.with(currentContext).setIndicatorsEnabled(true);
        Picasso.with(currentContext).
                load(currentUrl).
                resize(
                        Resources.getSystem().getDisplayMetrics().widthPixels/3,
                        Resources.getSystem().getDisplayMetrics().widthPixels/3
                ).into(holder.grid_image);
        holder.grid_image.invalidate();
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

}
