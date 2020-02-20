package com.coronationmb.service;

import android.content.Context;
import android.util.Log;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.Product;
import com.coronationmb.Model.SubscriptionHistoryModel;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.requestModel.ChangePasswModel;
import com.coronationmb.Model.requestModel.CreateAccount;
import com.coronationmb.Model.requestModel.CustomerInfoModel;
import com.coronationmb.Model.requestModel.FundAcct;
import com.coronationmb.Model.requestModel.LoginModel;
import com.coronationmb.Model.requestModel.SubscribeModel;
import com.coronationmb.Model.requestModel.SupportModel;
import com.coronationmb.Model.requestModel.TemporarySignUpModel;
import com.coronationmb.Model.requestModel.ViewTradeHistory;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.CreateAccountParameter;
import com.coronationmb.Model.responseModel.ImageDetails;
import com.coronationmb.Model.responseModel.MinimumInvestmentObject;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.Model.responseModel.PortfolioStatementHistoryModel;
import com.coronationmb.Model.responseModel.PortfolioTransactionModel;
import com.coronationmb.Model.responseModel.ProfileModel;
import com.coronationmb.Model.responseModel.SubscriptionResponseModel;
import com.coronationmb.Model.responseModel.TransactionHistoryModel;
import com.coronationmb.retrofitModule.RetrofitProxyService;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.coronationmb.retrofitModule.RetrofitClient.getRetrofitInstance;

public class GlobalRepository {

    Context context;
    RetrofitProxyService retrofitProxyService;

    public GlobalRepository(Context context) {
        retrofitProxyService = getRetrofitInstance();
        this.context=context;
    }

    public void login(String appID, LoginModel model, final OnApiResponse<WebResponse<JsonObject>> callback){

        retrofitProxyService.login(appID,model).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {

                if(response.isSuccessful() && response.body().getData()!=null){

                    if(response.body().getStatus().equals("1") ){
                        callback.onFailed(response.body().getMessage());
                    }
                    else if(response.body().getStatus().equals("01")){

                        try {
                            JSONObject json = new JSONObject(response.body().getData().toString());

                            String tempUserId=json.optString("userid")==null?"":json.optString("userid");
                            String firstname=json.optString("firstname")==null? "":json.optString("firstname");

                            SharedPref.setFULLNAME(context,firstname);

                            SharedPref.setUSERID(context,model.getCustUserID());

                            int acct_status=json.optInt("acct_status");
                            if(acct_status==1){
                              SharedPref.setIsInfowareActive(context,true);
                            }else {
                                SharedPref.setIsInfowareActive(context,false);
                            }

                            String infoware_id=json.optString("infoware_id");
                            SharedPref.setInfowareID(context,infoware_id);

                            callback.onSuccess(response.body());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(response.body().getStatus().equals("00")) {

                        try {

                            JSONObject json = new JSONObject(response.body().getData().toString());
                            JSONObject responseBody=json.optJSONObject("responseBody");
                            JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                            if(datatableObject!=null){
                                JSONArray rows= datatableObject.optJSONArray("Rows");
                                JSONObject rowsObject= rows.optJSONObject(0);

                                String userId=rowsObject.getString("0");
                                SharedPref.setUSERID(context,userId);

                                String Fullname=rowsObject.getString("1");
                                SharedPref.setFULLNAME(context,Fullname);

                                String email=rowsObject.getString("4");
                                SharedPref.setEMAIL(context,email);

                                String phoneNumber=rowsObject.getString("5");
                                SharedPref.setPHONE(context,phoneNumber);

                                String IsPasswordChangeRequired=rowsObject.getString("6");

                                if(IsPasswordChangeRequired.equals("No")){ // NO- means no need for user to change password
                                    SharedPref.setPASSWORDCHANGED(context,true);
                                }else {
                                    SharedPref.setPASSWORDCHANGED(context,false);
                                }

                                callback.onSuccess(response.body());

                            }else {
                                // callback.onFailed(responseBody.getString("StatusMessage"));
                                callback.onFailed("Access Denied");
                            }

                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                            callback.onFailed("failed to connect!, please try again");
                        }

                    }
                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());

                        String msg=jObjError.getString("message");

                        callback.onFailed("failed to connect!, please try again");

                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Something went wrong, please try again");
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                if(t.toString().contains("java.net.ConnectException")){
                    callback.onFailed("Failed to connect, please try again");
                }
                else {
                    callback.onFailed("Failed! pls try again");
                }
            }
        });
    }

