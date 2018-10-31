package bots.arduino;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.print("gestart");

		UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

		HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();

		System.out.print(usbDevices.size());
		System.out.print(usbDevices.toString());


		if (!usbDevices.isEmpty()) {
			for (UsbDevice device : usbDevices.values()) {
				int deviceVID = device.getVendorId();
				if (deviceVID == 0x2341)//Arduino Vendor ID
				{
					System.out.print("found arduino");
//					PendingIntent pi = PendingIntent.getBroadcast(this, 0,
//							new Intent(ACTION_USB_PERMISSION), 0);
//					usbManager.requestPermission(device, pi);
//					keep = false;
				}
			}
		}

	}


	public void testArduino(View view) {



	}

}
