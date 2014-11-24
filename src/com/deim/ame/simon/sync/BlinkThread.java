package com.deim.ame.simon.sync;

import com.deim.ame.simon.utils.Util;

import android.widget.ImageView;

public class BlinkThread implements Runnable {
	private ImageView[] imgViews;
	private int[] sequence;
	private int delay;
	private int i;
	private boolean userMove;
	
	public BlinkThread(ImageView[] imgViews, int[] sequence, int delay, int i,boolean userMove) {
		super();
		this.imgViews = imgViews;
		this.sequence = sequence;
		this.delay = delay;
		this.i = i;
		this.userMove = userMove;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("BLINKING to ..." + sequence[i]);
			Util.blink(imgViews, sequence, delay, i,userMove);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
