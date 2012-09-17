package com.sunlitjiang.happyShow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class HappyShowActivity extends Activity {
    /** Called when the activity is first created. */
	private static final int DURATIONTIME = 3500;  //millisecond
	
	private ImageView showedImage;
	private AnimationDrawable frameAnimation;
	private Window win;
	private BroadcastReceiver phoneStateReciever;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED 
        			| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD); 
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON 
        			| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        showedImage = (ImageView) findViewById(R.id.imageView_showedPic);
        showedImage.setBackgroundResource(R.drawable.slides);
        // Get the background, which has been compiled to an AnimationDrawable object.
        frameAnimation = (AnimationDrawable) showedImage.getBackground();

        ExternalStorageMedia st = new ExternalStorageMedia( getString(R.string.app_name) );
	    String[] sNamelist = st.getPictureArray();
	    if (sNamelist != null) {
		    for (String sPic : sNamelist) {
		        frameAnimation.addFrame(Drawable.createFromPath(sPic), DURATIONTIME);
		    }
	    }
        
		Intent intentForStartService = new Intent(this, HappyShowService.class);
		startService(intentForStartService);
		
		registerPhoneReciever();
		
        Log.d("Activity", "onCreate");
    }
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus){
		super.onWindowFocusChanged (hasFocus);
		if(hasFocus) {
			frameAnimation.start();
			Log.d("Activity", "onFocusChanged hasFocus");
		}
		else {
			Log.d("Activity", "onFocusChanged lostFocus");
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        Log.d("Activity", "onTouchDown");
			this.finish();
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onStop() {
		if (phoneStateReciever != null) {
			unregisterReceiver(phoneStateReciever);
		}
	    super.onStop();  // Always call the superclass method first
	    this.finish();
	    Log.d("Activity", "onStop");
	}
	
	private void registerPhoneReciever() {
		phoneStateReciever = new BroadcastReceiver() {  
			public void onReceive(Context context, Intent intent) {
				HappyShowActivity.this.finish();
			} //onReceive
		};
		IntentFilter phoneStateIntentFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
		phoneStateIntentFilter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		registerReceiver(phoneStateReciever, phoneStateIntentFilter);
	}
	
}

