package com.coronationmb.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.ui.onboarding.OnboardingFragment;

import java.util.ArrayList;
import java.util.List;

public class OnboardingViewAdapter extends FragmentPagerAdapter {
    Context context;
    List<String> titleList;
    List<Fragment> fragmentList;


    public OnboardingViewAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
        titleList=new ArrayList<>();
        fragmentList=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return OnboardingFragment.newInstance(R.drawable.splash_screen_1, "The smartest way to grow your wealth");
            case 1:
                return OnboardingFragment.newInstance(R.drawable.splash_screen_2,"Grow together with a joint account");

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
