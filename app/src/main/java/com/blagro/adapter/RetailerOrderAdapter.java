package com.blagro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.blagro.R;
import com.blagro.activity.OrderActivity;
import com.blagro.model.MyPojo;

import java.util.ArrayList;
import java.util.List;

public class RetailerOrderAdapter extends RecyclerView.Adapter<RetailerOrderAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<MyPojo> myPojoList;
    List<MyPojo> list;

    public RetailerOrderAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.list = myPojoList;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public RetailerOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_retailer_order, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrderAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_name.setText("Distributor - " + list.get(i).getDName());
        myViewHolder.item_rname.setText("Retailer - " + list.get(i).getRName());
        myViewHolder.item_no.setText("Order Number - " + list.get(i).getOrder_no());
        myViewHolder.item_time.setText("" + list.get(i).getDate());
        myViewHolder.qty.setText("Order Qty - " + list.get(i).getOrderQty());
        myViewHolder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("orderNo", list.get(i).getOrder_no());
                intent.putExtra("date", list.get(i).getDate());
                intent.putExtra("address", list.get(i).getAddress());
                intent.putExtra("dname", list.get(i).getDName());
                intent.putExtra("rname", list.get(i).getRName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<MyPojo>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<MyPojo> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = myPojoList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<MyPojo> getFilteredResults(String constraint) {
        List<MyPojo> results = new ArrayList<>();

        for (MyPojo item : myPojoList) {
            if (item.getDName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
            if (item.getRName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
            if (String.valueOf(item.getOrder_no()).contains(constraint)) {
                results.add(item);
            }
            if (item.getDate().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_no, item_time, item_name, qty, item_rname;
        CardView item_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_no = itemView.findViewById(R.id.item_no);
            item_time = itemView.findViewById(R.id.item_time);
            item_card = itemView.findViewById(R.id.item_card);
            item_name = itemView.findViewById(R.id.item_name);
            qty = itemView.findViewById(R.id.qty);
            item_rname = itemView.findViewById(R.id.item_rname);
        }
    }
}
