package com.blagro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blagro.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout_retailerList, layout_distributorrList, layout_retailer, layout_profile, layout_distributor;
    RelativeLayout layout_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       init();
    }

    private void init() {
        layout_retailerList = findViewById(R.id.layout_retailerList);
        layout_circle = findViewById(R.id.layout_circle);
        layout_distributorrList = findViewById(R.id.layout_distributorrList);
        layout_retailer = findViewById(R.id.layout_retailer);
        layout_distributor = findViewById(R.id.layout_distributor);
        layout_profile = findViewById(R.id.layout_profile);
        layout_retailerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetailerListActivity.class));
            }
        });

        layout_distributorrList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DistributorListActivity.class));
            }
        });

        layout_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateOrderActivity.class));
            }
        });
        layout_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateOrderActivity.class));
            }
        });
        layout_distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateOrderActivity.class));
            }
        });
        layout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }
}
