package bots.arduino;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import bots.arduino.activities.responses.CountdownActivity;
import bots.arduino.activities.responses.InstructionActivity;
import bots.arduino.arduino.ArduinoConnection;

public class App extends Application {

	public ArduinoConnection arduinoConnection;
	public Dumper dumper;
	public Activity activity;

	public boolean countdownActivityActive;
	public int countdown;

	public void init(Activity activity) {
		this.activity = activity;
		dumper = new Dumper(activity);
		arduinoConnection = new ArduinoConnection(activity, dumper);
		Thread.setDefaultUncaughtExceptionHandler(new UCExceptionHandler(dumper));

		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(
				new TimerTask() {
					@Override
					public void run() {
						countdown = countdown <= 0 ? 0 : countdown - 1;
					}
				}, 0, 1000
		);
	}

	public void handleJsonMessage(String json) {

		try {
			JSONObject jsonObj = new JSONObject(json);

			if (jsonObj.has("response")) {
				// app should show an screen/update the countdown/whatever

				int id = jsonObj.getInt("response");
				Class<? extends Activity> activityClass = null;
				switch (id) {
					case 16:
						activityClass = InstructionActivity.class;
						break;
					case 17:
						countdown = jsonObj.has("time") ? jsonObj.getInt("time") : countdown;
						if (!countdownActivityActive)
							activityClass = CountdownActivity.class;
						break;
				}

				if (activityClass != null) {
					Intent myIntent = new Intent(activity, activityClass);
					startActivity(myIntent);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();

			// non json message
		}
	}

}
