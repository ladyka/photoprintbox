package com.printapp.adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.printapp.R;
import com.printapp.models.SearchPhotos;

import java.util.ArrayList;

import retrofit2.Response;

public class SectionsPagerAdapter extends PagerAdapter {

    private Context context;
    public ArrayList searches;

    public SectionsPagerAdapter(Context context) {
        this.context = context;
        searches = new ArrayList();
        searches.add(new UserListSearchAdapter());
        searches.add(new GroupListSearchAdapter());
        searches.add(new GridAdapter(context));
    }

    public void update(int pagenum, Response<?> response){
        switch (pagenum){
            case 0:
            case 1: {
                ((ListSearchAdapter)searches.get(pagenum)).updateItems(response);
                break;
            }
            case 2:{
                ((GridAdapter) searches.get(pagenum)).setGridData((Response<SearchPhotos>) response);
                break;
            }
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;
        switch (position){
            case 0:
            case 1:{
                v = View.inflate(context, R.layout.main_search_list,null);
                RecyclerView searchRecView = (RecyclerView) v.findViewById(R.id.searchRecView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                searchRecView.setLayoutManager(linearLayoutManager);
                searchRecView.setAdapter((ListSearchAdapter) searches.get(position));
                break;
            }
            case 2:{
                v = View.inflate(context, R.layout.main_search_grid,null);
                GridView gridView = (GridView) v.findViewById(R.id.grid_view);
                gridView.setAdapter((GridAdapter) searches.get(position));
                break;
            }
        }
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return searches.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ПОЛЬЗОВАТЕЛИ";
            case 1:
                return "ГРУППЫ";
            case 2:
                return "ФОТОГРАФИИ";
        }
        return null;
    }
}