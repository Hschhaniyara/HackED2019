/*
 * Blink, with conditional compilation based on the device name.
 */

#include <Arduino.h>
#include "superfluous.h"


const int steeringPin1 = 28;
const int steeringPin2 = 29;

const int midTrigPin = 2;
const int midEchoPin = 3;
const int leftTrigPin = 11;
const int leftEchoPin = 10;
const int rightTrigPin = 8;
const int rightEchoPin = 9;

const int motorPin1 = 40;
const int motorPin2 = 41;
int lastState = 0;
int status = 1;
int movement = 0;
// int averageFrontDistance = 0;
// Left down trig 6, echo 7
// right down trig 12, echo 13


void setup() {
    // pinMode(motorPin, OUTPUT);
    // pinMode(steeringPin, OUTPUT);

    pinMode(midEchoPin, INPUT);
    pinMode(midTrigPin, OUTPUT);
    pinMode(leftEchoPin, INPUT);
    pinMode(leftTrigPin, OUTPUT);
    pinMode(rightEchoPin, INPUT);
    pinMode(rightTrigPin, OUTPUT);

    pinMode(motorPin1, OUTPUT);

    pinMode(motorPin2, OUTPUT);


    pinMode(steeringPin1, OUTPUT);

    pinMode(steeringPin2, OUTPUT);

    int i = 0;

    // Check here to determine the mode of the application.
    // Mode 1 should be follow mode.

    Serial.begin(9600);

    digitalWrite(motorPin1, LOW);

    digitalWrite(motorPin2, LOW);
}


int soundPing(int trigPin, int echoPin) {
  // Clears the trigPin
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  long duration = pulseIn(echoPin, HIGH);
  // Calculating the distance
  int distance= duration*0.034/2;

  return distance;
}

void sendToBluetooth(int mode, int leftDistance, int rightDistance, int frontDistance) {
  Serial.print(mode);
  Serial.print(" , ");
  Serial.print(leftDistance);
  Serial.print(" , ");
  Serial.print(rightDistance);
  Serial.print(" , ");
  Serial.print(frontDistance);
}

void followFunction() {
  int readingMid = soundPing(midTrigPin, midEchoPin);

  // Main object is being tracked by the middle sensor. Check distance from object.
  if (readingMid < 250) {
    if (readingMid < 25) {
      //Car is too close to the object. Back Up.
      Serial.println("Moving Backward");
      analogWrite(motorPin1, 150);
      analogWrite(motorPin2,0);
      lastState = 1;
    } else if (readingMid > 60) {
      //Car is too far away from the object. Move closer.
      Serial.println("Moving Forward");
      lastState = 2;
      analogWrite(motorPin1, 0);
      analogWrite(motorPin2,150);
    } else {// Object is in the acceptable range.
      if (lastState == 1 and readingMid < 55) {
        // Power motor in reverse direction to stop bot.
        digitalWrite(motorPin1, LOW);
        digitalWrite(motorPin2, HIGH);
        delay(500);
        lastState = 0;
      } else if (lastState == 2 and readingMid > 30) {
        // Power motor in forward direction to stop bot.
        digitalWrite(motorPin1, HIGH);
        digitalWrite(motorPin2, LOW);
        delay(500);
        lastState = 0;
      }
      //Stop power to motors.
      digitalWrite(motorPin1, LOW);
      digitalWrite(motorPin2, LOW);
    }
  } else {
    // When code arrives here, the main supersonic sensor has lost track of the object.
    // Read from the side facing sensors to determine which sensor is tracking the object,
    // and turn the arduino so that the object main sensor can track the object.
    int readingLeft = soundPing(leftTrigPin, leftEchoPin);
    int readingRight = soundPing(rightTrigPin, rightEchoPin);
    delay(10);

    if (readingLeft < 255) {
      // Object to be tracked is in the left sensor.
      if (readingLeft < readingRight) { //Turn car to the left.
        digitalWrite(steeringPin1, HIGH);
        digitalWrite(steeringPin2, LOW);
      } else { // Turn car to the right.(Left sensor is incorrect. )
        digitalWrite(steeringPin1, LOW);
        digitalWrite(steeringPin2, HIGH);
      }
      // Object to be tracked is in the right sensor.
    } else if (readingRight < 255) {
      if (readingRight < readingLeft) {
        digitalWrite(steeringPin1, LOW);
        digitalWrite(steeringPin2, HIGH);
      } else { // Turn car to the left. (Right sensor is incorrect. )
        digitalWrite(steeringPin1, HIGH);
        digitalWrite(steeringPin2, LOW);
      }
    }
  }
}

void mapRoom() {
  int readingMid = soundPing(midTrigPin, midEchoPin);
  int readingLeft = soundPing(leftTrigPin, leftEchoPin);
  int readingRight = soundPing(rightTrigPin, rightEchoPin);

  // Check which sensor is tracking the object.
  if (readingMid < 250) {
    if (readingMid < 25) {
      Serial.println("Moving Backward");
      analogWrite(motorPin1, 150);
      analogWrite(motorPin2,0);
      movement = 1;
      lastState = 1;
    } else if (readingMid > 60) {
      Serial.println("Moving Forward");
      lastState = 2;
      analogWrite(motorPin1, 0);
      analogWrite(motorPin2,150);
      movement = 2;
    } else {
      Serial.println("TURN");
      if (lastState == 1 and readingMid < 55) {
        digitalWrite(motorPin1, LOW);
        digitalWrite(motorPin2, HIGH);
        delay(500);
        lastState = 0;
        movement = 0; //Car is stopping, so stop tracking distance.
      } else if (lastState == 2 and readingMid > 30) {
        digitalWrite(motorPin1, HIGH);
        digitalWrite(motorPin2, LOW);
        delay(500);
        lastState = 0;
        movement = 0; //Car is stopping, so stop tracking distance.
      }
      //Stop motor, and set steering back to 0.
      movement = 0;
      digitalWrite(motorPin1, LOW);
      digitalWrite(motorPin2, LOW);
      digitalWrite(steeringPin1, LOW);
      digitalWrite(steeringPin2, LOW);
    }
  } else {
    delay(10);
    if (readingLeft < 255) {
      if (readingLeft < readingRight) {
        digitalWrite(steeringPin1, HIGH);
        digitalWrite(steeringPin2, LOW);
        movement = 4;
      } else {
        digitalWrite(steeringPin1, LOW);
        digitalWrite(steeringPin2, HIGH);
        movement = 3;
      }
    } else if (readingRight < 255) {
      if (readingRight < readingLeft) {
        digitalWrite(steeringPin1, LOW);
        digitalWrite(steeringPin2, HIGH);
        movement = 3;
      } else {
        digitalWrite(steeringPin1, HIGH);
        digitalWrite(steeringPin2, LOW);
        movement = 4;
      }
    }
  }
  sendToBluetooth(movement, readingLeft, readingRight, readingMid);
}

// This is the main function in the arduino program. It reads from the bluetooth
//  Adapter to see which mode has been selected through the app. The program
//  then runs the corresponding code.
void loop() {
  if(Serial.available() > 0) { // Send data only when you receive data:
      data = Serial.read();      //Read the incoming data and store it into variable data

      status = int(data);

      if (status == 2) {
        followFunction();
        break;
      } else if (status == 3) {
        mapRoom();
        break;
      }
    }

}
