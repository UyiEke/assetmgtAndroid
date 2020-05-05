package com.coronationmb.activityModule.activityModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.requestModel.CreateAccount;
import com.coronationmb.Model.requestModel.TemporarySignUpModel;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coronationmb.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
Context context;
    private ProgressDialog progress;
    GlobalRepository repo;

    @BindView(R.id.fullname)
    EditText fullname;

    @BindView(R.id.phone_no)
    EditText phone_no;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.back_to_login)
    TextView back_to_login;



    @BindView(R.id.submit)
    Button submit;

    String groupNumber;
    int val;
    List<TemporarySignUpModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugn_up);
        ButterKnife.bind(this);
        this.context = SignUpActivity.this;

        initUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Connecting.......");
        progress.setIndeterminate(true);
        progress.setProgress(0);

        repo=new GlobalRepository(context);
        list=new ArrayList<>();

         val=getIntent().getIntExtra("group",0);

        if(val==2){
           // groupAcctDalog();
        }

    }

    @OnClick(R.id.submit)
    public void signUpAction(){

        progress.show();
        String fName=fullname.getText().toString().trim();
        String eMail=email.getText().toString().trim();
        String phNumber=phone_no.getText().toString().trim();
        String passw=passwordEditText.getText().toString().trim();


        /*
        if(val==2){
            progress.dismiss();
            groupAcctDalog();
            return;
        }
        */


        if(TextUtils.isEmpty(fName)||TextUtils.isEmpty(eMail)||TextUtils.isEmpty(phNumber)||TextUtils.isEmpty(passw)){
            progress.dismiss();
            Utility.alertOnly(context,"Empty Fields","");
            return;
        }

        if(Utility.validatePhoneNumber(phNumber)){
            progress.dismiss();
            Utility.alertOnly(context, "Invalid phone number", "");
            return;
        }

        if(!Utility.validateEmail(eMail)){
            progress.dismiss();
            Utility.alertOnly(context, "Invalid email", "");
            return;
        }

        /*
        if(val==2){
            TemporarySignUpModel req=new TemporarySignUpModel();
            return;
        }
    */

        TemporarySignUpModel req=new TemporarySignUpModel();
        req.setEmail(eMail);
        req.setName(fName);
        req.setPhoneNumber(phNumber);
        req.setPassword(passw);
        repo.createTemporaryAccount(SharedPref.getApi_ID(context), req, new OnApiResponse<WebResponse<String>>() {
            @Override
            public void onSuccess(WebResponse<String> data) {
                progress.dismiss();
              //  alert(data.getMessage(),"");
                alert("Account created successfully, you can now login with your email and with the password you registered with","");
            }
            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,message,"");
            }
        });
    }


    public void createAcctAction(List<TemporarySignUpModel> model){

    }

@OnClick(R.id.back_to_login)
public void backtoLogin(){

        startActivity(new Intent(context,LoginActivity.class));

}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void alert(final String msg, String title) {

        new AlertDialog.Builder(context)
                .setTitle("Asset Management")
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // startActivity(new Intent(context,AcctSetUpActivity.class));
                        dialog.dismiss();
                        onBackPressed();
                    }
                })
                /*
                .setNegativeButton("SKIP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                */
                .setIcon(R.drawable.lionhead_icon)
                .show();
    }


    public void groupAcctDalog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Group Account");

        dialog.setMessage("You have choosen to create a group account, kindly Specify the number of people involved");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText usernum = new EditText(this);
        usernum.setHint("Total number group members");
        usernum.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(20,5,20,5);
        usernum.setLayoutParams(lp);
        layout.addView(usernum);

        dialog.setView(layout);
        dialog.setCancelable(false);
        dialog.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String num = usernum.getText().toString();

                        if(TextUtils.isEmpty(num)){

                            Toast.makeText(context,"Field Empty",Toast.LENGTH_LONG).show();
                                return;
                        }
                        else{
                            groupNumber=num;

                            Toast.makeText(context,"Total number of members captured!",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
        dialog.show();
    }




}
