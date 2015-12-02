package com.group2.smartfridge.smartfridge.diasuite;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import fr.inria.phoenix.diasuitebox.remote.client.RemoteClient;
import fr.inria.phoenix.diasuitebox.remote.client.impl.RemoteClientFactory;

public class ServerConnexionService extends Service {

	private final IBinder myBinder = new MyBinder();
	public RemoteClient client;
	private final ServerConnexionService service = this;
//	protected AndroidPrompter prompterDevice;
	public Service _this;

//	public fr.inria.phoenix.device.domassist.Views.MainActivity MainActivity;
	public static final String BROADCAST_ACTION = "fr.inria.phoenix.device.domassist.utils.notif";

	int counter = 0;

	private String loginInput = "", passwordInput = "";

	private PendingIntent pi, piRestart;
	private BroadcastReceiver br, brRestart;
	private AlarmManager am, amRestart;

	//AlarmManager for pausing
	final static private long ONE_SECOND = 1000;
	final static private long TEN_SECONDS = ONE_SECOND * 10;
	final static private long  ONE_HOUR = 1000 * 60*60;
	final static private long SIX_HOURS = ONE_HOUR * 6;
	final static private long ONE_DAY = ONE_HOUR * 24;

	private boolean wasConnected = false;

	public boolean IS_PAUSED = false;

	@Override
	public IBinder onBind(Intent arg0) {
		return myBinder;
	}


	@Override
	public void onCreate() {
		
		
		Log.e("ServerConnexionService ", "onCreate");
		
		
		brRestart = new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i) {

				//clearNotification();

				Log.e("ServerConnexionService ", "BroadcastReceiver onReceive "+IS_PAUSED);
				
				if(!IS_PAUSED)
					connectService();
		
				setUpAlarm();
			}
		};

		setUpAlarm();
	}
	
	public void cleanAlarm()
	{
		if(am != null &&  pi != null)
		{
			am.cancel(pi);
			am =null;
			pi = null;
		}
		if(br != null)
		{
			unregisterReceiver(br);
			br = null;
		}
	}

	public void cleanAlarmRestart()
	{
		if(amRestart != null &&  piRestart != null)
		{
			amRestart.cancel(piRestart);
			amRestart =null;
			piRestart = null;
		}
		if(brRestart != null)
		{
			unregisterReceiver(brRestart);
			brRestart = null;
		}

	}


	
	public void setUpAlarm()
	{
		registerReceiver(brRestart, new IntentFilter("fr.inria.phoenix.device.domassist.utils$Restart") );
		PendingIntent piRestart = PendingIntent.getBroadcast( this, 0, new Intent("fr.inria.phoenix.device.domassist.utils$Restart"),
				0 );
		amRestart = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

		long tmp = SystemClock.elapsedRealtime();

		long tmp_time =  (TEN_SECONDS *4);

		amRestart.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, tmp + tmp_time, piRestart );
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (client == null) {

                loginInput = "appart";
                passwordInput = "phoenixrocks";
//				loginInput = extras.getString(Configuration.EXTRA_LOGIN + Configuration.LOGIN);
//				passwordInput = extras.getString(Configuration.EXTRA_LOGIN + Configuration.PASSWORD);

				Log.e("ServerConnexionService ", "onStartCommand");
				//connectService();
			}
		}
		return Service.START_STICKY;
	}

	public class MyBinder extends Binder {
		public ServerConnexionService getService() {
			return ServerConnexionService.this;
		}
	}

//	public AndroidPrompter getPrompter() {
//		return prompterDevice;
//	}


	@Override
	public void onDestroy() {
		disconnectService();
		cleanAlarm();
		cleanAlarmRestart();
		Log.e("ServerConnexion","onDestroy");
		super.onDestroy();
	}


	public void disconnectService()
	{
		Log.e("ServerConnexion ","disconnectService");

		try {
			client.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            Log.e("ServerConnexion client.disconnect", "" + e);
		}

		this.client = null;

	}

	public void disconnectServiceTask()
	{
		DisconnectTask disco = new DisconnectTask();
		disco.execute();

	}

	public void connectService()
	{

		Log.e("ServerConnexion","connectService");
		this._this = this;

		InternetTestTask test = new InternetTestTask();
		test.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		
	}
	
	class InternetTestTask extends AsyncTask <String, Void, String> 
	{
		public boolean isConnected = false;

		protected String doInBackground(String... urls) {

			Log.e("ServerConnexion", "InternetTestTask doInBackground start");

			try {
				URL url = new URL("http://www.google.com");
				HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
				urlConnect.setConnectTimeout(2000);
				urlConnect.getContent();
				isConnected = true;
				Log.e("ServerConnexion", "PING  SUCCESS");

			} catch (NullPointerException np) {
				np.printStackTrace();
				isConnected = false;
				Log.e("ServerConnexion", "PING  FAIL");
			} catch (IOException io) {
				io.printStackTrace();
				isConnected = false;
				Log.e("ServerConnexion", "PING  FAIL");
			}

			return null;
		}

		private ConnexionTask _conne;
		protected void onPostExecute(String feed) {
			//refreshListPull();

			Log.e("ServerConnexion", "InternetTestTask onPostExecute "+isConnected+wasConnected);
			Log.e("ServerConnexion", "InternetTestTask onPostExecute ConnexionTask "+_conne);
			Log.e("ServerConnexion", "InternetTestTask onPostExecute client "+client);


			if(isConnected)
			{
				if(!wasConnected || client == null)
				{
					wasConnected = true;
					//connectService();
					if(_conne != null)
						_conne.cancel(true);
					_conne = new ConnexionTask();
					_conne.execute();
				}


			} else {
				Log.e("ServerConnexion", "onPostExecute NO INTERNET");

				if(wasConnected)
				{
					client = null;
					wasConnected = false;
				}
			}
		}
	}

	class DisconnectTask extends AsyncTask <String, Void, String> 
	{
		public boolean isConnected = false;

		protected String doInBackground(String... urls) {
			
			Log.e("ServerConnexion", "Disconnected Task DOINBACKGROUND");
			
			try {
				client.disconnect();
			} catch (Exception e) {
				client = null;
				Log.e("ServerConnexion client.disconnect",""+e);
			}
			//this.client = null;

			return null;
		}

		protected void onPostExecute(String feed) {
			//refreshListPull();

			Log.e("ServerConnexion", "Disconnected Task ONPOSTEXECUTE");
			client = null;
			wasConnected = false;
			
		}
	}
	
	class ConnexionTask extends AsyncTask <String, Void, String> 
	{
		public boolean isConnected = false;
		//public ServerConnexion _serverConnexion;

		protected String doInBackground(String... urls) {


			Log.e("ServerConnexion"," ConnexionTask doInBackground");
			//_serverConnexion = new ServerConnexion(passwordInput, loginInput, _this);
			try {
				client = RemoteClientFactory.getClient(loginInput, passwordInput);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("ServerConnexion","Fail getClient "+e.toString());
			}


			return null;
		}

		protected void onPostExecute(String feed) {

			Log.e("ServerConnexion","onPostExecute " + client);

			if (client != null) {
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						new AndroidMessenger(client, service);
//						prompterDevice = new AndroidPrompter(client, service);
					}
				}).start();
			}
			else {
				Log.e("ServerConnexion", "Can't connect client null");
			}
		}
	}

	



	private void sendIntent(Intent intent) {
		startActivity(intent);
	}


}
