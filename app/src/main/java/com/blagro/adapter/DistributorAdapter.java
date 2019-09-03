package com.blagro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blagro.R;
import com.blagro.activity.ViewDistributorOrderActivity;
import com.blagro.model.MyPojo;

import java.util.List;

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.MyViewHolder> {
    Context context;
    List<MyPojo> myPojoList;

    public DistributorAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_distributor, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_name.setText("Distributor Name - " + myPojoList.get(i).getName());
        myViewHolder.item_phone.setText("Phone Number -" + myPojoList.get(i).getMobile());
        myViewHolder.item_email.setText("Email - " + myPojoList.get(i).getEmail());
        myViewHolder.item_city.setText("City - " + myPojoList.get(i).getCity());
        myViewHolder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDistributorOrderActivity.class);
                intent.putExtra("DistributorId", myPojoList.get(i).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPojoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, item_phone, item_email, item_city;
        CardView item_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_phone = itemView.findViewById(R.id.item_phone);
            item_email = itemView.findViewById(R.id.item_email);
            item_city = itemView.findViewById(R.id.item_city);
            item_card = itemView.findViewById(R.id.item_card);
        }
    }
}
