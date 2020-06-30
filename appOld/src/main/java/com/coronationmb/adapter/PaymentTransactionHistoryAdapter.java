package com.coronationmb.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coronationmb.Model.SubscriptionHistoryModel;
import com.coronationmb.Model.responseModel.TransactionHistoryModel;
import com.coronationmb.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.coronationmb.service.Utility.formatDate;

public class PaymentTransactionHistoryAdapter extends RecyclerView.Adapter<PaymentTransactionHistoryAdapter.MyViewHolder> {
    List<TransactionHistoryModel> list;
    Context context;

    List<TransactionHistoryModel>templist;

    public PaymentTransactionHistoryAdapter(List<TransactionHistoryModel> alist, Context context) {
        this.list = alist;
        this.context = context;
        this.templist=new ArrayList<>();
        this.templist.addAll(alist);
    }


    @NonNull
    @Override
    public PaymentTransactionHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.context).inflate(R.layout.transaction_history_row_item, parent,false);
        return new PaymentTransactionHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentTransactionHistoryAdapter.MyViewHolder holder, int position) {
        holder.approvedAmtTexview.setText(list.get(position).getApprovedAmt());
        holder.transRefTextview.setText(list.get(position).getTransactionRef());
        holder.transAmtTexview.setText(list.get(position).getAmount());
        holder.approvedAmtTexview.setText(list.get(position).getApprovedAmt());
      //  holder.date.setText(list.get(position).getDate());

        String d=list.get(position).getDate();

        String f=formatDate(list.get(position).getDate(),"yyyy-MM-dd","EEEE, MMMM dd, yyyy");

        holder.date.setText(f);

        holder.statusTexview.setText(list.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transRefTextview,transAmtTexview,approvedAmtTexview,statusTexview,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            transRefTextview=itemView.findViewById(R.id.transRefTextview);
            transAmtTexview=itemView.findViewById(R.id.transAmtTexview);
            approvedAmtTexview=itemView.findViewById(R.id.approvedAmtTexview);
            statusTexview=itemView.findViewById(R.id.statusTexview);

        }
    }

/*
    public void filterDatess(String startDate,String endDate) throws ParseException {

        list.clear();
        if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
            list.addAll(templist);
        } else
        {
            for (TransactionHistoryModel postDetail : templist) {

                Date startDate1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
                Date endDate1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
                Date listDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postDetail.getDate());
                Calendar start = Calendar.getInstance();
                start.setTime(startDate1);
                Calendar end = Calendar.getInstance();
                end.setTime(endDate1);

                if (listDate.after(startDate1) && listDate.before(endDate1)) {
                    list.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }

    */

    public void filterDate(String startDate,String endDate)  {

        list.clear();
        if (TextUtils.isEmpty(startDate)|| TextUtils.isEmpty(endDate)) {
            list.addAll(templist);
        } else
        {
            for (TransactionHistoryModel postDetail : templist) {
                //19 Jul, 2018 00:00:01
                // 5/6/2019 12:23:41 PM
                Date startDate1=null;
                Date endDate1=null;
                Date listDate=null;

                // 2019-11-28T10:28:35.73

                SimpleDateFormat fmatIn =new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
               // SimpleDateFormat fmatOut =new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSS");

                SimpleDateFormat fmatOut =new SimpleDateFormat("yyyy-MM-dd");

                try{
                    Date stDa=fmatIn.parse(startDate);
                    Date endDa=fmatIn.parse(endDate);

                    String startDateX=fmatOut.format(stDa);
                    String endDateX=fmatOut.format(endDa);

                    startDate1 = fmatOut.parse(startDateX);
                    endDate1 = fmatOut.parse(endDateX);

                    listDate = fmatOut.parse(postDetail.getDate());
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    return;
                }

                //   Date startDate1=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(startDate);
                //  Date endDate1=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(endDate);
                // Date listDate=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(postDetail.getTxnDate());

                Calendar start = Calendar.getInstance();
                start.setTime(startDate1);
                Calendar end = Calendar.getInstance();
                end.setTime(endDate1);

                if (listDate.after(startDate1) && listDate.before(endDate1)) {
                    list.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }



}
