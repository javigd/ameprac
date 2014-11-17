package com.deim.ame.simon.sync;

import com.deim.ame.simon.utils.Util;

import android.widget.ImageView;

public class BlinkThread implements Runnable {
	private ImageView[] imgViews;
	private int[] sequence;
	private int delay;
	private int i;
	
	public BlinkThread(ImageView[] imgViews, int[] sequence, int delay, int i) {
		super();
		this.imgViews = imgViews;
		this.sequence = sequence;
		this.delay = delay;
		this.i = i;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("BLINKING to ..." + sequence[i]);
			Util.blink(imgViews, sequence, delay, i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
