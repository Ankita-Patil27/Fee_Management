package com.example.fee_management.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fee_management.R;
import com.example.fee_management.databinding.FragmentAddBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Fragment_Add extends Fragment {
    FragmentAddBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String UID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        EditText em = v.findViewById(R.id.student_email);
        EditText pass = v.findViewById(R.id.student_password);
        EditText prn = v.findViewById(R.id.et_prnno);
        EditText fn = v.findViewById(R.id.et_fullname);
        EditText dobd = v.findViewById(R.id.et_dob);
        EditText mn = v.findViewById(R.id.student_mobileno);
        EditText rn = v.findViewById(R.id.student_rollno);
        EditText stream = v.findViewById(R.id.student_stream);
        EditText year = v.findViewById(R.id.student_year);
        EditText total = v.findViewById(R.id.student_totalfee);
        EditText pfee = v.findViewById(R.id.student_fee_paid);
        EditText datefee = v.findViewById(R.id.student_fee_paid_date);
        EditText rfee = v.findViewById(R.id.student_fee_remaining);
        ProgressBar bar = v.findViewById(R.id.progressbar_student_register);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        UID = auth.getCurrentUser().getUid();
        Button btnreg = v.findViewById(R.id.btn_student_register);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                String email = em.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String prn_no = prn.getText().toString().trim();
                String full_name = fn.getText().toString().trim();
                String dob = dobd.getText().toString().trim();
                String mobile_no = mn.getText().toString().trim();
                String roll_no = rn.getText().toString().trim();
                String stream_course = stream.getText().toString().trim();
                String academic_year = year.getText().toString().trim();
                String total_fee = total.getText().toString().trim();
                String paid_fee = pfee.getText().toString().trim();
                String fee_paid_date = datefee.getText().toString().trim();
                String remaining_fee = rfee.getText().toString().trim();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            CollectionReference doc1 = firestore.collection("Students");
                            Map<String, Object> students = new HashMap<>();
                            students.put("PRN Number", prn_no);
                            students.put("Full Name", full_name);
                            students.put("DOB", dob);
                            students.put("PhoneNo", mobile_no);
                            students.put("Roll No", roll_no);
                            students.put("Stream", stream_course);
                            students.put("Email", email);
                            students.put("Academic Year", academic_year);
                            students.put("Total Fee", total_fee);
                            students.put("Fee Paid", paid_fee);
                            students.put("Fee Paid Date", fee_paid_date);
                            students.put("Remaining Fee", remaining_fee);
                            doc1.document(email).set(students).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess : User Created for UID : " + UID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure :  " + e.toString());
                                }
                            });
                            CollectionReference doc2 = firestore.collection("display");
                            Map<String, Object> view_student = new HashMap<>();
                            view_student.put("rollno", roll_no);
                            view_student.put("fullname", full_name);
                            view_student.put("stream", stream_course);
                            view_student.put("year", academic_year);
                            doc2.document(email).set(view_student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Log.d(TAG, "onFailure :  " + e.toString());
                                }
                            });
                            Toast.makeText(getContext(), "User Created", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_View()).commit();
                            //startActivity(new Intent(getContext(), Admin_Login.class));
                        } else {
                            Toast.makeText(getContext(), "Error Occured" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        return v;
    }
}
