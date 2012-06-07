package com.sunlitjiang.happyShow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class HappyShowActivity extends Activity {
    /** Called when the activity is first created. */
	private ImageView showedImage;
	private AnimationDrawable frameAnimation;
	private PowerManager powerManager;
	private WakeLock wakeLock;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        showedImage = (ImageView) findViewById(R.id.imageView_showedPic);
        showedImage.setBackgroundResource(R.drawable.slides);
        // Get the background, which has been compiled to an AnimationDrawable object.
        frameAnimation = (AnimationDrawable) showedImage.getBackground();
        
		Intent intentForStartService = new Intent(this, HappyShowService.class);
		startService(intentForStartService);
		
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();
        //powerManager.userActivity(0, false);

        Log.d("Activity", "onCreate");
    }
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus){
		super.onWindowFocusChanged (hasFocus);
		frameAnimation.start();
        Log.d("Activity", "onFocusChanged");
	}
	
	@Override
	protected void onNewIntent (Intent intent) {
		wakeLock.acquire();
		Log.d("Activity", "onNewIntent");
	}
	
	@Override
	public void onPause() {
		super.onPause();  // Always call the superclass method first
        Log.d("Activity", "onPause");
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
	    super.onStop();  // Always call the superclass method first
	    wakeLock.release();
	    Log.d("Activity", "onStop");
	}

}

