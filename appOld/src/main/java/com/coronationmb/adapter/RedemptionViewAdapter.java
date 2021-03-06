package com.coronationmb.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coronationmb.activityModule.activityModule.ui.userActionhistory.RedemptionHistory;

import java.util.ArrayList;
import java.util.List;

public class RedemptionViewAdapter extends FragmentPagerAdapter {
    Context context;
    List<String> titleList;
    List<Fragment> fragmentList;


    public RedemptionViewAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
        titleList=new ArrayList<>();
        fragmentList=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return RedemptionHistory.newInstance("","1");
            case 1:
                return RedemptionHistory.newInstance("","1");

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    public void addFragment(Fragment fragment,String title){
        titleList.add(title);
        fragmentList.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
