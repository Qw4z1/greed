package se.kicksortconsulting.android.greed.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.kicksortconsulting.android.greed.model.AbstractDice;

/**
 * Award points for a straight.
 * 
 * @author qw4z1
 *
 */
public class Straight implements Rule{

	private static final int STANDARD_RULE_SCORE = 1000;
	private static final String NAME = Straight.class.getSimpleName();
	
	
	@Override
	public int applyRule(List<AbstractDice> dice) {
		List<AbstractDice> copy = new ArrayList<AbstractDice>();
		copy.addAll(dice);
		Collections.sort(copy);
		for(int i =0 ; i < copy.size() -1; ++i) {
			if(copy.get(i).getCurrentValue() != (copy.get(i + 1).getCurrentValue() - 1)) {
				return 0;
			}
		}
		
		for(AbstractDice die: dice) {
			die.setUsed(true);
		}
		return STANDARD_RULE_SCORE;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
