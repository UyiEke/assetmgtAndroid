package com.coronationmb.activityModule.activityModule.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.Product;
import com.coronationmb.Model.UserDetailsParam;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.requestModel.CustomerInfoModel;
import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;
import com.coronationmb.Model.responseModel.ProfileModel;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.LoginActivity;
import com.coronationmb.adapter.MainDashboardPortfolioAdapter;
import com.coronationmb.adapter.ProfileAdapter;
import com.coronationmb.service.Constant;
import com.coronationmb.service.FileUtils;
import com.coronationmb.service.FileUtilx;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.portfolio_status)
    TextView portfolio_status;


    @BindView(R.id.custID)
    TextView custID;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.phone_no)
    TextView phone_no;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.lay1)
    CardView lay1;

    List<PortFolioModel> profileData;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    ProfileAdapter adapter;
    LinearLayoutManager layoutManager;

    List<Product> listOfProduct;

    private ProgressDialog progress;
    private ProfileViewModel profileViewModel;

    String imagePath, passPortPath;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    @BindView(R.id.usernameTextview)
    TextView usernameTextview;
    private int PICK_FROM_GALLERY=100;
    private Uri passPortUri;
    private int CAMERA_REQUEST=102;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);
        getImage();
     //   getProduct();
        return root;
    }

    private void initUI() {

        ((DashboardActivity)context).changeToolbarTitle("PROFILE");
        ((DashboardActivity)context).changeHamburgerIconClorBottomNav();

        /*
        if(imagePath!=null){

            Picasso.with(context)
                    .load(imagePath) // web image url
                    .fit().centerInside()
                  //  .rotate(-90)                    //if you want to rotate by 90 degrees
                    .into(profile_image);
        }
        */

        usernameTextview.setText(SharedPref.getFULLNAME(context));


      //  repo=new GlobalRepository(context);
        lay1.setVisibility(View.GONE);
        portfolio_status.setVisibility(View.GONE);
        layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter=new ProfileAdapter(new ArrayList<>(),context,listOfProduct);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);


        if(profileData!=null){

            progressBar.setVisibility(View.GONE);
            lay1.setVisibility(View.VISIBLE);

            name.setText(SharedPref.getFULLNAME(context)==null?"NA":SharedPref.getFULLNAME(context) );
            custID.setText(SharedPref.getUSERID(context)==null?"NA":SharedPref.getUSERID(context));
            email.setText(SharedPref.getEMAIL(context)==null?"NA":SharedPref.getEMAIL(context));
            phone_no.setText(SharedPref.getPHONE(context)==null?"NA":SharedPref.getPHONE(context));


            List<PortFolioModel> list =new ArrayList<>();

            for(int count = 0; count<profileData.size(); count++){

                if(!profileData.get(count).getFundType().equals("Unused Cash")){
                    list.add(profileData.get(count));
                }

            }

            adapter=new ProfileAdapter(list,context,listOfProduct);
            recycler.setAdapter(adapter);

        }else {
            getPortfolio();
        }


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            profileData =(List<PortFolioModel>) getArguments().getSerializable("portfolio");
          //  assetProducts = (List<AssetProduct>)getArguments().getSerializable("product");
        }
        initUI();
    }


    private void displayProfile(List<PortFolioModel> data){
        profileData=data;

        if(data.size()>0) {
            progressBar.setVisibility(View.GONE);
            lay1.setVisibility(View.VISIBLE);

            name.setText(SharedPref.getFULLNAME(context) == null ? "NA" : SharedPref.getFULLNAME(context));
            custID.setText(SharedPref.getUSERID(context) == null ? "NA" : SharedPref.getUSERID(context));
            email.setText(SharedPref.getEMAIL(context) == null ? "NA" : SharedPref.getEMAIL(context));
            phone_no.setText(SharedPref.getPHONE(context) == null ? "NA" : SharedPref.getPHONE(context));



            List<PortFolioModel> list =new ArrayList<>();

            for(int count = 0; count<data.size(); count++){

                if(!data.get(count).getFundType().equals("Unused Cash")){
                    list.add(data.get(count));
                }

            }



            adapter = new ProfileAdapter(list, context, listOfProduct);
            recycler.setAdapter(adapter);
        }else {
            progressBar.setVisibility(View.GONE);
            lay1.setVisibility(View.VISIBLE);
            portfolio_status.setVisibility(View.VISIBLE);
            name.setText(SharedPref.getFULLNAME(context)==null?"NA":SharedPref.getFULLNAME(context) );
            custID.setText(SharedPref.getUSERID(context)==null?"NA":SharedPref.getUSERID(context));
            email.setText(SharedPref.getEMAIL(context)==null?"NA":SharedPref.getEMAIL(context));
            phone_no.setText(SharedPref.getPHONE(context)==null?"NA":SharedPref.getPHONE(context));
        }
    }

    private void getPortfolio(){

        UserDetailsParam req=new UserDetailsParam();
        req.setProfile(Constant.profile);
        req.setAppId(SharedPref.getApi_ID(context));
        req.setParams(SharedPref.getUSERID(context));
        req.setFunctionId(Constant.Am_Portfolio);

        new GlobalRepository(context).getPortfolio(SharedPref.getApi_ID(context), req, new OnApiResponse<List<PortFolioModel>>() {
            @Override
            public void onSuccess(List<PortFolioModel> data) {
                displayProfile(data);
            }

            @Override
            public void onFailed(String message) {
                displayProfile(new ArrayList<>());
            }
        });

    }



    public void getProduct(){
        new GlobalRepository(context).getProduct(new OnApiResponse<WebResponse<List<Product>>>() {
            @Override
            public void onSuccess(WebResponse<List<Product>> data) {
                listOfProduct=data.getData();
            }

            @Override
            public void onFailed(String message) {
                listOfProduct=null;
            }
        });
    }

    @OnClick(R.id.profile_image)
    public void changeProfilePix(){

        new AlertDialog.Builder(context)
                .setTitle("Select")
                .setMessage("Choose Your Profile Picture")
                .setCancelable(true)
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // exitApp();
                        pickFromGallery();
                    }
                })
                /*
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                     //   dialog.dismiss();
                        pickFromCamera();
                    }
                })
                */
                .setIcon(R.drawable.lionhead_icon)
                .show();
    }

    public void pickFromGallery(){

        Intent intent = new Intent();
        //******call android default gallery
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //******code for crop image
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(
                    Intent.createChooser(intent,"Complete action using"),
                    PICK_FROM_GALLERY);
        } catch (ActivityNotFoundException e) {}

    }

    private void pickFromCamera(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
             //   Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
               // Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {
            passPortUri = data.getData();
            try {
                String path = FileUtilx.getPath(context,passPortUri);


                if(!TextUtils.isEmpty(path)){

                    File file = new File(path);
                    long lenght = file.length() / 1024; // size in Kb

                    if(lenght>2000){
                        Utility.alertOnly(context, "Image size cannot exceed 2MB", "");
                        return;

                    }

                }



                String dir[]=path.split("/");
                passPortPath=path;
            //    passportEditText.setText(dir[dir.length-1]);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), passPortUri);
                profile_image.setImageBitmap(bitmap);

                uploadProfilePixToServer(passPortUri,bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


       else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(photo);
        }

        /*
        else if (requestCode == PROOF_ADDRESS && resultCode == RESULT_OK && data != null && data.getData() != null) {
            proofAddressUri = data.getData();
            try {
                String path = FileUtilx.getPath(context,proofAddressUri);
                String dir[]=path.split("/");
                proofAddress=path;
                proofAddressEditText.setText(dir[dir.length-1]);
                //   Toast.makeText(context,"Brochure captured",Toast.LENGTH_SHORT).show();
                // brochurePath=path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */

    }

    private void uploadProfilePixToServer(Uri imageUri, Bitmap bitmap){
        // create part for file (photo, video, ...)
        MultipartBody.Part passport = prepareFilePart("files", imageUri);

        new GlobalRepository(context).uploadPofilePix(SharedPref.getApi_ID(context), SharedPref.getUSERID(context), passport, new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {
                Toast.makeText(context,"Upload of profile picture was successful",Toast.LENGTH_LONG).show();
               // getImageMain();
                ((DashboardActivity)context).profile_image.setImageBitmap(bitmap);
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(context,"Failed to upload picture",Toast.LENGTH_LONG).show();
            }
        });

    }

    /*
    private void getImage(){

        Picasso.with(getContext())
                .load(Constant.BaseUrl+"/profilePictureDownload/"+SharedPref.getUSERID(getContext())) // web image url
                .fit().centerInside()
                //  .rotate(-90)                    //if you want to rotate by 90 degrees
               // .into(((DashboardActivity)getContext()).profile_image);
                .into(profile_image);

    }
    */


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void getImage(){

        String cusId=SharedPref.getUSERID(context);

        new GlobalRepository(context).downloadPofilePix(cusId, new OnApiResponse<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {

                Bitmap bmp= BitmapFactory.decodeStream(data.byteStream());

                profile_image.setImageBitmap(bmp);


            }

            @Override
            public void onFailed(String message) {

            }
        });
    }


    public void getImageMain(){

        String cusId=SharedPref.getUSERID(context);

        new GlobalRepository(context).downloadPofilePix(cusId, new OnApiResponse<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {

                Bitmap bmp= BitmapFactory.decodeStream(data.byteStream());

                ((DashboardActivity)context).profile_image.setImageBitmap(bmp);


            }

            @Override
            public void onFailed(String message) {

            }
        });
    }



    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtilx to get the actual file by uri
        File file = FileUtils.getFile(context, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


/*
    private void getImageMain(){

        Picasso.with(getContext())
                .load(Constant.BaseUrl+"/profilePictureDownload/"+SharedPref.getUSERID(getContext())) // web image url
                .fit().centerInside()
                //  .rotate(-90)                    //if you want to rotate by 90 degrees
                 .into(((DashboardActivity)getContext()).profile_image);
               // .into(profile_image);

    }

    */

}