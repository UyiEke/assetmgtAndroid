package com.coronationmb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.responseModel.PortfolioStatementHistoryModel;
import com.coronationmb.Model.responseModel.PortfolioTransactionModel;
import com.coronationmb.R;

import java.util.List;

import static com.coronationmb.service.Utility.convertStringCase;
import static com.coronationmb.service.Utility.formatDate;

public class PortfolioTransactionHistoryAdapter extends RecyclerView.Adapter<PortfolioTransactionHistoryAdapter.MyViewHolder> {

    List<PortfolioTransactionModel> list;
    Context context;

    public PortfolioTransactionHistoryAdapter(List<PortfolioTransactionModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PortfolioTransactionHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.context).inflate(R.layout.portfolio_transaction_history_row_item, parent,false);
        return new PortfolioTransactionHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioTransactionHistoryAdapter.MyViewHolder holder, int position) {
        holder.valuation_dateTextview.setText(formatDate(list.get(position).getValueDate(),"MM/dd/yyyy H:mm:ss a","dd/MM/yyyy"));
        //holder.fund_nameTextview.setText(convertStringCase(list.get(position).getFundName()));
        holder.fund_nameTextview.setText(list.get(position).getFundName());
        holder.subscriptionTextview.setText(list.get(position).getSubscription());
        holder.marketVal.setText(list.get(position).getMktValue());
        holder.priceTextview.setText(list.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView valuation_dateTextview,fund_nameTextview,subscriptionTextview,marketVal,priceTextview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            valuation_dateTextview=itemView.findViewById(R.id.valuation_dateTextview);
            fund_nameTextview=itemView.findViewById(R.id.fund_nameTextview);
            subscriptionTextview=itemView.findViewById(R.id.subscriptionTextview);
            marketVal=itemView.findViewById(R.id.marketVal);
            priceTextview=itemView.findViewById(R.id.priceTextview);



        }
    }
}
