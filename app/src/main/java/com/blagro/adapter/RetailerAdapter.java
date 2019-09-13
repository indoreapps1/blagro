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
import com.blagro.activity.ViewRetailerOrderActivity;
import com.blagro.model.MyPojo;

import java.util.ArrayList;
import java.util.List;

public class RetailerAdapter extends RecyclerView.Adapter<RetailerAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<MyPojo> myPojoList;
    List<MyPojo> list;
    boolean flag;

    public RetailerAdapter(Context context, List<MyPojo> myPojoList, boolean flag) {
        this.context = context;
        this.list = myPojoList;
        this.myPojoList = myPojoList;
        this.flag = flag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_retailer, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_name.setText("Retailer Name - " + list.get(i).getName());
        myViewHolder.item_phone.setText("Phone Number -" + list.get(i).getMobile());
        myViewHolder.item_email.setText("Email - " + list.get(i).getEmail());
        myViewHolder.item_city.setText("City - " + list.get(i).getCity());
        myViewHolder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Intent intent = new Intent(context, ViewRetailerOrderActivity.class);
                    intent.putExtra("RetailerId", list.get(i).getId());
                    context.startActivity(intent);
                }
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
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
            if (item.getMobile().toLowerCase().contains(constraint)) {
                results.add(item);
            }
            if (item.getEmail().toLowerCase().contains(constraint)) {
                results.add(item);
            }
            if (item.getCity().toLowerCase().contains(constraint)) {
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
