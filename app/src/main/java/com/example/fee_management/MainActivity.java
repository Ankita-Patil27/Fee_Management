package com.example.fee_management;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView iv_logo;
    TextView tv_welcome;
    int Second_Delayed = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_logo = findViewById(R.id.iv_logo);
        tv_welcome = findViewById(R.id.tv_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,User_Mode.class);
                startActivity(intent);
                finish();
            }
        },Second_Delayed * 3000 );
    }
}