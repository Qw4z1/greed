package se.kicksortconsulting.android.greed.rules;

import java.util.List;

import se.kicksortconsulting.android.greed.model.AbstractDie;

public interface Rule {
	
	/**
	 * Applies the rule to a set of dice and returns the resulting score
	 * @param dice set of dice 
	 * @return int the calculated score
	 */
	public int applyRule(List<AbstractDie> dice);
	
	public String getName();
}
