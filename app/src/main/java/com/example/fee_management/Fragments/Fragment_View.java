package com.example.fee_management.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management.R;
import com.example.fee_management.Adapter.StudentAdapter;
import com.example.fee_management.ModelClass.StudentModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Fragment_View extends Fragment {
    RecyclerView student_list;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference reference = firestore.collection("display");
    StudentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view,container,false);
        student_list = v.findViewById(R.id.students_list);
        setupRecyclerView();
        return  v;
    }

    private void setupRecyclerView() {
        Query query = reference.orderBy("rollno", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<StudentModel> options = new FirestoreRecyclerOptions.Builder<StudentModel>()
                .setQuery(query,StudentModel.class)
                .build();
        adapter = new StudentAdapter(options);
        student_list.setLayoutManager(new LinearLayoutManager(getContext()));
        student_list.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null){
        adapter.stopListening();}
    }
}
