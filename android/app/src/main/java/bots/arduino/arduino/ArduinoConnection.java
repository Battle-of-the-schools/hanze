package bots.arduino.arduino;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.widget.TextView;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import bots.arduino.Dumper;

public class ArduinoConnection {

//	private Physicaloid physicaloid;

	private UsbDeviceConnection connection;
	private UsbDevice device;
	private Activity context;
	private boolean firstTry = true, wasOpened = false;
	private Dumper dumper;

	private TextView temp;

	public ArduinoConnection(Activity context, Dumper dumper, TextView temp) {

		this.context = context;
		this.dumper = dumper;
		this.temp = temp;
		reset();

		// start a interval that will try to connect to an Arduino
		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(
				new TimerTask() {
					@Override
					public void run() {

						if (connection != null && device != null)
							return;

						// This snippet will open the first usb device connected, excluding usb root hubs
						UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);


						HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
						dumper.dump("nr of devices" + usbDevices.size());
						if(!usbDevices.isEmpty())
						{
							boolean keep = true;
							for(Map.Entry<String, UsbDevice> entry : usbDevices.entrySet())
							{
								device = entry.getValue();
								usbManager.requestPermission(
										device,
										PendingIntent.getBroadcast(context, 0, new Intent("USB_PERMISSION"), 0)
								);
								// We are supposing here there is only one device connected and it is our serial device
								connection = usbManager.openDevice(device);
								keep = false;
								dumper.dump("connection? " + connection);

								if (connection != null)
									startReading();

								if(!keep)
									break;
							}
						}

					}
				}, 0, 200
		);
	}

	private void reset() {

		firstTry = true;
		wasOpened = false;
	}

	// A callback for received data must be defined
	private UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback()
	{
		@Override
		public void onReceivedData(byte[] arg0)
		{

			throw new RuntimeException("received");

		}
	};


	private void startReading() {

		dumper.dump(device.getDeviceName());
		dumper.dump(device.toString());

		UsbSerialDevice serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
		if(serialPort != null)
		{
			dumper.dump("serialport");
			if(serialPort.open())
			{
				dumper.dump("serialport open");
				// Devices are opened with default values, Usually 9600,8,1,None,OFF
				// CDC driver default values 115200,8,1,None,OFF
				serialPort.setBaudRate(9600);
				serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
				serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
				serialPort.setParity(UsbSerialInterface.PARITY_NONE);
				serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
				serialPort.read(mCallback);
			}else
			{
				// Serial port could not be opened, maybe an I/O error or it CDC driver was chosen it does not really fit
			}
		}else
		{
			// No driver for given device, even generic CDC driver could not be loaded
		}
	}

	public boolean writeString(String str) {

		return true;
	}



}
