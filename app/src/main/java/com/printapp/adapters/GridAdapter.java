package com.printapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.printapp.fragments.PhotoSelectDialogFragment;
import com.printapp.R;
import com.printapp.models.Photo;
import com.printapp.models.SearchPhotos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Response;


public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public GridAdapter(Context mContext) {
        this.photos = new ArrayList<>();
        this.mContext = mContext;
    }

    public void setGridData(Response<SearchPhotos> response) {
        this.photos = new ArrayList<Photo>();
        for(Photo photo :response.body().response.items){
            this.photos.add(photo);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.photos.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        final Photo item = photos.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.grid_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoSelectDialogFragment photoSelectDialogFragment = PhotoSelectDialogFragment.newInstance(item,"ADD");
                photoSelectDialogFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"TAG");

            }
        });
        //Picasso.with(mContext).setIndicatorsEnabled(true);
        Picasso.with(mContext)
                .load(photos.get(position).photo_75)
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imageView);

        return row;
    }

    class ViewHolder {
        ImageView imageView;
    }

}
