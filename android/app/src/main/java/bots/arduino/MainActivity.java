package bots.arduino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.physicaloid.lib.Physicaloid;

public class MainActivity extends AppCompatActivity {

	Physicaloid mPhysicaloid; // initialising library

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.print("gestart");

		mPhysicaloid = new Physicaloid(this);
		mPhysicaloid.setBaudrate(9600);

		if (mPhysicaloid.open()) {

			Toast.makeText(this, "Successfully connected", Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(this, "Cannot find Arduino", Toast.LENGTH_LONG).show();
		}



	}


	public void testArduino(View view) {




	}

}
