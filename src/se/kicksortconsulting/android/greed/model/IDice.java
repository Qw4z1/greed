package se.kicksortconsulting.android.greed.model;

public interface IDice {
	public int roll();
	public boolean isSaved();
	public boolean isUsed();
	public void reset();
	public void setIsSaved(boolean b);
	public int getCurrentValue();
	public void setUsed(boolean b);
}
