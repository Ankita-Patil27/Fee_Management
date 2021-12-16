package com.example.fee_management.Admin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fee_management.databinding.ActivityAdminRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Admin_Registration extends AppCompatActivity {

    ActivityAdminRegistrationBinding adminRegistrationBinding;
    String first_name,last_name,email,password,phone_no,userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminRegistrationBinding = ActivityAdminRegistrationBinding.inflate(getLayoutInflater());
        setContentView(adminRegistrationBinding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        adminRegistrationBinding.btnAdminSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_name = adminRegistrationBinding.etFirstname.getText().toString().trim();
                last_name = adminRegistrationBinding.etLastname.getText().toString().trim();
                email = adminRegistrationBinding.etEmail.getText().toString().trim();
                password = adminRegistrationBinding.etPassword.getText().toString().trim();
                phone_no = adminRegistrationBinding.etPhoneno.getText().toString().trim();

                //Validate Data
                if(TextUtils.isEmpty(first_name)){
                    adminRegistrationBinding.etFirstname.setError("First Name Required");
                    return;
                }
                if(TextUtils.isEmpty(last_name) ){
                    adminRegistrationBinding.etLastname.setError("Last Name Required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    adminRegistrationBinding.etEmail.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    adminRegistrationBinding.etPassword.setError("Password Required");
                    return;
                }
                /*if(phone_no == null){
                    adminRegistrationBinding.etPhoneno.setError("Phone Number Required");
                    return;
                }*/
               if((password.length()<=6) || (password.length()>=10) ){
                adminRegistrationBinding.etPassword.setError("Password Length between 6 to 10");
                return;
               }
                adminRegistrationBinding.progressBar2.setVisibility(View.VISIBLE);
                //Register the User in Firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Admin_Registration.this,"User Created",Toast.LENGTH_LONG).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference doc = firestore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("FirstName",first_name);
                            user.put("LastName",last_name);
                            user.put("Email",email);
                            user.put("PhoneNo",phone_no);
                            user.put("Password",password);
                            doc.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess : User Created for UID : " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure :  " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Admin_Login.class));
                            finish();
                        }else{
                            Toast.makeText(Admin_Registration.this,"Error Occured" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            adminRegistrationBinding.progressBar2.setVisibility(View.GONE);
                        }
                    }
                });




            }
        });
        adminRegistrationBinding.tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Registration.this,Admin_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}