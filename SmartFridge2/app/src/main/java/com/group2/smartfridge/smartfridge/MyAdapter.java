package com.group2.smartfridge.smartfridge;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group2.smartfridge.smartfridge.database.Product;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by philippediep on 29/11/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<Product> mList = Collections.emptyList();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView productName;
        public TextView unity;
        public TextView quantityValue;
        public ImageButton imgProduct;

        public ViewHolder(View v) {
            super(v);
            productName = (TextView) v.findViewById(R.id.productName);
            unity = (TextView) v.findViewById(R.id.unity);
            quantityValue = (TextView) v.findViewById(R.id.quantityValue);
            imgProduct = (ImageButton) v.findViewById(R.id.imgProduct);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Product> list) {
        mList = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Product product = mList.get(position);
        Log.d("product", product.getProductName());

        holder.productName.setText(product.getProductName());
        holder.unity.setText(product.getUnity());
        holder.quantityValue.setText(("" + product.getQuantity()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void refresh(List<Product> list){
        mList = list;
        notifyDataSetChanged();
    }
}
