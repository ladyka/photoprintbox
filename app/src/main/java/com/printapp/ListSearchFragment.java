package com.printapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.printapp.adapters.ListSearchAdapter;

public class ListSearchFragment extends Fragment {
    public ListSearchFragment() {}

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    public ListSearchAdapter listSearchAdapter;

    public static ListSearchFragment newInstance(int sectionNumber, ListSearchAdapter listSearchAdapter) {
        ListSearchFragment fragment = new ListSearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.listSearchAdapter = listSearchAdapter;
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
        rcv.setAdapter(listSearchAdapter);
        return rootView;
    }
}