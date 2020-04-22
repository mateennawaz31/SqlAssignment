package com.example.sqlassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DBAdapterClass extends RecyclerView.Adapter<DBAdapterClass.DBViewHolderClass>
{
    private ArrayList<DBModelClass> objectModelClassArrayList;

    public DBAdapterClass(ArrayList<DBModelClass> objectModelClassArrayList) {
        this.objectModelClassArrayList = objectModelClassArrayList;
    }

    @NonNull
    @Override
    public DBViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleLine= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_line,parent,false);
        DBViewHolderClass objectDbViewHolderClass=new DBViewHolderClass(singleLine);

        return objectDbViewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull DBViewHolderClass holder, int position) {
        holder.nameTV.setText(objectModelClassArrayList.get(position).getName());
        holder.addressTV.setText(objectModelClassArrayList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return objectModelClassArrayList.size();
    }

    class DBViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView nameTV,addressTV;

        public DBViewHolderClass(@NonNull View singleLine) {
            super(singleLine);
            nameTV=singleLine.findViewById(R.id.sl_nameTV);

            addressTV=singleLine.findViewById(R.id.sl_addressTV);
        }
    }
}
