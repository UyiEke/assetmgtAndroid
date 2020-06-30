package com.coronationmb.activityModule.activityModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coronationmb.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.customereid)
    EditText usernameEdit;

    @BindView(R.id.submit)
    Button submit;


    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorlayout;

    ProgressDialog progress;
    Context context;
    String apID;
    private String custId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        this.context = ForgotPasswordActivity.this;
        initUI();
    }

    public void initUI(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //apID="212";
        getToken();
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Connecting.......");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        repo=new GlobalRepository(context);
    }

    @OnClick(R.id.submit)
    public void resetPasswAction(View view){
        progress.show();
         custId=usernameEdit.getText().toString();
        if(TextUtils.isEmpty(custId)){
            progress.dismiss();
            Utility.alertOnly(context,"Enter valid customer ID","");
            return;
        }
        if(!Utility.isInternetAvailable(context)){
            progress.dismiss();
            Snackbar.make(coordinatorlayout,"Check your internet connection",Snackbar.LENGTH_LONG).show();
            return;
        }
        Utility.hideKeyboardFrom(context,view);

        if(SharedPref.getApi_ID(context)==null){
            getId();
            return;
        }
        if(SharedPref.getApp_token(context)==null){
            getToken2();
            return;
        }

        reset();

    }

    private void reset(){

        repo.resetPassword(SharedPref.getApi_ID(context), custId, new OnApiResponse<WebResponse<JsonObject>>() {
            @Override
            public void onSuccess(WebResponse<JsonObject> data) {
                progress.dismiss();
                alert("Password recovery link has been sent to your mail");
            }

            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,message,"");
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void alert(final String msg) {

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Asset Management")
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onBackPressed();
                    }
                })

                .setIcon(R.drawable.lionhead_icon)
                .show();

    }


    private void getToken2(){

        new GlobalRepository(context).getToken(new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {

                SharedPref.setApp_token(context,data);
                reset();
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }


    private void getToken(){

        new GlobalRepository(context).getToken(new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {

                SharedPref.setApp_token(context,data);
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }


    private void getId(){

        new GlobalRepository(context).getAppid(new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {

                SharedPref.setApi_ID(context, data);

                getToken2();
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }


}
