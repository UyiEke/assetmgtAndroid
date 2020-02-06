package com.coronationmb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.Product;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.Model.responseModel.ProfileModel;
import com.coronationmb.R;

import java.util.ArrayList;
import java.util.List;

import static com.coronationmb.service.Utility.formatStringToNaira;
import static com.coronationmb.service.Utility.round;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {
    List<PortFolioModel> list;
    Context context;
    List<Product> listOfProduct;

    public ProfileAdapter(List<PortFolioModel> list, Context context,List<Product> listOfProduct) {
        this.list = list;
        this.context = context;
        this.listOfProduct=listOfProduct;
    }


    @NonNull
    @Override
    public ProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_profile, parent,false);
        return new ProfileAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.MyViewHolder holder, int position) {
        if(list.get(position).getTotalAssetValue()!=null || !list.get(position).getTotalAssetValue().isEmpty()){

            holder.amt.setText(formatStringToNaira(round(Double.parseDouble(list.get(position).getTotalAssetValue()))));

        }

    holder.symbol.setText(list.get(position).getFundCode());
    holder.name.setText(list.get(position).getFundName());

        /*
    if(listOfProduct!=null){
        for (int i=0;i<listOfProduct.size();i++){

            String symbol=listOfProduct.get(i).getSymbol();
            for (int x=0;x<list.size();x++){
                if(symbol.equals(list.get(x).getSymbol())){
                    holder.name.setText(listOfProduct.get(i).getName());
                }
            }

        }
    }
    */

    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,symbol,amt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            symbol=(TextView)itemView.findViewById(R.id.symbol);
            amt=(TextView)itemView.findViewById(R.id.amt);
        }
    }
}
