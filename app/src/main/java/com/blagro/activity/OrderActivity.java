package com.blagro.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.OrderAdapter;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    int orderNo;
    RecyclerView order_recycle;
    List<MyPojo> myPojoList;
    OrderAdapter orderAdapter;
    TextView item_no, item_time, item_rname, item_name, item_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        order_recycle = findViewById(R.id.order_recycle);
        item_no = findViewById(R.id.item_no);
        item_time = findViewById(R.id.item_time);
        item_name = findViewById(R.id.item_name);
        item_rname = findViewById(R.id.item_rname);
        item_address = findViewById(R.id.item_address);
        Bundle bundle = getIntent().getExtras();
        orderNo = bundle.getInt("orderNo");
        item_no.setText("Order No - " + orderNo);
        item_time.setText("Date - " + bundle.getString("date"));
        item_name.setText("Distributor - " + bundle.getString("dname"));
        item_address.setText("Address - " + bundle.getString("address"));
        item_rname.setText("Retailer - " + bundle.getString("rname"));
        setOrderData();
    }

    private void setOrderData() {
        myPojoList = new ArrayList<>();
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading Orders...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callAllOrderData(orderNo, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                myPojoList.addAll(Arrays.asList(myPojos));
                                if (myPojoList != null) {
                                    orderAdapter = new OrderAdapter(OrderActivity.this, myPojoList);
                                    order_recycle.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                                    order_recycle.setAdapter(orderAdapter);
                                } else {
                                    Toast.makeText(OrderActivity.this, "No Orders Found", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(OrderActivity.this, "No Orders Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(OrderActivity.this, "No Orders Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OrderActivity.this, "No Orders Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }
}
