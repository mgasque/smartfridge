package com.group2.smartfridge.smartfridge;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aude on 29/11/2015.
 */
public class FragmentFloor2 extends Fragment {

    // Creation attribute for RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_floor2, container, false);
        // retrieve current activity
        //Activity a = getActivity();

        // Retrieve the recycler_view floor 2
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        String[] exemple = {"Patate", "tomates", "pomme"};
        mAdapter = new MyAdapter(exemple);
        mRecyclerView.setAdapter(mAdapter);



        //View layout = inflater.inflate(R.layout.fragment_floor2, container, false);



        return layout;
    }
}
