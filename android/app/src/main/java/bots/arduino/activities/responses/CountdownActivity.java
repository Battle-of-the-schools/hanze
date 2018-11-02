package bots.arduino.activities.responses;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import bots.arduino.App;
import bots.arduino.R;

public class CountdownActivity extends AppCompatActivity {

	private App app;
	private TextView countdown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_countdown);
		app = (App) this.getApplication();
		countdown = findViewById(R.id.countdown);

		final Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				h.postDelayed(this, 1000);

				runOnUiThread(() -> {

					int
							hours = app.countdown / 3600,
							minutes = (app.countdown % 3600) / 60,
							seconds = app.countdown % 60;

					String str = hours + ":";
					str += minutes < 10 ? "0" + minutes : minutes;
					str += ":";
					str += seconds < 10 ? "0" + seconds : seconds;
					countdown.setText(str);

				});
			}
		}, 1000); // 1 second delay

	}
}
