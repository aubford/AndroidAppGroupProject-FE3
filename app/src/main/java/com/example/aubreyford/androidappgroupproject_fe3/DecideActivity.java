package com.example.aubreyford.androidappgroupproject_fe3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DecideActivity extends AppCompatActivity {

    Button decide_back;
    Button decide_skip;
    ImageButton pic_a;
    ImageButton pic_b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);
        decide_skip = (Button) findViewById(R.id.decide_skip );

        DecideNavListener();

    }


    private void DecideNavListener() {

        decide_skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //LOAD ANOTHER SET

            }
        });
    }


}
