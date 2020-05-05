package com.coronationmb.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.activityModule.activityModule.ui.dashbrd.MainDashboardChart;
import com.coronationmb.activityModule.activityModule.ui.dashbrd.MainDashboardViewFragment;

import java.util.ArrayList;
import java.util.List;

public class MainDashboardViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    List<String> list;
    List<PortFolioModel> portFolioData;
    List<AssetProduct> assetProducts;

    public MainDashboardViewPagerAdapter(FragmentManager fm, Context context, List<PortFolioModel> portFolioData, List<AssetProduct> assetProducts) {
        super(fm);
        this.context=context;
        list=new ArrayList<>();
        this.portFolioData=portFolioData;
        this.assetProducts=assetProducts;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return MainDashboardViewFragment.newInstance(portFolioData,assetProducts);
            case 1:
                return MainDashboardChart.newInstance(portFolioData,assetProducts);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addFragment(Fragment fragment,String title){
        list.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}

