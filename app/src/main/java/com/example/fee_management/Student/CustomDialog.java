package com.example.fee_management.Student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fee_management.R;

public class CustomDialog extends AppCompatDialogFragment {
    EditText et_c_password;
    private CustomDialogListner listner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_password,null);
        dialogView.setBackgroundColor(Color.WHITE);
        builder.setView(dialogView).setTitle("Change Password").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = et_c_password.getText().toString();
                listner.applychanges(password);
            }
        });
        et_c_password= dialogView.findViewById(R.id.et_c_password);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner = (CustomDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() );
        }
    }

    public interface CustomDialogListner{
        void applychanges(String password);
    }
}
