package com.blagro.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.activity.CreateOrderActivity;
import com.blagro.activity.MainActivity;
import com.blagro.database.DbHelper;
import com.blagro.model.MyPojo;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    List<MyPojo> myPojoList;
    private ProductItemActionListener actionListener;

    public ProductAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    public void setActionListener(ProductItemActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.productTitle.setText(myPojoList.get(i).getName() + " (" + myPojoList.get(i).getUnit() + ")");
        myViewHolder.producGst.setText("GST- " + myPojoList.get(i).getGst());
        if (myPojoList.get(i).getCountValue() != 0) {
            myViewHolder.textView_nos.setText("" + myPojoList.get(i).getCountValue());
        }
        myViewHolder.increase_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count1 = (int) myPojoList.get(i).getCountValue();
                myPojoList.get(i).setCountValue(count1 + 1);
                //   addProduct(count1 + 1, position, true);
                notifyDataSetChanged();
            }
        });
        myViewHolder.decrement_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count1 = (int) myPojoList.get(i).getCountValue();
                if (count1 > 1) {
                    myPojoList.get(i).setCountValue(count1 - 1);
                }
                notifyDataSetChanged();
            }

        });
        myViewHolder.textView_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CreateOrderActivity rootActivity = (CreateOrderActivity) context;
                if (rootActivity != null) {
                    addItemToCart(i);
                    Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    assert vb != null;
                    vb.vibrate(20);
                    rootActivity.setItemCart();
                }

            }

        });

    }

    public void addItemToCart(int position) {
        DbHelper dbHelper = new DbHelper(context);
        MyPojo myBasket = new MyPojo();
        myBasket.setId(myPojoList.get(position).getId());
        myBasket.setName(myPojoList.get(position).getName());
        myBasket.setUnit(myPojoList.get(position).getUnit());
        myBasket.setQuant(myPojoList.get(position).getCountValue());
        myBasket.setGst(myPojoList.get(position).getGst());
        dbHelper.upsertBasketOrderData(myBasket);
        notifyDataSetChanged();
        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return myPojoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, decrement_Product, increase_Product, textView_addToCart, producGst;
        EditText textView_nos;
        CardView card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.productTitle);
            decrement_Product = itemView.findViewById(R.id.decrement_Product);
            decrement_Product = itemView.findViewById(R.id.decrement_Product);
            textView_nos = itemView.findViewById(R.id.textView_nos);
            increase_Product = itemView.findViewById(R.id.increase_Product);
            textView_addToCart = itemView.findViewById(R.id.textView_addToCart);
            card_view = itemView.findViewById(R.id.card_view);
            producGst = itemView.findViewById(R.id.producGst);
        }
    }


    public interface ProductItemActionListener {
        void onItemTap(CardView cardView);
    }

}
