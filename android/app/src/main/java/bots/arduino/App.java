package bots.arduino;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import bots.arduino.activities.responses.InstructionActivity;
import bots.arduino.arduino.ArduinoConnection;

public class App extends Application {

	public ArduinoConnection arduinoConnection;
	public Dumper dumper;
	public Activity activity;

	public void init(Activity activity) {
		this.activity = activity;
		dumper = new Dumper(activity);
		arduinoConnection = new ArduinoConnection(activity, dumper);
		Thread.setDefaultUncaughtExceptionHandler(new UCExceptionHandler(dumper));
	}

	public void handleJsonMessage(String json) {

		try {
			JSONObject jsonObj = new JSONObject(json);

			if (jsonObj.has("show")) {
				// app should show an screen

				int id = jsonObj.getInt("show");
				Class<? extends Activity> activityClass = null;
				switch (id) {
					case 16:
						activityClass = InstructionActivity.class;
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
