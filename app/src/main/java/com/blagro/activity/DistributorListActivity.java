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
import com.blagro.adapter.DistributorAdapter;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistributorListActivity extends AppCompatActivity {

    RecyclerView recycle_distributorList;
    Spinner spinner_city;
    ArrayList<String> arrayList;
    List<MyPojo> myPojoList, distributorPojoList;
    ArrayAdapter arrayAdapter;
    DistributorAdapter distributorAdapter;
    ProgressBar pb;
    String sCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_list);
        init();
    }

    private void init() {
        recycle_distributorList = findViewById(R.id.recycle_distributorList);
        spinner_city = findViewById(R.id.spinner_city);
        pb = findViewById(R.id.pb);
        setSpinnerData();
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = parent.getSelectedItem().toString();
                setDistributorList();
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
            final ProgressDialog progressDialog = new ProgressDialog(DistributorListActivity.this);
            progressDialog.setMessage("Fetching Cities...");
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(DistributorListActivity.this);
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
                                        arrayAdapter = new ArrayAdapter(DistributorListActivity.this, android.R.layout.simple_list_item_1, arrayList);
                                        spinner_city.setAdapter(arrayAdapter);
                                    }
                                }
                            } else {
                                Toast.makeText(DistributorListActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(DistributorListActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DistributorListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        return arrayList;

    }


    private void setDistributorList() {
        distributorPojoList = new ArrayList<>();
        distributorPojoList.clear();
        if (Utility.isOnline(this)) {
            pb.setVisibility(View.VISIBLE);
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callDistributerListService(sCity, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    pb.setVisibility(View.GONE);
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            if (!workName.trim().equalsIgnoreCase("[]")) {
                                MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                                if (myPojos != null) {
                                    distributorPojoList.addAll(Arrays.asList(myPojos));
                                    if (distributorPojoList != null) {
                                        distributorAdapter = new DistributorAdapter(DistributorListActivity.this, distributorPojoList);
                                        recycle_distributorList.setLayoutManager(new LinearLayoutManager(DistributorListActivity.this));
                                        recycle_distributorList.setAdapter(distributorAdapter);
                                    } else {
                                        Toast.makeText(DistributorListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(DistributorListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DistributorListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(DistributorListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DistributorListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}
