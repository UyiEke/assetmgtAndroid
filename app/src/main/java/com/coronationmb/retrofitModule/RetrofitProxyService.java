package com.coronationmb.retrofitModule;


import com.coronationmb.Model.Product;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.requestModel.ChangePasswModel;
import com.coronationmb.Model.requestModel.CompleteAccountCreation;
import com.coronationmb.Model.requestModel.CreateAccount;
import com.coronationmb.Model.requestModel.CustomerInfoModel;
import com.coronationmb.Model.requestModel.FundAcct;
import com.coronationmb.Model.requestModel.LoginModel;
import com.coronationmb.Model.requestModel.SubscribeModel;
import com.coronationmb.Model.requestModel.SubscriptionModel;
import com.coronationmb.Model.requestModel.SupportModel;
import com.coronationmb.Model.requestModel.TemporarySignUpModel;
import com.coronationmb.Model.requestModel.ViewTradeHistory;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.CreateAccountParameter;
import com.coronationmb.Model.responseModel.ImageDetails;
import com.coronationmb.Model.responseModel.MinimumInvestmentObject;
import com.coronationmb.Model.responseModel.SubscriptionResponseModel;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by UEke on 12/12/2017.
 */

public interface RetrofitProxyService {

    // login endpoint
    @POST("login/{appId}")
    Call<WebResponse<JsonObject>> login(@Path("appId")String appId, @Body LoginModel req);

    // reset password endpoint
    @POST("forgotPassword/{appId}/{custAid}")
    Call<WebResponse<JsonObject>> resetPassword(@Path("appId")String appId, @Path("custAid")String custAid);

    // create account endpoint
    @POST("createTemporaryAccount/{appId}")
    Call<WebResponse<String>> createTemporaryAccount(@Path("appId")String appId, @Body TemporarySignUpModel req);

    // change password endpoint
    @POST("changePassword/{appId}")
    Call<WebResponse<JsonObject>> changePassword(@Path("appId")String appId, @Body ChangePasswModel req);

    // get Trade history endpoint
    @POST("viewTradeHistory/{appId}")
    Call<WebResponse<JsonObject>> tradeHistory(@Path("appId")String appId, @Body ViewTradeHistory req);

    // get customer info endpoint
    @POST("customerInfo/{appId}")
    Call<WebResponse<JsonObject>> customerInfo(@Path("appId")String appId, @Body CustomerInfoModel req);

    // post support request/comment info endpoint
    @POST("support/{appId}")
    Call<WebResponse<JsonObject>> support(@Path("appId")String appId, @Body SupportModel req);

    // post fund account endpoint
    @POST("fundAccount/{appId}")
    Call<WebResponse<JsonObject>> fundAccount(@Path("appId")String appId, @Body FundAcct req);

    @GET("getAssetProduct")
    Call<WebResponse<List<Product>>> getProduct();

    // Get Asset Management : P2get-data
    @POST("fetchCustomerTransactionDetails/{appId}")
    Call<WebResponse<JsonObject>> amTransactionAction(@Path("appId")String appId, @Body UserDetailsParam req);

    @GET("createAccountParameter")
    Call<CreateAccountParameter> getCreateAccountParameter();

    @Multipart
    @POST("kycUploads/{appID}/{kycID}")
    Call<WebResponse<String>>kycUploads( @Path("appID") String appID,
                                         @Path("kycID") String kycID,
                                         @Part List<MultipartBody.Part> files);

    // Get Asset Management
    @POST("completeAccountCreation/{appId}")
    Call<WebResponse<JsonObject>>  CompleteAccountCreationByCustomer(@Path("appId")String appId, @Body CreateAccount req);

    // Post Subscription
    @POST("subscribe/{appId}")
    Call<WebResponse<SubscriptionResponseModel>> postSubscriptionRequest(@Path("appId")String appId, @Body SubscribeModel req);

    @GET("validFormOfID")
    Call<List<String>> getValidIDForm();


    // get transaction History
    @GET("getTransactionHistory")
    Call<WebResponse<JsonObject>> getTransactionHistory(@Query("custID") String custID,
                                                        @Query("profile") String profile,
                                                        @Query("appID") String appID);

    @Multipart
    @POST("profilePixUpload/{appID}/{custID}")
    Call<WebResponse<String>> profilePixUploads(
                                         @Path("appID") String appID,
                                         @Path("custID") String custID,
                                         @Part MultipartBody.Part file);


    // get profile Image
    @GET("profilePictureDownload/{custID}")
    Call<WebResponse<ImageDetails>> getProfilePix(@Query("custID") String custID);



    @GET("minimumInvestment")
    Call<MinimumInvestmentObject> getMinimumInvestment();





}

