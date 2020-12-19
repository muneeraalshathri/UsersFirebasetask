package com.mun.usersfirebasetask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mun.usersfirebasetask.adapters.UsersAdapter;
import com.mun.usersfirebasetask.models.User;
import com.mun.usersfirebasetask.sqlite.UserTableDataSource;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class SavedUsersActivity extends AppCompatActivity {

    ArrayList<User> users = new ArrayList<>();
    ListView listView;

    UserTableDataSource mUserTableDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_users);

        listView = findViewById(R.id.list_users);

        mUserTableDataSource = new UserTableDataSource(SavedUsersActivity.this);
        mUserTableDataSource.open();

        getUsers();
    }


    void getUsers() {

        users = mUserTableDataSource.selectAllUsers();

        UsersAdapter mUsersAdapter = new UsersAdapter(SavedUsersActivity.this, users);
        listView.setAdapter(mUsersAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(SavedUsersActivity.this, "Name is : " + users.get(i).getFirstName() + " " + users.get(i).getLastName(), Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                new AlertDialog.Builder(SavedUsersActivity.this)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to delete this user?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                               mUserTableDataSource.deleteUser(users.get(i).getId());
                                Toast.makeText(SavedUsersActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                                getUsers();

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return false;
            }
        });

    }


}