package com.deim.ame.simon.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.deim.ame.simon.PlayActivity;
import com.deim.ame.simon.Config.Constants;
import com.deim.ame.simon.sync.BlinkTask;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class SimonOnTouchListener implements OnTouchListener {
	private ImageView simonPlain;
	private ImageView[] imgViews;
	private boolean disabled;
	private MediaPlayer[] buttonSounds;
	private MediaPlayer transitionSound;
	
	public SimonOnTouchListener(ImageView[] imgViews, MediaPlayer[] buttonSounds, MediaPlayer transitionSound) {
		super();
		this.imgViews = imgViews;
		this.simonPlain = imgViews[Constants.PLAIN];
		this.disabled = false;
		this.buttonSounds = buttonSounds;
		this.transitionSound = transitionSound;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// Do nothing if disabled
		if(PlayActivity.isDisabled()) {
			return true;
		}
		/* Get the touch point on X coords */
		int touch_x = (int) event.getX();
		/* Get the touch point on Y coords */
		int touch_y = (int) event.getY();
		/* Get image location on screen */
		int[] location = new int[2];
		simonPlain.getLocationOnScreen(location);
		location[0] += simonPlain.getWidth() / 4;
		location[1] -= simonPlain.getWidth() / 4;
		/* Compute its angle */
		float angle = (float) Math.toDegrees(Math.atan2(touch_x - location[0],
				touch_y - location[1]));
		/* Get a positive 0-360 representation of the angle */
		angle += 180;
		// if touch in range (inside the circle)
		if (Math.sqrt(Math.pow(touch_x - location[0], 2)
				+ Math.pow(touch_y - location[1], 2)) < (simonPlain.getWidth() / 2)) {
			/* Set the proper ImageView as visible */
			if (angle >= 0 && angle < 90) {
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
	 * Make the ImageView blink to the given state (color), disabling the touch
	 * listener during the motion
	 * 
	 * @param state
	 */
	private void doBlink(int state) {
		// Disable all touch listeners
		// Generate the touch sequence according to the given state
		List<Integer> touchSeq = new ArrayList<Integer>(Arrays.asList(state,
				Constants.PLAIN));
		// Initialize the blink async task
		BlinkTask blink = new BlinkTask(imgViews, Constants.DEFAULT_BLINK_FREQ, true, buttonSounds, transitionSound);
		
		blink.execute(touchSeq);
	}
	
	public void setDisabled() {
		this.disabled = true;
	}
	
	public void setEnabled() {
		this.disabled = false;
	}
}
