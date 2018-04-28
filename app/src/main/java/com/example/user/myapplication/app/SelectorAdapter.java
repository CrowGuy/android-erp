package com.example.user.myapplication.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.myapplication.R;

import java.util.ArrayList;

public class SelectorAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> Name;
    ArrayList<String> Id;

    public SelectorAdapter(Activity context, ArrayList<String> Name, ArrayList<String> Id) {
        super();
        this.context = context;
        this.Name = Name;
        this.Id = Id;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Name.size();
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
        TextView txtViewName;
        TextView txtViewId;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.selector_row, null);
            holder = new ViewHolder();
            holder.txtViewName = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewId = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewName.setText(Name.get(position));
        holder.txtViewId.setText(Id.get(position));

        return convertView;
    }
}
