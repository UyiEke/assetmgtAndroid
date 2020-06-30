package com.coronationmb.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.R;

import java.util.ArrayList;
import java.util.List;

import static com.coronationmb.service.Utility.round;

public class MainDashboardAdapter extends RecyclerView.Adapter<MainDashboardAdapter.MyViewHolder> {
    List<AssetProduct> list;
    Context context;

    public MainDashboardAdapter(List<AssetProduct> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MainDashboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row_item, parent,false);

        return new MainDashboardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainDashboardAdapter.MyViewHolder holder, int position) {

            AssetProduct prod=(AssetProduct)list.get(position);
            holder.product_name.setText(prod.getFundName());
            if(!TextUtils.isEmpty(prod.getBidPrice())){
                holder.bid_price.setText(round(Double.parseDouble(prod.getBidPrice()))+"");
            }
            if(!TextUtils.isEmpty(prod.getOfferPrice())) {
                holder.offer_price.setText(round(Double.parseDouble(prod.getOfferPrice())) + "");
            }

    }




    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,bid_price,offer_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name=(TextView)itemView.findViewById(R.id.product_name);
            bid_price=(TextView)itemView.findViewById(R.id.bid_price);
            offer_price=(TextView)itemView.findViewById(R.id.offer_price);
        }
    }
}
