package com.deim.ame.simon;

import com.deim.ame.simon.Config.Constants;
import com.deim.ame.simon.utils.SimonOnTouchListener;
import com.deim.ame.simon.utils.Util;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class PlayActivity extends ActionBarActivity {
	
	private static OnTouchListener simonOnTouchListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		/* Get the main ImageViews */
		final ImageView[] imgViews = this.getImageViews();
		
		/* Show the startup motion */
		Util.startupBlink(imgViews);
		
		/* Initialize the Listener */
		simonOnTouchListener = new SimonOnTouchListener(imgViews);
		
		/* Set the onClick Listener over the Views area */
		PlayActivity.enableOnTouchListener(imgViews);
		
		Intent intent = getIntent();
		 
        // 2. get message value from intent
        int difficulty = Integer.parseInt(intent.getStringExtra("difficulty"));
        System.out.println("PlayActivityDifficulty: "+difficulty);
		
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
	
	
	/***************** AUXILIARY METHODS *****************/
	
	/**
	 * Get the main Simon game ImageViews defined
	 * @return
	 */
	private ImageView[] getImageViews() {
		
		/* Get the main ImageView objects */
		final ImageView simonPlain = (ImageView) findViewById(R.id.simonPlainImg);
		final ImageView simonRed = (ImageView) findViewById(R.id.simonRedImg);
		final ImageView simonBlue = (ImageView) findViewById(R.id.simonBlueImg);
		final ImageView simonGreen = (ImageView) findViewById(R.id.simonGreenImg);
		final ImageView simonYellow = (ImageView) findViewById(R.id.simonYellowImg);
		final ImageView simonLight = (ImageView) findViewById(R.id.simonLightImg);
		
		/* Set an array of available ImageViews */
		return new ImageView[] {simonRed, simonBlue, simonYellow, simonGreen, simonLight, simonPlain };
	}
	
	/**
	 * Set the OnTouchListener to the defined SimonOnTouchListener
	 * @param imgViews
	 */
	public static void enableOnTouchListener(ImageView[] imgViews) {
		imgViews[Constants.PLAIN].setOnTouchListener(simonOnTouchListener);
	}
	
	/**
	 * Disable the OnTouchListener on the given View
	 * @param imgView
	 */
	public static void disableOnTouchListener(ImageView imgView) {
		imgView.setOnTouchListener(null);
	}
	
}
