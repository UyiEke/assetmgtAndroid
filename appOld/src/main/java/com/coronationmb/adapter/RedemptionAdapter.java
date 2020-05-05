package com.coronationmb.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.SubscriptionHistoryModel;
import com.coronationmb.R;
import com.coronationmb.service.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.coronationmb.service.Utility.convertStringCase;
import static com.coronationmb.service.Utility.formatDate;

public class RedemptionAdapter extends RecyclerView.Adapter<RedemptionAdapter.MyViewHolder> {

    List<SubscriptionHistoryModel> list;
    Context context;

    List<SubscriptionHistoryModel>templist;

    int type;

    public RedemptionAdapter(List<SubscriptionHistoryModel> alist, Context context, int i) {
        this.list = alist;
        this.context = context;

        this.templist=new ArrayList<>();
        templist.addAll(alist);

        this.type=i;
    }

    @NonNull
    @Override
    public RedemptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        switch (this.type){

            case 1:
                 itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.redemption_row_item, parent,false);
                break;

            case 2:
                 itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_row_item, parent,false);
                 break;
        }

        return new RedemptionAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RedemptionAdapter.MyViewHolder holder, int position) {
        holder.date.setText(formatDate(list.get(position).getTxnDate(),"MM/dd/yyyy H:mm:ss a","EEEE, MMMM dd,yyyy"));
        holder.name.setText(convertStringCase(list.get(position).getFundName().trim()));
        holder.namexx.setText(list.get(position).getFundCode());

        holder.symbTextview.setText(list.get(position).getFundCode());
        holder.unitPriceTextview.setText(list.get(position).getUnitPrice());
        holder.quantityTextview.setText(list.get(position).getQuantity());

        holder.typeTextview.setText(list.get(position).getTxnType());

    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,date,symbTextview,unitPriceTextview,quantityTextview,typeTextview,namexx;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);

            namexx=itemView.findViewById(R.id.namexx);

            date=itemView.findViewById(R.id.date);
            symbTextview=itemView.findViewById(R.id.symbTextview);
            unitPriceTextview=itemView.findViewById(R.id.unitPriceTextview);
            quantityTextview=itemView.findViewById(R.id.quantityTextview);
            typeTextview=itemView.findViewById(R.id.typeTextview);

        }
    }


    public void filterDate(String startDate,String endDate)  {

        list.clear();
        if (TextUtils.isEmpty(startDate)|| TextUtils.isEmpty(endDate)) {
            list.addAll(templist);
        } else
        {
            for (SubscriptionHistoryModel postDetail : templist) {
                //19 Jul, 2018 00:00:01
                // 5/6/2019 12:23:41 PM
                Date startDate1=null;
                Date endDate1=null;
                Date listDate=null;

                SimpleDateFormat fmatIn =new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
                SimpleDateFormat fmatOut =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                try{
                    Date stDa=fmatIn.parse(startDate);
                    Date endDa=fmatIn.parse(endDate);

                    String startDateX=fmatOut.format(stDa);
                    String endDateX=fmatOut.format(endDa);

                    startDate1 = fmatOut.parse(startDateX);
                    endDate1 = fmatOut.parse(endDateX);

                    listDate = fmatOut.parse(postDetail.getTxnDate());
                }
                catch (Exception ex){
                    ex.printStackTrace();
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
