void setup()
{  
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
}
void loop()
{
//  String input = Serial.readString();
//
//  if (input.length() > 0) {
//
//    Serial.print(input);
//
//    digitalWrite(LED_BUILTIN, input == "1" ? HIGH : LOW);
//  }

  Serial.print("hoi");

  delay(100);
  
  Serial.print("doei");

  delay(100);
}
