package com.coronationmb.activityModule.activityModule.ui.portfolio_historymodule;

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
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.PortfolioStatementHistory;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.PortfolioTransactionHistory;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.RedemptionHistory;
import com.coronationmb.adapter.Portfolio_history_ViewAdapter;
import com.coronationmb.adapter.RedemptionViewAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Portfolio_HistoryFragment extends Fragment {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    Context context;

    private ArrayList<PortFolioModel> portFolioData;
    private Portfolio_HistoryViewModel portfolioHistoryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        portfolioHistoryViewModel = ViewModelProviders.of(this).get(Portfolio_HistoryViewModel.class);
        View root = inflater.inflate(R.layout.frament_portfolio_history, container, false);
        ButterKnife.bind(this,root);

        initUI();
        return root;
    }

    public void initUI(){
        ((DashboardActivity)context).changeToolbarTitle("History");
        Portfolio_history_ViewAdapter adapter=new Portfolio_history_ViewAdapter(getChildFragmentManager(),context);
        adapter.addFragment(PortfolioTransactionHistory.newInstance("Pending","1"),"Transaction");
       adapter.addFragment(PortfolioStatementHistory.newInstance("Completed","1"),"Statement");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager,true);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


}