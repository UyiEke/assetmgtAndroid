package com.coronationmb.activityModule.activityModule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.service.Constant;
import com.coronationmb.service.CustomeSpinner.SearchableSpinner;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.coronationmb.service.Utility.round;

public class NewRedemptionActivity extends AppCompatActivity {

    @BindView(R.id.product_symbol)
    SearchableSpinner product_symbol;

    @BindView(R.id.prduct_layout)
    TextInputLayout prduct_layout;

    @BindView(R.id.amount)
    EditText amount;

    @BindView(R.id.quantity)
    EditText quantity;

    @BindView(R.id.product_name)
    EditText product_name;

    @BindView(R.id.backarrow)
    ImageView backarrow;

    Context context;
    private ProgressDialog progress;

    ArrayAdapter<String> adapter;
    List<String> fundList;
    List<PortFolioModel> portFolioData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_redemption);
        ButterKnife.bind(this);
        context= NewRedemptionActivity.this;
        initUI();
    }

    public void initUI(){

        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("New Request");
*/
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

        portFolioData= (List<PortFolioModel>) getIntent().getSerializableExtra("portfolio");

        if(portFolioData!=null){

            if(portFolioData!=null){
                if(portFolioData.size()>0){
                    fundList.clear();
                }
            }

            for(int count=0;count<portFolioData.size();count++){

                if(!portFolioData.get(count).getFundName().equals("Cash"))
                    fundList.add(portFolioData.get(count).getFundName());
            }

        }

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, fundList);
        product_symbol.setAdapter(adapter);
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
                            if(TextUtils.isEmpty(portFolioData.get(count).getTotalAssetValue())) {
                                quantity.setText(round(Double.parseDouble(portFolioData.get(count).getTotalAssetValue().trim())) + "");
                            }
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
        String productSym=product_symbol.getSelectedItem().toString().trim();
        String productName=product_name.getText().toString().trim();
        String amt=amount.getText().toString().trim();
        String quantityVal=quantity.getText().toString().trim();

        if(TextUtils.isEmpty(productName)||TextUtils.isEmpty(amt)||TextUtils.isEmpty(quantityVal)){
            progress.dismiss();
            Utility.alertOnly(context,"Empty Fields","");
            return;
        }
        UserDetailsParam req=new UserDetailsParam();
        req.setAppId(SharedPref.getApi_ID(context));
        req.setFunctionId(Constant.Am_Portfolio);
        req.setProfile(Constant.profile);
        req.setParams(SharedPref.getUSERID(context)+"|"+productSym+"|"+productName+"|"+amt+"1"+amt+"|"+"1");

        repo.redemption(SharedPref.getApi_ID(context), req, new OnApiResponse<ArrayList<PortFolioModel>>() {
            @Override
            public void onSuccess(ArrayList<PortFolioModel> data) {
                alert("Request sent successfully");
            }

            @Override
            public void onFailed(String message) {
                Utility.alertOnly(context,"Request failed","");
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
                        onBackPressed();
                    }
                })

                .setIcon(R.drawable.lionhead_icon)
                .show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @OnClick(R.id.backarrow)
    public void backArrow(){
        onBackPressed();
    }

}
