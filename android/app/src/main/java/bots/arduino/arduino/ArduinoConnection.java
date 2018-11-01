package bots.arduino.arduino;

import android.app.Activity;
import android.util.Log;

import com.physicaloid.lib.Physicaloid;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import bots.arduino.Dumper;

public class ArduinoConnection {

	private Physicaloid physicaloid;
	private Activity context;
	private boolean firstTry = true, wasOpened = false;
	private Dumper dumper;

	public ArduinoConnection(Activity context, Dumper dumper) {

		this.context = context;
		this.dumper = dumper;
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
		physicaloid.addReadListener(size -> {
			if (size == 0) return;
			byte[] buf = new byte[size];
			physicaloid.read(buf, size);
			try {
				String readStr = new String(buf, "UTF-8");
				dumper.dump(readStr);
			} catch (UnsupportedEncodingException e) {}
		});
//		final Activity context = this.context;
//		new Thread(() -> {
//			StringBuilder s = new StringBuilder();
//			while (true) {
//
//				Physicaloid p = physicaloid;
//				if (p == null || !p.isOpened())
//					continue;
//
//				byte[] buf = new byte[1];
//				p.read(buf, buf.length);
//				String str = new String(buf);
//				if (str.equals("1"))
//					s.append(str);
//					context.runOnUiThread(() -> tempTextView.setText(s));
//
//				p.addReadListener()
//			}
//		}).start();
	}

	public boolean writeString(String str) {
		if (!physicaloid.isOpened() || str == null) return false;

		byte[] buf = str.getBytes();
		physicaloid.write(buf, buf.length);

		return true;
	}



}
