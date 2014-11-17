package com.deim.ame.simon.utils;

import com.deim.ame.simon.Config.Constants;
import com.deim.ame.simon.sync.BlinkThread;
import com.deim.ame.simon.*;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class Util {
	static Handler handler = new Handler();
	
	/**************AUXILIARY METHODS**************/
	
	/**
	 * Set a simon ImageView as "active" or visible passing the state (color) as parameter
	 * @param state, according to the four simon colors, -1 to set it plain, 4 to set all 4 active
	 */
	public static void setActive (ImageView[] imgViews, int state) {
		if(state != 5) {
			setActive(imgViews, 5);
			imgViews[state].setVisibility(View.VISIBLE);
		} else {
			imgViews[Constants.RED].setVisibility(View.INVISIBLE);
			imgViews[Constants.GREEN].setVisibility(View.INVISIBLE);
			imgViews[Constants.BLUE].setVisibility(View.INVISIBLE);
			imgViews[Constants.YELLOW].setVisibility(View.INVISIBLE);
			imgViews[Constants.LIGHT].setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * Make a sequence of ImageView blink for a specified time given, disabling touch listeners
	 * @param imgViews
	 * @param state
	 * @param delay
	 */
	public synchronized static void blink(final ImageView[] imgViews, final int[] sequence, final int delay, final int iter) {
		// Set the specified imageView as active
	    setActive(imgViews, sequence[iter]);
	    final int i = iter + 1;
	    //System.out.println("Sending to blink.." + sequence[iter]);
	    // Send a delayed thread to the queue in order to change to the next blink state
	    if (i < sequence.length) {
	    	handler.postDelayed(new BlinkThread(imgViews, sequence, delay, i), delay);
	    } else {
	    	PlayActivity.enableOnTouchListener(imgViews);
	    }
	}
	
	/**
	 * Make the LIGHT ImageView blink on startup
	 * @param imgViews
	 */
	public synchronized static void startupBlink(final ImageView[] imgViews) {
		int[] sequence = new int[2 * Constants.STARTUP_BLINKS];
		// Initialize the startup sequence
		for (int i = 0; i < 2 * Constants.STARTUP_BLINKS; i += 2) {
			sequence[i] = Constants.LIGHT;
			sequence[i+1] = Constants.PLAIN;
		}
		// Blink
		blink(imgViews, sequence, Constants.STARTUP_BLINK_FREQ, 0);
	}
	
	/**
	 * Check if the given sequence introduced by the user matches the one specified for the level
	 * @param inputSequence
	 * @param sequence
	 * @return
	 */
	public boolean validMove(int[] inputSequence, int[] sequence) {
		for (int i = 0; i < inputSequence.length; i++) {
			if (inputSequence[i] != sequence[i]) {
				return false;
			}
		}
		return true;
	}
	
}
