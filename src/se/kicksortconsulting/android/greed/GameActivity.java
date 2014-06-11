package se.kicksortconsulting.android.greed;

import java.util.List;

import se.kicksortconsulting.android.greed.fragment.NewGameFragment;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity implements NewGameFragment.OnStartNewGameListener{
	
	private static final int DEFAUL_NUMBER_OF_PLAYERS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, NewGameFragment.newInstance(DEFAUL_NUMBER_OF_PLAYERS)).commit();
		}
	}

	@Override
	public void onStartNewGame(List<String> playerNames) {
		// TODO Auto-generated method stub
		
	}

}
