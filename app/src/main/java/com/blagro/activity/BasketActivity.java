package com.blagro.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.adapter.BasketAdapter;
import com.blagro.database.DbHelper;
import com.blagro.model.MyPojo;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DbHelper dbHelper;
    BasketAdapter adapter;
    List<MyPojo> myPojoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        dbHelper = new DbHelper(this);
        myPojoList = dbHelper.GetAllBasketOrderData();
        if(myPojoList!=null && myPojoList.size()>0)
        adapter = new BasketAdapter(this, myPojoList);
    }






    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("basketItem")) {
                boolean basketFlag = intent.getBooleanExtra("basketFlag", false);
//                Do whatever you want with the code here
                if (basketFlag) {

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