    public void resetPassword(String appID, String custID,final OnApiResponse<WebResponse<JsonObject>> callback){
        retrofitProxyService.resetPassword(appID,custID).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {

                if(response.isSuccessful() && response.body()!=null){
                    callback.onSuccess(response.body());
                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        callback.onFailed("Failed to retrieve password, please try again");
                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Failed to retrieve password, please try again");
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("Failed to retrieve password, please try again");
            }
        });
    }

    public void createTemporaryAccount(String appID, TemporarySignUpModel model, final OnApiResponse<WebResponse<String>> callback){

        retrofitProxyService.createTemporaryAccount(appID,model).enqueue(new Callback<WebResponse<String>>() {
            @Override
            public void onResponse(Call<WebResponse<String>> call, Response<WebResponse<String>> response) {

                if(response.isSuccessful() && response.body().getStatus().equals("00")){
                    callback.onSuccess(response.body());
                }else {
                    callback.onFailed("Account creation failed!");
                }
            }
            @Override
            public void onFailure(Call<WebResponse<String>> call, Throwable t) {
              //  callback.onFailed("Something went wrong! Account creation failed, please try again");
                Log.d("onresponse","onresponse",t);
                if(t.toString().contains("java.net.SocketTimeoutException")){
                    callback.onFailed("Account creation failed! check your connection");
                }
                else {
                    callback.onFailed("Account creation failed! pls try again");
                }

            }
        });
    }

    public void changePassword(String appID, ChangePasswModel model, final OnApiResponse<WebResponse<JsonObject>> callback){

        retrofitProxyService.changePassword(appID,model).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {

                if(response.isSuccessful() && response.body()!=null){
                    callback.onSuccess(response.body());
                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        callback.onFailed(jObjError.getString("message"));
                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("attempt to change request failed");
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed(t.toString());
            }
        });
    }

    public void tradeHistory(String appID, ViewTradeHistory model, final OnApiResponse<WebResponse<JsonObject>> callback){

        retrofitProxyService.tradeHistory(appID,model).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {

                if(response.isSuccessful() && response.body()!=null){
                    callback.onSuccess(response.body());
                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        callback.onFailed(jObjError.getString("message"));
                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed(t.toString());
            }
        });
    }

    public void support(String appID, SupportModel model, final OnApiResponse<String> callback){

        retrofitProxyService.support(appID,model).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {

                if(response.isSuccessful() && response.body()!=null){

                    callback.onSuccess("Request sent successfully");
                    /*

                    try {
                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){

                            callback.onSuccess("Request sent successfully");

                        }else {
                            callback.onFailed(responseBody.getString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        callback.onFailed("Failed to send request");
                    }
                    */

                //    callback.onSuccess(response.body());


                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                       // callback.onFailed(jObjError.getString("message"));
                        callback.onFailed("Failed to send request");
                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Failed to send request");
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
              //  callback.onFailed(t.toString());
                callback.onFailed("Failed to send request");
            }
        });
    }

    public void customerInfo(String appID, CustomerInfoModel model, final OnApiResponse<List<ProfileModel>> callback){

        retrofitProxyService.customerInfo(appID,model).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {

                if(response.isSuccessful() && response.body()!=null){


                    try {
                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){
                            JSONArray rows= datatableObject.getJSONArray("Rows");

                            List<ProfileModel> list=new ArrayList<>();

                            for(int count=0; count<rows.length();count++){

                                JSONObject rowsObject= rows.optJSONObject(count);
                                if(rowsObject!=null) {
                                    String Symbol = rowsObject.getString("0");
                                    String Qty = rowsObject.getString("1");
                                    String MostRecentPrice = rowsObject.getString("2");
                                    String MktValue = rowsObject.getString("3");
                                    String CostBasis = rowsObject.getString("4");
                                    String Chg = rowsObject.getString("5");
                                    String ChgSincePurchase = rowsObject.getString("6");
                                    ProfileModel model = new ProfileModel();
                                    model.setSymbol(Symbol);
                                    model.setQty(Qty);
                                    model.setMostRecentPrice(MostRecentPrice);
                                    model.setMktValue(MktValue);
                                    model.setCostBasis(CostBasis);
                                    model.setChg(Chg);
                                    model.setChgSincePurchase(ChgSincePurchase);
                                    list.add(model);
                                }
                            }

                            callback.onSuccess(list);

                        }else {
                            callback.onFailed(responseBody.getString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                    }


                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        callback.onFailed(jObjError.getString("message"));
                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("attempt to change request failed");
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed(t.toString());
            }
        });
    }

    public void fundAccount(String appID, FundAcct model, final OnApiResponse<WebResponse<JsonObject>> callback){

        retrofitProxyService.fundAccount(appID,model).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {

                if(response.isSuccessful() && response.body()!=null){
                    callback.onSuccess(response.body());
                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        callback.onFailed(jObjError.getString("message"));
                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("attempt to change request failed");
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed(t.toString());
            }
        });
    }


    public void getProduct(final OnApiResponse<WebResponse<List<Product>>> callback){

        retrofitProxyService.getProduct().enqueue(new Callback<WebResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<WebResponse<List<Product>>> call, Response<WebResponse<List<Product>>> response) {

                if(response.isSuccessful() && response.body()!=null){
                    callback.onSuccess(response.body());
                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        callback.onFailed(jObjError.getString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("attempt to change request failed");
                    }
                }
            }
            @Override
            public void onFailure(Call<WebResponse<List<Product>>> call, Throwable t) {
                callback.onFailed(t.toString());
            }
        });
    }


    public void subscriptionList(String appID, UserDetailsParam req,final OnApiResponse<List<SubscriptionHistoryModel>> callback){

        retrofitProxyService.amTransactionAction(appID,req).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                List<SubscriptionHistoryModel> resp=new ArrayList<>();

                if(response.isSuccessful() && response.body().getData()!=null){
                    try {

                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){

                            JSONArray rows= datatableObject.optJSONArray("Rows");

                            for (int count=0; count<rows.length();count++){

                                SubscriptionHistoryModel subscription=new SubscriptionHistoryModel();

                                JSONObject rowsObject= rows.optJSONObject(count);
                                if(rowsObject!=null){

                                    String fundCode=rowsObject.optString("2");
                                    subscription.setFundCode(fundCode);
                                    String fundName=rowsObject.optString("3");
                                    subscription.setFundName(fundName);
                                    String quantity=rowsObject.optString("4");
                                    subscription.setQuantity(quantity);
                                    String unitPrice=rowsObject.optString("5");
                                    subscription.setUnitPrice(unitPrice);
                                    String txnDate=rowsObject.optString("9");
                                    subscription.setTxnDate(txnDate);
                                    resp.add(subscription);
                                }

                            }

                        }else {
                            callback.onFailed(responseBody.optString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        callback.onFailed("failed to connect!, please try again");
                    }

                    callback.onSuccess(resp);

                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());

                        String msg=jObjError.optString("message");

                        callback.onFailed("failed!, please try again");

                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Something went wrong, please try again");
                    }
                }

            }

            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("failed!, please try again");
            }
        });
    }


    public void getPortfolio(String appID, UserDetailsParam req,final OnApiResponse<List<PortFolioModel>> callback){

        retrofitProxyService.amTransactionAction(appID,req).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                List<PortFolioModel> resp=new ArrayList<>();

                if(response.isSuccessful() && response.body().getData()!=null){
                    try {

                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){

                            JSONArray rows= datatableObject.optJSONArray("Rows");

                            for (int count=0; count<rows.length();count++){

                                PortFolioModel model=new PortFolioModel();

                                JSONObject rowsObject= rows.optJSONObject(count);
                                if(rowsObject!=null){

                                    String fundName=rowsObject.optString("0");
                                    model.setFundName(fundName);
                                    String fundCode=rowsObject.optString("1");
                                    model.setFundCode(fundCode);
                                    String fundType=rowsObject.optString("2");
                                    model.setFundType(fundType);

                                    String custAID=rowsObject.optString("3");
                                    model.setCustAID(custAID);

                                    String totalAssetValue=rowsObject.optString("4");
                                    model.setTotalAssetValue(totalAssetValue);

                                    String valuationDate=rowsObject.optString("5");
                                    model.setValuationDate(valuationDate);

                                    resp.add(model);
                                }

                            }

                        }else {
                            callback.onFailed(responseBody.optString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        callback.onFailed("failed to connect!, please try again");
                    }

                    callback.onSuccess(resp);

                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());

                        String msg=jObjError.optString("message");

                        callback.onFailed("failed!, please try again");

                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Something went wrong, please try again");
                    }
                }

            }

            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("failed!, please try again");
            }
        });
    }

    public void redemption(String appID, UserDetailsParam req,final OnApiResponse<ArrayList<PortFolioModel>> callback){

        retrofitProxyService.amTransactionAction(appID,req).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                ArrayList<PortFolioModel> resp=new ArrayList<>();

                if(response.isSuccessful() && response.body().getData()!=null){
                    try {

                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){

                            JSONArray rows= datatableObject.optJSONArray("Rows");

                            for (int count=0; count<rows.length();count++){

                                PortFolioModel model=new PortFolioModel();

                                JSONObject rowsObject= rows.optJSONObject(count);
                                if(rowsObject!=null){

                                    String fundName=rowsObject.optString("0");
                                    model.setFundName(fundName);
                                    String fundCode=rowsObject.optString("1");
                                    model.setFundCode(fundCode);
                                    String fundType=rowsObject.optString("2");
                                    model.setFundType(fundType);

                                    String custAID=rowsObject.optString("3");
                                    model.setCustAID(custAID);

                                    String totalAssetValue=rowsObject.optString("4");
                                    model.setTotalAssetValue(totalAssetValue);

                                    String valuationDate=rowsObject.optString("5");
                                    model.setValuationDate(valuationDate);

                                    resp.add(model);
                                }

                            }

                        }else {
                            callback.onFailed(responseBody.optString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        callback.onFailed("failed to connect!, please try again");
                    }

                    callback.onSuccess(resp);

                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());

                        String msg=jObjError.optString("message");

                        callback.onFailed("failed!, please try again");

                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Something went wrong, please try again");
                    }
                }

            }

            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("failed!, please try again");
            }
        });
    }

    public void subscriptionAction(String appID, SubscribeModel req, final OnApiResponse<String> callback){

        retrofitProxyService.postSubscriptionRequest(appID,req).enqueue(new Callback<WebResponse<SubscriptionResponseModel>>() {
            @Override
            public void onResponse(Call<WebResponse<SubscriptionResponseModel>> call, Response<WebResponse<SubscriptionResponseModel>> response) {

                if(response.isSuccessful() && response.body().getData()!=null){

                    if(response.body().getData().isStatus()){
                        callback.onSuccess(response.body().getData().getData().getAuthorization_url());
                    }else {
                        callback.onFailed("Subscription request failed, please try again");
                    }

                }
                else {
                    callback.onFailed("Subscription request failed, please try again");
                }

            }

            @Override
            public void onFailure(Call<WebResponse<SubscriptionResponseModel>> call, Throwable t) {

                callback.onFailed("Subscription request failed, please try again");

            }
        });
    }

    public void getProductAss(String appID, UserDetailsParam req,final OnApiResponse<List<AssetProduct>> callback){

        retrofitProxyService.amTransactionAction(appID,req).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                List<AssetProduct> resp=new ArrayList<>();

                if(response.isSuccessful() && response.body().getData()!=null){
                    try {

                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){

                            JSONArray rows= datatableObject.optJSONArray("Rows");

                            for (int count=0; count<rows.length();count++){

                                AssetProduct model=new AssetProduct();

                                JSONObject rowsObject= rows.optJSONObject(count);
                                if(rowsObject!=null){
                                    String fundcode=rowsObject.optString("0");
                                    model.setFundCode(fundcode);

                                    String fundname=rowsObject.optString("1");
                                    model.setFundName(fundname);

                                    String MktValue=rowsObject.optString("4");
                                    model.setMktValue(MktValue);

                                    String FundType=rowsObject.optString("6");
                                    model.setFundType(FundType);

                                    String UnitPrice=rowsObject.optString("7");
                                    model.setUnitPrice(UnitPrice);

                                    String BidPrice=rowsObject.optString("8");
                                    model.setBidPrice(BidPrice);

                                    String OfferPrice=rowsObject.optString("9");
                                    model.setOfferPrice(OfferPrice);

                                    String MinInvest=rowsObject.optString("12");
                                    model.setMinInvest(MinInvest);

                                    resp.add(model);
                                }

                            }

                        }else {
                            callback.onFailed(responseBody.optString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        callback.onFailed("failed to connect!, please try again");
                    }

                    callback.onSuccess(resp);

                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());

                        String msg=jObjError.optString("message");

                        callback.onFailed("failed!, please try again");

                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Something went wrong, please try again");
                    }
                }

            }

            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("failed!, please try again");
            }
        });
    }

    public void getCreateAccountParameter(final OnApiResponse<CreateAccountParameter> callback){

        retrofitProxyService.getCreateAccountParameter().enqueue(new Callback<CreateAccountParameter>() {
            @Override
            public void onResponse(Call<CreateAccountParameter> call, Response<CreateAccountParameter> response) {

                if(response.isSuccessful() && response.body()!=null){
                    callback.onSuccess(response.body());
                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        callback.onFailed(jObjError.getString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("attempt to change request failed");
                    }
                }
            }
            @Override
            public void onFailure(Call<CreateAccountParameter> call, Throwable t) {
                callback.onFailed(t.toString());
            }
        });


    }

    public void kycUploads(String appID,String kycID,String custID, List<MultipartBody.Part>bodylist,final OnApiResponse<WebResponse<String>> callback){

        retrofitProxyService.kycUploads(appID,kycID,bodylist).enqueue(new Callback<WebResponse<String>>() {
            @Override
            public void onResponse(Call<WebResponse<String>> call, Response<WebResponse<String>> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onFailed("Account creation failed, pls try again");
                }
            }
            @Override
            public void onFailure(Call<WebResponse<String>> call, Throwable t) {
                callback.onFailed("Account creation failed, pls try again");
            }
        });
    }

    public void CompleteAccountCreationByCustomer(String appID,CreateAccount req,final OnApiResponse<String> callback){

        retrofitProxyService.CompleteAccountCreationByCustomer(appID,req).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                if(response.isSuccessful()){

                    if (response.body().getStatus().equals("00")){

                        JSONObject json = null;
                        try {
                            json = new JSONObject(response.body().getData().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        JSONArray Rows=datatableObject.optJSONArray("Rows");

                        JSONObject row1=Rows.optJSONObject(0);

                        String id=row1.optString("0");

                        callback.onSuccess(id);

                    }else {
                        callback.onFailed("Account creation failed");
                    }


                }else {
                    callback.onFailed("Account creation failed");
                }
            }
            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("Account creation failed");
            }
        });
    }

    public void getValidIDForm(final OnApiResponse<List<String>> callback){

        retrofitProxyService.getValidIDForm().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

                Log.d("getValidIDForm","getValidIDForm",t);

            }
        });
    }

    public void getPortfolioTransactionHistory(String appID, UserDetailsParam req,final OnApiResponse<List<PortfolioTransactionModel>> callback){
       // P_00005
        //  000940|CMMFUND

        retrofitProxyService.amTransactionAction(appID,req).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                List<PortfolioTransactionModel> resp=new ArrayList<>();

                if(response.isSuccessful() && response.body().getData()!=null){
                    try {

                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){

                            JSONArray rows= datatableObject.optJSONArray("Rows");

                            for (int count=0; count<rows.length();count++){

                                PortfolioTransactionModel model=new PortfolioTransactionModel();

                                JSONObject rowsObject= rows.optJSONObject(count);

                                if(rowsObject!=null){

                                    String valueDate=rowsObject.optString("0");
                                    model.setValueDate(valueDate);

                                    String fundName=rowsObject.optString("1");
                                    model.setFundName(fundName);

                                    String subscription=rowsObject.optString("2");
                                    model.setSubscription(subscription);

                                    String price=rowsObject.optString("3");
                                    model.setPrice(price);

                                    String mktValue=rowsObject.optString("4");
                                    model.setMktValue(mktValue);


                                    resp.add(model);
                                }

                            }

                        }else {
                            callback.onFailed(responseBody.optString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        callback.onFailed("failed to connect!, please try again");
                    }

                    callback.onSuccess(resp);

                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());

                        String msg=jObjError.optString("message");

                        callback.onFailed("failed!, please try again");

                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Something went wrong, please try again");
                    }
                }

            }

            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("failed!, please try again");
            }
        });


    }

    public void getPortfolioStatementHistory(String appID, UserDetailsParam req,final OnApiResponse<List<PortfolioStatementHistoryModel>> callback){
        //P_000080
        //  9-31-2018|9-10-2019|000940
        retrofitProxyService.amTransactionAction(appID,req).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                List<PortfolioStatementHistoryModel> resp=new ArrayList<>();

                if(response.isSuccessful() && response.body().getData()!=null){
                    try {

                        JSONObject json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        JSONObject datatableObject=responseBody.optJSONObject("DataTable");

                        if(datatableObject!=null){

                            JSONArray rows= datatableObject.optJSONArray("Rows");

                            for (int count=0; count<rows.length();count++){

                                PortfolioStatementHistoryModel model=new PortfolioStatementHistoryModel();

                                JSONObject rowsObject= rows.optJSONObject(count);

                                if(rowsObject!=null){

                                    String effectiveDate=rowsObject.optString("0");
                                    model.setEffectiveDate(effectiveDate);

                                    String desc=rowsObject.optString("5");
                                    model.setDescription(desc);

                                    String amount=rowsObject.optString("4");
                                    model.setAmount(amount);

                                    /*
                                    String price=rowsObject.optString("3");
                                    model.setPrice(price);

                                    String mktValue=rowsObject.optString("4");
                                    model.setMktValue(mktValue);
                                */

                                    resp.add(model);
                                }

                            }

                        }else {
                            callback.onFailed(responseBody.optString("StatusMessage"));
                        }

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        callback.onFailed("failed to connect!, please try again");
                    }

                    callback.onSuccess(resp);

                }else {
                    Log.e("create","onresponse");
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());

                        String msg=jObjError.optString("message");

                        callback.onFailed("failed!, please try again");

                        Log.e("create2","onresponse");

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed("Something went wrong, please try again");
                    }
                }

            }

            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("failed!, please try again");
            }
        });


    }

    public void getTransactionHistory(String custID,String profile,String appID,final OnApiResponse<List<TransactionHistoryModel>> callback){
        // /ebusiness-suit/fti-trans/000940/am/testApp
        retrofitProxyService.getTransactionHistory(custID,profile,appID).enqueue(new Callback<WebResponse<JsonObject>>() {
            @Override
            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                if(response.isSuccessful()&& response.body().getData()!=null){

                    List<TransactionHistoryModel> list=new ArrayList<>();

                    JSONObject json = null;
                    try {
                        json = new JSONObject(response.body().getData().toString());
                        JSONObject responseBody=json.optJSONObject("responseBody");
                        String outString=responseBody.optString("OutValue");


                        JSONArray outValue = new JSONArray(outString);

                        for(int count=0;count<outValue.length(); count++){

                            TransactionHistoryModel model=new TransactionHistoryModel();

                            JSONObject obj=outValue.optJSONObject(count);

                            String date=obj.optString("Date");
                            String TransactionReference=obj.optString("TransactionReference");
                            String Amount=obj.optString("Amount");
                            String ApprovedAmount=obj.optString("ApprovedAmount");
                            String Currency=obj.optString("Currency");
                            String Status=obj.optString("Status");

                            String ResponseDescription=obj.optString("ResponseDescription");
                            String Code=obj.optString("Code");

                            model.setDate(date);
                            model.setTransactionRef(TransactionReference);
                            model.setAmount(Amount);
                            model.setApprovedAmt(ApprovedAmount);
                            model.setCurrency(Currency);
                            model.setStatus(Status);
                            model.setResponseDesc(ResponseDescription);
                            model.setCode(Code);

                            list.add(model);
                        }

                        callback.onSuccess(list);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailed("failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                callback.onFailed("failed");
            }
        });
    }

    public void uploadPofilePix(String appID,String custID, MultipartBody.Part file,final OnApiResponse<String> callback){

        retrofitProxyService.profilePixUploads(appID,custID,file).enqueue(new Callback<WebResponse<String>>() {
            @Override
            public void onResponse(Call<WebResponse<String>> call, Response<WebResponse<String>> response) {

                if(response.isSuccessful()){
                callback.onSuccess("success");
                }
                else {
                    callback.onFailed("failed");
                }
            }

            @Override
            public void onFailure(Call<WebResponse<String>> call, Throwable t) {
                callback.onFailed("failed");
            }
        });


    }

    public void downloadPofilePix(String custID,final OnApiResponse<ImageDetails> callback){

        retrofitProxyService.getProfilePix(custID).enqueue(new Callback<WebResponse<ImageDetails>>() {
            @Override
            public void onResponse(Call<WebResponse<ImageDetails>> call, Response<WebResponse<ImageDetails>> response) {

                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("00")){

                        callback.onSuccess(response.body().getData());

                    }else {
                        callback.onFailed("failed");
                    }

                }
                else {
                    callback.onFailed("failed");
                }
            }

            @Override
            public void onFailure(Call<WebResponse<ImageDetails>> call, Throwable t) {
                callback.onFailed("failed");
            }
        });


    }

    public void getMinimumInvestment(final OnApiResponse<MinimumInvestmentObject> callback){

        retrofitProxyService.getMinimumInvestment().enqueue(new Callback<MinimumInvestmentObject>() {
            @Override
            public void onResponse(Call<MinimumInvestmentObject> call, Response<MinimumInvestmentObject> response) {

                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }

            }

            @Override
            public void onFailure(Call<MinimumInvestmentObject> call, Throwable t) {
                Log.d("getValidIDForm","getValidIDForm",t);
            }
        });

    }


}
