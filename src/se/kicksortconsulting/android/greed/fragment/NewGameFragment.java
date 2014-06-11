package se.kicksortconsulting.android.greed.fragment;

import java.util.ArrayList;
import java.util.List;

import se.kicksortconsulting.android.greed.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class NewGameFragment extends Fragment implements OnClickListener {
	public static final String TAG = NewGameFragment.class.getSimpleName();
	public interface OnStartNewGameListener {
		public void onStartNewGame(List<String> playerNames);
	}
	
	private static final String ARGS_NUM_PLAYERS = "numberOfPlayers";

	private int mPlayerCount;
	private OnStartNewGameListener mCallback;
	private LinearLayout mPlayerNameContainer;
	
	public static NewGameFragment newInstance(int numberOfPlayers) {
		NewGameFragment fragment = new NewGameFragment();
		Bundle args = new Bundle();
		args.putInt(ARGS_NUM_PLAYERS, numberOfPlayers);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPlayerCount = getArguments().getInt(ARGS_NUM_PLAYERS);
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnStartNewGameListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStartNewGameListener");
        }
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragments_new_game, container, false);
		mPlayerNameContainer = (LinearLayout) view.findViewById(R.id.playerNameContainer);

		for(int i = 1; i <= mPlayerCount; i++) {
			addPlayerEdittext(i, inflater);
		}
		
		Button addPlayerButton = (Button) view.findViewById(R.id.addPlayerButton);
		Button startGameButton = (Button) view.findViewById(R.id.startGameButton);
		
		addPlayerButton.setOnClickListener(this);
		startGameButton.setOnClickListener(this);
		
		return view;
	}
	
	private void addPlayerEdittext(int playerNumber, LayoutInflater inflater) {
		EditText playerText = (EditText) new EditText(getActivity());
		LayoutParams params = (LayoutParams) mPlayerNameContainer.getLayoutParams();
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.MATCH_PARENT;
		playerText.setLayoutParams(params);
		playerText.setHint(getResources().getString(R.string.player) + " " + playerNumber);
		mPlayerNameContainer.addView(playerText);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addPlayerButton:
			mPlayerCount++;
			addPlayerEdittext(mPlayerCount, getActivity().getLayoutInflater());
			break;
		case R.id.startGameButton:
			startGame();
		}	
	}
	
	private void startGame() {
		List<String> playerList = new ArrayList<String>();
		for(int i = 0; i < mPlayerNameContainer.getChildCount(); i++) {
			EditText text = (EditText) mPlayerNameContainer.getChildAt(i);
			if(TextUtils.isEmpty(text.getText())) {
				text.setError(getResources().getString(R.string.new_player_empty_error));
				return;
			}
			playerList.add(text.getText().toString());
		}
		mCallback.onStartNewGame(playerList);
	}
}
