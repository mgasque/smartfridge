package com.group2.smartfridge.smartfridge;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.group2.smartfridge.smartfridge.database.MyDBHandler;

import com.group2.smartfridge.smartfridge.diasuite.ServerConnexionService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Service
    private  ServerConnexionService connService;

    boolean mBound = false;

    private  final ServiceConnection conn = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            connService = ((ServerConnexionService.MyBinder) service).getService();
            mBound = true;
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {
            // connService = null;
            Log.d("", "");
            mBound = false;
        }
    };

    public ServerConnexionService getServiceConnexion() {
        return connService;
    }

    public void connectionToDiasuite()
    {
        Intent serviceIntent = new Intent(this, ServerConnexionService.class);
        startService(serviceIntent);
        bindService(new Intent(this, ServerConnexionService.class), conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("floorName", "floor2");

        fragment = new FragmentFloor();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();

        connectionToDiasuite();
        Bundle bundleDiasuite = getIntent().getExtras();
        changeActivity(parseDia(bundleDiasuite.getString("location")));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("SmartFridge");

            // set dialog message
            alertDialogBuilder
                    .setMessage("This will quit the application. Are you sure ? ")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_course) {

            MyDBHandler myHandler = new MyDBHandler(this, null, null, 1);
            myHandler.fillDb(this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.nav_floor2) {

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "floor2");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        } else if (id == R.id.nav_floor1) {

//            Intent intent = new Intent(this, DatabaseTestActivity.class);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "floor1");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        } else if (id == R.id.nav_vegetable) {

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "vegetable");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        }else if (id == R.id.nav_door) {

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "door");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onDestroy()
    {
        super.onDestroy();
        if (mBound) {
            unbindService(conn);
            mBound = false;
        }
    }


    public void changeActivity(String location) {

        Fragment fragment;


        if (location.equals("floor2")) {

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "floor2");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        } else if (location.equals("floor1")) {

//            Intent intent = new Intent(this, DatabaseTestActivity.class);
//            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "floor1");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        } else if (location.equals("vegetable")) {

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "vegetable");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        } else if (location.equals("door")) {

            Bundle bundle = new Bundle();
            bundle.putString("floorName", "door");

            fragment = new FragmentFloor();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
    }

    String parseDia(String msg){
        switch (msg) {
            case "floor_1":
                msg = "floor1";
                break;
            case "floor_2":
                msg = "floor2";
                break;
            case "door_":
                msg = "door";
                break;
            case "vegetable_":
                msg = "vegetable";
                break;
        }
        return msg;
    }

}

