package com.mun.usersfirebasetask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mun.usersfirebasetask.models.User;
import com.mun.usersfirebasetask.sqlite.UserTableDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetailsActivity extends AppCompatActivity {

    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPhone;


    Button btnAdd;
    Button btnUpdate;
    Button btnDelete;
    Button btnSaveLocal;

    ProgressDialog dialog;

    User currentUser = null;

    DatabaseReference dbReference;

    UserTableDataSource mUserTableDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUser();

            }
        });

        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               updateUser();

            }
        });

        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              deleteUser();

            }
        });

        btnSaveLocal = findViewById(R.id.btn_save_local);
        btnSaveLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUserTableDataSource.addUser(currentUser);
                Toast.makeText(UserDetailsActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait.");

        mUserTableDataSource = new UserTableDataSource(UserDetailsActivity.this);
        mUserTableDataSource.open();


        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("USER")) {
            currentUser = (User) b.getSerializable("USER");

            etFirstName.setText(currentUser.getFirstName());
            etLastName.setText(currentUser.getLastName());
            etEmail.setText(currentUser.getEmailAddress());
            etPhone.setText(currentUser.getPhoneNumber());

            btnAdd.setVisibility(View.GONE);
        } else {
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnSaveLocal.setVisibility(View.GONE);
        }
    }

    void addUser() {

        User user = new User();
        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setEmailAddress(etEmail.getText().toString());
        user.setPhoneNumber(etPhone.getText().toString());

        dialog.show();
        dbReference = FirebaseDatabase.getInstance().getReference().child("users").push();
        dbReference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                dialog.dismiss();
                Toast.makeText(UserDetailsActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                dialog.dismiss();
                Toast.makeText(UserDetailsActivity.this, "Error on Add", Toast.LENGTH_SHORT).show();

            }
        });

    }

    void updateUser() {

        currentUser.setFirstName(etFirstName.getText().toString());
        currentUser.setLastName(etLastName.getText().toString());
        currentUser.setEmailAddress(etEmail.getText().toString());
        currentUser.setPhoneNumber(etPhone.getText().toString());

        dialog.show();
        dbReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getId());
        dbReference.setValue(currentUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                dialog.dismiss();
                Toast.makeText(UserDetailsActivity.this, "Updated Success", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                dialog.dismiss();
                Toast.makeText(UserDetailsActivity.this, "Error on Update", Toast.LENGTH_SHORT).show();

            }
        });

    }

    void deleteUser(){

        dialog.show();
        dbReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getId());
        dbReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                dialog.dismiss();
                Toast.makeText(UserDetailsActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                dialog.dismiss();
                Toast.makeText(UserDetailsActivity.this, "Error on Delete", Toast.LENGTH_SHORT).show();

            }
        });

    }
}