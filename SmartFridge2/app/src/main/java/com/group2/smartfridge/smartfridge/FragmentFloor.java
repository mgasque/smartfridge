package com.group2.smartfridge.smartfridge;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_floor, container, false);
        // retrieve current activity
        //Activity a = getActivity();
        final String floorName = getArguments().getString("floorName");
        Log.d("log_tag", floorName);

        final MyDBHandler dbHandler = new MyDBHandler(getActivity(), null, null, 1);


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
        mAdapter = new MyAdapter(dbHandler.findProductByFloor(floorName));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.refresh(dbHandler.findProductByFloor(floorName));

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.details_product);
                dialog.setTitle(dbHandler.findProductByFloor(floorName).get(position).getProductName());
                dialog.show();


                final TextView unitText = (TextView) dialog.findViewById(R.id.unitText);
                unitText.setText(dbHandler.findProductByFloor(floorName).get(position).getUnity());


                final ImageButton unitSwitch = (ImageButton) dialog.findViewById(R.id.unitSwitch);
                unitSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (unitText.getText().toString()) {
                            case "Kg":
                                unitText.setText("U");
                                dbHandler.setUnityInDB(dbHandler.findProductByFloor(floorName).get(position).getProductName(), "U");
                                break;
                            case "U":
                                unitText.setText("L");
                                dbHandler.setUnityInDB(dbHandler.findProductByFloor(floorName).get(position).getProductName(), "L");
                                break;
                            case "L":
                                unitText.setText("%");
                                dbHandler.setUnityInDB(dbHandler.findProductByFloor(floorName).get(position).getProductName(), "%");
                                break;
                            case "%":
                                unitText.setText("Kg");
                                dbHandler.setUnityInDB(dbHandler.findProductByFloor(floorName).get(position).getProductName(), "Kg");
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
                            dbHandler.incrementQuantity(dbHandler.findProductByFloor(floorName).get(position).getProductName());
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
                            dbHandler.decrementQuantity(dbHandler.findProductByFloor(floorName).get(position).getProductName());
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
                        EditText valueText = (EditText) dialog.findViewById(R.id.valueText);
//                        Log.d("coucou","" + valueText.getText().toString());
                        dbHandler.setQuantityInDB(dbHandler.findProductByFloor(floorName).get(position).getProductName(), Float.parseFloat(valueText.getText().toString()));
                        dialog.dismiss();
                        mAdapter.refresh(dbHandler.findProductByFloor(floorName));
                    }
                });

                EditText valueText = (EditText) dialog.findViewById(R.id.valueText);
                valueText.setText("" + dbHandler.findProductByFloor(floorName).get(position).getQuantity());

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mAdapter.refresh(dbHandler.findProductByFloor(floorName));

                    }
                });


            }

            @Override
            public void onLongClick(View view, int position) {


            }

        }));


        return layout;
    }
}
