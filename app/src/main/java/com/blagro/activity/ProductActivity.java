package com.blagro.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.BasketAdapter;
import com.blagro.adapter.ProductAdapter;
import com.blagro.adapter.SpinnerProductAdapter;
import com.blagro.adapter.SpinnerSubCategoryAdapter;
import com.blagro.database.DbHelper;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.Data;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    Spinner spinner_category, spinner_subCategory, spinner_product;
    List<MyPojo> subCategoryPojoList, cateoryPojoList, productPojoList;
    SpinnerSubCategoryAdapter subCategoryAdapter;
    String selectedCategory, subCategory;
    ArrayList<String> categoryArrayList;
    ArrayAdapter<String> arrayCetoryAdapter;
    ProgressBar pb;
    ProductAdapter productAdapter;
    RecyclerView product_recycle;
    TextView tv_proceed;
    CardView card_view;
    TextView productTitle, decrement_Product, increase_Product, textView_addToCart, producGst;
    EditText textView_nos;
    List<MyPojo> myPojoList;
    int disId, retId;
    String sCity, sDistributor, sRetailer;
    DbHelper dbHelper;
    BasketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
    }

    private void init() {
        dbHelper = new DbHelper(this);
        spinner_category = findViewById(R.id.spinner_category);
        spinner_subCategory = findViewById(R.id.spinner_subCategory);
        spinner_product = findViewById(R.id.spinner_product);
        product_recycle = findViewById(R.id.product_recycle);
        product_recycle.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        card_view = findViewById(R.id.card_view);
        productTitle = findViewById(R.id.productTitle);
        decrement_Product = findViewById(R.id.decrement_Product);
        decrement_Product = findViewById(R.id.decrement_Product);
        textView_nos = findViewById(R.id.textView_nos);
        increase_Product = findViewById(R.id.increase_Product);
        textView_addToCart = findViewById(R.id.textView_addToCart);
        card_view = findViewById(R.id.card_view);
        producGst = findViewById(R.id.producGst);
        pb = findViewById(R.id.pb);
        tv_proceed = findViewById(R.id.tv_proceed);
        SharedPreferences sharedPreferences = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
        sDistributor = sharedPreferences.getString("sharedDistributor", null);
        sRetailer = sharedPreferences.getString("sharedRetailer", null);
        sCity = sharedPreferences.getString("sharedCity", null);
        disId = sharedPreferences.getInt("sharedDisId", 0);
        retId = sharedPreferences.getInt("sharedRetId", 0);
        setCategorySpinnerData();
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getSelectedItem().toString();
                getSubCategoryList();
//                setProductList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    subCategory = subCategoryPojoList.get(position).getSubCat();
                    setProductList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    setProductValue(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPojoList != null && myPojoList.size() > 0) {
                    if (disId != 0 && retId != 0) {
                        getAllCheckoutData();
                    } else {
                        Toast.makeText(ProductActivity.this, "Please Select products", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        selectedProdcutData();
    }

    private void getAllCheckoutData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String empId = sharedPreferences.getString("Username", null);
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Create Order Data...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callCheckoutData(empId, disId, retId, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
                    String s1 = workName.substring(1);
                    String s2 = s1.substring(0, s1.length() - 1);
                    uploadItems(s2);
                }
            });
        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadItems(String orderno) {
        ServiceCaller serviceCaller = new ServiceCaller(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Upload Items Data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        for (MyPojo myPojo : myPojoList) {
            serviceCaller.callCheckoutItemsData(orderno, myPojo.getItem_code(), myPojo.getQuant(), new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                }
            });
        }
        progressDialog.dismiss();
        Toast.makeText(ProductActivity.this, "Order Create Successfully", Toast.LENGTH_LONG).show();
        dbHelper.deleteAllBasketOrderData();
        SharedPreferences sharedPreferences1 = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.clear();
        editor.apply();
        Dialog dialog = new Dialog(ProductActivity.this);
        dialog.setContentView(R.layout.custom_popup_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv = dialog.findViewById(R.id.item_txt);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        tv.setText("Your Order no " + orderno + " is send to approval.");
        dialog.show();
        btn_ok.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, Main2Activity.class);
            startActivity(intent);
            finish();
        });

    }

    private void setProductValue(int i) {
        String name = productPojoList.get(i).getName();
        if (name != null) {
            card_view.setVisibility(View.VISIBLE);
            productTitle.setText(productPojoList.get(i).getName() + " (" + productPojoList.get(i).getUnit() + ")");
            producGst.setText("GST- " + productPojoList.get(i).getGst());
            if (productPojoList.get(i).getCountValue() != 0) {
                textView_nos.setText("" + productPojoList.get(i).getCountValue());
                textView_nos.setSelection(textView_nos.getText().toString().length());
            }
            increase_Product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String qty = textView_nos.getText().toString();
                    textView_nos.setText(""+(Integer.parseInt(qty) + 1));
                }
            });
            decrement_Product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String qty = textView_nos.getText().toString();
                    int qyt = Integer.parseInt(qty);
                    if (qyt > 1) {
                        textView_nos.setText(""+(Integer.parseInt(qty) - 1));
                    }
                }
            });
            textView_addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItemToCart(i);
                }
            });
        } else {
            card_view.setVisibility(View.GONE);
        }

    }

    public void addItemToCart(int position) {
        MyPojo myBasket = new MyPojo();
        myBasket.setId(productPojoList.get(position).getId());
        myBasket.setName(productPojoList.get(position).getName());
        myBasket.setUnit(productPojoList.get(position).getUnit());
        myBasket.setQuant(Integer.parseInt(textView_nos.getText().toString()));
        myBasket.setGst(productPojoList.get(position).getGst());
        myBasket.setItem_code(productPojoList.get(position).getItem_code());
        dbHelper.upsertBasketOrderData(myBasket);
        Toast.makeText(ProductActivity.this, "Added", Toast.LENGTH_SHORT).show();
        card_view.setVisibility(View.GONE);
        selectedProdcutData();
    }

    public void setItemCart() {
        DbHelper dbHelper = new DbHelper(this);
        List<MyPojo> myBasket = dbHelper.GetAllBasketOrderData();
        if (myBasket != null && myBasket.size() > 0) {
            //for no of added item in basket
//            cart_dot.setText(String.valueOf(myBasket.size()));
//            cart_dot.setVisibility(View.VISIBLE);
        } else {
//            cart_dot.setVisibility(View.GONE);
        }
    }

    private void selectedProdcutData() {
        myPojoList = dbHelper.GetAllBasketOrderData();
        if (myPojoList != null && myPojoList.size() > 0) {
            adapter = new BasketAdapter(this, myPojoList);
            product_recycle.setAdapter(adapter);
        }
    }

    private void setCategorySpinnerData() {
        categoryArrayList = new ArrayList<String>();
        cateoryPojoList = new ArrayList<MyPojo>();
        categoryArrayList.clear();
        cateoryPojoList.clear();
        if (Utility.isOnline(this)) {
            ServiceCaller serviceCaller = new ServiceCaller(ProductActivity.this);
            serviceCaller.callCateorygListService(new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                cateoryPojoList.addAll(Arrays.asList(myPojos));
                                if (cateoryPojoList != null && cateoryPojoList.size() > 0) {
                                    for (MyPojo pojo : cateoryPojoList) {
                                        categoryArrayList.addAll(Arrays.asList(pojo.getCategory()));
                                    }
                                    if (categoryArrayList != null && categoryArrayList.size() != 0) {
                                        arrayCetoryAdapter = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_dropdown_item_1line, categoryArrayList);
                                        spinner_category.setAdapter(arrayCetoryAdapter);
                                    }
                                }
                            } else {
//                                Toast.makeText(CreateOrderActivity.this, "No Category Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            arrayCetoryAdapter = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_dropdown_item_1line, categoryArrayList);
                            spinner_category.setAdapter(arrayCetoryAdapter);
//                            Toast.makeText(CreateOrderActivity.this, "No Category Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
//                        Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void getSubCategoryList() {
        if (Utility.isOnline(this)) {
            subCategoryPojoList = new ArrayList<MyPojo>();
            subCategoryPojoList.clear();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callSubCategoryListService(selectedCategory, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                subCategoryPojoList.addAll(Arrays.asList(myPojos));
                                subCategoryAdapter = new SpinnerSubCategoryAdapter(ProductActivity.this, R.layout.listitems_layout, R.id.item_txt, subCategoryPojoList);
                                spinner_subCategory.setAdapter(subCategoryAdapter);
                            } else {
                                subCategoryAdapter = new SpinnerSubCategoryAdapter(ProductActivity.this, R.layout.listitems_layout, R.id.item_txt, subCategoryPojoList);
                                spinner_subCategory.setAdapter(subCategoryAdapter);
                            }
                        } else {
                            subCategoryAdapter = new SpinnerSubCategoryAdapter(ProductActivity.this, R.layout.listitems_layout, R.id.item_txt, subCategoryPojoList);
                            spinner_subCategory.setAdapter(subCategoryAdapter);
//                            Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        subCategoryAdapter = new SpinnerSubCategoryAdapter(ProductActivity.this, R.layout.listitems_layout, R.id.item_txt, subCategoryPojoList);
                        spinner_subCategory.setAdapter(subCategoryAdapter);
//                        Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void setProductList() {
        productPojoList = new ArrayList<>();
        productPojoList.clear();
        if (Utility.isOnline(this)) {
            pb.setVisibility(View.VISIBLE);
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callProductListService(subCategory, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    pb.setVisibility(View.GONE);
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                productPojoList.addAll(Arrays.asList(myPojos));
                                setAdapter();
                            } else {
                                setAdapter();
                            }
                        } else {
                            setAdapter();
//                            Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        setAdapter();
//                        Toast.makeText(CreateOrderActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        SpinnerProductAdapter productAdapter = new SpinnerProductAdapter(ProductActivity.this, R.layout.listitems_layout, R.id.item_txt, productPojoList);
//        product_recycle.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        spinner_product.setAdapter(productAdapter);
    }

    //notifiy adapter if data delete
    private void notifiyAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("basketItem")) {
                boolean basketFlag = intent.getBooleanExtra("basketFlag", false);
//                Do whatever you want with the code here
                if (basketFlag) {
                    notifiyAdapter();
                }
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("basketItem"));
        super.onResume();

    }
}
