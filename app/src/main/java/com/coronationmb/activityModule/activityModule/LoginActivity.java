package com.coronationmb.activityModule.activityModule;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.requestModel.LoginModel;
import com.coronationmb.R;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {
    Context context;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ProgressDialog progress;

    @BindView(R.id.usernameEdit)
    EditText usernameEdit;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.forgotPassTextView)
    TextView forgotPassTextView;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorlayout;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.signUp)
    TextView signUp;

    private String custID;
    private String passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.context = LoginActivity.this;

        getToken();

       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }

        initUI();
    }

    public void initUI(){
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("please wait.......");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCanceledOnTouchOutside(false);


        if(SharedPref.getUSERID(context)!=null){
            usernameEdit.setText(SharedPref.getUSERID(context));
        }

        if(!Utility.isInternetAvailable(context)){
            Snackbar.make(coordinatorlayout,"Check your internet connection",Snackbar.LENGTH_LONG).show();
            return;
        }
    }

    private boolean checkPermission() {

        boolean a=ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        boolean b=ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        boolean c=ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED;

        boolean d=ContextCompat.checkSelfPermission(this,  Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;


        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,  Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,  Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_PHONE_STATE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission is neccessary");
                alertBuilder.setMessage("Permission to device storage is necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE,READ_PHONE_STATE,Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE,READ_PHONE_STATE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }

    @OnClick(R.id.submit)
    public void loginAction(View view){



        if(SharedPref.getApi_ID(context) == null){

            getId();

        }


         progress.show();
         custID=usernameEdit.getText().toString().trim();
         passw=passwordEditText.getText().toString().trim();
        if(TextUtils.isEmpty(custID)|| TextUtils.isEmpty(passw)){
            progress.dismiss();
            Utility.alertOnly(context,"Empty Fields","");
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
      //  login();
        getToken2();
    }


    private void login(){

        LoginModel req=new LoginModel();
        req.setCustUserID(custID);
        req.setProfile(Constant.profile);
        req.setPWD(passw);
        new GlobalRepository(context).login(SharedPref.getApi_ID(context), req, new OnApiResponse<WebResponse<JsonObject>>() {
            @Override
            public void onSuccess(WebResponse<JsonObject> data) {
                progress.dismiss();

                if(custID.contains("@")){

                    if(SharedPref.getIsInfowareActive(context)){
                        Utility.alertOnly(context,"Your account is now active, Kindly login with the Customer ID and password details sent to your mail","");
                    }
                    else {
                        Intent intent=new Intent(context,DashboardActivity.class);
                        intent.putExtra("Temp","temporal account");
                        startActivity(intent);
                    }
                }
                else if(SharedPref.getPASSWORDCHANGED(context)) {
                    startActivity(new Intent(context, DashboardActivity.class));
                  //  Utility.hideKeyboardFrom(context,view);

                }else {
                    startActivity(new Intent(context, ChangePasswordActivity.class));
               //     Utility.hideKeyboardFrom(context,view);
                }
            }
            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,message,"");
            }
        });

    }



    @OnClick(R.id.signUp)
    public void createAccount(){
        startActivity(new Intent(context, SignUpActivity.class));
    }

    @OnClick(R.id.forgotPassTextView)
    public void forgotPasswordAction(){
        startActivity(new Intent(context,ForgotPasswordActivity.class));
    }



    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private void getToken2(){

        new GlobalRepository(context).getToken(new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {

                    SharedPref.setApp_token(context,data);
                    login();
            }

            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,"Failed to connect, please try again","");
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
