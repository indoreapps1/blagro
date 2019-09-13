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

public class DistributorOrderAdapter extends RecyclerView.Adapter<DistributorOrderAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<MyPojo> myPojoList;
    List<MyPojo> list;

    public DistributorOrderAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
        this.list = myPojoList;
    }

    @NonNull
    @Override
    public DistributorOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_distributor_order, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrderAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_name.setText("Distributor - " + list.get(i).getDName() + "\n" + "Retailer - " + list.get(i).getRName());
        myViewHolder.item_no.setText("Order Number - " + list.get(i).getOrder_no());
        myViewHolder.item_time.setText("Order Date - " + list.get(i).getDate());
        myViewHolder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("orderNo", list.get(i).getOrder_no());
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
        TextView item_no, item_time, item_name;
        CardView item_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_no = itemView.findViewById(R.id.item_no);
            item_time = itemView.findViewById(R.id.item_time);
            item_name = itemView.findViewById(R.id.item_name);
            item_card = itemView.findViewById(R.id.item_card);
        }
    }
}
