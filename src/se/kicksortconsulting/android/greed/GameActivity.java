package se.kicksortconsulting.android.greed;

import java.util.List;

import se.kicksortconsulting.android.greed.fragment.GameFragment;
import se.kicksortconsulting.android.greed.fragment.GameOverFragment;
import se.kicksortconsulting.android.greed.fragment.NewGameFragment;
import android.app.Activity;
import android.os.Bundle;

/**
 * Activity holding a {@link NewGameFragment}
 * 
 * @author qw4z1
 *
 */
public class GameActivity extends Activity implements NewGameFragment.OnStartNewGameListener, GameFragment.GameOverListener{
	
	private static final int DEFAUL_NUMBER_OF_PLAYERS = 2;
	private static final String NEW_GAME_TAG = "newGameFragment";
	private static final String GAME_FRAGMENT_TAG = "gameFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, NewGameFragment.newInstance(DEFAUL_NUMBER_OF_PLAYERS), NEW_GAME_TAG).commit();
		} else {
			getFragmentManager().findFragmentByTag(NEW_GAME_TAG);
		}
	}

	@Override
	public void onStartNewGame(List<String> playerNames) {
		getFragmentManager().beginTransaction().replace(R.id.container, GameFragment.newInstance(playerNames), GAME_FRAGMENT_TAG).commit();
		
	}

	@Override
	public void onGameOver(String winnerName, int winningScore, int turnCount) {
		GreedApplication.setSavedGame(null);
		getFragmentManager().beginTransaction().replace(R.id.container, GameOverFragment.newInstance(winnerName, winningScore, turnCount), GAME_FRAGMENT_TAG).commit();
		
	}

}
