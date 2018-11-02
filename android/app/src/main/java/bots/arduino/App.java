package bots.arduino;

import android.app.Activity;
import android.app.Application;

import bots.arduino.arduino.ArduinoConnection;

public class App extends Application {

	public ArduinoConnection arduinoConnection;
	public Dumper dumper;

	public void init(Activity activity) {
		dumper = new Dumper(activity);
		arduinoConnection = new ArduinoConnection(activity, dumper);
		Thread.setDefaultUncaughtExceptionHandler(new UCExceptionHandler(dumper));
	}

}
