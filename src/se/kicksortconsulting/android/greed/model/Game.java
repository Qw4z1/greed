package se.kicksortconsulting.android.greed.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import se.kicksortconsulting.android.greed.rules.Rule;

/**
 * Class representing a game of Greed. Holds information about players, rules and dice.
 * 
 * @author qw4z1
 *
 */
public class Game {
	public static final String TAG = Game.class.getSimpleName();

	// Min score required for a player to collect points
	private static final int FIRST_ROUND_THRESHOLD = 300;
	private static final int GAME_WIN_THRESHOLD = 10000;

	private List<Player> mPlayers = new ArrayList<Player>();
	private final List<Rule> mRules;
	private List<AbstractDice> mDice;

	private int mCurrentPlayerId;
	private int mCurrentRoundScore;
	private int mTurnCount = 1;
	
	private boolean mHasRolled;

	/**
	 * Creates a new game instance with the player names, rules and dice that should be used.
	 * 
	 * @param playerNames the names of all the players in this round
	 * @param rules the list of rules that should be applied
	 * @param dice the list of dice that should be used
	 */
	public Game(List<String> playerNames, List<Rule> rules,
			List<AbstractDice> dice) {
		createPlayers(playerNames);
		mDice = dice;
		mRules = rules;
	}

	private void createPlayers(List<String> playerNames) {
		for (String name : playerNames) {
			mPlayers.add(new Player(name));
		}
	}

	/**
	 * Roll all the dice that has not been saved
	 */
	public void rollDice() {
		mHasRolled = true;
		for (IDice dice : mDice) {
			if (!dice.isSaved()) {
				dice.roll();
			}
		}
	}

	/**
	 * End current turn, add score to player total (if applicable) and reset the dice list and score.
	 * @param addScoreToTotal
	 */
	public void newTurn(boolean addScoreToTotal) {
		if (addScoreToTotal) {
			mPlayers.get(mCurrentPlayerId).addToScore(mCurrentRoundScore);
		}
		if (mCurrentPlayerId == mPlayers.size() - 1) {
			mCurrentPlayerId = 0;
			mTurnCount++;
		} else {
			mCurrentPlayerId++;
		}
		for (IDice dice : mDice) {
			dice.reset();
		}
		mCurrentRoundScore = 0;
		mHasRolled = false;
	}

	/**
	 * Toggle the saved state of a dice
	 * @param id the id of the dice
	 */
	public void toggleSavedDice(int id) {
		Log.d(TAG, "isSaved " + mDice.get(id).isSaved());
		mDice.get(id).setIsSaved(!mDice.get(id).isSaved());
	}

	/**
	 * Caclulate the score for the current round with respect to the set of rules.
	 * 
	 * @return boolean true if score should be added. Otherwise false.
	 */
	public boolean calculateRoundScore() {
		int roundScore = 0;
		for (Rule rule : mRules) {
			roundScore += rule.applyRule(mDice);
		}
		// Must reach 300 points before score can be saved
		if (!getCurrentPlayer().hasScored() && mCurrentRoundScore == 0) {
			if (roundScore < FIRST_ROUND_THRESHOLD) {
				return false;
			}
		}
		
		if (roundScore == 0) {
			return false;
		}
		mCurrentRoundScore += roundScore;
		return true;
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
	
	public List<AbstractDice> getAllDice() {
		return mDice;
	}

	public boolean isPlayerWinner() {
		return getCurrentTotal() >= GAME_WIN_THRESHOLD;
	}

	public int getTurnCount() {
		return mTurnCount;
	}

	public boolean hasRolled() {
		return mHasRolled;
	}

}
