package com.coronationmb.activityModule.activityModule.ui.helpDesk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.coronationmb.Model.requestModel.SupportModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.MainActivity;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.navigation.fragment.NavHostFragment.findNavController;


public class HelpDeskFragment extends Fragment {

    @BindView(R.id.custIDEdit)
    EditText custIDEdit;

    @BindView(R.id.subjectEdit)
    EditText subjectEdit;
    @BindView(R.id.complainEdit)
    EditText complainEdit;

    @BindView(R.id.submit)
    Button submit;
    GlobalRepository repo;
    private HelpDeskViewModel helpDeskViewModel;
    private ProgressDialog progress;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpDeskViewModel = ViewModelProviders.of(this).get(HelpDeskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_help_desk, container, false);

        ButterKnife.bind(this, root);
        context=getContext();

        ((DashboardActivity)context).changeToolbarTitle("HELP DESK");
        ((DashboardActivity)getContext()).changeHamburgerIconClor();

        initUI();
        return root;
    }

    private void initUI() {
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("please wait.......");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCanceledOnTouchOutside(false);

        if(SharedPref.getUSERID(context)!=null){
            custIDEdit.setText(SharedPref.getUSERID(context));
        }


        repo=new GlobalRepository(context);
    }

    @OnClick(R.id.submit)
    public void helpAction(){
        progress.show();
        String custID= custIDEdit.getText().toString().trim();
        String subject= subjectEdit.getText().toString().trim();
        String complain= complainEdit.getText().toString().trim();

        if(TextUtils.isEmpty(custID)||TextUtils.isEmpty(subject)||TextUtils.isEmpty(complain)){
            progress.dismiss();
            Utility.alertOnly(context,"Empty Fields","");
            return;
        }
        SupportModel req=new SupportModel();
        req.setCustAid(custID);
        req.setMessage(complain);
        req.setSubject(subject);
        req.setProfile(Constant.profile);



        repo.support(SharedPref.getApi_ID(context), req, new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {
                progress.dismiss();
                alert(data);
            }

            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,message,"");
            }
        });
    }

        public void alert(final String msg) {

            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Asset Management")
                    .setMessage(msg)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                          //  ((DashboardActivity)getContext()).onSupportNavigateUp();
                            findNavController(HelpDeskFragment.this).navigate(R.id.nav_home);

                        }
                    })

                    .setIcon(R.drawable.lionhead_icon)
                    .show();

        }




    public void backAction(){
        findNavController(this).navigateUp();
    }




}