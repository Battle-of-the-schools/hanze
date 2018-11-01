package bots.arduino;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class UCExceptionHandler implements Thread.UncaughtExceptionHandler {

	private Dumper dumper;

	public UCExceptionHandler(Dumper dumper) {
		this.dumper = dumper;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		//Catch your exception
		// Without System.exit() this will not work.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string
		Log.d("exception", sStackTrace);

		dumper.dump(sStackTrace);

		try {
			Thread.sleep(3000);					// wait 3 seconds before exit, so that volley can send the request
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.exit(2);
	}
}
