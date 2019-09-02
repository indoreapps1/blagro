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

public class RetailerOrderAdapter extends RecyclerView.Adapter<RetailerOrderAdapter.MyViewHolder> {
    Context context;
    List<MyPojo> myPojoList;

    public RetailerOrderAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public RetailerOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_retailer_order, viewGroup, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrderAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.item_no.setText(myPojoList.get(i).getOrder_no());
        myViewHolder.item_time.setText(myPojoList.get(i).getDate());

    }

    @Override
    public int getItemCount() {
        return myPojoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_no, item_time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_no=itemView.findViewById(R.id.item_no);
            item_time=itemView.findViewById(R.id.item_time);
        }
    }
}
