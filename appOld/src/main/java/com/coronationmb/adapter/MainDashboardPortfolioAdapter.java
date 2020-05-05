package com.coronationmb.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;

import java.util.ArrayList;
import java.util.List;

import static com.coronationmb.service.Utility.formatStringToNaira;
import static com.coronationmb.service.Utility.round;

public class MainDashboardPortfolioAdapter extends RecyclerView.Adapter<MainDashboardPortfolioAdapter.MyViewHolder> {
    List<PortFolioModel> list;
    Context context;

    public MainDashboardPortfolioAdapter(List<PortFolioModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MainDashboardPortfolioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.portfolio_row_item, parent,false);

        return new MainDashboardPortfolioAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainDashboardPortfolioAdapter.MyViewHolder holder, int position) {

            PortFolioModel prod=(PortFolioModel)list.get(position);
            holder.product_name_port.setText(prod.getFundName());
            holder.code.setText(prod.getFundCode());

            if(!TextUtils.isEmpty(prod.getTotalAssetValue())) {
                holder.portVal.setText(formatStringToNaira(round(Double.parseDouble(prod.getTotalAssetValue()))));
            }
    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,bid_price,offer_price;
        TextView product_name_port,code,portVal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

                    product_name_port=(TextView)itemView.findViewById(R.id.product_name_port);
                    code=(TextView)itemView.findViewById(R.id.code);
                    portVal=(TextView)itemView.findViewById(R.id.portVal);
            }

        }
    }

