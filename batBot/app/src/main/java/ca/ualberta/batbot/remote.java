package ca.ualberta.batbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class remote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        JoystickView joystick = (JoystickView) findViewById(R.id.joystickView);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {

                if (strength < 25) {
                    // motor 2 speed set to 0
                }
                if (strength >= 25 && strength < 50) {
                    // motor 2 speed set 1
                }
                if (strength >= 50 && strength < 75) {
                    // motor 2 speed set 2
                }
                if (strength >= 75 && strength < 101) {
                    // motor 2 speed set 3
                }



                if (angle >= 60 && angle <= 120) {
                    // motor wheel straight
                    // motor 2 forward
                }
                if (angle >= 240 && angle <= 300) {
                    // motor wheel straight
                    // motor 2 back
                }
                if (angle >= 150 && angle <= 210) {
                    // motor wheel full left
                    // motor 2 front
                }
                if ((angle >= 0 && angle <= 30) || (angle >= 330 && angle <= 360)) {
                    // motor wheel full right
                    // motor 2 front
                }
                if (angle > 30 && angle < 60) {
                    // motor wheel half right
                    // motor 2 forward
                }
                if (angle > 120 && angle < 150) {
                    // motor wheel half left
                    // motor 2 forward
                }
                if (angle > 210 && angle < 240) {
                    // motor wheel half right
                    // motor 2 back
                }
                if (angle > 300 && angle < 330) {
                    // motor wheel half left
                    // motor 2 back
                }
            }
        });
    }
}
