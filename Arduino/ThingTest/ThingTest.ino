#include <TheThingsNetwork.h>
    
#define loraSerial Serial1
#define debugSerial Serial

#define button 8

const char *appEui = "70B3D57ED0013EDC";
const char *appKey = "609820B02340A427E7E7D47BCB093A22";

bool buttonPressed = false;
bool lastButtonState = false;

//GPS
float flat = 0;
float flong = 0;

// Replace REPLACE_ME with TTN_FP_EU868 or TTN_FP_US915
#define freqPlan TTN_FP_EU868

TheThingsNetwork ttn(loraSerial, debugSerial, freqPlan);
    
void setup() {
  pinMode(button, INPUT_PULLUP);
  // Set callback for incoming messages
  ttn.onMessage(message);
  loraSerial.begin(57600);
  debugSerial.begin(9600);
      
  // Initialize LED output pin
  pinMode(LED_BUILTIN, OUTPUT);
    
  // Wait a maximum of 10s for Serial Monitor
  while (!debugSerial && millis() < 10000);
    
  //debugSerial.println("-- STATUS");
  ttn.showStatus();
  //debugSerial.println("-- JOIN");
  ttn.join(appEui, appKey);
  //debugSerial.println("-- LOOP"); // geeft aan de de loop begint
}

String inData = "";

void loop() {
  buttonHandler();

  while (debugSerial.available() > 0)
    {
        char recieved = debugSerial.read();
        inData += recieved; 

        // Process message when new line character is recieved
        if (recieved == '\n')
        {
            //debugSerial.print("Arduino Received: ");
            //debugSerial.print(inData);
      
            byte data[inData.length()];
            inData.getBytes(data, inData.length());
            ttn.sendBytes(data, sizeof(data));
            
    
            inData = ""; // Clear recieved buffer
        }
    }
    
  if (buttonPressed && !lastButtonState) {
    debugSerial.println("-- HELP");
    String outStr = "HELP";
    outStr.concat(',');
    outStr.concat(String(flat));
    outStr.concat(',');
    outStr.concat(String(flong));
    
    // Send it off
    byte data[outStr.length()];
    outStr.getBytes(data, outStr.length());
    ttn.sendBytes(data, sizeof(data));
  }
}

void message(const byte* payload, int length, int port) {
  //debugSerial.println("-- MESSAGE");
  debugSerial.println(String((char*) payload));
}

void buttonHandler() {
  lastButtonState = buttonPressed;
  buttonPressed = digitalRead(button);
}
