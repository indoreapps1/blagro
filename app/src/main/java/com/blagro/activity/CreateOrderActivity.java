package com.blagro.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.ProductAdapter;
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

    Spinner spinner_city, spinner_area, spinner_category;
    ArrayList<String> cityArrayList, categoryArrayList;
    List<MyPojo> cityPojoList, cateoryPojoList;
    ArrayAdapter<String> arrayCityAdapter, arrayCetoryAdapter;
    RecyclerView product_recycle;
    TextView cart_dot;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        spinner_city = findViewById(R.id.spinner_city);
        spinner_area = findViewById(R.id.spinner_area);
        spinner_category = findViewById(R.id.spinner_category);
        product_recycle = findViewById(R.id.product_recycle);
        cart_dot = findViewById(R.id.cart_dot);
        product_recycle.setLayoutManager(new LinearLayoutManager(this));
        setCitySpinnerData();
        setCategorySpinnerData();
    }

    private void setCitySpinnerData() {
        cityArrayList = new ArrayList<String>();
        cityPojoList = new ArrayList<MyPojo>();
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
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(CreateOrderActivity.this);
            serviceCaller.callCateorygListService(new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
//                    Toast.makeText(RetailerListActivity.this, workName, Toast.LENGTH_SHORT).show();
                    if (isComplete) {
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
                        Toast.makeText(CreateOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
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
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(this);
//            serviceCaller.callProductListService();


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }


    }
}
