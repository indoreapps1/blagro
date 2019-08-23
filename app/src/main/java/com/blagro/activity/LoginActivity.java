package com.blagro.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blagro.R;
import com.blagro.framework.IAsyncWorkCompletedCallback;
import com.blagro.framework.ServiceCaller;
import com.blagro.utilities.Contants;
import com.blagro.utilities.Utility;

public class LoginActivity extends AppCompatActivity {
    TextView tv_login;
    EditText edt_username, edt_pass;
    String sUsername, sPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        tv_login=findViewById(R.id.tv_login);
        edt_username=findViewById(R.id.edt_username);
        edt_pass=findViewById(R.id.edt_pass);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    if (Utility.isOnline(LoginActivity.this)) {
                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Loading Please Wait...");
                        progressDialog.show();
                        ServiceCaller serviceCaller = new ServiceCaller(LoginActivity.this);
                        serviceCaller.callLoginService(sUsername, new IAsyncWorkCompletedCallback() {
                            @Override
                            public void onDone(String workName, boolean isComplete) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login Sucessfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, Contants.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private boolean Validation(){
        sUsername=edt_username.getText().toString();
        sPass=edt_pass.getText().toString();
        if (sUsername.length()==0){
            edt_username.setError("Please Enter Username");
            edt_username.requestFocus();
            return false;
        }
        else if (sPass.length()==0){
            edt_pass.setError("Please Enter Password");
            edt_pass.requestFocus();
            return false;
        }
       return true;
    }
}
