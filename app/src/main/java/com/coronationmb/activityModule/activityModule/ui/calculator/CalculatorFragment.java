package com.coronationmb.activityModule.activityModule.ui.calculator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.adapter.CalculatorAdapter;
import com.coronationmb.adapter.RedemptionAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class CalculatorFragment extends Fragment {

    Context context;
    private CalculatorViewModel calculatorViewModel;


    @BindView(R.id.calculator_recycler)
    RecyclerView recycler;


    CalculatorAdapter adapter;
    LinearLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        calculatorViewModel = ViewModelProviders.of(this).get(CalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        ((DashboardActivity)getContext()).changeToolbarTitle("CALCULATOR");


        ( (DashboardActivity)getContext()).changeHamburgerIconClor();

        ButterKnife.bind(this,root);
        context= getContext();
        initUI();
        return root;
    }

    private void initUI() {

        adapter=new CalculatorAdapter(getStringList(),context);
        layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
    }

   public List<String> getStringList(){
        List<String> list=new ArrayList<>();
        list.add("What will be the value of my investment in the future?");
        list.add("What is the lump sum amount i need to invest now to achieve a future target?");
        list.add("What is the amount i need to invest monthly to achieve a future target");
        list.add("How long (years) will it take me to achieve my future target by investing a lump-sum now?");
        list.add("How long (years) will it take me to achieve my future target by making monthly contributions?");
        return list;
   }

}