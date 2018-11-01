package bots.arduino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bots.arduino.activities.PainActivity;

public class MainActivity extends AppCompatActivity {

	private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		app = (App) this.getApplication();
		app.init(this);
	}

	public void sos(View view) {
		app.arduinoConnection.writeString("{'sos': true, 'gps': 10}");
		Intent myIntent = new Intent(this, PainActivity.class);
		startActivity(myIntent);
	}

}
