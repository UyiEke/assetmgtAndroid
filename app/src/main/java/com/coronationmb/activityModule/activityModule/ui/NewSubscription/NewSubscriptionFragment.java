package com.coronationmb.activityModule.activityModule.ui.NewSubscription;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;

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
import com.coronationmb.Model.responseModel.MinimumInvestmentObject;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.NewSubscriptionActivity;
import com.coronationmb.activityModule.activityModule.PaystackWebActivity;
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

        int amount=Integer.parseInt(amt);
        int mini=Integer.parseInt(miniAmt);

        if(amount<mini){
            progress.dismiss();
            Utility.alertOnly(context,"Amount cannot be less than the minimum investment of N "+miniAmt,"");
            return;
        }

        Utility.hideKeyboardFrom(context,view);

        SubscribeModel req=new SubscribeModel();
        req.setAmount(amt);
        req.setAppid(Constant.APPID);
        req.setEmail(SharedPref.getEMAIL(context));
        req.setCustid(SharedPref.getUSERID(context));

        req.setReference(SharedPref.getUSERID(context)+new StringBuffer(Long.toString(System.currentTimeMillis())).reverse().substring(0,8));

        for(int count=0;count<portFolioData.size();count++){
            if(portFolioData.get(count).getFundName().equals(productName)){
                req.setProductcode(portFolioData.get(count).getFundCode());
            }
        }

        req.setProfile(Constant.profile);
        req.setNarration(SharedPref.getFULLNAME(context)+ " subscription");

        repo.subscriptionAction(Constant.APPID, req, new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {
                progress.dismiss();
                Intent intent=new Intent(context, PaystackWebActivity.class);
                intent.putExtra("PAYMENT_URL",data);
                startActivity(intent);
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
        req.setAppId(Constant.APPID);

        repo.getPortfolio(Constant.APPID, req, new OnApiResponse<List<PortFolioModel>>() {
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

}