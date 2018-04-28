package com.example.user.myapplication.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.data.repo.ClientRepo;
import com.example.user.myapplication.data.repo.InventoryRepo;
import com.example.user.myapplication.data.repo.ProductRepo;
import com.example.user.myapplication.data.repo.UserRepo;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> Name;
    ArrayList<String> Id;

    public ListViewAdapter(Activity context, ArrayList<String> Name, ArrayList<String> Id) {
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
        Button btnDelete;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_row_2col, null);
            holder = new ViewHolder();
            holder.txtViewName = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewId = (TextView) convertView.findViewById(R.id.textView2);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewName.setText(Name.get(position));
        holder.txtViewId.setText(Id.get(position));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String objStr = Id.get(position).replaceAll("[0-9]+","").trim();
                objStr = objStr.replace("編號: ", "");
                InventoryRepo inventoryRepo = new InventoryRepo();
                switch (objStr) {
                    case "CL":
                        ClientRepo clientRepo = new ClientRepo();
                        clientRepo.delete(Id.get(position).replaceAll("編號:","").trim());
                        inventoryRepo.deleteClient(Id.get(position).replaceAll("編號:","").trim());
                        Name.remove(position);
                        Id.remove(position);
                        notifyDataSetChanged();
                        break;
                    case "P":
                        ProductRepo productRepo = new ProductRepo();
                        productRepo.delete(Id.get(position).trim().replaceAll("編號:","").trim());
                        inventoryRepo.deleteProduct(Id.get(position).trim().replaceAll("編號:","").trim());
                        Name.remove(position);
                        Id.remove(position);
                        notifyDataSetChanged();
                        break;
                    default:
                        UserRepo userRepo = new UserRepo();
                        userRepo.delete(Name.get(position).trim());
                        Name.remove(position);
                        Id.remove(position);
                        notifyDataSetChanged();
                        break;
                }
            }
        });
        return convertView;
    }
}
