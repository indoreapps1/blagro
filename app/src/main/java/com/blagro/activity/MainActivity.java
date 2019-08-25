package com.blagro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blagro.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout_retailerList, layout_distributorrList;
    RelativeLayout layout_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout_retailerList=findViewById(R.id.layout_retailerList);
        layout_circle=findViewById(R.id.layout_circle);
        layout_distributorrList=findViewById(R.id.layout_distributorrList);
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
    }
}
