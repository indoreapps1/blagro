package com.blagro.activity;

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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.BasketAdapter;
import com.blagro.database.DbHelper;
import com.blagro.model.MyPojo;

import java.util.List;

public class BasketActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DbHelper dbHelper;
    BasketAdapter adapter;
    List<MyPojo> myPojoList;
    LinearLayout layout_profile;
    TextView txt_category, txt_distributor, txt_retailer, txt_city, tv_chekout;
    String sCity, sCategory, sDistributor, sRetailer;

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
        txt_distributor = findViewById(R.id.txt_distributor);
        txt_retailer = findViewById(R.id.txt_retailer);
        txt_city = findViewById(R.id.txt_city);
        tv_chekout = findViewById(R.id.tv_chekout);
        dbHelper=new DbHelper(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
        sCategory = sharedPreferences.getString("sharedCategory", null);
        sDistributor = sharedPreferences.getString("sharedDistributor", null);
        sRetailer = sharedPreferences.getString("sharedRetailer", null);
        sCity = sharedPreferences.getString("sharedCity", null);
        if (sCategory != null && sDistributor != null && sRetailer != null && sCity != null) {
            txt_category.setText("Product Category - " + sCategory);
            txt_distributor.setText("Distributor Name - " + sDistributor);
            txt_retailer.setText("Retailer Name - " + sRetailer);
            txt_city.setText("City - " + sCity);
        } else {
            Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show();
        }
        myPojoList = dbHelper.GetAllBasketOrderData();
        if (myPojoList != null && myPojoList.size() > 0) {
            adapter = new BasketAdapter(this, myPojoList);
            recyclerView.setAdapter(adapter);
        } else {
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
                dbHelper.deleteAllBasketOrderData();
                SharedPreferences sharedPreferences1= getSharedPreferences("StoreData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences1.edit();
                editor.clear();
                editor.apply();
                Intent intent=new Intent(BasketActivity.this, CreateOrderActivity.class);
                startActivity(intent);
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

}
