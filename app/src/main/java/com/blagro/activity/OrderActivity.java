package com.blagro.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    TextView txt, txt_1;
    int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        txt = findViewById(R.id.txt);
        txt_1 = findViewById(R.id.txt_1);
        Bundle bundle = getIntent().getExtras();
        orderID = bundle.getInt("orderNo");
        setOrderData();
    }

    private void setOrderData() {
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading Orders...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callAllOrderData(101, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
                    if (isComplete) {
//                        if (workName.trim().equalsIgnoreCase("")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null){
                                txt.setText("Order Number - " +myPojos[0].getOrder_no());

                            }else {
                                Toast.makeText(OrderActivity.this, "No Orders Found", Toast.LENGTH_SHORT).show();
                            }
//                        } else {
//                            Toast.makeText(OrderActivity.this, "No Orders Found", Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        Toast.makeText(OrderActivity.this, "No Orders Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }
}
