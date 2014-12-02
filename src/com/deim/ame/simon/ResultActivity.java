package com.deim.ame.simon;

import com.deim.ame.simon.Config.Constants;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends ActionBarActivity {
	private static int difficulty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		// Get difficulty value from intent
		Intent intent = getIntent();
		difficulty = intent.getIntExtra("difficulty", 0);
		
		// Get the level reached
		int level = intent.getIntExtra("level", 0);
		
		// Show feedback to user
		final TextView resultTxt = (TextView) findViewById(R.id.resultTxt);
		/* Maximum level reached */
		if (level == 0) {
			resultTxt.setText(Constants.LEVEL_MAX);
		} else {
			resultTxt.setText(Constants.LEVEL_REACHED + level);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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
	
	public void newGame(View view) {
		/* Start a new Game */
	    Intent intent = new Intent(this, PlayActivity.class);
	    intent.putExtra("difficulty", difficulty);
	    startActivity(intent);
	    
	}

}
