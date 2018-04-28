package com.example.user.myapplication.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.data.repo.InventoryRepo;

import java.util.ArrayList;

public class InventoryListViewAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> inventoryId;
    ArrayList<String> clientName;
    ArrayList<String> productName;
    ArrayList<String>Color;
    ArrayList<String> Number;
    ArrayList<String> Date;

    public InventoryListViewAdapter(Activity context, ArrayList<String> inventoryId, ArrayList<String> clientName, ArrayList<String> productName, ArrayList<String> Color, ArrayList<String> Number, ArrayList<String> Date) {
        super();
        this.context = context;
        this.inventoryId = inventoryId;
        this.clientName = clientName;
        this.productName = productName;
        this.Color = Color;
        this.Number = Number;
        this.Date = Date;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return clientName.size();
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
        TextView txtViewClientName;
        TextView txtViewProductName;
        TextView txtViewColor;
        TextView txtViewNumber;
        TextView txtViewDate;
        TextView textViewHidden;
        Button btnDelete;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        InventoryListViewAdapter.ViewHolder holder;
        final LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_row_5col, null);
            holder = new InventoryListViewAdapter.ViewHolder();
            holder.txtViewClientName = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewProductName = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewColor = (TextView) convertView.findViewById(R.id.textView3);
            holder.txtViewNumber = (TextView) convertView.findViewById(R.id.textView4);
            holder.txtViewDate = (TextView) convertView.findViewById(R.id.textView5);
            holder.textViewHidden = (TextView) convertView.findViewById(R.id.textViewHidden);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        }
        else
        {
            holder = (InventoryListViewAdapter.ViewHolder) convertView.getTag();
        }

        holder.txtViewClientName.setText(clientName.get(position));
        holder.txtViewProductName.setText(productName.get(position));
        holder.txtViewColor.setText(Color.get(position));
        holder.txtViewNumber.setText(Number.get(position));
        holder.txtViewDate.setText(Date.get(position));
        holder.textViewHidden.setText(inventoryId.get(position));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InventoryRepo inventoryRepo = new InventoryRepo();
                inventoryRepo.delete(inventoryId.get(position).trim());
                inventoryId.remove(position);
                clientName.remove(position);
                productName.remove(position);
                Color.remove(position);
                Number.remove(position);
                Date.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
