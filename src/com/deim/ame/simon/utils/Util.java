package com.deim.ame.simon.utils;

import java.util.ArrayList;
import java.util.List;

import com.deim.ame.simon.Config.Constants;
import com.deim.ame.simon.sync.BlinkTask;
import com.deim.ame.simon.*;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class Util {
	static Handler handler = new Handler();

	/************** AUXILIARY METHODS **************/

	/**
	 * Make the LIGHT ImageView blink on startup
	 * 
	 * @param imgViews
	 */
	public synchronized static List<Integer> getStartupBlink(final ImageView[] imgViews) {
		List<Integer> sequence = new ArrayList<Integer>(
				2 * Constants.STARTUP_BLINKS);
		// Initialize the startup sequence
		for (int i = 0; i < Constants.STARTUP_BLINKS; i++) {
			sequence.add(Constants.LIGHT);
			sequence.add(Constants.PLAIN);
		}
		
		return sequence;
	}

	/**
	 * Check if the given sequence introduced by the user matches the one
	 * specified for the level
	 * 
	 * @param inputSequence
	 * @param sequence
	 * @return
	 */
	public static boolean validMove(List<Integer> inputSequence,
			List<Integer> sequence) {
		for (int i = 0; i < inputSequence.size(); i++) {
			if (inputSequence.get(i) != sequence.get(i)) {
				return false;
			}
		}
		return true;
	}

}
