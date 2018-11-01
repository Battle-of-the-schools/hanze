package bots.arduino;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Dumper {

	private Context context;

	public Dumper(Context context) {
		this.context = context;
	}

	public void dump(String data) {
		try {
			RequestQueue requestQueue = Volley.newRequestQueue(this.context);
			String URL = "http://ptsv2.com/t/kikker4/post";
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("data", data);

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
	}

}
