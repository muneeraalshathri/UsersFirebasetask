package com.mun.usersfirebasetask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.mun.usersfirebasetask.adapters.UsersAdapter;
import com.mun.usersfirebasetask.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    DatabaseReference dbReference;
    ArrayList<User> users = new ArrayList<>();

    ListView listView;
    Button btnAdd;
    Button btnGetWeatherData;
    Button btnShowSavedData;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        listView = findViewById(R.id.list_users);
        btnAdd = findViewById(R.id.btn_add_user);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsersListActivity.this, UserDetailsActivity.class);
                startActivity(intent);

            }
        });

        btnGetWeatherData = findViewById(R.id.btn_get_weather_data);
        btnGetWeatherData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsersListActivity.this, WeatherActivity.class);
                startActivity(intent);

            }
        });

        btnShowSavedData = findViewById(R.id.btn_show_saved_data);
        btnShowSavedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsersListActivity.this, SavedUsersActivity.class);
                startActivity(intent);

            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait.");

        fetchUsersData();
    }


    void fetchUsersData(){

        dialog.show();

        dbReference = FirebaseDatabase.getInstance().getReference().child("users");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dialog.dismiss();

                users = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    user.setId(snapshot.getKey());
                    users.add(user);
                }

                UsersAdapter usersAdapter = new UsersAdapter(UsersListActivity.this, users);
                listView.setAdapter(usersAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(UsersListActivity.this, UserDetailsActivity.class);
                        intent.putExtra("USER", users.get(i));
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }

        });

    }

}