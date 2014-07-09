package se.kicksortconsulting.android.greed.fragment;

import java.util.ArrayList;
import java.util.List;

import se.kicksortconsulting.android.greed.GreedApplication;
import se.kicksortconsulting.android.greed.R;
import se.kicksortconsulting.android.greed.model.AbstractDice;
import se.kicksortconsulting.android.greed.model.SixSidedDice;
import se.kicksortconsulting.android.greed.model.Game;
import se.kicksortconsulting.android.greed.rules.OneOrFive;
import se.kicksortconsulting.android.greed.rules.Rule;
import se.kicksortconsulting.android.greed.rules.Straight;
import se.kicksortconsulting.android.greed.rules.ThreeOfAKind;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Fragmen containing the board and {@link Game} instance.
 * 
 * @author qw4z1
 *
 */
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
	
	private Button mScoreButton;
	private Button mDiceButton;

	private Game mGame;
	private GameOverListener mListener;

	/**
	 * Callback when game is over
	 * @author qw4z1
	 *
	 */
	public interface GameOverListener {
		public void onGameOver(String winnerName, int winningScore, int turnCount);
	}
	
	public static GameFragment newInstance(List<String> playerNames) {
		Bundle args = new Bundle();
		args.putStringArrayList(ARGS_PLAYER_LIST,
				(ArrayList<String>) playerNames);
		GameFragment fragment = new GameFragment();
		fragment.setArguments(args);

		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(GreedApplication.getSavedGame() == null) {
			List<String> playerNameList = getArguments().getStringArrayList(
					ARGS_PLAYER_LIST);
			List<Rule> rules = createRules();
			List<AbstractDice> dice = createDice();
			mGame = new Game(playerNameList, rules, dice);
		} else {
			mGame = GreedApplication.getSavedGame();
		}
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

		mDiceButton1.setOnClickListener(mDiceClickListener);
		mDiceButton2.setOnClickListener(mDiceClickListener);
		mDiceButton3.setOnClickListener(mDiceClickListener);
		mDiceButton4.setOnClickListener(mDiceClickListener);
		mDiceButton5.setOnClickListener(mDiceClickListener);
		mDiceButton6.setOnClickListener(mDiceClickListener);

		mTotalScore = (TextView) view.findViewById(R.id.totalScore);
		mCurrentPlayer = (TextView) view.findViewById(R.id.currentPlayer);
		mRoundScore = (TextView) view.findViewById(R.id.roundScore);

		mScoreButton = (Button) view.findViewById(R.id.scoreButton);
		if(!mGame.hasRolled()) {
			mScoreButton.setEnabled(false);
		}
		mScoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				endTurn(true, "Score saved!");
			}
			
		});

		mDiceButton = (Button) view.findViewById(R.id.rollDiceButton);
		mDiceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rollDice();
				updateDice();
				boolean scored = mGame.calculateRoundScore();
				if (scored) {
					updateTextfields();
					if(mGame.isPlayerWinner()) {
						String winnerName = mGame.getCurrentPlayerName();
						int winningScore = mGame.getCurrentTotal();
						int turnCount = mGame.getTurnCount();
						mListener.onGameOver(winnerName, winningScore, turnCount);
					}
				} else {
					endTurn(false, "Not enough points");
				}
			}

		});

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
            mListener = (GameOverListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement GameOverListener");
        }
	}
	
	@Override
	public void onPause() {
		super.onPause();
		GreedApplication.setSavedGame(mGame);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateDice();
		updateTextfields();
		
	}
	
	private List<AbstractDice> createDice() {
		List<AbstractDice> diceList = new ArrayList<AbstractDice>();
		for (int i = 0; i < NUMBER_OF_DICE; i++) {
			diceList.add(new SixSidedDice(i));
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
	
	private void updateDice() {
		List<AbstractDice> diceList = mGame.getAllDice();
		mDiceButton1.setImageLevel(diceList.get(0).getCurrentValue() - 1);
		mDiceButton1.setSelected(diceList.get(0).isSaved());
		mDiceButton2.setImageLevel(diceList.get(1).getCurrentValue() - 1);
		mDiceButton2.setSelected(diceList.get(1).isSaved());
		mDiceButton3.setImageLevel(diceList.get(2).getCurrentValue() - 1);
		mDiceButton3.setSelected(diceList.get(2).isSaved());
		mDiceButton4.setImageLevel(diceList.get(3).getCurrentValue() - 1);
		mDiceButton4.setSelected(diceList.get(3).isSaved());
		mDiceButton5.setImageLevel(diceList.get(4).getCurrentValue() - 1);
		mDiceButton5.setSelected(diceList.get(4).isSaved());
		mDiceButton6.setImageLevel(diceList.get(5).getCurrentValue() - 1);
		mDiceButton6.setSelected(diceList.get(5).isSaved());
	}

	private void updateTextfields() {
		mTotalScore.setText("" + mGame.getCurrentTotal());
		mCurrentPlayer.setText("" + mGame.getCurrentPlayerName());
		mRoundScore.setText("" + mGame.getCurrentRoundScore());
	}
	
	private void clearDiceSelection() {
		mDiceButton1.setSelected(false);
		mDiceButton2.setSelected(false);
		mDiceButton3.setSelected(false);
		mDiceButton4.setSelected(false);
		mDiceButton5.setSelected(false);
		mDiceButton6.setSelected(false);
	}

	private void rollDice() {
		mGame.rollDice();
		mScoreButton.setEnabled(true);
		mDiceButton.setEnabled(false);
	}

	private void endTurn(boolean addScoreToTotal, String message) {
		new AlertDialog.Builder(getActivity())
				.setTitle(getResources().getString(R.string.turn_ended))
				.setMessage(message)
				.setPositiveButton("OK", mDialogClickListener).create().show();
		mGame.newTurn(addScoreToTotal);
	}

	DialogInterface.OnClickListener mDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			updateTextfields();
			clearDiceSelection();
			mScoreButton.setEnabled(false);
			mDiceButton.setEnabled(true);
		}
	};

	OnClickListener mDiceClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(!mGame.hasRolled()) {
				return;
			}
			int diceId = 0;
			switch (v.getId()) {
			case R.id.imageButton1:
				diceId = 0;
				break;
			case R.id.imageButton2:
				diceId = 1;
				break;
			case R.id.imageButton3:
				diceId = 2;
				break;
			case R.id.imageButton4:
				diceId = 3;
				break;
			case R.id.imageButton5:
				diceId = 4;
				break;
			case R.id.imageButton6:
				diceId = 5;
				break;
			}
			mGame.toggleSavedDice(diceId);
			mDiceButton.setEnabled(true);

			v.setSelected(!v.isSelected());
		}
	};
}
