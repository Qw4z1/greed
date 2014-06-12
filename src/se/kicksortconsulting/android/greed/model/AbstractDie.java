package se.kicksortconsulting.android.greed.model;

import java.util.Random;

public abstract class AbstractDie implements IDice, Comparable<AbstractDie> {

	private boolean mIsUsed;
	private boolean mIsSaved;
	private int mCurrentValue;
	
	@Override
	public abstract int roll();
	
	public abstract int getMaxValue();

	public void setIsUsed(boolean isUsed) {
		mIsUsed = isUsed;
	}

	public boolean isSaved() {
		return mIsSaved;
	}
	
	public void setIsSaved(boolean isSaved) {
		mIsSaved = isSaved;
	}
	
	public boolean isUsed() {
		return mIsUsed;
	}

	protected void setCurrentValue(int value) {
		mCurrentValue = value;
	}
	public void reset() {
		mCurrentValue = 0;
		mIsUsed = false;
		mIsSaved = false;
	}

	@Override
	public int getCurrentValue() {
		return mCurrentValue;
	}

	@Override
	public int compareTo(AbstractDie another) {
		return this.getCurrentValue() - another.getCurrentValue();
	}

}
