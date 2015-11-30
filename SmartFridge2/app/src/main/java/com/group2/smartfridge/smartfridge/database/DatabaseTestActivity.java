package com.group2.smartfridge.smartfridge.database;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.group2.smartfridge.smartfridge.R;

import java.util.List;

public class DatabaseTestActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText quantityBox;
    EditText floorBox;
    Context context;
    EditText unityBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        quantityBox = (EditText) findViewById(R.id.productQuantity);
        floorBox = (EditText) findViewById(R.id.productFloor);
        unityBox = (EditText) findViewById(R.id.productUnity);

        Button button1 = (Button) findViewById(R.id.button);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);

        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
                dbHandler.incrementQuantity(productBox.getText().toString());
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
                float quantity =
                        Float.parseFloat(quantityBox.getText().toString());

                Product product =
                        new Product(productBox.getText().toString(), quantity, floorBox.getText().toString(), unityBox.getText().toString());

                dbHandler.addProduct(product);
                productBox.setText("");
                quantityBox.setText("");
                unityBox.setText("");
                floorBox.setText("");
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                MyDBHandler dbHandler = new MyDBHandler(context, null,
                        null, 1);

                boolean result = dbHandler.deleteProduct(
                        productBox.getText().toString());

                if (result)
                {
                    idView.setText("Record Deleted");
                    productBox.setText("");
                    quantityBox.setText("");
                    floorBox.setText("");
                }
                else
                    idView.setText("No Match Found");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);

                List<Product> product =
                        dbHandler.findProductByFloor(floorBox.getText().toString());

                Log.d("log_tag", "coucou " + product.get(0).getFloorName() + product.get(0).getProductName() + product.get(0).getQuantity() + product.get(0).getUnity());
            }
        });


    }



}
