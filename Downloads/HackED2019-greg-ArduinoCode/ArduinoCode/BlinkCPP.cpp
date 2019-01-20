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





void loop() {
  int readingMid = soundPing(midTrigPin, midEchoPin);
  // digitalWrite(motorPin,  ! HIGH);

  Serial.print("Reading: ");
  Serial.println(readingMid);

  // Check which sensor is tracking the object.
  if (readingMid < 250) {
    if (readingMid < 25) {
      Serial.println("Moving Backward");


      analogWrite(motorPin1, 150);
      analogWrite(motorPin2,0);
      lastState = 1;
      // digitalWrite(motorPin1, HIGH);

      // digitalWrite(motorPin2, LOW);
    } else if (readingMid > 60) {
      Serial.println("Moving Forward");
      lastState = 2;

      analogWrite(motorPin1, 0);
      analogWrite(motorPin2,150);
    } else {
      Serial.println("TURN");
      if (lastState == 1 and readingMid < 55) {
        digitalWrite(motorPin1, LOW);

        digitalWrite(motorPin2, HIGH);
        delay(500);
        lastState = 0;
      } else if (lastState == 2 and readingMid > 30) {
        digitalWrite(motorPin1, HIGH);

        digitalWrite(motorPin2, LOW);
        delay(500);

        lastState = 0;
      }

      digitalWrite(motorPin1, LOW);

      digitalWrite(motorPin2, LOW);
    }
  } else {
    int readingLeft = soundPing(leftTrigPin, leftEchoPin);
    int readingRight = soundPing(rightTrigPin, rightEchoPin);
    delay(10);

    if (readingLeft < 255) {
      if (readingLeft < readingRight) {
        digitalWrite(steeringPin1, HIGH);

        digitalWrite(steeringPin2, LOW);
      } else {
        digitalWrite(steeringPin1, LOW);

        digitalWrite(steeringPin2, HIGH);
      }
      // digitalWrite(steeringPin1, HIGH);
      //
      // digitalWrite(steeringPin2, LOW);
      //
      // // digitalWrite(steeringPin, HIGH);
      // digitalWrite(motorPin, HIGH);
    } else if (readingRight < 255) {
      if (readingRight < readingLeft) {
        digitalWrite(steeringPin1, LOW);

        digitalWrite(steeringPin2, HIGH);
      } else {
        digitalWrite(steeringPin1, HIGH);

        digitalWrite(steeringPin2, LOW);
      }
      // digitalWrite(steeringPin,  HIGH);
      // digitalWrite(steeringPin1, LOW);
      //
      // digitalWrite(steeringPin2, HIGH);
      // digitalWrite(motorPin, HIGH);
    }
    // 
    // analogWrite(motorPin1, 0);
    // analogWrite(motorPin2,150);

    // if (readingLeft < 255) {
    //   digitalWrite(steeringPin1, HIGH);
    //
    //   digitalWrite(steeringPin2, LOW);
    //
    //   // digitalWrite(steeringPin, HIGH);
    //   // digitalWrite(motorPin, HIGH);
    // } else if (readingRight < 255) {
    //   // digitalWrite(steeringPin,  HIGH);
    //   digitalWrite(steeringPin1, LOW);
    //
    //   digitalWrite(steeringPin2, HIGH);
    //   // digitalWrite(motorPin, HIGH);
    // }
  }
}


// void loop() {
//   // Clears the trigPin
// digitalWrite(rightTrigPin, LOW);
// delayMicroseconds(2);
// // Sets the trigPin on HIGH state for 10 micro seconds
// digitalWrite(rightTrigPin, HIGH);
// delayMicroseconds(10);
// digitalWrite(rightTrigPin, LOW);
// // Reads the echoPin, returns the sound wave travel time in microseconds
// long duration = pulseIn(rightEchoPin, HIGH);
// // Calculating the distance
// int distance= duration*0.034/2;
// // Prints the distance on the Serial Monitor
// Serial.print("Distance: ");
// Serial.println(distance);
// }

// #include <Arduino.h>
//
//
// int motorPin = 8;
//
// void setup() {
//
// }
//
// void loop() {
//   int i = 0;
//   while (1) {
//     if (i = 10000) {
//       break;
//     }
//     i = i + 1;
//     digitalWrite(motorPin, HIGH);
//   }
//
//
//    digitalWrite(motorPin, LOW);
//    delay(1000);
// }
