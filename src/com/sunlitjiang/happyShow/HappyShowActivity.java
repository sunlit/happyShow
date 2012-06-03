package com.sunlitjiang.happyShow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class HappyShowActivity extends Activity {
    /** Called when the activity is first created. */
	private ImageView showedImage;
	private AnimationDrawable frameAnimation;

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

    }
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus){
		super.onWindowFocusChanged (hasFocus);
		frameAnimation.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			this.finish();
			return true;
		}
		return super.onTouchEvent(event);
	}
}

