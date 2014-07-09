package se.kicksortconsulting.android.greed.model;

/**
 * Abstract class representing a dice with an arbitrarily chosen max value
 * @author qw4z1
 *
 */
public abstract class AbstractDice implements IDice, Comparable<AbstractDice> {

	private boolean mIsUsed;
	private boolean mIsSaved;
	private int mCurrentValue;
	
	@Override
	public abstract int roll();
	
	/**
	 * Returns the max value of the dice eg the side count.
	 * @return the side count
	 */
	public abstract int getMaxValue();

	public void setUsed(boolean isUsed) {
		mIsUsed = isUsed;
	}

	/**
	 * Check dice has been saved in the current turn
	 */
	public boolean isSaved() {
		return mIsSaved;
	}
	
	public void setIsSaved(boolean isSaved) {
		mIsSaved = isSaved;
	}
	
	/**
	 * Checks if the dice has been used in the current turn
	 */
	public boolean isUsed() {
		return mIsUsed;
	}

	/**
	 * Set the current value of the dice. If value is greater then the current max value, then max value will be set instead.
	 * @param int value the value that the dice should be set to
	 */
	protected void setCurrentValue(int value) {
		if(value > getMaxValue()) {
			mCurrentValue = getMaxValue();
		} else {
			mCurrentValue = value;
		}
	}
	
	/**
	 * Reset the dice
	 */
	public void reset() {
		//mCurrentValue = 0;
		mIsUsed = false;
		mIsSaved = false;
	}

	@Override
	public int getCurrentValue() {
		return mCurrentValue;
	}

	@Override
	public int compareTo(AbstractDice another) {
		return this.getCurrentValue() - another.getCurrentValue();
	}

}
