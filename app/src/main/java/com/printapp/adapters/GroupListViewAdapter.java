package com.printapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.printapp.ItemActivity;
import com.printapp.models.Group;
import com.printapp.models.SearchGroups;

import java.util.ArrayList;

import retrofit2.Response;

public class GroupListViewAdapter extends ListViewAdapter {

    ArrayList<Group> items = new ArrayList<>();
    @Override
    public void updateItems(Response<?> rawResponse) {
        try {
            Response<SearchGroups> response = (Response<SearchGroups>) rawResponse;
            items.clear();
            for (Object ob : response.body().response.items) {
                try {
                    Group group = (Group) ob;
                    items.add(group);
                    System.out.println(group.name);
                } catch (Exception e) {
                    //users.add(new User());
                    System.out.println(e.getLocalizedMessage());
                }
                notifyDataSetChanged();
            }
        }catch (Exception e){
            items.add(new Group());
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        final Group currentGroup = items.get(position);
        holder.list_txt.setText(currentGroup.name);
        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ItemActivity.class);
                intent.putExtra("ID",-((long)currentGroup.id));
                ((Activity) v.getContext()).startActivityForResult(intent, 1);
                //Toast.makeText(v.getContext(),String.valueOf(currentUser.id),Toast.LENGTH_SHORT).show();
            }
        });    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
