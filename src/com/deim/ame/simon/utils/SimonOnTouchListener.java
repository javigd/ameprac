package com.deim.ame.simon.utils;
import com.deim.ame.simon.PlayActivity;
import com.deim.ame.simon.Config.Constants;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class SimonOnTouchListener implements OnTouchListener {
	ImageView[] imgViews;
	ImageView simonPlain;
	
	public SimonOnTouchListener(ImageView[] imgViews) {
		super();
		this.imgViews = imgViews;
		this.simonPlain = imgViews[Constants.PLAIN];
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		/* Get the touch point on X coords */
		int touch_x = (int) event.getX();
		System.out.println("x:" + touch_x);
		/* Get the touch point on Y coords */
		int touch_y = (int) event.getY();
		System.out.println("y:" + touch_y);
		/* Get image location on screen */
		int[] location = new int[2];
		simonPlain.getLocationOnScreen(location);
		location[0] += simonPlain.getWidth() / 4;
		location[1] -= simonPlain.getWidth() / 4;
		/* Compute its angle */
		float angle = (float) Math.toDegrees(Math.atan2(touch_x - location[0], touch_y - location[1]));
		/* Get a positive 0-360 representation of the angle */
		angle += 180;
		System.out.println("angle:" + angle);
		// if touch in range (inside the circle)
		if(Math.sqrt(Math.pow(touch_x - location[0], 2) + Math.pow(touch_y- location[1], 2)) < (simonPlain.getWidth() / 2)) {
			/* Set the proper ImageView as visible */
	        if(angle >= 0 && angle < 90) {
	        	doBlink(Constants.GREEN);
	        } else if (angle >= 90 && angle < 180) {
	        	doBlink(Constants.YELLOW);
	        } else if (angle >= 180 && angle < 270) {
	        	doBlink(Constants.BLUE);
	        } else {
	        	doBlink(Constants.RED);
	        }
		}
        
        return false;
	}
	
	/**
	 * Make the ImageView blink to the given state (color), disabling the touch listener during the motion
	 * @param state
	 */
	private void doBlink(int state) {
		PlayActivity.disableOnTouchListener(imgViews[Constants.PLAIN]);
		int[] touchSeq = {state, Constants.PLAIN};
		Util.blink(imgViews, touchSeq, Constants.DEFAULT_BLINK_FREQ, 0,true);
	}

}
