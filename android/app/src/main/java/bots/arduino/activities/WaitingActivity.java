package bots.arduino.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import bots.arduino.R;

public class WaitingActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);

		Handler handler = new android.os.Handler();
//		handler.postDelayed(() -> {
//
//			((App) this.getApplication()).handleJsonMessage("{'show': 16}");
//
//		}, 10000);
	}
}
