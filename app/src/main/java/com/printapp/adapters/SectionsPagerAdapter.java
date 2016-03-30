package com.printapp.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.printapp.GridSearchFragment;
import com.printapp.ListSearchFragment;
import com.printapp.models.SearchPhotos;

import retrofit2.Response;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    Context context;
    public ListSearchFragment[] getSfarr() {
        return (ListSearchFragment[]) this.sfarr;
    }

    public Fragment[] sfarr;
    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.sfarr = new Fragment[3];
        this.sfarr[0] = ListSearchFragment.newInstance(0, new UserListSearchAdapter());
        this.sfarr[1] = ListSearchFragment.newInstance(1, new GroupListSearchAdapter());
        this.sfarr[2] = GridSearchFragment.newInstance(new GridAdapter(context));
    }

    public void update(int pagenum, Response<?> response, HorizontalViewAdapter horizontal_data){
        switch (pagenum){
            case 0:
            case 1: {
                ((ListSearchFragment) sfarr[pagenum]).listSearchAdapter.updateItems(response, horizontal_data);
                break;
            }
            case 2:{
                //this.sfarr[2] = GridSearchFragment.newInstance(new GridAdapter(context));
                ((GridSearchFragment) sfarr[pagenum]).gridAdapter.setHorizontalViewAdapter(horizontal_data);
                ((GridSearchFragment) sfarr[pagenum]).gridAdapter.setGridData((Response<SearchPhotos>) response);
            }
        }
    }
    @Override
    public Fragment getItem(int position) {
        return sfarr[position];
    }

    @Override
    public int getCount() {
        return sfarr.length;
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