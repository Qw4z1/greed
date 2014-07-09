package se.kicksortconsulting.android.greed.model;

public class Player {
	private String mName;
	private int mScore;
	
	public Player(String name) {
		mName = name;
	}
	
	public String getName() {
		return mName;
	}
	
	public int getScore() {
		return mScore;
	}
	
	public void addToScore(int score) {
		mScore += score;
	}
	
	public boolean hasScored() {
		return mScore > 0;
	}
}
