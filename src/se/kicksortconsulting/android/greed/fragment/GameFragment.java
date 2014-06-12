package se.kicksortconsulting.android.greed.fragment;

import java.util.ArrayList;
import java.util.List;

import se.kicksortconsulting.android.greed.R;
import se.kicksortconsulting.android.greed.model.AbstractDie;
import se.kicksortconsulting.android.greed.model.SixSidedDie;
import se.kicksortconsulting.android.greed.model.Game;
import se.kicksortconsulting.android.greed.rules.OneOrFive;
import se.kicksortconsulting.android.greed.rules.Rule;
import se.kicksortconsulting.android.greed.rules.Straight;
import se.kicksortconsulting.android.greed.rules.ThreeOfAKind;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameFragment extends Fragment {
	public static final String TAG = GameFragment.class.getSimpleName();

	private static final String ARGS_PLAYER_LIST = "playerList";
	private static final int NUMBER_OF_DICE = 6;

	private ImageButton mDiceButton1;
	private ImageButton mDiceButton2;
	private ImageButton mDiceButton3;
	private ImageButton mDiceButton4;
	private ImageButton mDiceButton5;
	private ImageButton mDiceButton6;

	private TextView mTotalScore;
	private TextView mCurrentPlayer;
	private TextView mRoundScore;

	private Game mGame;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		List<String> playerNameList = getArguments().getStringArrayList(
				ARGS_PLAYER_LIST);
		List<Rule> rules = createRules();
		List<AbstractDie> dice = createDice();
		mGame = new Game(playerNameList, rules, dice);
	}
	
	private List<AbstractDie> createDice() {
		List<AbstractDie> diceList = new ArrayList<AbstractDie>();
		for(int i = 0; i < NUMBER_OF_DICE; i++) {
			diceList.add(new SixSidedDie( i));
		}
		return diceList;
	}
	
	private List<Rule> createRules() {
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(new Straight());
		rules.add(new ThreeOfAKind());
		rules.add(new OneOrFive());
		return rules;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_game, container, false);

		mDiceButton1 = (ImageButton) view.findViewById(R.id.imageButton1);
		mDiceButton2 = (ImageButton) view.findViewById(R.id.imageButton2);
		mDiceButton3 = (ImageButton) view.findViewById(R.id.imageButton3);
		mDiceButton4 = (ImageButton) view.findViewById(R.id.imageButton4);
		mDiceButton5 = (ImageButton) view.findViewById(R.id.imageButton5);
		mDiceButton6 = (ImageButton) view.findViewById(R.id.imageButton6);

		mDiceButton1.setImageLevel(0);
		mDiceButton2.setImageLevel(1);
		mDiceButton3.setImageLevel(2);
		mDiceButton4.setImageLevel(3);
		mDiceButton5.setImageLevel(4);
		mDiceButton6.setImageLevel(5);
		
		mDiceButton1.setOnClickListener(mDiceClickListener);
		mDiceButton2.setOnClickListener(mDiceClickListener);
		mDiceButton3.setOnClickListener(mDiceClickListener);
		mDiceButton4.setOnClickListener(mDiceClickListener);
		mDiceButton5.setOnClickListener(mDiceClickListener);
		mDiceButton6.setOnClickListener(mDiceClickListener);
		
		mTotalScore = (TextView) view.findViewById(R.id.totalScore);
		mCurrentPlayer = (TextView) view.findViewById(R.id.currentPlayer);
		mRoundScore = (TextView) view.findViewById(R.id.roundScore);
		
		updateTextfields();
		
		Button diceButton = (Button) view.findViewById(R.id.rollDiceButton);
		diceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rollDice();
				updateTextfields();
				updateDice();
			}
			
		});

		return view;
	}
	
	private void updateDice() {
		List<AbstractDie> diceList = mGame.getAllDice();
		mDiceButton1.setImageLevel(diceList.get(0).getCurrentValue());
		mDiceButton2.setImageLevel(diceList.get(1).getCurrentValue());
		mDiceButton3.setImageLevel(diceList.get(2).getCurrentValue());
		mDiceButton4.setImageLevel(diceList.get(3).getCurrentValue());
		mDiceButton5.setImageLevel(diceList.get(4).getCurrentValue());
		mDiceButton6.setImageLevel(diceList.get(5).getCurrentValue());
	}
	
	private void updateTextfields() {
		mTotalScore.setText(""+mGame.getCurrentTotal());
		mCurrentPlayer.setText(""+mGame.getCurrentPlayerName());
		mRoundScore.setText(""+mGame.getCurrentRoundScore());
	}

	private void rollDice() {
		mGame.rollDice();
		mGame.calculateRoundScore();
	}
	
	public static GameFragment newInstance(List<String> playerNames) {
		Bundle args = new Bundle();
		args.putStringArrayList(ARGS_PLAYER_LIST,
				(ArrayList<String>) playerNames);

		GameFragment fragment = new GameFragment();
		fragment.setArguments(args);

		return fragment;
	}
	
	OnClickListener mDiceClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int diceId = 0;
			switch (v.getId()) {
			case R.id.imageButton1: diceId = 0; break;
			case R.id.imageButton2: diceId = 1; break;
			case R.id.imageButton3: diceId = 2; break;
			case R.id.imageButton4: diceId = 3; break;
			case R.id.imageButton5: diceId = 4; break;
			case R.id.imageButton6: diceId = 5; break;
			}
			mGame.toggleSavedDice(diceId);
			
			v.setSelected(!v.isSelected());
		}
		
	};

}
