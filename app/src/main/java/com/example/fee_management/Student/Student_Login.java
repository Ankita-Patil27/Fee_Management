package com.example.fee_management.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.fee_management.Student.Student_HomeScreen;
import com.example.fee_management.databinding.ActivityStudentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Student_Login extends AppCompatActivity {

    ActivityStudentLoginBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        binding.btnAdminSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar.setVisibility(View.VISIBLE);
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    binding.etEmail.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    binding.etPassword.setError("Password Required");
                    return;
                }
                if((password.length()<=6) || (password.length()>=10) ){
                    binding.etPassword.setError("Password Length between 6 to 10");
                    return;
                }
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), Student_HomeScreen.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }
}