package com.group2.smartfridge.smartfridge.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.group2.smartfridge.smartfridge.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DatabaseTestActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText quantityBox;
    EditText floorBox;
    Context context;
    EditText unityBox;
    byte[] imgByte;
    ImageView myImg;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


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
        myImg = (ImageView) findViewById(R.id.testImg);

        // Image d'exemple récupéré dans les drawable (à la place de récupération ds une box)
        Drawable mDrawable = ContextCompat.getDrawable(context, R.drawable.steak);
        Bitmap img = ((BitmapDrawable) mDrawable).getBitmap();
        // Then convert to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        imgByte = stream.toByteArray();


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
                        new Product(productBox.getText().toString(), quantity, floorBox.getText().toString(), unityBox.getText().toString(), imgByte);

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

                if (result) {
                    idView.setText("Record Deleted");
                    productBox.setText("");
                    quantityBox.setText("");
                    floorBox.setText("");
                } else
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

            myImg.setImageBitmap(BitmapFactory.decodeByteArray(product.get(0).getImg(),0,product.get(0).getImg().length));
            }


        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DatabaseTest Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.group2.smartfridge.smartfridge.database/http/host/path")
        );
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DatabaseTest Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.group2.smartfridge.smartfridge.database/http/host/path")
        );
        client.disconnect();
    }
}
