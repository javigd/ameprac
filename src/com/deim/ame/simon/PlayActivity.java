package com.deim.ame.simon;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class PlayActivity extends ActionBarActivity {
	private Handler mHandler = new Handler();
	private static final int RED = 0;
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int GREEN = 3;
	private static final int LIGHT = 4;
	private static final int PLAIN = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		/* Instatiate the main ImageViews */
		final ImageView simonPlain = (ImageView) findViewById(R.id.simonPlainImg);
		final ImageView simonRed = (ImageView) findViewById(R.id.simonRedImg);
		final ImageView simonBlue = (ImageView) findViewById(R.id.simonBlueImg);
		final ImageView simonGreen = (ImageView) findViewById(R.id.simonGreenImg);
		final ImageView simonYellow = (ImageView) findViewById(R.id.simonYellowImg);
		final ImageView simonLight = (ImageView) findViewById(R.id.simonLightImg);
		
		/* Set an array of available ImageViews */
		final ImageView[] imgViews = {simonRed, simonBlue, simonYellow, simonGreen, simonLight, simonPlain };
		
		//TODO: add sequence as an int array parameter below
		//showSequence(imgViews);
		setActive(imgViews, LIGHT);
		
		/* Set the onClick Listener */
		simonPlain.setOnTouchListener(new OnTouchListener() {
			
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
				/* Set the proper ImageView as visible */
		        if(angle >= 0 && angle < 90) {
		            setActive(imgViews, GREEN);
		        } else if (angle >= 90 && angle < 180) {
		        	setActive(imgViews, YELLOW);
		        } else if (angle >= 180 && angle < 270) {
		        	setActive(imgViews, BLUE);
		        } else {
		        	setActive(imgViews, RED);
		        }
		        
		        return false;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**************PRIVATE METHODS**************/
	
	/**
	 * Set a simon ImageView as "active" or visible passing the state (color) as parameter
	 * @param state, according to the four simon colors, -1 to set it plain, 4 to set all 4 active
	 */
	private void setActive (ImageView[] imgViews, int state) {
		if(state != 5) {
			setActive(imgViews, 5);
			imgViews[state].setVisibility(View.VISIBLE);
		} else {
			imgViews[RED].setVisibility(View.INVISIBLE);
			imgViews[GREEN].setVisibility(View.INVISIBLE);
			imgViews[BLUE].setVisibility(View.INVISIBLE);
			imgViews[YELLOW].setVisibility(View.INVISIBLE);
		}
	}

}
