package com.blagro.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.RetailerAdapter;
import com.blagro.adapter.RetailerOrderAdapter;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewRetailerOrderActivity extends AppCompatActivity {
    RecyclerView view_order_recycle;
    int id;
    RetailerOrderAdapter retailerAdapter;
    List<MyPojo> myPojoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_retailer_order);
        view_order_recycle = findViewById(R.id.view_order_recycle);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("RetailerId");
        setRetailerOderData();
    }

    private void setRetailerOderData() {
        myPojoList = new ArrayList<>();
        myPojoList.clear();
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading Orders...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callViewRetailerOrderData(id, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                myPojoList.addAll(Arrays.asList(myPojos));
                                if (myPojoList != null) {
                                    retailerAdapter = new RetailerOrderAdapter(ViewRetailerOrderActivity.this, myPojoList);
                                    view_order_recycle.setLayoutManager(new LinearLayoutManager(ViewRetailerOrderActivity.this));
                                    view_order_recycle.setAdapter(retailerAdapter);
                                } else {
                                    Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}
