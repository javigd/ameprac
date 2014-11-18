package com.deim.ame.simon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends ActionBarActivity {
	int difficulty = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final RadioGroup difficultyRadioGroup = (RadioGroup)findViewById(R.id.difficultyGroup);
	    
	    difficultyRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                
                    case R.id.radioButtonEasy:
                    	difficulty = 0;
                    break;

                    case R.id.radioButtonMedium:
                    	difficulty = 1;
                    break;

                    case R.id.radioButtonHard:
                    	difficulty = 2;
                    break;
                }
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void newPlay(View view) {
		
	    System.out.println("Difficulty: "+difficulty);
	    Intent intent = new Intent(this, PlayActivity.class);
	    startActivity(intent);
	}
}
