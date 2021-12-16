package com.example.fee_management.Admin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.fee_management.databinding.ActivityAdminLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Login extends AppCompatActivity {

    ActivityAdminLoginBinding adminLoginBinding;
    FirebaseAuth firebaseAuth;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminLoginBinding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(adminLoginBinding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        adminLoginBinding.btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate data
                email = adminLoginBinding.etEmail.getText().toString().trim();
                password = adminLoginBinding.etPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    adminLoginBinding.etEmail.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    adminLoginBinding.etPassword.setError("Password Required");
                    return;
                }
                if((password.length()<=6) || (password.length()>=10) ){
                    adminLoginBinding.etPassword.setError("Password Length between 6 to 10");
                    return;
                }
                adminLoginBinding.loginProgressbar.setVisibility(View.VISIBLE);

                //login
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"User Signed In",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Admin_HomeScreen.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error Occured" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            adminLoginBinding.loginProgressbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        adminLoginBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}