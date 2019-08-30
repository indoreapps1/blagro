package com.blagro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blagro.R;
import com.blagro.model.MyPojo;

import java.util.List;

public class RetailerAdapter extends RecyclerView.Adapter<RetailerAdapter.MyViewHolder> {
    Context context;
    List<MyPojo> myPojoList;

    public RetailerAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_retailer, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.item_name.setText("Retailer Name - "+myPojoList.get(i).getName());
        myViewHolder.item_phone.setText("Phone Number -"+myPojoList.get(i).getMobile());
        myViewHolder.item_email.setText("Email - "+myPojoList.get(i).getEmail());
        myViewHolder.item_city.setText("City - "+myPojoList.get(i).getCity());
    }

    @Override
    public int getItemCount() {
        return myPojoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, item_phone, item_email, item_city;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name=itemView.findViewById(R.id.item_name);
            item_phone=itemView.findViewById(R.id.item_phone);
            item_email=itemView.findViewById(R.id.item_email);
            item_city=itemView.findViewById(R.id.item_city);
        }
    }
}
