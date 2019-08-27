package com.blagro.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blagro.R;
import com.blagro.activity.CreateOrderActivity;
import com.blagro.database.DbHelper;
import com.blagro.model.MyPojo;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.MyViewHolder> {
    Context context;
    List<MyPojo> myPojoList;
    DbHelper dbHelper;

    public BasketAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public BasketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_basket, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BasketAdapter.MyViewHolder myViewHolder, final int i) {
            myViewHolder.item_name.setText(myPojoList.get(i).getName());
            myViewHolder.item_quantity.setText(myPojoList.get(i).getQuant());
            myViewHolder.item_gst.setText(myPojoList.get(i).getGst());

            myViewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbHelper dbHelper = new DbHelper(context);
                    if (myPojoList.size() == 0) {//delete category data if all item deleted from this ctegory
                        Intent intent=new Intent(context, CreateOrderActivity.class);
                        context.startActivity(intent);
                    }
                    else {
                        dbHelper.deleteBasketOrderDataById(myPojoList.get(i).getId());
                        myPojoList.remove(i);
                    }
//                        if (myPojoList.size() == 0) {
//                            ((FragmentActivity) context).getSupportFragmentManager().popBackStack();//back to previes screen
//                        } else {
//                            Intent myIntent = new Intent("basketItem");
//                            myIntent.putExtra("basketFlag", true);
//                            LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
//                        }

                    notifyDataSetChanged();
                }
            });

    }


    @Override
    public int getItemCount() {
        return myPojoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_gst, item_quantity, item_name, tv_delete;
        CardView card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_gst = itemView.findViewById(R.id.item_gst);
            item_quantity = itemView.findViewById(R.id.item_quantity);
            item_name = itemView.findViewById(R.id.item_name);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }


}
