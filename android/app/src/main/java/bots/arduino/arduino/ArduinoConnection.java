package bots.arduino.arduino;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.physicaloid.lib.Physicaloid;

import java.util.Timer;
import java.util.TimerTask;

public class ArduinoConnection {

	private Physicaloid physicaloid;
	private Context context;
	private boolean firstTry = true, wasOpened = false;

	private TextView tempTextView;

	public ArduinoConnection(Context context, TextView textView) {

		this.context = context;
		this.tempTextView = textView;
		reset();
		startReading();

		// start a interval that will try to connect to an Arduino
		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(
				new TimerTask() {
					@Override
					public void run() {

						if (physicaloid.isOpened()) return;

						if (physicaloid.open()) {

							Log.d("INFO", "Successfully connected to Arduino");
							wasOpened = true;

						} else {

							Log.d("WARNING", "CANNOT FIND ARDUINO");

						}
						firstTry = false;

					}
				}, 0, 200
		);
	}

	private void reset() {
		physicaloid = new Physicaloid(context);
		physicaloid.setBaudrate(9600);
		firstTry = true;
		wasOpened = false;
	}

	private void startReading() {
		new Thread(() -> {
			while (true) {

				Physicaloid p = physicaloid;
				if (p == null || !p.isOpened())
					continue;

				byte[] buf = new byte[1];

				p.read(buf, buf.length);
				String str = new String(buf);
				if (str.length() > 0)
					tempTextView.setText(str);
			}
		}).start();
	}

	public boolean writeString(String str) {
		if (!physicaloid.isOpened() || str == null) return false;

		byte[] buf = str.getBytes();
		physicaloid.write(buf, buf.length);

		return true;
	}



}
