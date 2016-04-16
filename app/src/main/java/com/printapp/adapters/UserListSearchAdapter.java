package com.printapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.printapp.utils.CircleTransform;
import com.printapp.activities.ItemActivity;
import com.printapp.models.SearchUsers;
import com.printapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Response;

public class UserListSearchAdapter extends ListSearchAdapter {

    ArrayList<User> items = new ArrayList<>();
    HorizontalViewAdapter data;

    @Override
    public void updateItems(Response<?> rawResponse) {
        this.data = HorizontalViewAdapter.getHorizontalViewAdapter();
        try {
            Response<SearchUsers> response = (Response<SearchUsers>) rawResponse;
            items.clear();
            for (Object ob : response.body().response.items) {
                try {
                    User user = (User) ob;
                    items.add(user);
                    System.out.println(user.first_name + " " + user.last_name);
                } catch (Exception e) {
                    //users.add(new User());
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        }catch (Exception e){
            items.add(new User());
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        final User currentUser = items.get(position);
        Picasso.with(holder.list_img.getContext()).load(currentUser.photo_50).transform(new CircleTransform()).into(holder.list_img);
        holder.list_txt.setText(currentUser.first_name+" "+currentUser.last_name);
        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ItemActivity.class);
                intent.putExtra("ID",(long)currentUser.id);
                System.out.println("LIST LENGTH IN ADAPTER  "+data.photos.size());
                intent.putExtra("LIST", data.photos);
                ((Activity) v.getContext()).startActivityForResult(intent, 1);
                //Toast.makeText(v.getContext(),String.valueOf(currentUser.id),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
