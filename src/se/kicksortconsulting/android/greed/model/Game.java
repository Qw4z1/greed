package se.kicksortconsulting.android.greed.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import se.kicksortconsulting.android.greed.rules.Rule;

public class Game {
	public static final String TAG = Game.class.getSimpleName();

	private List<Player> mPlayers = new ArrayList<Player>();
	private final List<Rule> mRules;
	private List<AbstractDie> mDice;

	private int mCurrentPlayerId;
	private int mCurrentRoundScore;

	public Game(List<String> playerNames, List<Rule> rules,
			List<AbstractDie> dice) {
		createPlayers(playerNames);
		mDice = dice;
		mRules = rules;
	}

	private void createPlayers(List<String> playerNames) {
		for (String name : playerNames) {
			mPlayers.add(new Player(name));
		}
	}

	public void rollDice() {
		for (IDice dice : mDice) {
			if (!dice.isSaved()) {
				dice.roll();
			}
		}
	}

	public List<AbstractDie> getAllDice() {
		return mDice;
	}

	public void newTurn() {
		mPlayers.get(mCurrentPlayerId).addToScore(mCurrentRoundScore);

		if (mCurrentPlayerId == mPlayers.size() - 1) {
			mCurrentPlayerId = 0;
		} else {
			mCurrentPlayerId++;
		}
		for (IDice dice : mDice) {
			dice.reset();
		}
	}

	public void toggleSavedDice(int id) {
		mDice.get(id).setIsSaved(!mDice.get(id).isSaved());
	}

	public void calculateRoundScore() {
		int roundScore = 0;
		for (AbstractDie dice : mDice) {
			dice.setIsUsed(false);
		}
		for (Rule rule : mRules) {
			roundScore += rule.applyRule(mDice);
		}
		mCurrentRoundScore = roundScore;
	}

	public int getCurrentTotal() {
		return getCurrentPlayer().getScore() + mCurrentRoundScore;
	}

	public String getCurrentPlayerName() {
		return getCurrentPlayer().getName();
	}

	private Player getCurrentPlayer() {
		return mPlayers.get(mCurrentPlayerId);
	}

	public int getCurrentRoundScore() {
		return mCurrentRoundScore;
	}
}
