package com.coronationmb.activityModule.activityModule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.ImageDetails;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.ui.dashbrd.DashboardViewModel;
import com.coronationmb.activityModule.activityModule.ui.dashbrd.MainDashboardChart;
import com.coronationmb.activityModule.activityModule.ui.dashbrd.MainDashboardViewFragment;
import com.coronationmb.activityModule.activityModule.ui.portfolio_historymodule.Portfolio_HistoryFragment;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.PortfolioStatementHistory;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.PortfolioTransactionHistory;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.RedemptionHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coronationmb.activityModule.activityModule.ui.userActionhistory.SubscriptionHistory;
import com.coronationmb.adapter.MainDashboardAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends BaseActivity implements RedemptionHistory.OnFragmentInteractionListener,
        MainDashboardViewFragment.OnFragmentInteractionListener, MainDashboardChart.OnFragmentInteractionListener,
        SubscriptionHistory.OnFragmentInteractionListener, PortfolioTransactionHistory.OnFragmentInteractionListener,
        PortfolioStatementHistory.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;

    @BindView(R.id.toolbarTextview)
    public TextView toolbarTextview;

    public TextView userid;

    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    Context context;
    public TextView usernameTextview;
    public CircleImageView profile_image;
    private List<String> listOfValidIDs;

    NavController navController;

    Toolbar toolbar;

    Bundle bundle;
    DashboardViewModel dashboardViewModel;
    private List<PortFolioModel> port;
    private List<AssetProduct> product;

   // ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawer;

    GlobalRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bundle=savedInstanceState;

        ButterKnife.bind(this);
        context=DashboardActivity.this;

        repo=new GlobalRepository(context);

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        getPortfolio();
        getProduct();


        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.account_history, R.id.subscription,
                R.id.redemption,R.id.calculator, R.id.refer_friend, R.id.help_desk,
                R.id.settings,R.id.new_subscribe,R.id.new_redemption,R.id.profile)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        Menu menu = navigationView.getMenu();
        MenuItem login = menu.findItem(R.id.logout);

        SpannableString spannableString = new SpannableString("Log Out");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue_grey)), 0, spannableString.length(), 0);

        login.setTitle(spannableString);

        String accountStatus=getIntent().getStringExtra("Temp");

        if(accountStatus!=null){

            if(accountStatus.equals("temporal account")){
                completeAccountDialog("You are yet to complete your account setup hence there is limit to what you can do with this application."+"\n"+
                        "Click on CONTINUE to complete your account setup");
            }

        }

        View headerLayout = mNavigationView.getHeaderView(0);
        profile_image = (CircleImageView) headerLayout.findViewById(R.id.profile_image);
        usernameTextview=(TextView)headerLayout.findViewById(R.id.usernameTextview);

        userid=(TextView)headerLayout.findViewById(R.id.userid);

        usernameTextview.setText(SharedPref.getFULLNAME(context));

        userid.setText("Customer ID: "+SharedPref.getUSERID(context));

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void changeToolbarTitle(String title){
        toolbarTextview.setText(title);
    }


    @Override
    public void onBackPressed() {
      //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //  super.onBackPressed();
            new AlertDialog.Builder(context)
                    .setTitle(getString(R.string.app_alias))
                    .setMessage(context.getString(R.string.exiting_app_question))
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            exitApp();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(R.drawable.lionhead_icon)
                    .show();
        }
    }


    private void exitApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        }
        else{
            this.finish();
        }
        // System.exit(0);
    }

    private void completeAccountDialog(String msg){

        new AlertDialog.Builder(context)
                .setTitle("Asset Management")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context,AcctSetUpActivity.class));
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


    public void logout(MenuItem item) {

        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_alias))
                .setMessage(context.getString(R.string.log_out))
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.lionhead_icon)
                .show();


    }


    @OnClick({R.id.home_layout,R.id.subscribe_layout,R.id.redeem_layout,R.id.profile_layout})
    public void bottomNavAction(View view){

        int id=view.getId();
       // changeHamburgerIconClorBottomNav();

        switch (id){

            case R.id.home_layout:

                dashboardViewModel= ViewModelProviders.of(DashboardActivity.this).get(DashboardViewModel.class);
                dashboardViewModel.getPortfolio().observe(this, new Observer<List<PortFolioModel>>() {
                    @Override
                    public void onChanged(List<PortFolioModel> portFolioModels) {
                        port=portFolioModels;
                    }
                });

                dashboardViewModel.getProductList().observe(this, new Observer<List<AssetProduct>>() {
                    @Override
                    public void onChanged(List<AssetProduct> assetProducts) {
                        product=assetProducts;
                    }
                });

                Bundle bundle0=new Bundle();
                bundle0.putSerializable("portfolio", (Serializable) port);
                bundle0.putSerializable("product", (Serializable) product);


               // navController.navigate(R.id.nav_home);
                navController.navigate(R.id.nav_home,bundle0);

                break;

            case R.id.subscribe_layout:
                
                dashboardViewModel= ViewModelProviders.of(DashboardActivity.this).get(DashboardViewModel.class);
                dashboardViewModel.getPortfolio().observe(this, new Observer<List<PortFolioModel>>() {
                    @Override
                    public void onChanged(List<PortFolioModel> portFolioModels) {
                        port=portFolioModels;
                    }
                });

                dashboardViewModel.getProductList().observe(this, new Observer<List<AssetProduct>>() {
                    @Override
                    public void onChanged(List<AssetProduct> assetProducts) {
                        product=assetProducts;
                    }
                });

                Bundle bundle=new Bundle();
                bundle.putSerializable("portfolio", (Serializable) port);
                bundle.putSerializable("product", (Serializable) product);

                navController.navigate(R.id.new_subscribe,bundle);

                break;

            case R.id.redeem_layout:

                dashboardViewModel= ViewModelProviders.of(DashboardActivity.this).get(DashboardViewModel.class);
                dashboardViewModel.getPortfolio().observe(this, new Observer<List<PortFolioModel>>() {
                    @Override
                    public void onChanged(List<PortFolioModel> portFolioModels) {
                        port=portFolioModels;
                    }
                });

                dashboardViewModel.getProductList().observe(this, new Observer<List<AssetProduct>>() {
                    @Override
                    public void onChanged(List<AssetProduct> assetProducts) {
                        product=assetProducts;
                    }
                });


                Bundle bundle2=new Bundle();
                bundle2.putSerializable("portfolio", (Serializable) port);
                bundle2.putSerializable("product", (Serializable) product);


                navController.navigate(R.id.new_redemption,bundle2);

                break;

            case R.id.profile_layout:


                dashboardViewModel= ViewModelProviders.of(DashboardActivity.this).get(DashboardViewModel.class);
                dashboardViewModel.getPortfolio().observe(this, new Observer<List<PortFolioModel>>() {
                    @Override
                    public void onChanged(List<PortFolioModel> portFolioModels) {
                        port=portFolioModels;
                    }
                });

                dashboardViewModel.getProductList().observe(this, new Observer<List<AssetProduct>>() {
                    @Override
                    public void onChanged(List<AssetProduct> assetProducts) {
                        product=assetProducts;
                    }
                });


                Bundle bundle3=new Bundle();
                bundle3.putSerializable("portfolio", (Serializable) port);
                bundle3.putSerializable("product", (Serializable) product);


                navController.navigate(R.id.profile,bundle3);

                break;


        }

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
                dashboardViewModel.setPortfolio(data);
            }
            @Override
            public void onFailed(String message) {

            }
        });

    }

    private void getProduct(){

        UserDetailsParam req=new UserDetailsParam();
        req.setProfile(Constant.profile);
        req.setAppId(SharedPref.getApi_ID(context));
        req.setFunctionId(Constant.product);

        repo.getProductAss(SharedPref.getApi_ID(context), req, new OnApiResponse<List<AssetProduct>>() {
            @Override
            public void onSuccess(List<AssetProduct> data) {
                product=data;
                dashboardViewModel.setProductList(data);

            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    public void changeHamburgerIconClor(){

        int color = Color.parseColor("#002A5C");
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        for (int i = 0; i < toolbar.getChildCount(); i++){
            final View v = toolbar.getChildAt(i);
            if(v instanceof ImageButton) {
                ((ImageButton)v).setColorFilter(colorFilter);
            }
        }

        toolbar.setBackgroundColor(Color.parseColor("#ffffff"));

        toolbarTextview.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    public void changeHamburgerIconClorBottomNav(){

        int color = Color.parseColor("#ffffff");
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        for (int i = 0; i < toolbar.getChildCount(); i++){
            final View v = toolbar.getChildAt(i);
            if(v instanceof ImageButton) {
                ((ImageButton)v).setColorFilter(colorFilter);
            }
        }

        toolbar.setBackgroundColor(Color.parseColor("#00000000"));

        toolbarTextview.setTextColor(getResources().getColor(R.color.white));

    }

/*
    public void getImage(){
        new GlobalRepository(context).downloadPofilePix(SharedPref.getUSERID(context), new OnApiResponse<byte[]>() {
            @Override
            public void onSuccess(byte[] data) {

            }

            @Override
            public void onFailed(String message) {

            }
        });
    }
    */

}
