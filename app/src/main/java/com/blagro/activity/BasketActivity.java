package com.blagro.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.BasketAdapter;
import com.blagro.database.DbHelper;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.Data;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DbHelper dbHelper;
    BasketAdapter adapter;
    List<MyPojo> myPojoList;
    LinearLayout layout_profile;
    TextView txt_category, txt_distributor, txt_retailer, txt_city, tv_chekout, txt_dis_id, txt_ret_id, txt_subcategory;
    int disId, retId;
    String sCity, sCategory, sDistributor, sRetailer, subCategory;
    List<Data> dataList;
    Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        layout_profile = findViewById(R.id.layout_profile);
        txt_category = findViewById(R.id.txt_category);
        txt_subcategory = findViewById(R.id.txt_subcategory);
        txt_distributor = findViewById(R.id.txt_distributor);
        txt_retailer = findViewById(R.id.txt_retailer);
        txt_city = findViewById(R.id.txt_city);
        tv_chekout = findViewById(R.id.tv_chekout);
        txt_dis_id = findViewById(R.id.txt_dis_id);
        txt_ret_id = findViewById(R.id.txt_ret_id);
        dbHelper = new DbHelper(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
        sCategory = sharedPreferences.getString("sharedCategory", null);
        sDistributor = sharedPreferences.getString("sharedDistributor", null);
        sRetailer = sharedPreferences.getString("sharedRetailer", null);
        subCategory = sharedPreferences.getString("subCategory", null);
        sCity = sharedPreferences.getString("sharedCity", null);
        disId = sharedPreferences.getInt("sharedDisId", 0);
        retId = sharedPreferences.getInt("sharedRetId", 0);
        if (sCategory != null && sDistributor != null && sRetailer != null && sCity != null && subCategory != null) {
            txt_category.setText("Product Category - " + sCategory);
            txt_subcategory.setText("Sub Category - " + subCategory);
            txt_distributor.setText("Distributor Name - " + sDistributor);
            txt_retailer.setText("Retailer Name - " + sRetailer);
            txt_city.setText("City - " + sCity);
            txt_dis_id.setText("Distributor Id" + disId);
            txt_ret_id.setText("Retailer Id" + retId);
        } else {
//            dbHelper.deleteAllBasketOrderData();
        }
        myPojoList = dbHelper.GetAllBasketOrderData();
        if (myPojoList != null && myPojoList.size() > 0) {
//            Toast.makeText(this, myPojoList.size()+"", Toast.LENGTH_SHORT).show();
            adapter = new BasketAdapter(this, myPojoList);
            recyclerView.setAdapter(adapter);
        } else {
//            SharedPreferences sharedPreferences1 = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences1.edit();
//            editor.clear();
//            editor.apply();
            Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show();
        }
        layout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasketActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        tv_chekout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPojoList != null && myPojoList.size() > 0) {
                    if (disId != 0 && retId != 0) {
                        getAllCheckoutData();
                    } else {
                        Toast.makeText(BasketActivity.this, "Please Select products", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


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
        dataList = new ArrayList<>();
        data = new Data();
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
        Toast.makeText(BasketActivity.this, "Order Create Successfully", Toast.LENGTH_LONG).show();
        dbHelper.deleteAllBasketOrderData();
        SharedPreferences sharedPreferences1 = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.clear();
        editor.apply();
        Dialog dialog=new Dialog(BasketActivity.this);
        dialog.setContentView(R.layout.custom_popup_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv=dialog.findViewById(R.id.item_txt);
        Button btn_ok=dialog.findViewById(R.id.btn_ok);
        tv.setText("Your Order no "+orderno+" is send to approval.");
        dialog.show();
        btn_ok.setOnClickListener(v->{
            Intent intent = new Intent(BasketActivity.this, Main2Activity.class);
            startActivity(intent);
            finish();
        });

    }
}
