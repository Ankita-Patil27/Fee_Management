package com.example.fee_management.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fee_management.R;
import com.example.fee_management.ModelClass.StudentModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StudentAdapter extends FirestoreRecyclerAdapter<StudentModel, StudentAdapter.studentViewHolder> {

    public StudentAdapter(@NonNull FirestoreRecyclerOptions<StudentModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_student, parent,false);
        return new studentViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull studentViewHolder studentViewHolder, int i,@NonNull StudentModel studentModel) {
        studentViewHolder.rollno.setText(studentModel.getRollno());
        studentViewHolder.name.setText(studentModel.getFullname());
        studentViewHolder.stream.setText(studentModel.getStream());
        studentViewHolder.year.setText(studentModel.getYear());
    }


    class  studentViewHolder extends RecyclerView.ViewHolder  {

        TextView rollno,name,stream,year;

        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
            rollno = itemView.findViewById(R.id.rv_rollno);
            name = itemView.findViewById(R.id.rv_fullname);
            stream = itemView.findViewById(R.id.rv_stream);
            year = itemView.findViewById(R.id.rv_year);
        }
    }
}
