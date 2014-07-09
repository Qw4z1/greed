package se.kicksortconsulting.android.greed.rules;

import java.util.List;

import se.kicksortconsulting.android.greed.model.AbstractDice;

/**
 * Give points for a One or a Five
 * 
 * @author qw4z1
 *
 */
public class OneOrFive implements Rule {

	private static final String NAME = OneOrFive.class.getSimpleName();

	@Override
	public int applyRule(List<AbstractDice> dice) {
		int returnScore = 0;
		for (AbstractDice die : dice) {
			returnScore += applyRule(die);
		}
		return returnScore;
	}

	private int applyRule(AbstractDice dice) {
		if (dice.isUsed()) {
			return 0;
		}
		if (dice.getCurrentValue() == 5 && !dice.isSaved()) {
			dice.setUsed(true);;
			return 50;
		}
		if (dice.getCurrentValue() == 1 && !dice.isSaved()) {
			dice.setUsed(true);
			return 100;
		}
		return 0;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
