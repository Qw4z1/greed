package se.kicksortconsulting.android.greed.fragment;

import se.kicksortconsulting.android.greed.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameOverFragment extends Fragment {

	private static final String ARG_WINNER_NAME = "arg_winner_name";
	private static final String ARG_WINNING_SCORE = "arg_winning_score";
	private static final String ARG_TURN_COUNT = "arg_turn_count";
	
	private String mWinnerName;
	private int mWinningScore;
	private int mTurnCount;
	
	public static GameOverFragment newInstance(String winnerName, int winningScore,
			int turnCount) {
		Bundle args = new Bundle();
		args.putString(ARG_WINNER_NAME, winnerName);
		args.putInt(ARG_WINNING_SCORE, winningScore);
		args.putInt(ARG_TURN_COUNT, turnCount);
		
		GameOverFragment fragment = new GameOverFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		mWinnerName= args.getString(ARG_WINNER_NAME);
		mWinningScore = args.getInt(ARG_WINNING_SCORE);
		mTurnCount = args.getInt(ARG_TURN_COUNT);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_game_over, container, false);
		
		TextView winnerText = (TextView) view.findViewById(R.id.tv_winner);
		winnerText.setText(mWinnerName);
		
		TextView scoreText = (TextView) view.findViewById(R.id.tv_score);
		scoreText.setText("" + mWinningScore);
		
		TextView turnText = (TextView) view.findViewById(R.id.tv_rounds);
		turnText.setText("" + mTurnCount);
		
		final Button endButton = (Button) view.findViewById(R.id.btn_end);
		endButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Finish game
				getActivity().finish();
				
			}
			
		});
		
		return view;
	}
}
