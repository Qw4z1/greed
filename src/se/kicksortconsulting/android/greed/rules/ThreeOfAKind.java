package se.kicksortconsulting.android.greed.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.kicksortconsulting.android.greed.model.AbstractDice;

public class ThreeOfAKind implements Rule {
	private static final String NAME = ThreeOfAKind.class.getSimpleName();

	private static final int STANDARD_RULE_MULTIPLYER = 100;
	private static final int ONES_RULE_SCORE = 1000;

	@Override
	public int applyRule(List<AbstractDice> diceList) {
		List<AbstractDice> copy = new ArrayList<AbstractDice>();
		for(AbstractDice dice: diceList) {
			if(!dice.isSaved()) {
				copy.add(dice);
			}
		}
		
		if(copy.size() < 3) {
			return 0;
		}
		
		List<Integer> scoreList = new ArrayList<Integer>();

		Collections.sort(copy);
		for (int i = 0; i < copy.size() - 2; i++) {
			// xxxbbb
			// axxxbb
			// aaxxxb
			// aaaxxx
			if ((copy.get(i).getCurrentValue() == copy.get(i + 1)
					.getCurrentValue() && copy.get(i).getCurrentValue() == copy
					.get(i + 2).getCurrentValue())
					&& (!copy.get(i).isUsed() && !copy.get(i + 1).isUsed() && !copy
							.get(i + 2).isUsed())) {
				copy.get(i).setUsed(true);
				copy.get(i+1).setUsed(true);
				copy.get(i+2).setUsed(true);
				scoreList.add(copy.get(i).getCurrentValue());
			}
		}
		int returnScore = 0;
		for (Integer integer : scoreList) {
			if (integer.intValue() == 1) {
				returnScore += ONES_RULE_SCORE;
			} else {
				returnScore += (integer.intValue() * STANDARD_RULE_MULTIPLYER);
			}
		}
		return returnScore;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
