void setup()
{  
  Serial.begin(9600);
//  Serial.
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

//  Serial.println("hoi");
//
//  delay(1000);
//  
//  Serial.println("doei");
//
//  delay(3000);

  Serial.print(Serial.readString());
}
