package bots.arduino.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;

import bots.arduino.App;
import bots.arduino.R;

public class PainActivity extends AppCompatActivity {

	private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pain);

		app = (App) this.getApplication();
	}

	private void chosen(int painLevel, View view) {
		app.arduinoConnection.writeString("{'pain': " + painLevel + "}");
		view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

		Handler handler = new android.os.Handler();
		handler.postDelayed(() -> {
			Intent myIntent = new Intent(this, BodyActivity.class);
			startActivity(myIntent);
		}, 200);
	}

	public void heavy(View view) {
		chosen(3, view);
	}

	public void bad(View view) {
		chosen(2, view);
	}

	public void ok(View view) {
		chosen(1, view);
	}

}
