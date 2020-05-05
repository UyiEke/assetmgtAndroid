package com.coronationmb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.CalculatorActivity;
import com.coronationmb.activityModule.activityModule.ui.calculator.CalculatorBottomFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CalculatorAdapter  extends RecyclerView.Adapter<CalculatorAdapter.MyViewHolder>  {

    List<String> list;
    Context context;

    public CalculatorAdapter(List<String> list, Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public CalculatorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.calculator_row_item, parent,false);
        return new CalculatorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculatorAdapter.MyViewHolder holder, int position) {
        holder.display_text.setText(list.get(position));
        holder.cal_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  CalculatorBottomFragment.newInstance(position,list.get(position)).show(((FragmentActivity) context).getSupportFragmentManager(), "");
          Intent intent=new Intent(context, CalculatorActivity.class);
          intent.putExtra("calculatorIndex",position);
          intent.putExtra("msg",list.get(position));
          context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView display_text;
        RelativeLayout cal_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            display_text=itemView.findViewById(R.id.display_text);
            cal_layout=itemView.findViewById(R.id.cal_layout);
        }
    }
}
