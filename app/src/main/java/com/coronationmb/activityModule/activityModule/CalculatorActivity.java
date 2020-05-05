package com.coronationmb.activityModule.activityModule;

import android.content.Context;
import android.os.Bundle;

import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coronationmb.R;

public class CalculatorActivity extends AppCompatActivity {


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
    Context context;
    String charaterMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        context=CalculatorActivity.this;
        ButterKnife.bind(this);
        repo=new GlobalRepository(context);

        calculatorIndex=getIntent().getIntExtra("calculatorIndex",0);


        if(calculatorIndex == 3){
            monhly_contributn.setVisibility(View.VISIBLE);
            monhly_contributn.setHint("Initial lump sum");
        }else {
            monhly_contributn.setVisibility(View.GONE);
        }

        if(calculatorIndex != 0){
            initial_lump.setHint("Target Amount");
        }

        charaterMsg=getIntent().getStringExtra("msg");
        initUI();
    }

    public void initUI(){
        cal_title.setText(charaterMsg);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public String firstCalculation(String initial_lumpVal,String monhly_contributnVal,
                                   String investment_periodVal,String estimated_rateVal){

        // A=  P (1 + r/n)^(nt) i.e A-future Value,P-principal,r-interest rate, t- time period,
        // n- number of times interest applied per time period (most time is always -1)

        String principal,rate,time;

        if(TextUtils.isEmpty(initial_lumpVal)){
             principal="0";
        }else {
             principal=initial_lumpVal;
        }

        if(TextUtils.isEmpty(estimated_rateVal)){
             rate="0";
        }else {
             rate=estimated_rateVal;
        }

        if(TextUtils.isEmpty(investment_periodVal)){
             time="0";
        }else {
             time=investment_periodVal;
        }

        int n=1;

        double h=Math.pow((1+ (Double.parseDouble(rate)/100)), (Integer.parseInt(time)));

        Double val= Double.parseDouble(principal) * Math.pow((1+ (Double.parseDouble(rate)/100)), (Integer.parseInt(time)));

        return val+"";
    }


    public String secondCalculation(String target,String monthly_contributnVal,
                                    String investment_periodVal,String estimated_rateVal){

        // P= A/(1+r/n)^nt i.e A-future Value,P-principal,r-interest rate, t- time period


        String A,r,time;

        if(TextUtils.isEmpty(target)){
            A="0";
        }else {
            A=target;
        }

        if(TextUtils.isEmpty(estimated_rateVal)){
            r="0";
        }else {
            r=estimated_rateVal;
        }


        if(TextUtils.isEmpty(investment_periodVal)){
            time="0";
        }else {
            time=investment_periodVal;
        }

        Double val= Double.parseDouble(A)/calMath(r,time);

        return val+"";
    }

    private double calMath(String r, String t){

        Double val= Math.pow((1+(Double.parseDouble(r)/100)),Double.parseDouble(t));
        return val;
    }

    public String thirdCalculation(String target,String monhly_contributnVal,
                                   String investment_periodVal,String estimated_rateVal){
//        P= Ar/(n[(1+r/n)^t -1])

        String A,rate,time;

        if(TextUtils.isEmpty(target)){
            A="0";
        }else {
            A=target;
        }

        if(TextUtils.isEmpty(estimated_rateVal)){
            rate="0";
        }else {
            rate=estimated_rateVal;
        }


        if(TextUtils.isEmpty(investment_periodVal)){
            time="0";
        }else {
            time=investment_periodVal;
        }

        Double val= (Double.parseDouble(A)* (Double.parseDouble(rate)/100))/(calMath(estimated_rateVal,time)-1);

        return val+"";
    }

    public String fourthCalculation(String target,String monthly_contributn,
                                    String investment_periodVal,String estimated_rateVal){

    //   t= LN(A/p)/LN(1+r/n)

        String targetVal,rate,time,monthly_contributnVal;

        if(TextUtils.isEmpty(target)){
            targetVal="0";
        }else {
            targetVal=target;
        }

        if(TextUtils.isEmpty(estimated_rateVal)){
            rate="0";
        }else {
            rate=estimated_rateVal;
        }


        if(TextUtils.isEmpty(investment_periodVal)){
            time="0";
        }else {
            time=investment_periodVal;
        }


        if(TextUtils.isEmpty(monthly_contributn)){
            monthly_contributnVal="0";
        }else {
            monthly_contributnVal=monthly_contributn;
        }

/*
        String targetVal=target;
        String rate=estimated_rateVal;
        int n=1;
        String time=investment_periodVal;
        */

     //   Double val= (n * Double.parseDouble(targetVal)/(n* Integer.parseInt(time)) ) /(1+Integer.parseInt(rate));

        Double val= (  (Math.log(Double.parseDouble(targetVal)/Double.parseDouble(monthly_contributnVal)))/ (Math.log(1+(Double.parseDouble(rate)/100))) );

        return val+"";
    }

    @OnClick(R.id.calculate)
    public void calculate(){

        if (calculate.getText().equals("Calculate New")){
            calculateNew();
            return;
        }

        String initial_lumpVal=initial_lump.getText().toString().trim();
        String monhly_contributnVal=monhly_contributn.getText().toString().trim();
        String investment_periodVal=investment_period.getText().toString().trim();
        String estimated_rateVal=estimated_rate.getText().toString().trim();

        switch (calculatorIndex){

            case 0:
                String result=firstCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

               // String msg="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";
                String msg= "Future value of investment";
                showResult(result,msg);

                break;

            case 1:

                String result2=secondCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

              //  String msg2="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";
                String msg2= "initial lump sum required";
                showResult(result2,msg2);
                break;

            case 2:

                String result3=thirdCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

            //    String msg3="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";

                String msg3="Amount for monthly investment";
                showResult(result3,msg3);
                break;

            case 3:

                String result4=fourthCalculation(initial_lumpVal,monhly_contributnVal,investment_periodVal,estimated_rateVal);

            //    String msg4="For an initial lump sum of "+initial_lumpVal+", a monthly contribution of "+monhly_contributnVal+" invested for "+investment_periodVal+" at rate of "+estimated_rateVal+" %";
                String msg4="Number of years required";
                showResult(result4,msg4);
                break;

                default:
                    calculateNew();

        }

    }

    public void showResult(String result, String msg){
        cal_title.setVisibility(View.GONE);
        calcview_layout.setVisibility(View.GONE);
        result_layout.setVisibility(View.VISIBLE);
        calculate.setText("Calculate New");

        if(!TextUtils.isEmpty(result)) {

            if(msg.equals("Number of years required")){

                result_amt.setText(Utility.round(Double.parseDouble(result)) + "");

            }else {
                result_amt.setText(Utility.formatStringToNaira(Utility.round(Double.parseDouble(result))) + "");
            }
            result_msg.setText(msg);
        }
    }

    public void calculateNew(){

        cal_title.setVisibility(View.VISIBLE);
        calcview_layout.setVisibility(View.VISIBLE);
        result_layout.setVisibility(View.GONE);
        calculate.setText("Calculate");

        initial_lump.setText(null);
        monhly_contributn.setText(null);
        investment_period.setText(null);
        estimated_rate.setText(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  //  411194

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
