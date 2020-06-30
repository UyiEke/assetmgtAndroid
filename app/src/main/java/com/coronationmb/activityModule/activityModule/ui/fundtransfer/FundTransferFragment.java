package com.coronationmb.activityModule.activityModule.ui.fundtransfer;

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

import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.NewFundTransferActivity;
import com.coronationmb.activityModule.activityModule.NewSubscriptionActivity;
import com.coronationmb.adapter.RedemptionViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FundTransferFragment extends Fragment {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    Context context;

    private FundTransferViewModel fundTransferViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fundTransferViewModel = ViewModelProviders.of(this).get(FundTransferViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fund_transfer, container, false);
        ButterKnife.bind(this,root);
        initUI();

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }



    public void initUI(){

        ((DashboardActivity)context).changeToolbarTitle("Fund Transfer");
        RedemptionViewAdapter adapter=new RedemptionViewAdapter(getChildFragmentManager(),context);
        adapter.addFragment(FundTransferHistoryFragment.newInstance("Pending",""),"Pending");
        adapter.addFragment(FundTransferHistoryFragment.newInstance("Completed",""),"Completed");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager,true);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @OnClick(R.id.fab)
    public void fabAction(){
        startActivity(new Intent(context, NewFundTransferActivity.class));
    }


}