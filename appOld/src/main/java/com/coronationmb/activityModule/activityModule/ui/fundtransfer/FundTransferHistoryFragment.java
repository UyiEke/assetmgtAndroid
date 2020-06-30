package com.coronationmb.activityModule.activityModule.ui.fundtransfer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.SubscriptionHistoryModel;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.ui.userActionhistory.RedemptionHistory;
import com.coronationmb.adapter.RedemptionAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FundTransferHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FundTransferHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FundTransferHistoryFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RedemptionHistory.OnFragmentInteractionListener mListener;

    RedemptionAdapter adapter;
    LinearLayoutManager layoutManager;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.msg)
    TextView msg;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    GlobalRepository repo;


    public FundTransferHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FundTransferHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FundTransferHistoryFragment newInstance(String param1, String param2) {
        FundTransferHistoryFragment fragment = new FundTransferHistoryFragment();
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
        View root= inflater.inflate(R.layout.fragment_fund_transfer_history, container, false);
        ButterKnife.bind(this, root);
        initUI();
        return root;
    }

    private void initUI(){
        repo=new GlobalRepository(context);
        List<SubscriptionHistoryModel> list=new ArrayList();
        layoutManager= new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getHistory("1");
        adapter=new RedemptionAdapter(list,context,1);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
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
        if (context instanceof RedemptionHistory.OnFragmentInteractionListener) {
            mListener = (RedemptionHistory.OnFragmentInteractionListener) context;
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

    private void getHistory(String txnType){

        UserDetailsParam req=new UserDetailsParam();
        req.setAppId(SharedPref.getApi_ID(context));
        req.setFunctionId("P_00072");
        req.setProfile(Constant.profile);
        req.setParams(SharedPref.getUSERID(context)+"|"+txnType);

        repo.subscriptionList(SharedPref.getApi_ID(context), req, new OnApiResponse<List<SubscriptionHistoryModel>>() {
            @Override
            public void onSuccess(List<SubscriptionHistoryModel> data) {
                progressBar.setVisibility(View.GONE);
                msg.setVisibility(View.GONE);
                adapter=new RedemptionAdapter(data,context,1);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailed(String message) {
                progressBar.setVisibility(View.GONE);
                msg.setVisibility(View.VISIBLE);
            }
        });
    }

}
