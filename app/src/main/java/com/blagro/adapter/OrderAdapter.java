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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context context;
    private List<MyPojo> myPojoList;

    public OrderAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.txt_category.setText("Product Category - " + myPojoList.get(i).getCategory());
        myViewHolder.txt_product.setText("" + myPojoList.get(i).getName());
        myViewHolder.txt_quantity.setText("" + myPojoList.get(i).getQrt() + " (" + myPojoList.get(i).getUnit() + ")");
        myViewHolder.txt_gst.setText("" + myPojoList.get(i).getGst());
        myViewHolder.rate.setText("" + myPojoList.get(i).getRate());
        myViewHolder.amount.setText("" + myPojoList.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return myPojoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_category, txt_product, txt_quantity, txt_gst, rate, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_product = itemView.findViewById(R.id.txt_product);
            txt_quantity = itemView.findViewById(R.id.txt_quantity);
            txt_gst = itemView.findViewById(R.id.txt_gst);
            rate = itemView.findViewById(R.id.rate);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
