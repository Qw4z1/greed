package se.kicksortconsulting.android.greed.rules;

import java.util.List;

import se.kicksortconsulting.android.greed.model.AbstractDie;

public class OneOrFive implements Rule {

	private static final String NAME = OneOrFive.class.getSimpleName();

	@Override
	public int applyRule(List<AbstractDie> dice) {
		int returnScore = 0;
		for (AbstractDie die : dice) {
			returnScore += applyRule(die);
		}
		return returnScore;
	}

	private int applyRule(AbstractDie dice) {
		if (dice.isUsed()) {
			return 0;
		}
		if (dice.getCurrentValue() == 5) {
			return 50;
		}
		if (dice.getCurrentValue() == 1) {
			return 100;
		}
		return 0;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
