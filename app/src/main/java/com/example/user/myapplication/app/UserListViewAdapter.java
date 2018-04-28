package com.example.user.myapplication.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.myapplication.R;

import java.util.ArrayList;

public class UserListViewAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> Account;
    ArrayList<String> Password;
    ArrayList<String> Priority;

    public UserListViewAdapter(Activity context, ArrayList<String> Account, ArrayList<String> Password, ArrayList<String> Priority) {
        super();
        this.context = context;
        this.Account = Account;
        this.Password = Password;
        this.Priority = Priority;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Account.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtViewAccount;
        TextView txtViewPassword;
        TextView txtViewPriority;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_row_3col, null);
            holder = new ViewHolder();
            holder.txtViewAccount = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewPassword = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewPriority = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewAccount.setText(Account.get(position));
        holder.txtViewPassword.setText(Password.get(position));
        holder.txtViewPriority.setText(Priority.get(position));

        return convertView;
    }
}
