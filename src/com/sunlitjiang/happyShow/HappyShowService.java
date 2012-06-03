package com.sunlitjiang.happyShow;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class HappyShowService extends Service {
    KeyguardManager mKeyguardManager = null;  
    private KeyguardLock mKeyguardLock = null;  
	private BroadcastReceiver screenoffReciever = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override  
	public void onStart(Intent intent, int startId) {
		mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);  
		mKeyguardLock = mKeyguardManager.newKeyguardLock("");  
		mKeyguardLock.disableKeyguard();

		screenoffReciever = new BroadcastReceiver(){  
			public void onReceive(Context context, Intent intent) {
					try {  
						Intent intentToActivity = new Intent();  
						intentToActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
						intentToActivity.setClass(context, HappyShowActivity.class);  
						context.startActivity(intentToActivity);  
					} catch (Exception e) {  
						Log.i("Exception in HappyShowService:", e.toString());  
					}  
			} //onReceive
		};
		registerReceiver(screenoffReciever, new IntentFilter(Intent.ACTION_SCREEN_OFF));
	}
}
