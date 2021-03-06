package com.coronationmb.activityModule.activityModule.ui.dashbrd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.ImageDetails;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.adapter.MainDashboardViewPagerAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardFragment extends Fragment {

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private DashboardViewModel dashboardViewModel;

    List<AssetProduct> product;

    List<PortFolioModel> portFolio;
    private List<PortFolioModel> portFolioData;
    private List<AssetProduct> assetProducts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,root);


        return root;
    }

    private void initUI(){

        MainDashboardViewPagerAdapter adapter=new MainDashboardViewPagerAdapter(getChildFragmentManager(),context,portFolioData,assetProducts);
       adapter.addFragment(new MainDashboardViewFragment(),"main");
        adapter.addFragment(new MainDashboardChart(),"chart");


        ((DashboardActivity)context).changeToolbarTitle("DASHBOARD");
        ((DashboardActivity)context).changeHamburgerIconClorBottomNav();

        viewpager.setAdapter(adapter);
        getImage();
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

    private void getImage(){

        Picasso.with(context)
                .load(Constant.BaseUrl+"/profilePictureDownload/"+SharedPref.getUSERID(context)) // web image url
                .fit().centerInside()
                //  .rotate(-90)                    //if you want to rotate by 90 degrees
                .into(((DashboardActivity)context).profile_image);

    }



}