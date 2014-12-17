#define fastWrite(_pin_, _state_) ( _pin_ < 8 ? (_state_ ?  PORTD |= 1 << _pin_ : PORTD &= ~(1 << _pin_ )) : (_state_ ?  PORTB |= 1 << (_pin_ -8) : PORTB &= ~(1 << (_pin_ -8)  )))
// the macro sets or clears the appropriate bit in port D if the pin is less than 8 or port B if between 8 and 13

//REQUIRES TESTING TO ENSURE WORKS OK FOR SHORT EXP - FOR NOW, FORCE
//EXPOSURE > 30 MS

const char SET_EXP_HEADER = 't';
const char SET_READOUT_HEADER = 'r';
const char SET_FRAMES_HEADER = 'f';
const char ACQ_FRAMES_HEADER = 'a';
const char LAMP_ON_HEADER = 'i';
const char LAMP_OFF_HEADER = 'o';

int exposure = 100;
int readout = 30;
int frames = 1;
bool run = false;
bool change = false;
bool lampOn = false;

void setup() {
  Serial.begin(9600);
  pinMode(7, OUTPUT);          // set outPin pin as output
  pinMode(2, OUTPUT);
}

void loop(){
  
  run = processSerial();
  
  if (run == true)
  {
    //Serial.println("Running....");
    for (int i=1; i<= frames; i++){
      //N.B. supposedly faster to put code here rather than in fcn
      //N.B.2 delay blocks everything else, but that's ok unless we 
      //want to add abort functionality.  
      fastWrite(7, HIGH);       // set Pin high
      delay(exposure);      // waits "on" microseconds
      fastWrite(7, LOW);        // set pin low
      delay(readout);      //wait "off" microseconds
    }
    //Serial.println("Done!");
  }
  
  if (change == true){
      if (lampOn == true){
          fastWrite(2, HIGH);
      }
      else {
          fastWrite(2, LOW);
      }
      change = false;
  }
}




bool processSerial()
{
 static long val = 0;
 bool ret = false;

  if ( Serial.available())
  {
    char ch = Serial.read();

    if(ch >= '0' && ch <= '9')              // is ch a number?
    {
       val = val * 10 + ch - '0';           // yes, accumulate the value
    }
    else if(ch == SET_EXP_HEADER)
    {
      exposure = val;
//      Serial.print("Setting exposure to ");
//      Serial.println(exposure);
//      debug();
      val = 0;
      Serial.println(""+1);
      ret = false;
    }
    else if(ch == SET_READOUT_HEADER)
    {
      if(val > 0)
      {
//        Serial.print("Setting readout to ");
//        Serial.println(val);
        readout = val;
      }
      debug();
      val = 0;
      ret = false;
    }
    else if(ch ==  SET_FRAMES_HEADER)
    {
      frames = val;
      debug();
      val = 0;
      ret = false;
    }
    else if(ch == ACQ_FRAMES_HEADER)
    {
     //Serial.println("GO!");
     val = 0;
     ret = true;
    }
    else if (ch == LAMP_ON_HEADER)
    {
      lampOn = true;
      change = true;
    }
    else if (ch == LAMP_OFF_HEADER)
    {
      lampOn = false;
      change = false;
    }
  }
  
  return ret;
}

// void Pulse(double on, double off) {
//  fastWrite(7, HIGH);       // set Pin high
//  delay(on);      // waits "on" microseconds
//  fastWrite(7, LOW);        // set pin low
//  delay(off);      //wait "off" microseconds
//}

void debug()
{
  Serial.print("Exposure time = ");
  Serial.print(exposure);
  Serial.println(" ms");
  Serial.print("Readout time = ");
  Serial.print(readout);
  Serial.println(" ms");
  Serial.print("# frames = ");
  Serial.print(frames);
  Serial.println(" frames");  
}

void error(){
 Serial.println("Entered value less than 30 ms: forced to 30 ms!");
}
