package se.kicksortconsulting.android.greed;

import se.kicksortconsulting.android.greed.model.Game;
import android.app.Application;

public class GreedApplication extends Application {

	private static Game mGame;
	
	public static void setSavedGame(Game game) {
		mGame = game;
	}
	
	public static Game getSavedGame() {
		return mGame;
	}
}
