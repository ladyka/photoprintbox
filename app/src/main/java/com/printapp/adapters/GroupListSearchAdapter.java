package com.printapp.adapters;

import android.content.Intent;
import android.view.View;

import com.printapp.activities.ItemActivity;
import com.printapp.models.Group;
import com.printapp.models.SearchGroups;
import com.printapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Response;

public class GroupListSearchAdapter extends ListSearchAdapter {

    ArrayList<Group> items = new ArrayList<>();
    HorizontalViewAdapter data;

    @Override
    public void updateItems(Response<?> rawResponse) {
        this.data = HorizontalViewAdapter.getHorizontalViewAdapter();
        try{
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
        Picasso.with(holder.list_img.getContext()).load(currentGroup.photo_50).transform(new CircleTransform()).into(holder.list_img);
        holder.list_txt.setText(currentGroup.name);
        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ItemActivity.class);
                intent.putExtra("ID",-((long)currentGroup.id));
                intent.putExtra("TITLE", currentGroup.name);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
