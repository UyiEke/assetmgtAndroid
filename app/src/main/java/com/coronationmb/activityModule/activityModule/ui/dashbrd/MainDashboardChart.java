package com.coronationmb.activityModule.activityModule.ui.dashbrd;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.adapter.MainDashboardPortfolioAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainDashboardChart.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainDashboardChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDashboardChart extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.piechart)
    PieChart piechart;

    public List<PortFolioModel> chartPortFolio;

    DashboardViewModel dashboardViewModel;
//    private List<PortFolioModel> portFolio;

    // ArrayList<PortFolioModel> portFolio;

    public MainDashboardChart() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainDashboardChart newInstance(List<PortFolioModel> portFolioData, List<AssetProduct> assetProducts) {

        MainDashboardChart fragment = new MainDashboardChart();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) portFolioData);
        args.putSerializable(ARG_PARAM2, (Serializable) assetProducts);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            chartPortFolio = (List<PortFolioModel>) getArguments().getSerializable(ARG_PARAM1);
           // product = (List<AssetProduct>) getArguments().getSerializable(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_main_dashboard_chart, container, false);
        ButterKnife.bind(this, root);

        ((DashboardActivity)getContext()).changeToolbarTitle("DASHBOARD");

        if(!SharedPref.getUSERID(getContext()).contains("@")){
            if (chartPortFolio != null) {
                if (chartPortFolio.size() > 0) {
                    displayGraph(chartPortFolio);
                }
            } else {


                dashboardViewModel= ViewModelProviders.of(getActivity()).get(DashboardViewModel.class);
                dashboardViewModel.getPortfolio().observe(this, new Observer<List<PortFolioModel>>() {
                    @Override
                    public void onChanged(List<PortFolioModel> portFolioModels) {
                        chartPortFolio=portFolioModels;
                    }
                });
                if(chartPortFolio!=null) {
                    if (chartPortFolio.size() > 0) {
                        displayGraph(chartPortFolio);
                    } else {
                        getPortfolio();
                    }
                }else {
                    getPortfolio();
                }
            }
        }
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public  void displayGraph(List<PortFolioModel> portFolio){
        ArrayList NoOfEmp = new ArrayList();

        for(int count=0;count<portFolio.size();count++){


            if (!TextUtils.isEmpty(portFolio.get(count).getTotalAssetValue())){

                NoOfEmp.add(new PieEntry((float)Double.parseDouble(portFolio.get(count).getTotalAssetValue()), portFolio.get(count).getFundCode()));

            }

        }

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList code = new ArrayList();

        for(int x=0;x<portFolio.size();x++){
            code.add(portFolio.get(x).getFundCode());
        }

        PieData data = new PieData(dataSet);
        piechart.setData(data);

        piechart.setDrawHoleEnabled(true);
        piechart.setHoleColor(R.color.transparent);

        piechart.animateXY(5000, 5000);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getPortfolio(){

        UserDetailsParam req=new UserDetailsParam();
        req.setProfile(Constant.profile);
        req.setAppId(SharedPref.getApi_ID(getContext()));
        req.setParams(SharedPref.getUSERID(getContext()));
        req.setFunctionId(Constant.Am_Portfolio);
        new GlobalRepository(getContext()).getPortfolio(SharedPref.getApi_ID(getContext()), req, new OnApiResponse<List<PortFolioModel>>() {
            @Override
            public void onSuccess(List<PortFolioModel> data) {
                chartPortFolio=data;
                if(data!=null) {
                    if(data.size()>0) {
                        displayGraph(data);
                    }
                }
            }

            @Override
            public void onFailed(String message) {

            }
        });

    }

}
