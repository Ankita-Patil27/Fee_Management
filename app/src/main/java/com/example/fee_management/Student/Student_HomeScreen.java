package com.example.fee_management.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fee_management.R;
import com.example.fee_management.databinding.ActivityStudentHomeScreenBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Student_HomeScreen extends AppCompatActivity implements CustomDialog.CustomDialogListner {

    ActivityStudentHomeScreenBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    EditText et_c_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        et_c_password = findViewById(R.id.et_c_password);
        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Student_HomeScreen.this, Student_Login.class));
                finish();
            }
        });
        binding.ivPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        displayInfo();

    }

    private void displayInfo() {
        String UID = auth.getCurrentUser().getEmail();
        DocumentReference doc = firestore.collection("Students").document(UID);
        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String year = value.getString("Academic Year");
                String DOB = value.getString("DOB");
                String fee_paid = value.getString("Fee Paid");
                String Email = value.getString("Email");
                String fee_paid_date = value.getString("Fee Paid Date");
                String full_name = value.getString("Full Name");
                String PRN = value.getString("PRN Number");
                String phone_no = value.getString("PhoneNo");
                String remaining_fee = value.getString("Remaining Fee");
                String roll_no = value.getString("Roll No");
                String stream = value.getString("Stream");
                String total_fee = value.getString("Total Fee");
                binding.txtStrm.setText(stream);
                binding.txtUid.setText(PRN);
                binding.txPh.setText(phone_no);
                binding.txtDob.setText(DOB);
                binding.txtEmail.setText(Email);
                binding.txtYear.setText(year);
                binding.txtRollno.setText(roll_no);
                binding.txTfee.setText(total_fee);
                binding.txtFpaid.setText(fee_paid);
                binding.txtPdate.setText(fee_paid_date);
                binding.txtName.setText(full_name);
                binding.txtFrem.setText(remaining_fee);
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialog() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(getSupportFragmentManager(),"Change Password");
    }




    @Override
    public void applychanges(String password) {
        FirebaseUser User = auth.getCurrentUser();
        User.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Student_Login.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}