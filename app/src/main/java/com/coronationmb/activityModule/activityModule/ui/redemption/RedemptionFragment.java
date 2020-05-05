package com.coronationmb.activityModule.activityModule.ui.redemption;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.NewRedemptionActivity;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.RedemptionHistory;
import com.coronationmb.adapter.RedemptionViewAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedemptionFragment extends Fragment {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    Context context;
    private List<PortFolioModel> portFolioData;
    private RedemptionViewModel redemptionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        redemptionViewModel = ViewModelProviders.of(this).get(RedemptionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_redemption, container, false);
        ButterKnife.bind(this,root);
        context= getContext();
        getPortfolio();

        initUI();
        return root;
    }

    public void initUI(){
        ((DashboardActivity)context).changeToolbarTitle("Redemption");
        RedemptionViewAdapter adapter=new RedemptionViewAdapter(getChildFragmentManager(),context);
        adapter.addFragment(RedemptionHistory.newInstance("Pending","1"),"Pending");
      //  adapter.addFragment(RedemptionHistory.newInstance("Completed","1"),"Completed");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager,true);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    /*
    @OnClick(R.id.fab)
    public void fabAction(){
        Intent intent=new Intent(context, NewRedemptionActivity.class);
        intent.putExtra("portfolio",portFolioData);
        startActivity(intent);
    }
    */

    private void getPortfolio(){

        UserDetailsParam req=new UserDetailsParam();

        req.setProfile(Constant.profile);
        req.setParams(SharedPref.getUSERID(context));
        req.setFunctionId(Constant.Am_Portfolio);
        req.setAppId(SharedPref.getApi_ID(context));

        new GlobalRepository(context).getPortfolio(SharedPref.getApi_ID(context), req, new OnApiResponse<List<PortFolioModel>>() {

            @Override
            public void onSuccess(List<PortFolioModel> data) {
                portFolioData=data;
            }
            @Override
            public void onFailed(String message) {

            }
        });

    }

}