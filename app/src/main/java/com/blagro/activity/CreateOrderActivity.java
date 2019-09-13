package com.blagro.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.AreaAdapter;
import com.blagro.adapter.ProductAdapter;
import com.blagro.adapter.SpinnerDistributorAdapter;
import com.blagro.adapter.SpinnerRetailerAdapter;
import com.blagro.adapter.SpinnerSubCategoryAdapter;
import com.blagro.database.DbHelper;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateOrderActivity extends AppCompatActivity {
    Spinner spinner_city, spinner_area;
    List<MyPojo> areaPojoList;
    Spinner spinnerDistributer, spinner_retailer;
    ArrayList<String> cityArrayList, distubterArrayList, retailerArrayList;
    List<MyPojo> cityPojoList, distubterPojoList, retailerPojoList;
    ArrayAdapter<String> arrayCityAdapter;
    SpinnerRetailerAdapter spinnerRetailerAdapter;
    SpinnerDistributorAdapter spinnerDistributorAdapter;
    TextView cart_dot, txt_cart, tv_proceed;
    String distributer, retailer, city;
    LinearLayout layout_profile;
    RelativeLayout layout_cart;
    int retailerId, distributorId, aArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        init();
    }

    private void init() {
        spinner_city = findViewById(R.id.spinner_city);
        spinnerDistributer = findViewById(R.id.spinner_dis);
        spinner_retailer = findViewById(R.id.spinner_retailer);
        cart_dot = findViewById(R.id.cart_dot);
        txt_cart = findViewById(R.id.txt_cart);
        tv_proceed = findViewById(R.id.tv_proceed);
        layout_profile = findViewById(R.id.layout_profile);
        layout_cart = findViewById(R.id.layout_cart);
        spinner_area = findViewById(R.id.spinner_area);
        getAreaData();
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    aArea = areaPojoList.get(position).getId();
                    setCitySpinnerData();
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
                city = parent.getSelectedItem().toString();
                getDistributerList();
                getRetailerList();
                storeData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerDistributer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    distributer = distubterPojoList.get(position).getName();
                    distributorId = distubterPojoList.get(position).getId();
                    storeData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_retailer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                retailer = retailerPojoList.get(position).getName();
                retailerId = retailerPojoList.get(position).getId();
                storeData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setItemCart();

        tv_proceed.setOnClickListener(v -> {
            storeData();
            Intent intent = new Intent(CreateOrderActivity.this, ProductActivity.class);
            startActivity(intent);
        });
        layout_profile.setOnClickListener(v -> {
            Intent intent = new Intent(CreateOrderActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        layout_cart.setOnClickListener(v -> {
            Intent intent = new Intent(CreateOrderActivity.this, BasketActivity.class);
            startActivity(intent);
        });
    }

    private void getAreaData() {
        areaPojoList = new ArrayList<MyPojo>();
        areaPojoList.clear();
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(CreateOrderActivity.this);
            serviceCaller.callAreaListService(new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                areaPojoList.addAll(Arrays.asList(myPojos));
                                if (areaPojoList != null && areaPojoList.size() > 0) {
                                    AreaAdapter arrayAdapter = new AreaAdapter(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, R.id.item_txt, areaPojoList);
                                    spinner_area.setAdapter(arrayAdapter);
                                }
                            } else {
//                                Toast.makeText(CreateOrderActivity.this, "No Area Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            Toast.makeText(CreateOrderActivity.this, "No Area Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
//                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCitySpinnerData() {
        cityArrayList = new ArrayList<String>();
        cityPojoList = new ArrayList<MyPojo>();
        cityArrayList.clear();
        cityPojoList.clear();
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(CreateOrderActivity.this);
            progressDialog.setMessage("Loading Data...");
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(CreateOrderActivity.this);
            serviceCaller.callCityListService(String.valueOf(aArea), new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                cityPojoList.addAll(Arrays.asList(myPojos));
                                if (cityPojoList != null && cityPojoList.size() > 0) {
                                    for (MyPojo pojo : cityPojoList) {
                                        cityArrayList.addAll(Arrays.asList(pojo.getCity()));
                                    }
                                    if (cityArrayList != null && cityArrayList.size() != 0) {
                                        arrayCityAdapter = new ArrayAdapter<String>(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, cityArrayList);
                                        spinner_city.setAdapter(arrayCityAdapter);
                                    }
                                }
                            } else {
                                arrayCityAdapter = new ArrayAdapter<String>(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, cityArrayList);
                                spinner_city.setAdapter(arrayCityAdapter);
//                                Toast.makeText(CreateOrderActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            arrayCityAdapter = new ArrayAdapter<String>(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, cityArrayList);
                            spinner_city.setAdapter(arrayCityAdapter);
//                            Toast.makeText(CreateOrderActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        arrayCityAdapter = new ArrayAdapter<String>(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, cityArrayList);
                        spinner_city.setAdapter(arrayCityAdapter);
//                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void storeData() {
        SharedPreferences sharedPreferences = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sharedCity", city);
        editor.putString("sharedDistributor", distributer);
        editor.putString("sharedRetailer", retailer);
        editor.putInt("sharedDisId", distributorId);
        editor.putInt("sharedRetId", retailerId);
        editor.apply();
    }

    public void setItemCart() {
        DbHelper dbHelper = new DbHelper(this);
        List<MyPojo> myBasket = dbHelper.GetAllBasketOrderData();
        if (myBasket != null && myBasket.size() > 0) {
            //for no of added item in basket
            cart_dot.setText(String.valueOf(myBasket.size()));
            cart_dot.setVisibility(View.VISIBLE);
        } else {
            cart_dot.setVisibility(View.GONE);
        }
    }


    private void getDistributerList() {
        distubterPojoList = new ArrayList<MyPojo>();
        distubterPojoList.clear();
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callDistributerListService(city, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                distubterPojoList.addAll(Arrays.asList(myPojos));
                                if (distubterPojoList != null && distubterPojoList.size() > 0) {
                                    spinnerDistributorAdapter = new SpinnerDistributorAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, distubterPojoList);
                                    spinnerDistributer.setAdapter(spinnerDistributorAdapter);
                                }
                            } else {
                                spinnerDistributorAdapter = new SpinnerDistributorAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, distubterPojoList);
                                spinnerDistributer.setAdapter(spinnerDistributorAdapter);
//                                Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            spinnerDistributorAdapter = new SpinnerDistributorAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, distubterPojoList);
                            spinnerDistributer.setAdapter(spinnerDistributorAdapter);
//                            Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        spinnerDistributorAdapter = new SpinnerDistributorAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, distubterPojoList);
                        spinnerDistributer.setAdapter(spinnerDistributorAdapter);
//                        Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }


    }

    private void getRetailerList() {
        if (Utility.isOnline(this)) {
            retailerPojoList = new ArrayList<MyPojo>();
            retailerPojoList.clear();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callRetailerListService(city, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                retailerPojoList.addAll(Arrays.asList(myPojos));
                                if (retailerPojoList != null && retailerPojoList.size() > 0) {
                                    spinnerRetailerAdapter = new SpinnerRetailerAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, retailerPojoList);
                                    spinner_retailer.setAdapter(spinnerRetailerAdapter);
                                }
                            } else {
                                spinnerRetailerAdapter = new SpinnerRetailerAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, retailerPojoList);
                                spinner_retailer.setAdapter(spinnerRetailerAdapter);
//                                Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            spinnerRetailerAdapter = new SpinnerRetailerAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, retailerPojoList);
                            spinner_retailer.setAdapter(spinnerRetailerAdapter);
//                            Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        spinnerRetailerAdapter = new SpinnerRetailerAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, retailerPojoList);
                        spinner_retailer.setAdapter(spinnerRetailerAdapter);
//                        Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}
