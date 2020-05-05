package com.coronationmb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.SubscriptionHistoryModel;
import com.coronationmb.Model.responseModel.PortfolioStatementHistoryModel;
import com.coronationmb.R;

import java.util.List;

import static com.coronationmb.service.Utility.convertStringCase;
import static com.coronationmb.service.Utility.formatDate;

public class PortfolioStmtHistoryAdapter extends RecyclerView.Adapter<PortfolioStmtHistoryAdapter.MyViewHolder> {

    List<PortfolioStatementHistoryModel> list;
    Context context;

    public PortfolioStmtHistoryAdapter(List<PortfolioStatementHistoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PortfolioStmtHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.portfolio_stmt_history_row_item, parent,false);
        return new PortfolioStmtHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioStmtHistoryAdapter.MyViewHolder holder, int position) {
        holder.value_dateTextview.setText(formatDate(list.get(position).getEffectiveDate(),"MM/dd/yyyy H:mm:ss a","dd/MM/yyyy"));
        holder.descTextview.setText(convertStringCase(list.get(position).getDescription()));

        if(list.get(position).getAmount().contains("-")){
            holder.creditTextview.setText("0.00");
            holder.debitTextview.setText(list.get(position).getAmount());
        }else {
            holder.debitTextview.setText("0.00");
            holder.creditTextview.setText(list.get(position).getAmount());
        }

        holder.transTypeTextview.setText(list.get(position).getTranstype());

    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView value_dateTextview,descTextview,creditTextview,debitTextview,transTypeTextview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            value_dateTextview=itemView.findViewById(R.id.value_dateTextview);
            descTextview=itemView.findViewById(R.id.descTextview);
            creditTextview=itemView.findViewById(R.id.creditTextview);
            debitTextview=itemView.findViewById(R.id.debitTextview);
            transTypeTextview=itemView.findViewById(R.id.transTypeTextview);

        }
    }
}
