package com.coronationmb.activityModule.activityModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.requestModel.SubscribeModel;
import com.coronationmb.Model.requestModel.SubscriptionModel;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.service.Constant;
import com.coronationmb.service.CustomeSpinner.SearchableSpinner;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.coronationmb.service.Utility.round;

public class NewSubscriptionActivity extends AppCompatActivity {

    @BindView(R.id.product_symbol)
    SearchableSpinner product_symbol;

    @BindView(R.id.prduct_layout)
    TextInputLayout prduct_layout;

    @BindView(R.id.amount)
    EditText amount;

    @BindView(R.id.backarrow)
    ImageView backarrow;

    @BindView(R.id.quantity)
    EditText quantity;

    @BindView(R.id.product_name)
    EditText product_name;

    Context context;
    private ProgressDialog progress;

    ArrayAdapter<String> adapter;
    List<String> fundList;
    List<PortFolioModel> portFolioData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subscription);
        ButterKnife.bind(this);
        context= NewSubscriptionActivity.this;
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
        fundList.add("Select Fund Type");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fundList);
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

        getPortfolio();

    }

    AdapterView.OnItemSelectedListener listener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String product=product_symbol.getSelectedItem().toString().trim();
            if(product!="Select Fund Type"){

                prduct_layout.setVisibility(View.VISIBLE);

                //  product_name.setText(product);

                for(int count=0;count<portFolioData.size();count++){

                    if(portFolioData!=null) {
                        if (product.equals(portFolioData.get(count).getFundName())) {
                            product_name.setText(portFolioData.get(count).getFundCode());
                         //   quantity.setText(round(Double.parseDouble(portFolioData.get(count).getTotalAssetValue().trim())) + "");
                        }
                    }
                }

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @OnClick(R.id.submit)
    public void sendRequest(){
        progress.show();
        String productName=product_symbol.getSelectedItem().toString().trim();
        String amt=amount.getText().toString().trim();
        String quantityVal=quantity.getText().toString().trim();

        if(TextUtils.isEmpty(productName)||TextUtils.isEmpty(amt)||TextUtils.isEmpty(quantityVal)){
            progress.dismiss();
            Utility.alertOnly(context,"Empty Fields","");
            return;
        }
        SubscribeModel req=new SubscribeModel();
        req.setAmount(amt);
        req.setAppid(SharedPref.getApi_ID(context));
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

        repo.subscriptionAction(SharedPref.getApi_ID(context), req, new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {
                progress.dismiss();
              Intent intent=new Intent(context,PaystackWebActivity.class);
                intent.putExtra("PAYMENT_URL",data);
                startActivity(intent);
            }
            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,"failed","");
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
                if(data!=null){
                   if(data.size()>0){
                       fundList.clear();
                   }
                }

                for(int count=0;count<data.size();count++){

                    if(data.get(count).getFundName()!="Cash")
                    fundList.add(data.get(count).getFundName());
                }
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, fundList);
                product_symbol.setAdapter(adapter);
            }

            @Override
            public void onFailed(String message) {

            }
        });

    }

    @OnClick(R.id.backarrow)
    public void backArrow(){
        onBackPressed();
    }

}
