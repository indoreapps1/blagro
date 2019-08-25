package com.blagro.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistributorListActivity extends AppCompatActivity {

    RecyclerView recycle_distributorList;
    Spinner spinner_city;
    ArrayList<String> arrayList;
    List<MyPojo> myPojoList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_list);
        recycle_distributorList = findViewById(R.id.recycle_distributorList);
        spinner_city = findViewById(R.id.spinner_city);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, setSpinnerData());
        spinner_city.setAdapter(arrayAdapter);
    }

    private List<String> setSpinnerData() {
        arrayList = new ArrayList<String>();
        myPojoList = new ArrayList<MyPojo>();
        if (Utility.isOnline(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(DistributorListActivity.this);
            progressDialog.setMessage("Fetching Cities...");
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(DistributorListActivity.this);
            serviceCaller.callCityListService(new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    progressDialog.dismiss();
//                    Toast.makeText(RetailerListActivity.this, workName, Toast.LENGTH_SHORT).show();
                    if (isComplete) {
                        MyPojo[] myPojos = new Gson().fromJson(workName, MyPojo[].class);
                        if (myPojos != null){
                            myPojoList.addAll(Arrays.asList(myPojos));
                            if (myPojoList!=null && myPojoList.size()>0){
                                for (MyPojo pojo:myPojoList){
                                    arrayList.addAll(Arrays.asList(pojo.getCity()));
                                }
                            }
                        }else {
                            Toast.makeText(DistributorListActivity.this, "No City Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(DistributorListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        return arrayList;

    }
}
