package com.example.user.myapplication.app;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.data.repo.InventoryRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryInAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> clientName;
    ArrayList<String> productName;
    ArrayList<String> Color;
    ArrayList<String> Number;
    ArrayList<String> InventoryId;
    private HashMap<String, String> mapValues = new HashMap<String, String>();

    public InventoryInAdapter(Activity context, ArrayList<String> clientName, ArrayList<String> productName, ArrayList<String> Color, ArrayList<String> Number, ArrayList<String> InventoryId) {
        super();
        this.context = context;
        this.clientName = clientName;
        this.productName = productName;
        this.Color = Color;
        this.Number = Number;
        this.InventoryId = InventoryId;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return clientName.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        // TODO Auto-generated method stub
        return mapValues;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtViewClientName;
        TextView txtViewProductName;
        TextView txtViewColor;
        EditText editTextNumber;
        TextView txtViewNumber;
        TextView textViewInventoryId;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        final InventoryInAdapter.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_row_4text_1edit, null);
            holder = new InventoryInAdapter.ViewHolder();
            holder.txtViewClientName = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewProductName = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewColor = (TextView) convertView.findViewById(R.id.textView3);
            holder.editTextNumber = (EditText) convertView.findViewById(R.id.editText1);
            holder.txtViewNumber = (TextView) convertView.findViewById(R.id.textView5);
            holder.textViewInventoryId = (TextView) convertView.findViewById(R.id.textViewHidden);
            convertView.setTag(holder);
        }
        else
        {
            holder = (InventoryInAdapter.ViewHolder) convertView.getTag();
        }

        holder.txtViewClientName.setText(clientName.get(position));
        holder.txtViewProductName.setText(productName.get(position));
        holder.txtViewColor.setText(Color.get(position));
        holder.txtViewNumber.setText(Number.get(position));
        holder.textViewInventoryId.setText(InventoryId.get(position));

        holder.editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count,int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                InventoryRepo inventoryRepo = new InventoryRepo();
                try {
                    float inventoryNum =  inventoryRepo.getInventoryNum(holder.textViewInventoryId.getText().toString().trim());
                    holder.txtViewNumber.setText(Float.toString(inventoryNum + Float.parseFloat(s.toString())));
                    mapValues.put(holder.textViewInventoryId.getText().toString().trim(), Float.toString(inventoryNum + Float.parseFloat(s.toString())));
                }catch(NumberFormatException e) {

                }
            }
        });

        return convertView;
    }
}
