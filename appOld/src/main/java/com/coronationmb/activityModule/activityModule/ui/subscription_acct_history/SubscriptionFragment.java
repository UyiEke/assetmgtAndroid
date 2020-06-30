package com.coronationmb.activityModule.activityModule.ui.subscription_acct_history;

import android.content.Context;
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
import com.coronationmb.activityModule.activityModule.ui.PaymentTransactionHistory.PaymentTransactionHistoryFragment;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.RedemptionHistory;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.SubscriptionHistory;
import com.coronationmb.adapter.SubscriptionViewAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscriptionFragment extends Fragment {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

  //  @BindView(R.id.fab)
   // FloatingActionButton fab;

    private SubscriptionViewModel subscriptionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subscriptionViewModel = ViewModelProviders.of(this).get(SubscriptionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subscription, container, false);
        ButterKnife.bind(this,root);
        initUI();

       ((DashboardActivity)context).changeToolbarTitle("Account History");

        ( (DashboardActivity)context).changeHamburgerIconClor();


        return root;
    }

    public void initUI(){

       // ((DashboardActivity)context).changeToolbarTitle("Subscription");
        SubscriptionViewAdapter adapter=new SubscriptionViewAdapter(getChildFragmentManager(),context);

       adapter.addFragment(new RedemptionHistory(),"Redemption");
        adapter.addFragment(new SubscriptionHistory(),"Subscription");
        adapter.addFragment(new PaymentTransactionHistoryFragment(),"Transaction");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager,true);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    /*
    @OnClick(R.id.fab)
    public void fabAction(){
        startActivity(new Intent(context, NewSubscriptionActivity.class));
    }
    */


}