package bots.arduino.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;

import bots.arduino.App;
import bots.arduino.R;

public class BodyActivity extends AppCompatActivity {

	enum BodyPart {
		HEAD(1), CHEST(2), LEFT_ARM(3), RIGHT_ARM(4), LEFT_LEG(5), RIGHT_LEG(6);

		int id;

		BodyPart(int id) {
			this.id = id;
		}
	}

	private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_body);

		app = (App) this.getApplication();
	}

	private void chosen(BodyPart bodyPart, View view) {

		app.arduinoConnection.writeString("{'bodyPart': " + bodyPart.id + "}");
		view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

		Handler handler = new android.os.Handler();
		handler.postDelayed(() -> {
			Intent myIntent = new Intent(this, CauseActivity.class);
			startActivity(myIntent);

		}, 200);
	}


	public void chest(View view) {
		chosen(BodyPart.CHEST, view);
	}

	public void head(View view) {
		chosen(BodyPart.HEAD, view);
	}

	public void leftArm(View view) {
		chosen(BodyPart.LEFT_ARM, view);
	}

	public void rightArm(View view) {
		chosen(BodyPart.RIGHT_ARM, view);
	}

	public void leftLeg(View view) {
		chosen(BodyPart.LEFT_LEG, view);
	}

	public void rightLeg(View view) {
		chosen(BodyPart.RIGHT_LEG, view);
	}

}
