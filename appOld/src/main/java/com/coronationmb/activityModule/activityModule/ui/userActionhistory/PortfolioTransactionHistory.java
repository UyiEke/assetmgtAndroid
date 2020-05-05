package com.coronationmb.activityModule.activityModule.ui.userActionhistory;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.SubscriptionHistoryModel;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.responseModel.PortfolioStatementHistoryModel;
import com.coronationmb.Model.responseModel.PortfolioTransactionModel;
import com.coronationmb.R;
import com.coronationmb.adapter.PortfolioStmtHistoryAdapter;
import com.coronationmb.adapter.PortfolioTransactionHistoryAdapter;
import com.coronationmb.adapter.RedemptionAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PortfolioTransactionHistory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PortfolioTransactionHistory#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PortfolioTransactionHistory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PortfolioStatementHistory.OnFragmentInteractionListener mListener;

    PortfolioTransactionHistoryAdapter adapter;
    LinearLayoutManager layoutManager;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.msg)
    TextView msg;

    static String actionType;

    List<PortfolioTransactionModel> portStmtHistoryList;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Context context;
    GlobalRepository repo;

    public PortfolioTransactionHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RedemptionHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static PortfolioTransactionHistory newInstance(String param1, String param2) {
        actionType=param2;
        PortfolioTransactionHistory fragment = new PortfolioTransactionHistory();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_user_action_history, container, false);
        ButterKnife.bind(this, root);
        context=getContext();
        initUI();
        return root;
    }

    private void initUI(){
        repo=new GlobalRepository(context);
        portStmtHistoryList=new ArrayList();
        layoutManager= new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter=new PortfolioTransactionHistoryAdapter(portStmtHistoryList,context);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        getportfolioTransactionHistory();  // 0-subscription, 1-redemption
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
        this.context=context;
        if (context instanceof PortfolioStatementHistory.OnFragmentInteractionListener) {
            mListener = (PortfolioStatementHistory.OnFragmentInteractionListener) context;
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

    private void getportfolioTransactionHistory(){

        // P_000050
        //  000940|CMMFUND

        UserDetailsParam req=new UserDetailsParam();
        req.setAppId(SharedPref.getApi_ID(context));
        req.setFunctionId(Constant.portfolioTransactionHistory);
        req.setProfile(Constant.profile);
        DateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy");
        req.setParams(SharedPref.getUSERID(context)+"|CMMFUND");
        //  9-31-2018|9-10-2019|000940
        repo.getPortfolioTransactionHistory(SharedPref.getApi_ID(context), req, new OnApiResponse<List<PortfolioTransactionModel>>() {
            @Override
            public void onSuccess(List<PortfolioTransactionModel> data) {
                if(data!=null){

                    if(data.size()>0){

                        progressBar.setVisibility(View.GONE);
                        msg.setVisibility(View.GONE);

                        portStmtHistoryList=data;

                        adapter=new PortfolioTransactionHistoryAdapter(portStmtHistoryList,context);
                        recycler.setAdapter(adapter);
                    }else {
                        progressBar.setVisibility(View.GONE);
                        msg.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailed(String message) {
                progressBar.setVisibility(View.GONE);
                adapter=new PortfolioTransactionHistoryAdapter(portStmtHistoryList,context);
                recycler.setAdapter(adapter);
            }
        });
    }
}
