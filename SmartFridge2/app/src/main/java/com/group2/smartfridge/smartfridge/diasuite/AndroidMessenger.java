package com.group2.smartfridge.smartfridge.diasuite;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;

//import com.phoenix.inria.starterdiasuite.DialogActivity;
//import com.phoenix.inria.starterdiasuite.MainActivity;
//import com.phoenix.inria.starterdiasuite.utils.C;

import com.group2.smartfridge.smartfridge.FragmentFloor;
import com.group2.smartfridge.smartfridge.MainActivity;
import com.group2.smartfridge.smartfridge.R;

import fr.inria.phoenix.diasuitebox.remote.client.RemoteClient;
import fr.inria.phoenix.diasuitebox.remote.client.RemoteDevice;
import fr.inria.phoenix.diasuitebox.remote.client.RemoteDeviceCallback;

public class AndroidMessenger implements RemoteDeviceCallback {

    private final ServerConnexionService activity;
    private RemoteDevice messenger;
 

    public AndroidMessenger(RemoteClient client, ServerConnexionService service) {
        this.activity = service;
        RemoteDeviceInformationsMessenger messengerInfo = new RemoteDeviceInformationsMessenger();

        try {
            messenger = client.register(messengerInfo);
            messenger.addCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void handleAction(String action, Object... parameters) {
//        sendNotification(parameters);
        if (action.equals("sendMessage") || action.equals("sendMessageWithImage")) {
        	 Intent start = new Intent(activity, MainActivity.class);
             start.putExtra("location" ,(String) parameters[3]);
             activity.startActivity(start);
        }
    }

    public  boolean testConnection(RemoteClient client)
    {

        RemoteDeviceInformationsMessenger messengerInfo = new RemoteDeviceInformationsMessenger();
        try {
            messenger = client.register(messengerInfo);

            Log.e("TESTCONNECTION", "messenger " + messenger);
            Log.e("TESTCONNECTION", "messenger this " + this);

            messenger.addCallback(this);
            messenger.removeCallback(this);

            client.unregister(messenger);

            Log.e("ANDROIDLOGGER", "messenger testConnection client unregister");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


//
//    private void sendNotification(Object[] parameters) {
//        Intent start = new Intent(activity, MainActivity.class);
//        start.putExtra(C.EXTRA_NOTIF + C.TITLE, "New message!");
//        start.putExtra(C.EXTRA_NOTIF + C.CONTENT, (String) parameters[1]);
//        start.putExtra(C.EXTRA_NOTIF + C.ID, C.CANCEL_MSGR);
//        start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.startActivity(start);
//    }

    @Override
    public Object handleSource(String arg0, Object... arg1) {
        // pas de sources
        return null;
    }

    @Override
    public void idUpdated() {
        // Auto-generated method stub
    }
}
