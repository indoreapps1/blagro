package com.blagro.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.AreaAdapter;
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
    Spinner spinner_city, spinner_area;
    ArrayList<String> arrayList, areaList;
    List<MyPojo> myPojoList, retailerPojoList, areaPojoList;
    ArrayAdapter<String> arrayAdapter;
    RetailerAdapter retailerAdapter;
    ProgressBar pb;
    String sCity;
    int aArea;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_list);
        init();
    }

    private void init() {
        recycle_retailerList = findViewById(R.id.recycle_retailerList);
        spinner_city = findViewById(R.id.spinner_city);
        spinner_area = findViewById(R.id.spinner_area);
        pb = findViewById(R.id.pb);
        search = findViewById(R.id.search);
        getAreaData();
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    aArea = areaPojoList.get(position).getId();
//                  aArea = parent.getSelectedItem().toString();
                    setSpinnerData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void getAreaData() {
        areaPojoList = new ArrayList<MyPojo>();
        areaPojoList.clear();
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(RetailerListActivity.this);
            serviceCaller.callAreaListService(new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                areaPojoList.addAll(Arrays.asList(myPojos));
                                if (areaPojoList != null && areaPojoList.size() > 0) {
                                    AreaAdapter arrayAdapter = new AreaAdapter(RetailerListActivity.this, android.R.layout.simple_dropdown_item_1line, R.id.item_txt, areaPojoList);
                                    spinner_area.setAdapter(arrayAdapter);
                                }
                            } else {
                                Toast.makeText(RetailerListActivity.this, "No Area Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RetailerListActivity.this, "No Area Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
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
            serviceCaller.callCityListService(String.valueOf(aArea), new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
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
                                arrayAdapter = new ArrayAdapter<String>(RetailerListActivity.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                                spinner_city.setAdapter(arrayAdapter);
                                Toast.makeText(RetailerListActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            arrayAdapter = new ArrayAdapter<String>(RetailerListActivity.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                            spinner_city.setAdapter(arrayAdapter);
                            Toast.makeText(RetailerListActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        arrayAdapter = new ArrayAdapter<String>(RetailerListActivity.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                        spinner_city.setAdapter(arrayAdapter);
                        Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
                                    setAdapter();
                                } else {
                                    setAdapter();
                                    Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                setAdapter();
                                Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            setAdapter();
                            Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        setAdapter();
                        Toast.makeText(RetailerListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        retailerAdapter = new RetailerAdapter(RetailerListActivity.this, retailerPojoList, false);
        recycle_retailerList.setLayoutManager(new LinearLayoutManager(RetailerListActivity.this));
        recycle_retailerList.setAdapter(retailerAdapter);
    }
}
