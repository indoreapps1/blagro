package com.blagro.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.RetailerAdapter;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetailerListActivity extends AppCompatActivity {

    RecyclerView recycle_retailerList;
    Spinner spinner_city;
    ArrayList<String> arrayList;
    List<MyPojo> myPojoList, retailerPojoList;
    ArrayAdapter<String> arrayAdapter;
    RetailerAdapter retailerAdapter;
    ProgressBar pb;
    String sCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_list);
        init();
    }

    private void init() {
        recycle_retailerList = findViewById(R.id.recycle_retailerList);
        spinner_city = findViewById(R.id.spinner_city);
        pb = findViewById(R.id.pb);
        setSpinnerData();
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = parent.getSelectedItem().toString();
                setRetailerList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<String> setSpinnerData() {
        arrayList = new ArrayList<String>();
        myPojoList = new ArrayList<MyPojo>();
        arrayList.clear();
        myPojoList.clear();
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(RetailerListActivity.this);
            progressDialog.setMessage("Fetching Cities...");
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(RetailerListActivity.this);
            serviceCaller.callCityListService(new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
//                    Toast.makeText(RetailerListActivity.this, workName, Toast.LENGTH_SHORT).show();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                myPojoList.addAll(Arrays.asList(myPojos));
                                if (myPojoList != null && myPojoList.size() > 0) {
                                    for (MyPojo pojo : myPojoList) {
                                        arrayList.addAll(Arrays.asList(pojo.getCity()));
                                    }
                                    if (arrayList != null && arrayList.size() > 0) {
                                        arrayAdapter = new ArrayAdapter<String>(RetailerListActivity.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                                        spinner_city.setAdapter(arrayAdapter);
                                    }
                                }
                            } else {
                                Toast.makeText(RetailerListActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RetailerListActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RetailerListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        return arrayList;

    }

    private void setRetailerList() {
        retailerPojoList = new ArrayList<>();
        if (Utility.isOnline(this)) {
            pb.setVisibility(View.VISIBLE);
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callRetailerListService(sCity, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    pb.setVisibility(View.GONE);
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            if (!workName.trim().equalsIgnoreCase("[]")) {
                                MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                                if (myPojos != null) {
                                    retailerPojoList.addAll(Arrays.asList(myPojos));
                                    if (retailerPojoList != null) {
                                        retailerAdapter = new RetailerAdapter(RetailerListActivity.this, retailerPojoList);
                                        recycle_retailerList.setLayoutManager(new LinearLayoutManager(RetailerListActivity.this));
                                        recycle_retailerList.setAdapter(retailerAdapter);
                                    } else {
                                        Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RetailerListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}
