package com.coronationmb.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;

import static android.content.Context.TELEPHONY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Constant {
    public  static final Spanned nairaSymbol= Html.fromHtml("&#8358");
    public static final String profile="am";
    public static final String APPID="cmgrp";
    public static String BaseUrl="http://197.253.42.227:8080/assetmanagement/";

    //Adhl@28

    // customer info (infoFNID)
    public static final String CSCS_Numbers = "1";
    public static final String Open_Orders = "2" ;
    public static String Executed_Orders = "3" ;
    public static String Cancelled_Orders = "4";
    public static String Rejected_Orders = "5";
    public static String Bio_Data = "6";
    public static String Portfolio = "7";
    public static String Portfolio_Transactions = "8" ;
    public static String Customer_Balances = "9";
    //customer info

    // Asset Management info (infoFNID)
    public static final String Am_Portfolio="P_00003";
    public static final String redeem="P_00035";
    public static final String product="P_00002";
    public static final String subscriptionAndRedemptionHistory="P_00072";


    public static String portfolioStmtHistory="P_00080";
    public static String portfolioTransactionHistory="P_00005";
}
