package bots.arduino.activities.responses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bots.arduino.App;
import bots.arduino.R;

public class InstructionActivity extends AppCompatActivity {

	private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instruction);
		app = (App) this.getApplication();
	}

	@Override
	protected void onStart() {
		super.onStart();
		app.countdownActivityActive = true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		app.countdownActivityActive = false;
	}
}
