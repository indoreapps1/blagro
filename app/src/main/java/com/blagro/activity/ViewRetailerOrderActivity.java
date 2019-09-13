package com.blagro.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioGroup radio_grp;
    RadioButton Delivered, Approve, Pending, Cancel;
    String type = "Delivered";
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_retailer_order);
        view_order_recycle = findViewById(R.id.view_order_recycle);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("RetailerId");
        init();
        setRetailerOderData();
    }

    private void init() {
        radio_grp = findViewById(R.id.radio_grp);
        Delivered = findViewById(R.id.Delivered);
        Approve = findViewById(R.id.Approve);
        Pending = findViewById(R.id.Pending);
        Cancel = findViewById(R.id.Cancel);
        search =findViewById(R.id.search);
        radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = radio_grp.getCheckedRadioButtonId();
                if (id == R.id.Delivered) {
                    type = "Delivered";
                    setRetailerOderData();
                } else if (id == R.id.Approve) {
                    type = "Approve";
                    setRetailerOderData();
                } else if (id == R.id.Pending) {
                    type = "Pending";
                    setRetailerOderData();
                } else {
                    type = "Cancel";
                    setRetailerOderData();
                }
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                if (retailerAdapter != null) {
                    retailerAdapter.getFilter().filter(text);
                }
                return true;
            }

        });
    }

    private void setRetailerOderData() {
        myPojoList = new ArrayList<>();
        myPojoList.clear();
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading " + type + " Orders...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callViewRetailerOrderData(id, type, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                myPojoList.addAll(Arrays.asList(myPojos));
                                if (myPojoList != null) {
                                    setAdapter();
                                } else {
                                    setAdapter();
                                    Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                setAdapter();
                                Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            setAdapter();
                            Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        setAdapter();
                        Toast.makeText(ViewRetailerOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        retailerAdapter = new RetailerOrderAdapter(ViewRetailerOrderActivity.this, myPojoList);
        view_order_recycle.setLayoutManager(new LinearLayoutManager(ViewRetailerOrderActivity.this));
        view_order_recycle.setAdapter(retailerAdapter);
    }
}
