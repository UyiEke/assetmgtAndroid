package com.coronationmb.activityModule.activityModule.ui.dashbrd;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.ui.changePassword.ChangePasswdViewModel;
import com.coronationmb.adapter.MainDashboardPortfolioAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;

import com.coronationmb.adapter.MainDashboardAdapter;
import com.coronationmb.service.Utility;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainDashboardViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainDashboardViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDashboardViewFragment extends Fragment {


    @BindView(R.id.dayTextview)
    TextView dayTextview;

    @BindView(R.id.greetTextview)
    TextView greetTextview;

    @BindView(R.id.product_recycler)
    RecyclerView product_recycler;

    @BindView(R.id.profile_recycler)
    RecyclerView profile_recycler;
    GlobalRepository repo;
   // List<AssetProduct> product;

    Context context;

    public List<PortFolioModel> portFolio;

    @BindView(R.id.product_progressBar)
    ProgressBar product_progressBar;

    @BindView(R.id.portfolio_progressBar)
    ProgressBar portfolio_progressBar;

    @BindView(R.id.portfolio_status)
    TextView portfolio_status;

    @BindView(R.id.product_status)
    TextView product_status;

    LinearLayoutManager productLayoutManager;
    LinearLayoutManager portfolioLayoutManager;
    MainDashboardAdapter productAdapter;
    MainDashboardPortfolioAdapter portfolioAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
   // private List<PortFolioModel> mParam1;
    private List<AssetProduct> product;

    private OnFragmentInteractionListener mListener;

    DashboardViewModel dashboardViewModel;


    public MainDashboardViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainDashboardViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDashboardViewFragment newInstance(String param1, String param2) {
        MainDashboardViewFragment fragment = new MainDashboardViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(List<PortFolioModel> portFolioData, List<AssetProduct> assetProducts) {

        MainDashboardViewFragment fragment = new MainDashboardViewFragment();
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
            portFolio = (List<PortFolioModel>) getArguments().getSerializable(ARG_PARAM1);
            product = (List<AssetProduct>) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_main_dashboard_view, container, false);
        ButterKnife.bind(this,root);
        initUI();
        return root;
    }

    public void initUI(){
       // greet();
        repo=new GlobalRepository(context);
        ((DashboardActivity)context).changeToolbarTitle("DASHBOARD");
        productLayoutManager=new LinearLayoutManager(context);
        productLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        portfolioLayoutManager=new LinearLayoutManager(context);
        portfolioLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        profile_recycler.setLayoutManager(portfolioLayoutManager);
        product_recycler.setLayoutManager(productLayoutManager);

        if(product!=null){
            product_progressBar.setVisibility(View.GONE);
            productAdapter= new MainDashboardAdapter(product,context);
            product_recycler.setAdapter(productAdapter);
        }else {
            getProduct();
        }

        if(portFolio!=null){
            showBalance(portFolio);
            portfolio_progressBar.setVisibility(View.GONE);



            List<PortFolioModel> list =new ArrayList<>();

            for(int count = 0; count<portFolio.size(); count++){

                if(!portFolio.get(count).getFundType().equals("Unused Cash")){
                    list.add(portFolio.get(count));
                }

            }


            portfolioAdapter= new MainDashboardPortfolioAdapter(list,context);
            profile_recycler.setAdapter(portfolioAdapter);
        }else {
            if(!SharedPref.getUSERID(context).contains("@")) {
                getPortfolio();
            }else {
                portfolio_status.setVisibility(View.VISIBLE);
                portfolio_progressBar.setVisibility(View.GONE);
            }
        }

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

        this.context = context;

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


    private void getProduct(){

        UserDetailsParam req=new UserDetailsParam();
        req.setProfile(Constant.profile);
        req.setAppId(SharedPref.getApi_ID(context));
        req.setFunctionId(Constant.product);


        Gson gson=new Gson();
        String objString = gson.toJson(req);

        Log.d("prouct",objString);

        repo.getProductAss(SharedPref.getApi_ID(context), req, new OnApiResponse<List<AssetProduct>>() {
            @Override
            public void onSuccess(List<AssetProduct> data) {
                product_progressBar.setVisibility(View.GONE);
                product_progressBar.setVisibility(View.GONE);
                product=data;
                productAdapter= new MainDashboardAdapter(data,context);
                product_recycler.setAdapter(productAdapter);
            }

            @Override
            public void onFailed(String message) {
                product_progressBar.setVisibility(View.GONE);
                productAdapter= new MainDashboardAdapter(new ArrayList<>(),context);
                product_recycler.setAdapter(productAdapter);
            }
        });
    }

    private void getPortfolio(){

        UserDetailsParam req=new UserDetailsParam();
        req.setProfile(Constant.profile);
        req.setAppId(SharedPref.getApi_ID(context));
        req.setParams(SharedPref.getUSERID(context));
        req.setFunctionId(Constant.Am_Portfolio);


        Gson gson=new Gson();
        String objString = gson.toJson(req);

        Log.d("portfoio",objString);


        repo.getPortfolio(SharedPref.getApi_ID(context), req, new OnApiResponse<List<PortFolioModel>>() {
            @Override
            public void onSuccess(List<PortFolioModel> data) {
                portFolio=data;
                showBalance(portFolio);
                portfolio_progressBar.setVisibility(View.GONE);
                portfolio_status.setVisibility(View.GONE);


                List<PortFolioModel> list =new ArrayList<>();

                for(int count = 0; count<data.size(); count++){

                    if(!data.get(count).getFundType().equals("Unused Cash")){
                        list.add(data.get(count));
                    }

                }

                portfolioAdapter= new MainDashboardPortfolioAdapter(list,context);
                profile_recycler.setAdapter(portfolioAdapter);

            }

            @Override
            public void onFailed(String message) {
                portfolio_status.setVisibility(View.VISIBLE);
                portfolio_progressBar.setVisibility(View.GONE);
            }
        });

    }



    public void showBalance(List<PortFolioModel> data){

        for(PortFolioModel port:data){

            if(port.getFundType().equals("Unused Cash")){
                dayTextview.setText("Welcome "+SharedPref.getFULLNAME(context));

                if(!TextUtils.isEmpty(port.getTotalAssetValue())) {
                    greetTextview.setText("Your Cash Balance: " + Utility.formatStringToNaira(Utility.round(Double.parseDouble(port.getTotalAssetValue()))));
                }else {

                    greetTextview.setText("Your Cash Balance: " + Utility.formatStringToNaira(0.00));

                }

                }

        }

    }

}
