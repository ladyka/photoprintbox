package com.printapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.printapp.adapters.GridAdapter;

public class GridSearchFragment extends Fragment {

    public GridSearchFragment(){}

    public GridAdapter gridAdapter;

    public static GridSearchFragment newInstance(GridAdapter gridAdapter) {
        Bundle args = new Bundle();

        GridSearchFragment fragment = new GridSearchFragment();
        fragment.setArguments(args);

        fragment.gridAdapter = gridAdapter;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_grid,container,false);
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(gridAdapter);
        return gridView;
    }
}
