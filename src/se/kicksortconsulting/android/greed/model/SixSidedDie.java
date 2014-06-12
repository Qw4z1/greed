package se.kicksortconsulting.android.greed.model;

import java.util.Random;

import android.util.Log;

public class SixSidedDie extends AbstractDie{
	
	private static final int MAX_VALUE = 6;
	private int mId;
	private Random mRandom = new Random();
	
	public SixSidedDie(int id) {
		mId = id;
		reset();
	}
	
	public int roll() {
		setIsUsed(false);
		setCurrentValue( mRandom.nextInt(MAX_VALUE) + 1);
		Log.d("TEST", "Value" + getCurrentValue());
		return getCurrentValue();
	}

	@Override
	public int getMaxValue() {
		return MAX_VALUE;
	}

}
