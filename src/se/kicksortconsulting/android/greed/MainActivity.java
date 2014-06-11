package se.kicksortconsulting.android.greed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button newGameButton = (Button) findViewById(R.id.btn_new_game);
        final Button aboutButton = (Button) findViewById(R.id.btn_about);
        final Button rulesButton = (Button) findViewById(R.id.btn_rules);
        
        newGameButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        rulesButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_new_game : startNewGame(); break;
		case R.id.btn_about : startAboutActivity(); break;
		case R.id.btn_rules : startRulesActivity(); break;
		
		}
		
	}

	private void startNewGame() {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	private void startAboutActivity() {
		// TODO Auto-generated method stub
		
	}
	
	private void startRulesActivity() {
		// TODO Auto-generated method stub
		
	}

}
