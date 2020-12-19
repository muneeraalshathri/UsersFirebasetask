package com.mun.usersfirebasetask.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mun.usersfirebasetask.models.User;

import java.util.ArrayList;

public class UserTableDataSource {

    public static final String TABLE_NAME = "user";

    //columns names
    public static final String COLUMN_ID   = "id";
    public static final String COLUMN_USER_ID   = "user_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL  = "email";
    public static final String COLUMN_PHONE  = "phone";

    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + "("
                    + COLUMN_ID   +" text,"
                    + COLUMN_USER_ID   +" integer,"
                    + COLUMN_FIRST_NAME   +" text,"
                    + COLUMN_LAST_NAME   +" text,"
                    + COLUMN_EMAIL + " text,"
                    + COLUMN_PHONE  + " text)";


    private static final String[] allColumns = {

            COLUMN_ID,
            COLUMN_USER_ID,
            COLUMN_FIRST_NAME,
            COLUMN_LAST_NAME,
            COLUMN_EMAIL,
            COLUMN_PHONE
    };


    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;


    public UserTableDataSource(Context context) {

        dbHelper = new DatabaseOpenHelper(context);
    }


    public void open() {
        database = dbHelper.getWritableDatabase();
    }


    public void close() {
        dbHelper.close();
    }


    public long addUser(User user) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_USER_ID, user.getUserId());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmailAddress());
        values.put(COLUMN_PHONE, user.getPhoneNumber());

        long result = database.insert(TABLE_NAME, null, values);
        return result;
    }

    public void updateUser(User user) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_USER_ID, user.getUserId());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmailAddress());
        values.put(COLUMN_PHONE, user.getPhoneNumber());

        database.update(TABLE_NAME, values, COLUMN_ID +"='" + user.getId() + "'", null);

        return;
    }


    public ArrayList<User> selectAllUsers() {

        ArrayList<User> users = new ArrayList<>();

        User oneUser;

        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                oneUser = new User();

                oneUser.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                oneUser.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                oneUser.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                oneUser.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                oneUser.setEmailAddress(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                oneUser.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));

                users.add(oneUser);
            }
        }
        return users;
    }


    public void deleteUser(String id) {

        database.delete(TABLE_NAME, COLUMN_ID +"='" + id + "'", null);

    }

}
