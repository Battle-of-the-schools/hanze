package bots.arduino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bots.arduino.arduino.ArduinoConnection;

public class MainActivity extends AppCompatActivity {

	private ArduinoConnection arduinoConnection;

	public TextView statusText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		statusText = this.findViewById(R.id.status_text);
		arduinoConnection = new ArduinoConnection(this, statusText);
		Thread.setDefaultUncaughtExceptionHandler(new UCExceptionHandler(this));
	}

	boolean ledOn = false;

	public void testButton(View view) {


		ledOn = !ledOn;
		arduinoConnection.writeString(ledOn ? "1" : "0");

		Toast.makeText(this, ledOn ? "led on" : "led off", Toast.LENGTH_LONG).show();
		throw new RuntimeException("bla");
	}

}
