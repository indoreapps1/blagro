package com.blagro.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.blagro.adapter.ProductAdapter;
import com.blagro.adapter.SpinnerDistributorAdapter;
import com.blagro.adapter.SpinnerRetailerAdapter;
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

    Spinner spinner_city, spinnerDistributer, spinner_category, spinner_retailer;
    ArrayList<String> cityArrayList, categoryArrayList, distubterArrayList, retailerArrayList;
    List<MyPojo> cityPojoList, cateoryPojoList, productPojoList, distubterPojoList, retailerPojoList;
    ArrayAdapter<String> arrayCityAdapter, arrayCetoryAdapter;
    SpinnerRetailerAdapter spinnerRetailerAdapter;
    SpinnerDistributorAdapter spinnerDistributorAdapter;
    RecyclerView product_recycle;
    TextView cart_dot, txt_cart, tv_proceed;
    ProductAdapter productAdapter;
    String selectedCategory, distributer, retailer, city;
    LinearLayout layout_profile;
    RelativeLayout layout_cart;
    ProgressBar pb;
    int retailerId, distributorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        init();
    }

    private void init() {
        spinner_city = findViewById(R.id.spinner_city);
        spinnerDistributer = findViewById(R.id.spinner_area);
        spinner_retailer = findViewById(R.id.spinner_retailer);
        spinner_category = findViewById(R.id.spinner_category);
        product_recycle = findViewById(R.id.product_recycle);
        cart_dot = findViewById(R.id.cart_dot);
        txt_cart = findViewById(R.id.txt_cart);
        tv_proceed = findViewById(R.id.tv_proceed);
        layout_profile = findViewById(R.id.layout_profile);
        layout_cart = findViewById(R.id.layout_cart);
        pb = findViewById(R.id.pb);
        product_recycle.setLayoutManager(new LinearLayoutManager(this));
        setCitySpinnerData();
//        setCategorySpinnerData();

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
//                distributer = parent.getSelectedItem().toString();
//                setProductList();
                distributer = distubterPojoList.get(position).getName();
                distributorId = distubterPojoList.get(position).getId();
                setCategorySpinnerData();
                storeData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_retailer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                retailer = parent.getSelectedItem().toString();
//                setProductList();
                retailer = retailerPojoList.get(position).getName();
                retailerId = retailerPojoList.get(position).getId();
                setCategorySpinnerData();
                storeData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedCategory =  parent.getItemAtPosition(position).toString();
                selectedCategory = cateoryPojoList.get(position).getCategory();
                setProductList();
                storeData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        setItemCart();

        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateOrderActivity.this, BasketActivity.class);
                startActivity(intent);
            }
        });
        layout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateOrderActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        layout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateOrderActivity.this, BasketActivity.class);
                startActivity(intent);
            }
        });
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
            serviceCaller.callCityListService(new IAsyncWorkCompletedCallback() {
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
                                Toast.makeText(CreateOrderActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(CreateOrderActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }


    private void setCategorySpinnerData() {
        categoryArrayList = new ArrayList<String>();
        cateoryPojoList = new ArrayList<MyPojo>();
        categoryArrayList.clear();
        cateoryPojoList.clear();
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(CreateOrderActivity.this);
            serviceCaller.callCateorygListService(new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                cateoryPojoList.addAll(Arrays.asList(myPojos));
                                if (cateoryPojoList != null && cateoryPojoList.size() > 0) {
                                    for (MyPojo pojo : cateoryPojoList) {
                                        categoryArrayList.addAll(Arrays.asList(pojo.getCategory()));
                                    }
                                    if (categoryArrayList != null && categoryArrayList.size() != 0) {
                                        arrayCetoryAdapter = new ArrayAdapter<String>(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, categoryArrayList);
                                        spinner_category.setAdapter(arrayCetoryAdapter);
                                    }
                                }
                            } else {
                                Toast.makeText(CreateOrderActivity.this, "No Category Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CreateOrderActivity.this, "No Category Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        editor.putString("sharedCategory", selectedCategory);
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

    private void setProductList() {
        productPojoList = new ArrayList<>();
        productPojoList.clear();
        if (Utility.isOnline(this)) {
            pb.setVisibility(View.VISIBLE);
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callProductListService("grocery", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    pb.setVisibility(View.GONE);
//                    Toast.makeText(CreateOrderActivity.this, "" + selectedCategory, Toast.LENGTH_SHORT).show();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                productPojoList.addAll(Arrays.asList(myPojos));
                                if (productPojoList != null) {
                                    productAdapter = new ProductAdapter(CreateOrderActivity.this, productPojoList);
                                    product_recycle.setLayoutManager(new LinearLayoutManager(CreateOrderActivity.this));
                                    product_recycle.setAdapter(productAdapter);
                                }
                            }
                        } else {
                            Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }


    }

    private void getDistributerList() {
//        distubterArrayList = new ArrayList<String>();
//        distubterArrayList.clear();
        distubterPojoList = new ArrayList<MyPojo>();
        distubterPojoList.clear();
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callDistributerListService(city, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                distubterPojoList.addAll(Arrays.asList(myPojos));
                                if (distubterPojoList != null && distubterPojoList.size() > 0) {
                                    spinnerDistributorAdapter = new SpinnerDistributorAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, distubterPojoList);
                                    spinnerDistributer.setAdapter(spinnerDistributorAdapter);
//                                    for (MyPojo pojo : distubterPojoList) {
//                                        distubterArrayList.addAll(Arrays.asList(pojo.getName()));
//                                    }
//                                    if (distubterArrayList != null && distubterArrayList.size() != 0) {
//                                        ArrayAdapter adapter = new ArrayAdapter<String>(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, distubterArrayList);
//                                        spinnerDistributer.setAdapter(adapter);
//                                    }

                                }
                            } else {
                                Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }


    }

    private void getRetailerList() {
        if (Utility.isOnline(this)) {
//            retailerArrayList = new ArrayList<String>();
//            retailerArrayList.clear();
            retailerPojoList = new ArrayList<MyPojo>();
            retailerPojoList.clear();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callRetailerListService(city, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                retailerPojoList.addAll(Arrays.asList(myPojos));
                                if (retailerPojoList != null && retailerPojoList.size() > 0) {
                                    spinnerRetailerAdapter = new SpinnerRetailerAdapter(CreateOrderActivity.this, R.layout.listitems_layout, R.id.item_txt, retailerPojoList);
                                    spinner_retailer.setAdapter(spinnerRetailerAdapter);
//                                    for (MyPojo pojo : retailerPojoList) {
//                                        retailerArrayList.addAll(Arrays.asList(pojo.getName()));
//                                    }
//                                    if (retailerArrayList != null && retailerArrayList.size() != 0) {
//                                        ArrayAdapter adapter = new ArrayAdapter<String>(CreateOrderActivity.this, android.R.layout.simple_dropdown_item_1line, retailerArrayList);
//                                        spinner_retailer.setAdapter(adapter);
//                                    }
                                }
                            } else {
                                Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}
