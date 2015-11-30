package com.group2.smartfridge.smartfridge;

import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group2.smartfridge.smartfridge.database.MyDBHandler;
import com.group2.smartfridge.smartfridge.database.Product;

import java.util.List;

/**
 * Created by Aude on 29/11/2015.
 */
public class FragmentFloor extends Fragment {

    // Creation attribute for RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_floor, container, false);
        // retrieve current activity
        //Activity a = getActivity();
        String floorName = getArguments().getString("floorName");
        Log.d("log_tag", floorName);

        MyDBHandler dbHandler = new MyDBHandler(getActivity(), null, null, 1);

        List<Product> product =
                    dbHandler.findProductByFloor(floorName);
        // Retrieve the recycler_view floor 2
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        // specify an adapter
        mAdapter = new MyAdapter(product);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.details_product);
                dialog.setTitle("Pommes");
                dialog.show();

                ImageButton unitSwitch = (ImageButton) dialog.findViewById(R.id.unitSwitch);
                unitSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView unitText = (TextView) dialog.findViewById(R.id.unitText);
                        switch (unitText.getText().toString()) {
                            case "Kg":
                                unitText.setText("U");
                                break;
                            case "U":
                                unitText.setText("L");
                                break;
                            case "L":
                                unitText.setText("%");
                                break;
                            case "%":
                                unitText.setText("Kg");
                                break;
                            default:
                                unitText.setText("Kg");
                                break;
                        }
                    }
                });

                ImageButton plus = (ImageButton) dialog.findViewById(R.id.plusButton);
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText valueText = (EditText) dialog.findViewById(R.id.valueText);
                        float value = Float.parseFloat(valueText.getText().toString());
                        if (value < 99) {
                            value++;
                            valueText.setText(Float.toString(value));
                        } else {
                            Snackbar.make(v, "Vous ne pouvez pas avoir plus de 100 éléments", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });

                ImageButton moins = (ImageButton) dialog.findViewById(R.id.lessButton);
                moins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText valueText = (EditText) dialog.findViewById(R.id.valueText);
                        float value = Float.parseFloat(valueText.getText().toString());
                        if (value > 0) {
                            value--;
                            valueText.setText(Float.toString(value));
                        } else {
                            Snackbar.make(v, "Vous ne pouvez pas avoir une quantité négative d'un aliment", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });

                ImageButton okButton = (ImageButton) dialog.findViewById(R.id.okButton);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {


            }

        }));
//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });

        //View layout = inflater.inflate(R.layout.fragment_floor, container, false);



        return layout;
    }
}
