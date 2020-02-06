package com.coronationmb.activityModule.activityModule.ui.userActionhistory;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.SubscriptionHistoryModel;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.R;
import com.coronationmb.adapter.RedemptionAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RedemptionHistory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RedemptionHistory#newInstance} factory method to
 * create an instance of this fragment.
 */

public class RedemptionHistory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RedemptionAdapter adapter;
    LinearLayoutManager layoutManager;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.msg)
    TextView msg;

    @BindView(R.id.startDate)
    EditText startDate;

    @BindView(R.id.endDate)
    EditText endDate;




    static String actionType;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Context context;
    GlobalRepository repo;

    List<SubscriptionHistoryModel> list;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    SimpleDateFormat dateformat;



    public RedemptionHistory() {
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
    public static RedemptionHistory newInstance(String param1, String param2) {
        actionType=param2;
        RedemptionHistory fragment = new RedemptionHistory();

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
        layoutManager= new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
      if(list!=null){

          msg.setVisibility(View.GONE);
          progressBar.setVisibility(View.GONE);
          adapter=new RedemptionAdapter(list,context,1);
          recycler.setAdapter(adapter);
      }else {
          list=new ArrayList();
          adapter=new RedemptionAdapter(list,context,1);
          recycler.setAdapter(adapter);
          getHistory("1");  // 0-subscription, 1-redemption
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
        this.context=context;
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

    private void getHistory(String txnType){

        UserDetailsParam req=new UserDetailsParam();
        req.setAppId(Constant.APPID);
        req.setFunctionId(Constant.subscriptionAndRedemptionHistory);
        req.setProfile(Constant.profile);
        req.setParams(SharedPref.getUSERID(context)+"|"+"1");

        repo.subscriptionList(Constant.APPID, req, new OnApiResponse<List<SubscriptionHistoryModel>>() {
            @Override
            public void onSuccess(List<SubscriptionHistoryModel> data) {
                list=data;
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


    @OnClick(R.id.lay2)
    public void searchRecord() {
        String start_Date=startDate.getText().toString().trim();
        String end_Date=endDate.getText().toString().trim();

        if(TextUtils.isEmpty(start_Date) && TextUtils.isEmpty(end_Date)){

            Toast.makeText(getContext(),"InValid Date Entries",Toast.LENGTH_LONG).show();
            return;
        }

        if(!Utility.compare2Date(start_Date, end_Date)){

            Toast.makeText(getContext(),"InValid Date Entries",Toast.LENGTH_LONG).show();

            return;
        }

        else {

                adapter.filterDate(start_Date+" "+"00:00:01",end_Date+" "+"23:59:59");

        }
    }

    public void datePickerAction(final EditText edit_Text){
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //   String outPutformat = "yyyy-MMM-dd"; //In which you need put here
                String outPutformat = "dd MMM, yyyy";
                dateformat = new SimpleDateFormat(outPutformat, Locale.US);
                edit_Text.setText(dateformat.format(myCalendar.getTime()));
            }
        };
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        Utility.hideKeyboardFrom(context,edit_Text);

    }

    @OnClick({R.id.startDate,R.id.endDate})
    public void EditTextAction(View view){
        int id=view.getId();

        switch (id){

            case R.id.startDate: datePickerAction(startDate);
                break;

            case R.id.endDate: datePickerAction(endDate);
                break;

        }


    }


}
