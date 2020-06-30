package com.coronationmb.activityModule.activityModule.ui.calculator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coronationmb.R;
import com.coronationmb.service.GlobalRepository;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculatorBottomFragment extends BottomSheetDialogFragment {

    GlobalRepository repo;
    int calculatorIndex;


    @BindView(R.id.calculate)
    Button calculate;


    @BindView(R.id.cal_title)
    TextView cal_title;

    @BindView(R.id.result_layout)
    LinearLayout result_layout;

    @BindView(R.id.result_msg)
    TextView result_msg;

    @BindView(R.id.result_amt)
    TextView result_amt;

    // input layout

    @BindView(R.id.initial_lump)
    EditText initial_lump;

    @BindView(R.id.monhly_contributn)
    EditText monhly_contributn;

    @BindView(R.id.investment_period)
    EditText investment_period;

    @BindView(R.id.estimated_rate)
    EditText estimated_rate;

    @BindView(R.id.calcview_layout)
    LinearLayout calcview_layout;

    String charaterMsg;

    Context context;

    BottomSheetBehavior bottomSheetBehavior;

    public static CalculatorBottomFragment newInstance(int position,String msg) {
        CalculatorBottomFragment fragment = new CalculatorBottomFragment();
        Bundle args = new Bundle();
        args.putInt("calculatorIndex",position);
        args.putString("msg",msg);

         fragment.setArguments(args);
        return fragment;
    }

    public CalculatorBottomFragment() {
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(context, R.layout.calculator_bottom_layout, null);
        ButterKnife.bind(this, view);
        repo=new GlobalRepository(context);
        cal_title.setText(charaterMsg);
        context = getContext();

                dialog.setContentView(view);
    }

    @OnClick(R.id.calculate)
    public void calculate(){

        String initial_lumpVal=initial_lump.getText().toString().trim();
        String monhly_contributnVal=monhly_contributn.getText().toString().trim();
        String investment_periodVal=investment_period.getText().toString().trim();
        String estimated_rateVal=estimated_rate.getText().toString().trim();

        switch (calculatorIndex){

            case 1:
                String result=firstCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

                String msg="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";

                showResult(result,msg);

                break;

            case 2:

                String result2=secondCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

                String msg2="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";

                showResult(result2,msg2);
                break;

            case 3:

                String result3=thirdCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

                String msg3="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";

                showResult(result3,msg3);
                break;

            case 4:

                String result4=fourthCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

                String msg4="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";

                showResult(result4,msg4);
                break;

        }


    }

    public void showResult(String result, String msg){
        cal_title.setVisibility(View.GONE);
        calcview_layout.setVisibility(View.GONE);
        result_layout.setVisibility(View.VISIBLE);
        calculate.setText("Calculate New");

        result_amt.setText(result);
      //  result_text.setText(msg);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            calculatorIndex=bundle.getInt("calculatorIndex");
            charaterMsg=bundle.getString("msg");
        }
    }

    public String firstCalculation(String initial_lumpVal,String monhly_contributnVal,
                                   String investment_periodVal,String estimated_rateVal){

        // A=  P (1 + r/n)^(nt)

        String principal=initial_lumpVal;
        String rate=estimated_rateVal;
        int n=12;
        String time=investment_periodVal;

        Double val= Double.parseDouble(principal) * (1+ Double.parseDouble(rate)/12) * (12*Integer.parseInt(time));

        return val+"";
    }


    public String secondCalculation(String target,String monhly_contributnVal,
                                    String investment_periodVal,String estimated_rateVal){

        // P= (nA^1/nt)/(1+r)

        String targetVal=target;
        String rate=estimated_rateVal;
        int n=12;
        String time=investment_periodVal;

        Double val= (n * Double.parseDouble(targetVal)/(n* Integer.parseInt(time)) ) /(1+Integer.parseInt(rate));

        return val+"";
    }

    public String thirdCalculation(String target,String monhly_contributnVal,
                                   String investment_periodVal,String estimated_rateVal){

        // P= (nA^1/nt)/(1+r)

        String targetVal=target;
        String rate=estimated_rateVal;
        int n=12;
        String time=investment_periodVal;

        Double val= (n * Double.parseDouble(targetVal)/(n* Integer.parseInt(time)) ) /(1+Integer.parseInt(rate));

        return val+"";
    }

    public String fourthCalculation(String target,String monhly_contributnVal,
                                    String investment_periodVal,String estimated_rateVal){

        // P= (nA^1/nt)/(1+r)

        String targetVal=target;
        String rate=estimated_rateVal;
        int n=12;
        String time=investment_periodVal;

        Double val= (n * Double.parseDouble(targetVal)/(n* Integer.parseInt(time)) ) /(1+Integer.parseInt(rate));

        return val+"";
    }
}
