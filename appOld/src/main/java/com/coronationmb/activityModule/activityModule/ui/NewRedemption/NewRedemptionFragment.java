package com.coronationmb.activityModule.activityModule.ui.NewRedemption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.NewRedemptionActivity;
import com.coronationmb.activityModule.activityModule.NewSubscriptionActivity;
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
import static com.coronationmb.service.Utility.round;

public class NewRedemptionFragment extends Fragment {



    @BindView(R.id.product_symbol)
    SearchableSpinner product_symbol;



    @BindView(R.id.amount)
    EditText amount;

    @BindView(R.id.quantity)
    EditText quantity;

    @BindView(R.id.fund_name_editText)
    EditText product_name;



    private ProgressDialog progress;
    GlobalRepository repo;

    ArrayAdapter<String> adapter;
    List<String> fundList;
    List<PortFolioModel> portFolioData;

    private NewRedemptionViewModel newRedemptionViewModel;

    private DashboardViewModel dashboardViewModel;
    private List<AssetProduct> assetProducts;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_new_redemption, container, false);
        ButterKnife.bind(this,root);


        ((DashboardActivity)context).changeToolbarTitle("REDEEM");
        ((DashboardActivity)context).changeHamburgerIconClorBottomNav();

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
        fundList.add("Select Fund Type");



        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, fundList);
        product_symbol.setOnItemSelectedListener(listener);
        product_symbol.setAdapter(adapter);


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

              //  prduct_layout.setVisibility(View.VISIBLE);
                //  product_name.setText(product);

                for(int count=0;count<portFolioData.size();count++){

                    if(portFolioData!=null) {
                        if (product.equals(portFolioData.get(count).getFundName())) {
                            product_name.setText(portFolioData.get(count).getFundCode());

                            if(!TextUtils.isEmpty(portFolioData.get(count).getTotalAssetValue())) {
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
    public void sendRequest(View view){
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

        Double quantVal= Double.parseDouble(quantityVal);
        Double amtV= Double.parseDouble(amt);

        if(amtV>quantVal && amtV != quantVal){

            progress.dismiss();
            Utility.alertOnly(context,"Your amount cannot be greater than available balance","");
            return;

        }


        Utility.hideKeyboardFrom(context,view);

        UserDetailsParam req=new UserDetailsParam();
        req.setAppId(SharedPref.getApi_ID(context));
        req.setFunctionId(Constant.redeem);
        req.setProfile(Constant.profile);
        req.setParams(SharedPref.getUSERID(context)+"|"+productSym+"|"+productName+"|"+amt+"|1|"+amt+"|"+"1");


        repo.redemption(SharedPref.getApi_ID(context), req, new OnApiResponse<ArrayList<PortFolioModel>>() {
            @Override
            public void onSuccess(ArrayList<PortFolioModel> data) {
                progress.dismiss();
                alert("Redemption request sent successfully");
            }

            @Override
            public void onFailed(String message) {
                progress.dismiss();
                Utility.alertOnly(context,"Redemption request failed, Please try again","");
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
                       // onBackPressed();
                     //   NavController navController = Navigation.findNavController((Activity)getContext(), R.id.nav_host_fragment);
                      //  navController.navigate(R.id.nav_home);

                        findNavController(NewRedemptionFragment.this).navigate(R.id.nav_home);

                    }
                })

                .setIcon(R.drawable.lionhead_icon)
                .show();

    }

}