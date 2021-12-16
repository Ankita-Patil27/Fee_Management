package com.example.fee_management.Admin.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fee_management.Fragments.Fragment_Add;
import com.example.fee_management.Fragments.Fragment_View;
import com.example.fee_management.R;
import com.example.fee_management.databinding.ActivityAdminHomeScreenBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Admin_HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    
    ActivityAdminHomeScreenBinding homeScreenBinding;
    ActionBarDrawerToggle toggle;
    FirebaseAuth firebaseAuth;
    String userID;
    FirebaseFirestore firestore;
    TextView title,subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = ActivityAdminHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(homeScreenBinding.getRoot());
        View header = homeScreenBinding.navMenu.getHeaderView(0);
        title = header.findViewById(R.id.tv_title);
        subtitle = header.findViewById(R.id.tv_subtitle);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference doc = firestore.collection("Users").document(userID);
        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String first = value.getString("FirstName");
                String last = value.getString("LastName");
                String FullName = first + " "+ last ;
                title.setText(FullName);
                String Email = value.getString("Email");
                subtitle.setText(Email);
            }
        });
        setSupportActionBar(homeScreenBinding.toolbar);

        homeScreenBinding.navMenu.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,homeScreenBinding.layoutDrawer,homeScreenBinding.toolbar,R.string.open,R.string.close);
        homeScreenBinding.layoutDrawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_View()).commit();
        homeScreenBinding.navMenu.setCheckedItem(R.id.view_menu);
        }

        homeScreenBinding.ivSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Admin_Login.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(homeScreenBinding.layoutDrawer.isDrawerOpen(GravityCompat.START)){
            homeScreenBinding.layoutDrawer.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_Add()).commit();
                break;
            case R.id.view_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_View()).commit();
                break;
        }
        homeScreenBinding.layoutDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}