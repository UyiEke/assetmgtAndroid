package com.coronationmb.activityModule.activityModule.ui.changePassword;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.requestModel.ChangePasswModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.LoginActivity;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChangePasswdFragment extends Fragment {


    @BindView(R.id.usernameEdit)
    EditText usernameEdit;

    @BindView(R.id.oldPasswdEditText)
    EditText oldPasswdEditText;

    @BindView(R.id.newpasswordEditText)
    EditText newpasswordEditText;

    @BindView(R.id.confirmpasswordEditText)
    EditText confirmpasswordEditText;

    @BindView(R.id.submit)
    Button submit;

    Context context;

    private ChangePasswdViewModel changePasswdViewModel;
    private ProgressDialog progress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        changePasswdViewModel = ViewModelProviders.of(this).get(ChangePasswdViewModel.class);
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this,root);

        ((DashboardActivity)context).changeToolbarTitle("SETTINGS");


        ( (DashboardActivity)context).changeHamburgerIconClor();

        initUI();
        return root;
    }

    private void initUI() {
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("please wait.......");
        progress.setIndeterminate(true);
        progress.setProgress(0);

        usernameEdit.setText(SharedPref.getUSERID(context));
    }

    @OnClick(R.id.submit)
    public void changePasswd(){
        progress.show();
        String custId=usernameEdit.getText().toString().trim();
        String oldPasswd=oldPasswdEditText.getText().toString().trim();
        String newPasswd=newpasswordEditText.getText().toString().trim();

        String confirmpasswordE=confirmpasswordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(custId)||TextUtils.isEmpty(oldPasswd)||TextUtils.isEmpty(newPasswd)
                ||TextUtils.isEmpty(confirmpasswordE)){
            progress.dismiss();
            Utility.alertOnly(context,"Empty Field","");
            return;
        }





        if(!isPasswordPolicyValid(newPasswd)){

            progress.dismiss();
            Utility.alertOnly(context,"Password should be minimum of nine character, at least one uppercase letter, one lowercase letter, one number and one special character","");
            return;
        }



        if(!isPasswordPolicyValid(confirmpasswordE)){

            progress.dismiss();
            Utility.alertOnly(context,"Password should be minimum of nine character, at least one uppercase letter, one lowercase letter, one number and one special character","");
            return;
        }






        if(!(confirmpasswordE.equals(newPasswd))){
            progress.dismiss();
            Utility.alertOnly(context,"Password Mis-match","");
            return;
        }

        ChangePasswModel req=new ChangePasswModel();
        req.setCurrentPassword(oldPasswd);
        req.setCustAID(custId);
        req.setNewPassword(newPasswd);
        req.setProfile("am");


        new GlobalRepository(context).changePassword(SharedPref.getApi_ID(context), req, new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {
                progress.dismiss();
                alert(data,"");
            }

            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,message,"");
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }



    public void alert(final String msg, String title) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg)
                .setTitle(title)
                .setIcon(R.drawable.lionhead_icon);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        startActivity(new Intent(context, LoginActivity.class));
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public boolean isPasswordPolicyValid(String passwd){ // returns true, if its valid

        if (passwd.length() < 9){

            return false;

        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(passwd);

        if (!matcher.matches()) {
            //  System.out.println("string '"+str + "' contains special character");
            return true;
        } else {
            //   System.out.println("string '"+str + "' doesn't contains special character");
            return false;
        }

    }

}