/**
 * main page
 * allows user to navigate to different modes
 *
 * @author: Harshal Chhaniyara
 * @since: 1.0
 *
 * Copyright 2018 HSC
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package ca.ualberta.batbot;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout myLayout;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        myLayout = (ConstraintLayout) findViewById(R.id.myLayout);

        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        Button mapping = findViewById(R.id.map_button);
        Button remote = findViewById(R.id.remote_button);
        Button followMe = findViewById(R.id.follow_button);
        mapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent newprofile_intent = new Intent(MainActivity.this, newprofile.class);
//                startActivityForResult(newprofile_intent, 0);
            }
        });
        remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent login_intent = new Intent(MainActivity.this, login.class);
//                startActivityForResult(login_intent, 0);

            }
        });
        followMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, transfer.class);
//                startActivity(intent);
            }
        });
    }
}
