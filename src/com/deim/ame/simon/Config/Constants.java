package com.deim.ame.simon.Config;

public class Constants {
	
	/* Miscellaneous Constants */
	public static final int STARTUP_BLINK_FREQ = 100;
	public static final int STARTUP_BLINKS = 3;
	public static final int BLINK_FREQ_EASY = 2000;
	public static final int BLINK_FREQ_MEDIUM = 1000;
	public static final int BLINK_FREQ_HARD = 200;
	public static final int DEFAULT_BLINK_FREQ = BLINK_FREQ_HARD;
	public static final int TOTAL_COLORS = 4;
	public static final int MAX_ROUNDS = 10;
	public static final int TRANSITION_DELAY = 50;
	public static final int[] DELAY = {Constants.BLINK_FREQ_EASY, Constants.BLINK_FREQ_MEDIUM, Constants.BLINK_FREQ_HARD };

	
	/* ImageView Constants */
	public static final int RED = 0;
	public static final int BLUE = 1;
	public static final int YELLOW = 2;
	public static final int GREEN = 3;
	public static final int LIGHT = 4;
	public static final int PLAIN = 5;

	/* Messages */
	public static final String LEVEL_REACHED = "You reached level: ";
	public static final String LEVEL_MAX = "You reached the maximum level!";
	
}
