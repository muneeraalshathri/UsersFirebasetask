package com.mun.usersfirebasetask.adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mun.usersfirebasetask.R;
import com.mun.usersfirebasetask.models.User;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {

    private static ArrayList<User> users;
    public Context mContext;

    public UsersAdapter(Context context, ArrayList<User> comingUsers) {
        users = comingUsers;
        mContext = context;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_user, null);

        TextView tvName = convertView.findViewById(R.id.tv_user_name);
        tvName.setText("Name: " + users.get(position).getFirstName() + " " + users.get(position).getLastName());


        TextView tvEmail = convertView.findViewById(R.id.tv_user_email);
        tvEmail.setText("Email: " + users.get(position).getEmailAddress());

        TextView tvPhone = convertView.findViewById(R.id.tv_user_phone);
        tvPhone.setText("Phone: " + users.get(position).getPhoneNumber());

        return convertView;
    }

    //region other methods
    public int getCount() {
        return users.size();
    }

    public Object getItem(int position) {
        return users.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    //endregion

}