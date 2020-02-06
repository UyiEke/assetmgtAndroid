package com.coronationmb.activityModule.activityModule.ui.PaymentTransactionHistory;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.responseModel.TransactionHistoryModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.adapter.PaymentTransactionHistoryAdapter;
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


public class  PaymentTransactionHistoryFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    GlobalRepository repo;
    private PaymentTransactionHistoryViewModel paymentTransactionHistoryViewModel;
    private ProgressDialog progress;
    Context context;
    private LinearLayoutManager layoutManager;
    PaymentTransactionHistoryAdapter adapter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.status)
    TextView status;



    @BindView(R.id.startDate)
    EditText startDate;

    @BindView(R.id.endDate)
    EditText endDate;

    List<TransactionHistoryModel> list;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    SimpleDateFormat dateformat;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentTransactionHistoryViewModel = ViewModelProviders.of(this).get(PaymentTransactionHistoryViewModel.class);
        View root = inflater.inflate(R.layout.transaction_history_layout, container, false);

        ButterKnife.bind(this, root);
        context=getContext();

       // ((DashboardActivity)context).changeToolbarTitle("Payment History");
        initUI();
        return root;
    }

    private void initUI() {
        repo=new GlobalRepository(context);
        layoutManager= new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        if(list!=null){

            status.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            adapter=new PaymentTransactionHistoryAdapter(list,context);
            recycler.setAdapter(adapter);
        }else {
            list=new ArrayList();
            adapter=new PaymentTransactionHistoryAdapter(list,context);
            recycler.setAdapter(adapter);
            getTransactionHistory();
        }

    }

    private void getTransactionHistory(){

        repo.getTransactionHistory(SharedPref.getUSERID(context), Constant.profile, Constant.APPID, new OnApiResponse<List<TransactionHistoryModel>>() {
            @Override
            public void onSuccess(List<TransactionHistoryModel> data) {
                list=data;
                status.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                adapter=new PaymentTransactionHistoryAdapter(data,context);
                recycler.setLayoutManager(layoutManager);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailed(String message) {
                status.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
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


