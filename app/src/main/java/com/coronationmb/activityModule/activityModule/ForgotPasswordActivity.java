package com.coronationmb.activityModule.activityModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.Utility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.coronationmb.R;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.customereid)
    EditText usernameEdit;

    @BindView(R.id.submit)
    Button submit;

    ProgressDialog progress;
    Context context;
    GlobalRepository repo;
    String apID;

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

        apID="212";
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Connecting.......");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        repo=new GlobalRepository(context);
    }

    @OnClick(R.id.submit)
    public void resetPasswAction(){
        progress.show();
        String custId=usernameEdit.getText().toString();
        if(TextUtils.isEmpty(custId)){
            progress.dismiss();
            Utility.alertOnly(context,"Enter valid customer ID","");
            return;
        }
        repo.resetPassword(apID, custId, new OnApiResponse<WebResponse<JsonObject>>() {
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


}
