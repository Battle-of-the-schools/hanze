package bots.arduino;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

public class UCExceptionHandler implements Thread.UncaughtExceptionHandler {

	private Context context;

	public UCExceptionHandler(Context context) {
		this.context = context;
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

		try {
			RequestQueue requestQueue = Volley.newRequestQueue(this.context);
			String URL = "http://ptsv2.com/t/kikker/post";
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("exception", sStackTrace);

			JsonObjectRequest jsonOblect = new JsonObjectRequest(
					Request.Method.POST, URL, jsonBody, response -> {
			}, error -> {
			}
			) {
			};
			requestQueue.add(jsonOblect);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

		try {
			Thread.sleep(3000);					// wait 3 seconds before exit, so that volley can send the request
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.exit(2);
	}
}
