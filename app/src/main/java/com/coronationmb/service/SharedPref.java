package com.coronationmb.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static String USERID="userID";
    public static String FULLNAME="Fullname";
    public static String EMAIL="Email";
    public static String PHONE="Phone";
    public static String PASSWORDCHANGED="isPASSWORDCHANGED";

    public static String isInfowareActive="isInfowareActive";
    public static String InfowareID="InfowareID";

    public static String APP_AGREEMENT="App_agreement";

    public static String PROFILE_IMAGE="profile_image";




    public SharedPref() {
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("AssetManagement",Context.MODE_PRIVATE);
    }

    public static String getUSERID(Context context) {
        return getSharedPreferences(context).getString(USERID,null);
    }

    public static void setUSERID(Context context,String UserID) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putString(USERID,UserID).apply();
    }


    public static String getProfileImage(Context context) {
        //return PROFILE_IMAGE;
        return getSharedPreferences(context).getString(PROFILE_IMAGE,null);
    }

    public static void setProfileImage(Context context,String profileImage) {
       // PROFILE_IMAGE = profileImage;
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putString(PROFILE_IMAGE,profileImage).apply();
    }



    public static int getAppAgreement(Context context) {
        return getSharedPreferences(context).getInt(APP_AGREEMENT,0);
    }

    public static void setAppAgreement(Context context,int agreement) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putInt(APP_AGREEMENT,agreement).apply();
    }



    public static String getFULLNAME(Context context) {
        return getSharedPreferences(context).getString(FULLNAME,null);
    }

    public static void setFULLNAME(Context context,String FULLNAME) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putString(SharedPref.FULLNAME,FULLNAME).apply();
    }

    public static String getEMAIL(Context context) {
        return getSharedPreferences(context).getString(EMAIL,null);

    }

    public static void setEMAIL(Context context,String EMAIL) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putString(SharedPref.EMAIL,EMAIL).apply();
    }

    public static String getPHONE(Context context) {
        return getSharedPreferences(context).getString(PHONE,null);
    }

    public static void setPHONE(Context context,String PHONE) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putString(SharedPref.PHONE,PHONE).apply();
    }

    public static boolean getIsInfowareActive(Context context) {

        return getSharedPreferences(context).getBoolean(isInfowareActive,false);
    }

    public static void setIsInfowareActive(Context context,boolean isInfowareActive) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putBoolean(SharedPref.isInfowareActive,isInfowareActive).apply();
    }

    public static boolean getPASSWORDCHANGED(Context context) {
        return getSharedPreferences(context).getBoolean(PASSWORDCHANGED,false);
    }

    public static void setPASSWORDCHANGED(Context context,boolean PASSWORDCHANGED) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putBoolean(SharedPref.PASSWORDCHANGED,PASSWORDCHANGED).apply();
    }


    public static void setInfowareID(Context context,String InfowareID) {
        SharedPreferences.Editor editor=getSharedPreferences(context).edit();
        editor.putString(SharedPref.InfowareID,InfowareID).apply();
    }

    public static String getInfowareID(Context context) {
        return getSharedPreferences(context).getString(InfowareID,null);
    }

}
