void setup()
{  
  Serial.begin(9600);
}
void loop()
{
  String input = Serial.readString();
  Serial.print(input);
}
