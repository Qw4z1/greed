package se.kicksortconsulting.android.greed.rules;

import java.util.Collections;
import java.util.List;

import se.kicksortconsulting.android.greed.model.AbstractDie;

public class Straight implements Rule{

	private static final int STANDARD_RULE_SCORE = 1000;
	private static final String NAME = Straight.class.getSimpleName();
	
	
	@Override
	public int applyRule(List<AbstractDie> dice) {
		Collections.sort(dice);
		for(int i =0 ; i < dice.size() -1; ++i) {
			if(dice.get(i).getCurrentValue() != (dice.get(i + 1).getCurrentValue() - 1)) {
				return 0;
			}
		}
		
		for(AbstractDie die: dice) {
			die.setIsUsed(true);
		}
		return STANDARD_RULE_SCORE;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
