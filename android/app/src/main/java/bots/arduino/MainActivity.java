package bots.arduino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import bots.arduino.arduino.ArduinoConnection;

public class MainActivity extends AppCompatActivity {

	private ArduinoConnection arduinoConnection;

	public TextView statusText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Dumper dumper = new Dumper(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		arduinoConnection = new ArduinoConnection(this, dumper);
		Thread.setDefaultUncaughtExceptionHandler(new UCExceptionHandler(dumper));
	}

	public void testButton(View view) {
		arduinoConnection.writeString("{'hallo_timo': 4555435, 'pizza': [4, 5, 5, 6]}");
	}

	public void setFragment() {
		
	}

}
