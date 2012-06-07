package com.sunlitjiang.happyShow;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class HappyShowService extends Service {
	private BroadcastReceiver screenoffReciever = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// We don't provide binding, so return null
		return null;
	}
	
	@Override
    public void onCreate() {

		screenoffReciever = new BroadcastReceiver(){  
			public void onReceive(Context context, Intent intent) {
					try {  
						Intent intentToActivity = new Intent();  
						intentToActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
						intentToActivity.setClass(context, HappyShowActivity.class);  
						context.startActivity(intentToActivity);
						Log.d("screenoffReciever", "startActivity");
					} catch (Exception e) {  
						Log.i("Exception in HappyShowService:", e.toString());  
					}  
			} //onReceive
		};
		registerReceiver(screenoffReciever, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Want this service to continue running until it is explicitly stopped, 
		// so return sticky.
	    return START_STICKY;
	}
}
