package com.deim.ame.simon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deim.ame.simon.Config.Constants;
import com.deim.ame.simon.sync.BlinkTask;
import com.deim.ame.simon.utils.SimonOnTouchListener;
import com.deim.ame.simon.utils.Util;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class PlayActivity extends ActionBarActivity {

	private static SimonOnTouchListener simonOnTouchListener;
	private static ImageView[] imgViews;
	private static List<Integer> sequence;
	private static List<Integer> userSequence;
	private static int difficulty;
	private static int round;
	private static MediaPlayer[] buttonSounds;
	private static MediaPlayer transitionSound;
	private static boolean disabled;
	private static Context context;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		/* Get the main ImageViews */
		imgViews = this.getImageViews();

		/* Initialize sounds */
		buttonSounds = getButtonSounds();
		transitionSound = MediaPlayer.create(PlayActivity.this,
				R.raw.transition);

		/* Set the touch var as enabled */
		disabled = false;
		
		/* Save the context */
		context = this;

		/* Initialize the Listener */
		simonOnTouchListener = new SimonOnTouchListener(imgViews, buttonSounds,
				transitionSound);

		/* Set the onClick Listener over the Views area */
		enableOnTouchListener();

		// Get difficulty value from intent
		Intent intent = getIntent();
		difficulty = intent.getIntExtra("difficulty", 0);

		/* Start a new Game */
		newGame();
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
	 * 
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
		return new ImageView[] { simonRed, simonBlue, simonYellow, simonGreen,
				simonLight, simonPlain };
	}

	private MediaPlayer[] getButtonSounds() {
		MediaPlayer[] sounds = new MediaPlayer[Constants.TOTAL_COLORS];
		sounds[0] = MediaPlayer.create(PlayActivity.this, R.raw.touch0);
		sounds[1] = MediaPlayer.create(PlayActivity.this, R.raw.touch1);
		sounds[2] = MediaPlayer.create(PlayActivity.this, R.raw.touch2);
		sounds[3] = MediaPlayer.create(PlayActivity.this, R.raw.touch3);
		return sounds;
	}

	/**
	 * Set the OnTouchListener to the defined SimonOnTouchListener
	 * 
	 * @param imgViews
	 */
	public static void enableOnTouchListener() {
		simonOnTouchListener.setEnabled();
		enableTouch();
		imgViews[Constants.PLAIN].setOnTouchListener(simonOnTouchListener);
	}

	/**
	 * Disable the OnTouchListener on the given View
	 * 
	 * @param imgView
	 */
	public static void disableOnTouchListener() {
		System.out.println("disabling listener...");
		disableTouch();
		simonOnTouchListener.setDisabled();
	}

	/***************** GAME LOGIC *****************/

	/**
	 * Initialize a new game.
	 */
	private static void newGame() {
		// Initialize game variables
		round = 1;
		sequence = new ArrayList<Integer>();
		// Initialize a new round
		newRound();
	}

	/**
	 * Initialize a new round.
	 */
	private static void newRound() {
		if (round >= Constants.MAX_ROUNDS) {
			finishGame(0);
		}
		userSequence = new ArrayList<Integer>();
		showSequence();
		round++;
	}

	/**
	 * Add a random color to the show sequence
	 * 
	 * @param round
	 */
	private static void addColorToSequence() {
		Random random = new Random();
		sequence.add((Integer) Math.abs((random.nextInt() % 4)));
	}

	/**
	 * Show the sequence to the user, disabling all touch listeners
	 * 
	 * @param round
	 * @param imgViews
	 * @param difficulty
	 */
	private static void showSequence() {
		// Disable all touch listeners
		disableOnTouchListener();
		disableTouch();
		// Add a new color to the show sequence
		addColorToSequence();
		// Generates the show sequence with a PLAIN color on the last position
		ArrayList<Integer> showSeq = new ArrayList<Integer>();
		showSeq.addAll(sequence);
		showSeq.add(Constants.PLAIN);
		// Initialize and execute the blink async task
		BlinkTask blink = new BlinkTask(imgViews, Constants.DELAY[difficulty],
				false, buttonSounds, transitionSound);
		blink.execute(showSeq);
	}

	/**
	 * Add a user move to the user input sequence
	 * 
	 * @param move
	 */
	public static void addUserMove(int move) {
		userSequence.add(move);
		if (!Util.validMove(userSequence, sequence)) {
			finishGame(round);
		} else if (userSequence.size() >= sequence.size()) {
			newRound();
		}
	}
	
	public static void finishGame(int levelReached) {
	    Intent intent = new Intent(context, ResultActivity.class);
	    intent.putExtra("difficulty", difficulty);
	    intent.putExtra("level", levelReached);
	    context.startActivity(intent);
	}

	public static boolean isDisabled() {
		return disabled;
	}

	public static void enableTouch() {
		disabled = false;
	}

	public static void disableTouch() {
		disabled = true;
	}
}
