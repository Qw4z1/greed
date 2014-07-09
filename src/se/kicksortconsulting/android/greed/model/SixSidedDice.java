package se.kicksortconsulting.android.greed.model;

import java.util.Random;


/**
 * Class representing a six sided dice
 * 
 * @author qw4z1
 *
 */
public class SixSidedDice extends AbstractDice{
	private static final int MAX_VALUE = 6;
	private final int mId;
	private Random mRandom = new Random();
	
	/**
	 * Creates a dice with default values
	 * @param id id of the dice.
	 */
	public SixSidedDice(int id) {
		mId = id;
		setCurrentValue(id +1);
	}
	
	@Override
	public int roll() {
		setUsed(false);
		setCurrentValue( mRandom.nextInt(MAX_VALUE) + 1);
		return getCurrentValue();
	}

	@Override
	public int getMaxValue() {
		return MAX_VALUE;
	}
	
	public int getId() {
		return mId;
	}

}
