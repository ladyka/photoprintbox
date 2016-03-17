package com.printapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.printapp.adapters.ListViewAdapter;

public class SearchFragment extends Fragment {
    public SearchFragment() {}

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    public ListViewAdapter lva;

    public static SearchFragment newInstance(int sectionNumber, ListViewAdapter listViewAdapter) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.lva = listViewAdapter;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView rcv = (RecyclerView) rootView.findViewById(R.id.searchrecview);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        LinearLayoutManager sllm = new LinearLayoutManager(getContext());
        sllm.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(sllm);
        rcv.setAdapter(lva);
        return rootView;
    }
}