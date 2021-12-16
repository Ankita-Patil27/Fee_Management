package com.example.fee_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fee_management.Admin.Admin.Admin_HomeScreen;
import com.example.fee_management.Admin.Admin.Admin_Login;
import com.example.fee_management.Student.Student_HomeScreen;
import com.example.fee_management.Student.Student_Login;
import com.example.fee_management.databinding.ActivityUserModeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_Mode extends AppCompatActivity {

    ActivityUserModeBinding userModeBinding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModeBinding = ActivityUserModeBinding.inflate(getLayoutInflater());
        setContentView(userModeBinding.getRoot());
        auth = FirebaseAuth.getInstance();
        userModeBinding.layoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                if (user == null){
                Intent intent = new Intent(User_Mode.this, Admin_Login.class);
                startActivity(intent);}
                else{
                    Intent intent = new Intent(User_Mode.this, Admin_HomeScreen.class);
                    startActivity(intent);
                }
            }
        });
        userModeBinding.layoutStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                if (user == null){
                Intent intent = new Intent(User_Mode.this, Student_Login.class);
                startActivity(intent);}
                else{
                    Intent intent = new Intent(User_Mode.this, Student_HomeScreen.class);
                    startActivity(intent);
                }
            }
        });
    }
}