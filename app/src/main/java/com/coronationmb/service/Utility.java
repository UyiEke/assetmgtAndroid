package com.coronationmb.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.coronationmb.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import static com.coronationmb.service.Constant.nairaSymbol;


public class Utility {

  //  public static String BaseUrl="http://132.10.200.30:8091/";

    public static void alertOnly(final Context context, final String msg, String title) {

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Asset Management")
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                      //  exitApp();
                        dialog.dismiss();
                    }
                })
                /*
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                */
                .setIcon(R.drawable.lionhead_icon)
                .show();



    }

    public static boolean validateEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static  boolean validatePhoneNumber(String phoneNumbers){
        return (phoneNumbers.length()>11 || !(phoneNumbers.substring(0, 1).equals("0")) || phoneNumbers.length()<11);
    }

    public static  boolean validateAccountNumber(String phoneNumbers){
        return (phoneNumbers.length()>10 || phoneNumbers.length()<10);
    }

    public static boolean checkDate(String dobs){

        DateFormat format = new SimpleDateFormat("dd MMM, yyyy");
        Date dateEntered=null;
        Date date=null;
        try {
            dateEntered = format.parse(dobs);

            DateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
            date = new Date();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateEntered.after(date);
    }


    public static boolean compare2Date(String startDate,String endDate){

        DateFormat format = new SimpleDateFormat("dd MMM, yyyy");
        Date start_dateEntered=null;
        Date end_dateEntered=null;
        try {
            start_dateEntered = format.parse(startDate);

            DateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
            end_dateEntered = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return end_dateEntered.after(start_dateEntered);
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String formatStringToNaira(double number) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("NGN"));
        String formatedNumber = format.format(number);
        formatedNumber = formatedNumber.replace("NGN", nairaSymbol);
        return formatedNumber;

    }

    public static Double round(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean isInternetAvailable(Context context){

        boolean isAvailable=false;

        ConnectivityManager ConnectionManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true )
        {
            isAvailable=true;
        }
        else
        {
            isAvailable=false;
        }
        return isAvailable;
    }

    public static String formatDate(String dateString, String presentDateFormat, String desiredDateFormat) {
        //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat fmt = new SimpleDateFormat(presentDateFormat);
        Date date = null;
        try {
            date = fmt.parse(dateString);
        } catch (ParseException ex) {

        }
        //SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM,yyyy");
        SimpleDateFormat fmtOut = new SimpleDateFormat(desiredDateFormat);

        // check for NullPointerException here!

        return (fmtOut.format(date));
    }




    public static Date getDateformat(String dateString, String presentDateFormat, String desiredDateFormat) {
        //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat fmt = new SimpleDateFormat(desiredDateFormat);
        Date date = null;
        try {
            date = fmt.parse(dateString);
        } catch (ParseException ex) {

        }
        //SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM,yyyy");
       // SimpleDateFormat fmtOut = new SimpleDateFormat(desiredDateFormat);

        // check for NullPointerException here!

        return date;
    }



    public static String convertStringCase(String nameValue) {

        if (nameValue != null) {
            String name=nameValue.trim();
            String formattedString = "";
            String[] letters = name.split(" ");
            if (letters.length > 0) {
                for (String n : letters) {
                    if (n != "" && !n.isEmpty()) {
                        String x=n;
                        formattedString = formattedString + " " + n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();
                    }
                }
            } else {
                formattedString = formattedString + name.substring(0, 1).toUpperCase() + name.substring(1);
            }

            return formattedString;
        }
        else {
            return null;
        }
    }

    /*
     * Gets the file path of the given Uri.
     */

    /*
     * Gets the file path of the given Uri.
     */

    @SuppressLint("NewApi")
    public static String getPath(Uri uri, Context context) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
