package bots.arduino.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;

import bots.arduino.App;
import bots.arduino.R;

public class CauseActivity extends AppCompatActivity {
	private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cause);

		app = (App) this.getApplication();
	}

	private void chosen(int type, View view) {
		app.arduinoConnection.writeString("{'cause': " + type + "}");
		view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));
//		Intent myIntent = new Intent(this, BodyActivity.class);
//		startActivity(myIntent);
	}

	public void blood(View view) {
		chosen(3, view);
	}

	public void broken(View view) {
		chosen(2, view);
	}

	public void pain(View view) {
		chosen(1, view);
	}
}
