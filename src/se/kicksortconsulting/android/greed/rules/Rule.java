package se.kicksortconsulting.android.greed.rules;

import java.util.List;

import se.kicksortconsulting.android.greed.model.AbstractDice;

/**
 * Interface exposing the methods of a Rule.
 * 
 * @author qw4z1
 *
 */
public interface Rule {
	
	/**
	 * Applies the rule to a set of dice and returns the resulting score
	 * @param dice set of dice 
	 * @return int the calculated score
	 */
	public int applyRule(List<AbstractDice> dice);
	
	/**
	 * 
	 * @return the name of the rule
	 */
	public String getName();
}
