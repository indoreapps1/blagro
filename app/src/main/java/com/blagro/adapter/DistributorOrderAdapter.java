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
import com.blagro.activity.OrderActivity;
import com.blagro.model.MyPojo;

import java.util.List;

public class DistributorOrderAdapter extends RecyclerView.Adapter<DistributorOrderAdapter.MyViewHolder> {
    Context context;
    List<MyPojo> myPojoList;

    public DistributorOrderAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public DistributorOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_distributor_order, viewGroup, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrderAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_no.setText("Order Number - "+myPojoList.get(i).getOrder_no());
        myViewHolder.item_time.setText("Oeder Date - "+myPojoList.get(i).getDate());
        myViewHolder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("orderNo", myPojoList.get(i).getOrder_no());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPojoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_no, item_time;;
        CardView item_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_no=itemView.findViewById(R.id.item_no);
            item_time=itemView.findViewById(R.id.item_time);
            item_card=itemView.findViewById(R.id.item_card);
        }
    }
}
