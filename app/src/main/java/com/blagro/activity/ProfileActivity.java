package com.blagro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.database.DbHelper;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView tv_logout;
    ProgressBar pb;
    String susername;
    EditText edt_name, edt_number, edt_email, edt_desingnation, edt_area, edt_city;
    SharedPreferences loginSharedPreferences, dataSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    private void init() {
        tv_logout = findViewById(R.id.tv_logout);
        pb = findViewById(R.id.pb);
        edt_name = findViewById(R.id.edt_name);
        edt_number = findViewById(R.id.edt_number);
        edt_email = findViewById(R.id.edt_email);
        edt_desingnation = findViewById(R.id.edt_desingnation);
        edt_area = findViewById(R.id.edt_area);
        edt_city = findViewById(R.id.edt_city);
        dbHelper=new DbHelper(this);
        loginSharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        susername = loginSharedPreferences.getString("Username", null);
        dataSharedPreference = getSharedPreferences("StoreData", Context.MODE_PRIVATE);
        setProfile();
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void setProfile() {
        if (Utility.isOnline(this)) {
            pb.setVisibility(View.VISIBLE);
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.callGetProfileData(susername, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    pb.setVisibility(View.GONE);
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("[]")) {
                            MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                            if (myPojos != null) {
                                edt_name.setText(myPojos[0].getEmp_name());
                                edt_number.setText(myPojos[0].getMobile_n());
                                edt_email.setText(myPojos[0].getEmail());
                                edt_desingnation.setText(myPojos[0].getDesignation());
                                edt_area.setText(myPojos[0].getArea());
                                edt_city.setText(myPojos[0].getCity());
                            } else {
                                Toast.makeText(ProfileActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to exit");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor loginEditor=loginSharedPreferences.edit();
                loginEditor.clear();
                loginEditor.apply();
                SharedPreferences.Editor dataEditor=dataSharedPreference.edit();
                dataEditor.clear();
                dataEditor.apply();
                dbHelper.deleteAllBasketOrderData();
                Intent intent=new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }


}
