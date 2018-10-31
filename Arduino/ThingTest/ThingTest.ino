#include <TheThingsNetwork.h>
    
#define loraSerial Serial1
#define debugSerial Serial

const char *appEui = "70B3D57ED0013EDC";
const char *appKey = "609820B02340A427E7E7D47BCB093A22";

bool buttonPressed = false;

// Replace REPLACE_ME with TTN_FP_EU868 or TTN_FP_US915
#define freqPlan TTN_FP_EU868
    
TheThingsNetwork ttn(loraSerial, debugSerial, freqPlan);
    
void setup() {
  // Set callback for incoming messages
  ttn.onMessage(message);
  loraSerial.begin(57600);
  debugSerial.begin(9600);
      
  // Initialize LED output pin
  pinMode(LED_BUILTIN, OUTPUT);
    
  // Wait a maximum of 10s for Serial Monitor
  while (!debugSerial && millis() < 10000);
    
  debugSerial.println("-- STATUS");
  ttn.showStatus();
  debugSerial.println("-- JOIN");
  ttn.join(appEui, appKey);
  debugSerial.println("-- LOOP"); // geeft aan de de loop begint
}

String inData = "";

void loop() {

  while (debugSerial.available() > 0)
    {
        char recieved = debugSerial.read();
        inData += recieved; 

        // Process message when new line character is recieved
        if (recieved == '\n')
        {
            debugSerial.print("Arduino Received: ");
            debugSerial.print(inData);
      
            byte data[inData.length()];
            inData.getBytes(data, inData.length());
            ttn.sendBytes(data, sizeof(data));
            
    
            inData = ""; // Clear recieved buffer
        }
    }
    
  if (buttonPressed) {
    byte data[] = ("HELP");
    // Send it off
    ttn.sendBytes(data, sizeof(data));
  }
}

void message(const byte* payload, int length, int port) {
  debugSerial.println("-- MESSAGE");
    
  // Only handle messages of a single byte
  if (length != 1) {
    return;
  }
    
  if (payload[0] == 0) {
    debugSerial.println("LED: off");
    digitalWrite(LED_BUILTIN, LOW);
          
  } else if (payload[0] == 1) {
    debugSerial.println("LED: on");
    digitalWrite(LED_BUILTIN, HIGH);
  }
}
