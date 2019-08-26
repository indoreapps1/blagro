package com.blagro.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blagro.R;
import com.blagro.activity.CreateOrderActivity;
import com.blagro.activity.MainActivity;
import com.blagro.database.DbHelper;
import com.blagro.model.MyPojo;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    List<MyPojo> myPojoList;

    public ProductAdapter(Context context, List<MyPojo> myPojoList) {
        this.context = context;
        this.myPojoList = myPojoList;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.productTitle.setText(myPojoList.get(i).getName());
        if (myPojoList.get(i).getCountValue() != 0) {
            myViewHolder.textView_nos.setText(""+myPojoList.get(i).getCountValue());
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
                    // addProduct(count1 - 1, position, true);
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
        myBasket.setName(myPojoList.get(position).getName());
//        myBasket.setUnit(myPojoList.get(position).getCountValue());
//        myBasket.setQuantity(FilteruserList.get(position).getCountValue());

//        if (categoryName != null && !categoryName.equals("")) {
//            myBasket.setCategoryName(categoryName);
//        } else {
//            Result result = dbHelper.getCategoryData(FilteruserList.get(position).getCategoryId());
//            myBasket.setCategoryName(result.getCategoryName());
//        }
        dbHelper.upsertBasketOrderData(myBasket);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myPojoList.size();
    }

//    private void setProductValue() {
//        textView_nos.setText("" + count);
//        txt_stock.setText("Quantity-" + count);
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, decrement_Product, textView_nos, increase_Product, textView_addToCart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle=itemView.findViewById(R.id.productTitle);
            decrement_Product=itemView.findViewById(R.id.decrement_Product);
            decrement_Product=itemView.findViewById(R.id.decrement_Product);
            textView_nos=itemView.findViewById(R.id.textView_nos);
            increase_Product=itemView.findViewById(R.id.increase_Product);
            textView_addToCart=itemView.findViewById(R.id.textView_addToCart);
        }
    }


    public interface ProductItemActionListener {
        void onItemTap(ImageView imageView);
    }

}
