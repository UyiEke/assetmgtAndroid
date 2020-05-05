package com.coronationmb.activityModule.activityModule.ui.NewSubscription;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.requestModel.SubscribeModel;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.CmbResponse;
import com.coronationmb.Model.responseModel.MinimumInvestmentObject;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.NewSubscriptionActivity;
import com.coronationmb.activityModule.activityModule.PaystackWebActivity;
import com.coronationmb.activityModule.activityModule.ui.NewRedemption.NewRedemptionFragment;
import com.coronationmb.activityModule.activityModule.ui.dashbrd.DashboardViewModel;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.SubscriptionHistory;
import com.coronationmb.adapter.SubscriptionViewAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.CustomeSpinner.SearchableSpinner;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class NewSubscriptionFragment extends Fragment {


    @BindView(R.id.product_symbol)
    SearchableSpinner product_symbol;


    @BindView(R.id.amount)
    EditText amount;

    @BindView(R.id.quantity)
    EditText quantity;


    @BindView(R.id.minimum_invest_editText)
    EditText minimum_investment;

    @BindView(R.id.fund_name_editText)
    EditText product_name;

    MinimumInvestmentObject dataObj;


    Context context;
    private ProgressDialog progress;
    GlobalRepository repo;

    ArrayAdapter<String> adapter;
    List<String> fundList;
    List<PortFolioModel> portFolioData;
    private DashboardViewModel dashboardViewModel;
    private List<PortFolioModel> portFolioModels;
    private List<AssetProduct> assetProducts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_new_subscription, container, false);
        ButterKnife.bind(this,root);
        context= getContext();

        ((DashboardActivity)getContext()).changeToolbarTitle("SUBSCRIBE");
        ((DashboardActivity)getContext()).changeHamburgerIconClorBottomNav();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            portFolioData =(List<PortFolioModel>) getArguments().getSerializable("portfolio");
            assetProducts = (List<AssetProduct>)getArguments().getSerializable("product");
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
        repo=new GlobalRepository(context);

        fundList=new ArrayList<>();
        //fundList.add("Select Fund Type");

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, fundList);
        product_symbol.setOnItemSelectedListener(listener);
        product_symbol.setAdapter(adapter);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String amt=amount.getText().toString();
                quantity.setText(amt);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getMinimum();

        /*
        if(portFolioData!=null) {
            showPort(portFolioData);
        }else {
            getPortfolio();
        }
        */

    }

    AdapterView.OnItemSelectedListener listener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String product=product_symbol.getSelectedItem().toString().trim();
            if(product!="Select Fund Type"){

                for(int count=0;count<dataObj.getProduct().size();count++){

                        if (product.equals(dataObj.getProduct().get(count).getProductName())) {
                            product_name.setText(dataObj.getProduct().get(count).getProductCode());
                        }
                }

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @OnClick(R.id.submit)
    public void sendRequest(View view){
        progress.show();
        String productName=product_symbol.getSelectedItem().toString().trim();
        String amt=amount.getText().toString().trim();
        String quantityVal=quantity.getText().toString().trim();
        String miniAmt=minimum_investment.getText().toString().trim();

        if(TextUtils.isEmpty(productName)||TextUtils.isEmpty(amt)||TextUtils.isEmpty(quantityVal)){
            progress.dismiss();
            Utility.alertOnly(context,"Empty Fields","");
            return;
        }

        Long amount=Long.parseLong(amt);
        int mini=Integer.parseInt(miniAmt);

        if(amount<mini){
            progress.dismiss();
            Utility.alertOnly(context,"Amount cannot be less than the minimum investment of N "+miniAmt,"");
            return;
        }

        Utility.hideKeyboardFrom(context,view);

        paymentPreview(amt,productName);

    }

    private void sendSubscriptionRequest(String amount, String ref,String productName,boolean isCMBaccount,
                                         String acctNum, String token){

        progress.show();
        SubscribeModel req=new SubscribeModel();
        req.setAmount(amount);
        req.setAppid(SharedPref.getApi_ID(context));
        req.setEmail(SharedPref.getEMAIL(context));
        req.setCustid(SharedPref.getUSERID(context));
        req.setReference(ref);

        if(isCMBaccount){
            req.setBankcode(Constant.cmbBankCode);
            req.setBankaccount(acctNum);
        }else {
            req.setBankcode("other");
        }

        for(int count=0;count<portFolioData.size();count++){
            if(portFolioData.get(count).getFundName().equals(productName)){
                req.setProductcode(portFolioData.get(count).getFundCode());
            }
        }

        req.setProfile(Constant.profile);
        req.setNarration(SharedPref.getFULLNAME(context)+ " subscription");

        repo.subscriptionAction(SharedPref.getApi_ID(context), req, isCMBaccount,new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {
                progress.dismiss();

                if(isCMBaccount){
                    alert("Debit transaction was Successful");
                }else {

                    Intent intent=new Intent(context, PaystackWebActivity.class);
                    intent.putExtra("PAYMENT_URL",data);
                    startActivity(intent);

                }
            }
            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,"Subscription request failed","");
            }
        });

    }

    private void getPortfolio(){

        UserDetailsParam req=new UserDetailsParam();

        req.setProfile(Constant.profile);
        req.setParams(SharedPref.getUSERID(context));
        req.setFunctionId(Constant.Am_Portfolio);
        req.setAppId(SharedPref.getApi_ID(context));

        repo.getPortfolio(SharedPref.getApi_ID(context), req, new OnApiResponse<List<PortFolioModel>>() {
            @Override
            public void onSuccess(List<PortFolioModel> data) {
                portFolioData=data;
              //  showPort(data);
            }

            @Override
            public void onFailed(String message) {

            }
        });

    }

    private void showPort(MinimumInvestmentObject data){

        if(data!=null){
            if(data.getProduct().size()>0){
                fundList.clear();
            }
        }
        for(int count=0;count<data.getProduct().size();count++){

            fundList.add(data.getProduct().get(count).getProductName());

        }


        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, fundList);
        product_symbol.setAdapter(adapter);

    }

    private void getMinimum(){

        repo.getMinimumInvestment(new OnApiResponse<MinimumInvestmentObject>() {
            @Override
            public void onSuccess(MinimumInvestmentObject data) {

                dataObj=data;

                minimum_investment.setText(data.getMinimumAmount());

                showPort(data);


            }

            @Override
            public void onFailed(String message) {

            }
        });

    }


    private void paymentPreview(String amt,String prodName){
        progress.dismiss();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.payment_preview_layout);
       // dialog.setTitle("Title...");
        TextView transRef = (TextView) dialog.findViewById(R.id.transRef);
        String refs=SharedPref.getUSERID(context)+new StringBuffer(Long.toString(System.currentTimeMillis())).reverse().substring(0,12);
        transRef.setText(refs);

        TextView product_name = (TextView) dialog.findViewById(R.id.product_name);
        product_name.setText(prodName);

        TextView amountTextview = (TextView) dialog.findViewById(R.id.amount);
        amountTextview.setText(amt);

        Spinner pay_Spinner = (Spinner) dialog.findViewById(R.id.pay_Spinner);

        TextView payInfo = (TextView) dialog.findViewById(R.id.payInfo);
       // payInfo.setText(amt);

        LinearLayout pay_layout = (LinearLayout) dialog.findViewById(R.id.pay_layout);

        EditText acct_number = (EditText) dialog.findViewById(R.id.acct_number);
        EditText actt_name = (EditText) dialog.findViewById(R.id.actt_name);
        EditText token = (EditText) dialog.findViewById(R.id.token);

        Button proceedButton = (Button) dialog.findViewById(R.id.proceedButton);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acctNum=null;
                String tokenVal=null;
                String val= pay_Spinner.getSelectedItem().toString().trim();
                boolean isCMBaccount = false;

                if(val.equals("Pay with CMB")){

                    isCMBaccount=true;

                     acctNum=acct_number.getText().toString().trim();
                     tokenVal=token.getText().toString().trim();
                    if(TextUtils.isEmpty(acctNum) || TextUtils.isEmpty(tokenVal)){
                        Toast.makeText(context,"Empty fields", Toast.LENGTH_LONG).show();
                        return;
                    }

                }

                dialog.dismiss();
                sendSubscriptionRequest(amt,refs,prodName,isCMBaccount,acctNum,tokenVal);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        pay_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val= pay_Spinner.getSelectedItem().toString().trim();
                if(val.equals("Pay with CMB")){
                    pay_layout.setVisibility(View.VISIBLE);
                    payInfo.setVisibility(View.GONE);
                    amountTextview.setText(Utility.formatStringToNaira(Utility.round(Double.parseDouble(amt))));
                }else {
                    pay_layout.setVisibility(View.GONE);
                    payInfo.setVisibility(View.VISIBLE);
                    amountTextview.setText(Utility.formatStringToNaira(Utility.round(Double.parseDouble(getCap(amt)))));
                 //   amountTextview.setText(getCap(amt));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        token.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String acct= acct_number.getText().toString();
                if(TextUtils.isEmpty(acct)){
                    return;
                }
                cmbAccount(acct,actt_name);
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void cmbAccount(String acctNum,EditText ed){

        new GlobalRepository(getContext()).verifyCoronationToken(acctNum, new OnApiResponse<CmbResponse>() {
            @Override
            public void onSuccess(CmbResponse data) {
                ed.setVisibility(View.VISIBLE);
                ed.setText(data.getAccountName());
            }

            @Override
            public void onFailed(String message) {
                ed.setVisibility(View.VISIBLE);
                ed.setText("Account can't be verified");
            }
        });

    }


    private String getCap(String amt){

        double val= (Double.parseDouble(amt)) * 0.014;

        if(val>2000){
            return((Double.parseDouble(amt)) +2000)+"";
        }else {
           return((Double.parseDouble(amt))+val)+"";
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
                        // onBackPressed();
                        //   NavController navController = Navigation.findNavController((Activity)getContext(), R.id.nav_host_fragment);
                        //  navController.navigate(R.id.nav_home);

                        findNavController(NewSubscriptionFragment.this).navigate(R.id.nav_home);

                    }
                })

                .setIcon(R.drawable.lionhead_icon)
                .show();

    }


}