package com.deim.ame.simon.sync;

import java.util.List;

import com.deim.ame.simon.PlayActivity;
import com.deim.ame.simon.Config.Constants;
import com.deim.ame.simon.utils.Util;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

public class BlinkTask extends AsyncTask<List<Integer>, Integer, Integer> {
	private ImageView[] imgViews;
	private List<Integer> startupSeq;
	private int delay;
	private boolean userMove;
    private static MediaPlayer[] buttonSounds;
    private static MediaPlayer transitionSound;

	/**
	 * Make a sequence of ImageView blink for a specified time given, disabling
	 * touch listeners
	 * 
	 * @param imgViews
	 * @param delay
	 * @param userMove
	 */
	public BlinkTask(ImageView[] imgViews, int delay, boolean userMove, MediaPlayer[] buttonSounds, MediaPlayer transitionSound) {
		super();
		this.imgViews = imgViews;
		this.delay = delay;
		this.userMove = userMove;
		this.buttonSounds = buttonSounds;
		this.transitionSound = transitionSound;
		/* Get the transition animation if required */
		if(!userMove) {
			startupSeq = Util.getStartupBlink(imgViews);
		}
	}

	@Override
	protected Integer doInBackground(List<Integer>... params) {
		List<Integer> sequence = params[0];
		// Do the startup Transition if required
		if(!userMove) {
			doStartupTransition();
		}
		// Do the animation
		for (int i = 0; i < sequence.size(); i++) {
			int state = sequence.get(i);
			// Set the specified imageView as active
			publishProgress(state);
			// Send a delayed thread to the queue in order to change to the next
			// blink state
			if (i != sequence.size() - 1) {
				buttonSounds[state].start();
				// Sleep
				doSleep(delay);
				// Transition
				doTransition();
			}
		}

		return sequence.get(0);
	}
	
	protected void onProgressUpdate(Integer... progress) {
		setActive(imgViews, progress[0]);
	}

	protected void onPostExecute(Integer result) {
		/* Notify user move if required */
		if (userMove) {
			PlayActivity.addUserMove(result);
		}
		PlayActivity.enableTouch();
		PlayActivity.enableOnTouchListener();
	}

	/**
	 * Sleep for a given delay
	 * 
	 * @param delay
	 */
	private void doSleep(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Make a PLAIN transition
	 */
	private void doTransition() {
		publishProgress(Constants.PLAIN);
		doSleep(Constants.TRANSITION_DELAY);
	}
	
	/**
	 * Make a STARTUP transition
	 */
	private void doStartupTransition() {
		for (Integer state : startupSeq) {
			transitionSound.start();
			publishProgress(state);
			doSleep(Constants.STARTUP_BLINK_FREQ);
		}
	}

	/**
	 * Set a simon ImageView as "active" or visible given the state (color)
	 * 
	 * @param state
	 *            , according to the four simon colors, -1 to set it plain, 4 to
	 *            set all 4 active
	 */
	public synchronized static void setActive(ImageView[] imgViews, int state) {
		if (state != 5) {
			/* Clear the ImageViews state first */
			setActive(imgViews, 5);
			/* Bring the requested color to foreground */
			imgViews[state].setVisibility(View.VISIBLE);
		} else {
			/* Clear screen turning off all ImageViews except for the PLAIN one */
			imgViews[Constants.RED].setVisibility(View.INVISIBLE);
			imgViews[Constants.GREEN].setVisibility(View.INVISIBLE);
			imgViews[Constants.BLUE].setVisibility(View.INVISIBLE);
			imgViews[Constants.YELLOW].setVisibility(View.INVISIBLE);
			imgViews[Constants.LIGHT].setVisibility(View.INVISIBLE);
		}
	}

}
