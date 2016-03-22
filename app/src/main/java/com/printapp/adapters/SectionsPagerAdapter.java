package com.printapp.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.printapp.SearchFragment;

import retrofit2.Response;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public SearchFragment[] getSfarr() {
        return this.sfarr;
    }

    public SearchFragment[] sfarr;
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.sfarr = new SearchFragment[2];
        this.sfarr[0] = SearchFragment.newInstance(0, new UserListViewAdapter());
        this.sfarr[1] = SearchFragment.newInstance(1, new GroupListViewAdapter());
    }

    public void update(int pagenum, Response<?> response, HorizontalViewAdapter horizontal_data){
        sfarr[pagenum].lva.updateItems(response,horizontal_data);
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
        }
        return null;
    }
}