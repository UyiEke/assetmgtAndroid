package com.coronationmb.activityModule.activityModule;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.coronationmb.Model.OnApiResponse;
import com.coronationmb.Model.WebResponse;
import com.coronationmb.Model.requestModel.CreateAccount;
import com.coronationmb.Model.responseModel.CreateAccountParameter;
import com.coronationmb.R;
import com.coronationmb.service.Constant;
import com.coronationmb.service.CustomeSpinner.SearchableSpinner;
import com.coronationmb.service.FileUtils;
import com.coronationmb.service.FileUtilx;
import com.coronationmb.service.GlobalRepository;
import com.coronationmb.service.SharedPref;
import com.coronationmb.service.Utility;
import com.google.gson.JsonObject;

import net.igenius.customcheckbox.CustomCheckBox;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AcctSetUpActivity extends AppCompatActivity {
    private static final int SELECT_PASSPORT =100 ;
    private static final int PROOF_ADDRESS =101 ;
    private static final int VALID_ID =102 ;
    private static final int SIGNATURE =103 ;
    private static final int BIRTH_CERT =104 ;
    private static final int SPONSOR =105 ;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @BindView(R.id.personal_detail_layout)
    LinearLayout personal_detail_layout;

    @BindView(R.id.bank_detail_layout)
    LinearLayout bank_detail_layout;

    @BindView(R.id.identification_layout)
    LinearLayout identification_layout;

    @BindView(R.id.next_kin_layout)
    LinearLayout next_kin_layout;

    @BindView(R.id.required_layout)
    LinearLayout required_layout;


    @BindView(R.id.success_checkbox)
    CustomCheckBox success_checkbox;

    @BindView(R.id.success_msg)
    RelativeLayout success_msg;


    @BindView(R.id.sponsor_layout)
    LinearLayout sponsor_layout;



    @BindView(R.id.state_layout)
    LinearLayout state_layout;

    @BindView(R.id.state_country)
    SearchableSpinner state_country;



    @BindView(R.id.submit)
    Button submit;


    @BindView(R.id.backarrow)
    ImageView backarrow;


    @BindView(R.id.sponsor_layout_view)
    LinearLayout sponsor_layout_view;

    // personal info views

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.cust_lga)
    EditText cust_lga;

    @BindView(R.id.user_title)
    EditText user_title;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.gender)
    Spinner gender;

    @BindView(R.id.surname)
    EditText surname;
    @BindView(R.id.fname)
    EditText fname;
    @BindView(R.id.other_name)
    EditText other_name;

    @BindView(R.id.dob)
    EditText dob;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.country)
    SearchableSpinner country;

    @BindView(R.id.phone_no)
    EditText phone_no;

    /*
    @BindView(R.id.idType)
    SearchableSpinner idType;

    @BindView(R.id.idNumber)
    EditText idNumber;
    */

    // bank_detail_layout info views
    @BindView(R.id.acct_name)
    EditText acct_name;

    @BindView(R.id.acct_number)
    EditText acct_number;

    @BindView(R.id.bvnEditTextview)
    EditText bvnEditTextview;

    @BindView(R.id.bankName)
    SearchableSpinner bankName;

    @BindView(R.id.reinvestCheck)
    RadioButton reinvestCheck;

    // identification_layout info views
    @BindView(R.id.idSpinner)
    SearchableSpinner idSpinner;

    @BindView(R.id.identifNumber)
    EditText identifNumber;

    @BindView(R.id.dateOfExpiry)
    EditText dateOfExpiry;


    //sponsor views

    @BindView(R.id.sponspor_surname)
    EditText sponspor_surname;

    @BindView(R.id.sponspor_fname)
    EditText sponspor_fname;

    @BindView(R.id.sponspor_other_name)
    EditText sponspor_other_name;

    @BindView(R.id.sponspor_residential_address)
    EditText sponspor_residential_address;

    @BindView(R.id.residential_address)
    EditText residential_address;

    @BindView(R.id.sponspor_country)
    SearchableSpinner sponspor_country;

    @BindView(R.id.sponspor_city)
    EditText sponspor_city;

    @BindView(R.id.sponspor_lga)
    EditText sponspor_lga;

    @BindView(R.id.sponspor_nationality)
    EditText sponspor_nationality;

    @BindView(R.id.sponspor_phone)
    EditText sponspor_phone;


    @BindView(R.id.sponspor_email)
    EditText sponspor_email;

    // next_kin_layout info views

    @BindView(R.id.relationship)
    EditText relationship;

    @BindView(R.id.fullname)
    EditText fullname;

    @BindView(R.id.nextofkin_phonenumber)
    EditText nextofkin_phonenumber;

    @BindView(R.id.nextofkin_email)
    EditText nextofkin_email;


    // required_layout info views
    @BindView(R.id.passportEditText)
    EditText passportEditText;

    @BindView(R.id.passport_upload)
    LinearLayout passport_upload;

    @BindView(R.id.proofAddressEditText)
    EditText proofAddressEditText;

    @BindView(R.id.proof_address_upload)
    LinearLayout proof_address_upload;

    @BindView(R.id.id_EditText)
    EditText id_EditText;
    @BindView(R.id.id_upload)
    LinearLayout id_upload;

    @BindView(R.id.signatureEditText)
    EditText signatureEditText;
    @BindView(R.id.signature_upload)
    LinearLayout signature_upload;

    @BindView(R.id.birth_EditText)
    EditText birth_EditText;
    @BindView(R.id.birth_upload)
    LinearLayout birth_upload;

    @BindView(R.id.sponsoridEditText)
    EditText sponsoridEditText;
    @BindView(R.id.sponsor_upload)
    LinearLayout sponsor_upload;

    @BindView(R.id.nextofkin_address)
    EditText nextofkin_address;

    //end of requirement

    List<String> listOfValidIDs;

    Context context;
    GlobalRepository repo;
    String AppID;

    public SimpleDateFormat dateformat;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    int pageInt;

    String genderVal,user_titleVal,surnameVal,fnameVal,other_nameVal,DOB,acct_nameVal,acct_numberVal,nextofkin_emailVal,
            bankNameVal,bvnVal,idVal,identifNumberVal,dateOfExpiryVal,relationshipVal,fullnameVal,nextofkin_phonenumberVal;
     String passPortPath,proofAddress,idPath;

     String custResidential_address,custCountry,csutLga,custCity,custPhno,custEmail;

     String sponspor_surnameVal,sponspor_fnameVal,sponspor_other_nameVal,sponspor_residential_addressVal,sponspor_countryVal,
             sponspor_cityVal,  sponspor_lgaVal, sponspor_nationalityVal,sponspor_phoneVal,sponspor_emailVal;

     boolean isMinor=false;
    private String sponsorPath;
    private String birthCertPath;
    private String signaturePath;

    int ageDifference;
    boolean reInvest=false;
    private ProgressDialog progress;
    private String nextOfKinAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        context = AcctSetUpActivity.this;

        repo = new GlobalRepository(context);
        initUI();

      //  email
    }

    private void initUI() {
        getValidIDs();
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("please wait.......");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCanceledOnTouchOutside(false);

        personal_detail_layout.setVisibility(View.VISIBLE);
        bank_detail_layout.setVisibility(View.GONE);
        identification_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.GONE);
        sponsor_layout.setVisibility(View.GONE);
        success_msg.setVisibility(View.GONE);
        required_layout.setVisibility(View.GONE);

        getParameter();
        pageInt = 1;

        requestPermissionAndContinue();
    }

    @OnClick(R.id.dob)
    public void dobAction(){
        datePickerAction(dob);
    }

    @OnClick(R.id.dateOfExpiry)
    public void dateOfExpiry(){
        datePickerAction(dateOfExpiry);
    }

    @OnClick(R.id.submit)
    public void submitAction() {
        int d = pageInt;
        switch (pageInt) {

            case 1:

                title.setText("Personal Details");
                personal_detail_layout.setVisibility(View.VISIBLE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                submit.setText("Next");

                personalInfoAction();
              //  bankInfoAction();

                break;

            case 2:
                title.setText("Bank Details");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.VISIBLE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);

                submit.setText("Next");
                bankInfoAction();
               // identification();

                break;

            case 3:
                title.setText("Identification");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.VISIBLE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                submit.setText("Next");
               // sponsorInfActiono();
                identification();

                break;

            case 4:
                title.setText("Next of Kin");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.VISIBLE);
                required_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                submit.setText("Next");
                kinsInfoAction();

                break;


            case 5:
                title.setText("Sponsor");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.VISIBLE);
                required_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                submit.setText("Next");
                sponsorInfoAction();

                break;

            case 6:
                title.setText("Identification & Uploads");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.VISIBLE);
                sponsor_layout_view.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);

                if(isMinor){
                    sponsor_layout_view.setVisibility(View.VISIBLE);
                }else {
                    sponsor_layout_view.setVisibility(View.GONE);
                }
                submit.setText("Submit");
                requiredDoc();

                break;

            default:
        }

    }

    private void personalInfoAction() {
        custPhno=phone_no.getText().toString().trim();
        genderVal = gender.getSelectedItem().toString();
        user_titleVal = user_title.getText().toString().trim();
        surnameVal = surname.getText().toString().trim();
        fnameVal = fname.getText().toString().trim();
        other_nameVal = other_name.getText().toString().trim();
        DOB = dob.getText().toString().trim();
        custResidential_address=residential_address.getText().toString().trim();
        custEmail=email.getText().toString().trim();
        custCountry=country.getSelectedItem().toString().trim();
        custCity=city.getText().toString().trim();
        csutLga=cust_lga.getText().toString().trim();
     //   custIdType=idType.getSelectedItem().toString().trim();
       // custIdNumber=idNumber.getText().toString().trim();

        idVal= idSpinner.getSelectedItem().toString().trim();
        identifNumberVal= identifNumber.getText().toString().trim();
        dateOfExpiryVal= dateOfExpiry.getText().toString().trim();


        if (TextUtils.isEmpty(genderVal) || TextUtils.isEmpty(user_titleVal) || TextUtils.isEmpty(surnameVal)
                || TextUtils.isEmpty(fnameVal) || TextUtils.isEmpty(other_nameVal) || TextUtils.isEmpty(DOB)
                || TextUtils.isEmpty(custResidential_address) || TextUtils.isEmpty(custCountry) || TextUtils.isEmpty(custCity)
                || TextUtils.isEmpty(csutLga)|| TextUtils.isEmpty(custEmail)||TextUtils.isEmpty(idVal) || TextUtils.isEmpty(identifNumberVal) || TextUtils.isEmpty(dateOfExpiryVal)
                || TextUtils.isEmpty(custPhno))
        {
            Utility.alertOnly(context, "One of the field is empty", "");
            return;
        }
        if(Utility.checkDate(DOB)){
            Utility.alertOnly(context, "Invalid date of birth", "");
            return;
        }

        if(!Utility.validateEmail(custEmail)){
            Utility.alertOnly(context, "Invalid email", "");
            return;
        }


        if(!Utility.checkDate(dateOfExpiryVal)){
            Utility.alertOnly(context, "ID card expiry date not valid", "");
            return;
        }

        checkIfCustomerIsMinor(DOB);

        pageInt = 2;

        title.setText("Bank Details");
        personal_detail_layout.setVisibility(View.GONE);
        bank_detail_layout.setVisibility(View.VISIBLE);
        identification_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.GONE);
        success_msg.setVisibility(View.GONE);
        submit.setText("Next");

    }

    private void kinsInfoAction() {

        relationshipVal = relationship.getText().toString().trim();
        fullnameVal = fullname.getText().toString().trim();
        nextofkin_phonenumberVal = nextofkin_phonenumber.getText().toString().trim();
        nextofkin_emailVal = nextofkin_email.getText().toString().trim();
        nextOfKinAddress=nextofkin_address.getText().toString().trim();

        if (TextUtils.isEmpty(relationshipVal) || TextUtils.isEmpty(fullnameVal) || TextUtils.isEmpty(nextofkin_phonenumberVal)
                || TextUtils.isEmpty(nextofkin_emailVal)|| TextUtils.isEmpty(nextOfKinAddress)) {
            Utility.alertOnly(context, "One of the field is empty", "");
            return;
        }

        if(Utility.validatePhoneNumber(nextofkin_phonenumberVal)){
            Utility.alertOnly(context, "Invalid phone number", "");
            return;
        }

        if(!Utility.validateEmail(nextofkin_emailVal)){
            Utility.alertOnly(context, "Invalid email", "");
            return;
        }

        if(isMinor)
        {
        title.setText("Sponsor");
        personal_detail_layout.setVisibility(View.GONE);
        bank_detail_layout.setVisibility(View.GONE);
        identification_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.GONE);
        sponsor_layout.setVisibility(View.VISIBLE);
        required_layout.setVisibility(View.GONE);
        success_msg.setVisibility(View.GONE);
        submit.setText("Submit");
        pageInt = 5;
        }
        else {
            pageInt = 6;
            title.setText("Identification & Uploads");
            personal_detail_layout.setVisibility(View.GONE);
            bank_detail_layout.setVisibility(View.GONE);
            identification_layout.setVisibility(View.GONE);
            next_kin_layout.setVisibility(View.GONE);
            sponsor_layout.setVisibility(View.GONE);

            if(isMinor){
                sponsor_layout_view.setVisibility(View.VISIBLE);
            }else {
                sponsor_layout_view.setVisibility(View.GONE);
            }
            required_layout.setVisibility(View.VISIBLE);
            success_msg.setVisibility(View.GONE);
            submit.setText("Submit");
        }

    }

    private void bankInfoAction() {
        acct_nameVal = acct_name.getText().toString().trim();
        acct_numberVal = acct_number.getText().toString().trim();
        bankNameVal = bankName.getSelectedItem().toString().trim();
        bvnVal = bvnEditTextview.getText().toString().trim();

        if (TextUtils.isEmpty(acct_nameVal) || TextUtils.isEmpty(acct_numberVal) || TextUtils.isEmpty(bankNameVal)
                || TextUtils.isEmpty(bvnVal)) {
            Utility.alertOnly(context, "One of the field is empty", "");
            return;
        }
        if(acct_numberVal.length()!=10){
            Utility.alertOnly(context, "Invalid account number", "");
            return;
        }

        if(bvnVal.length()!=11){
            Utility.alertOnly(context, "Invalid BVN", "");
            return;
        }

        if(reinvestCheck.isChecked()){
            reInvest=true;
        }else {
            reInvest=false;
        }

        pageInt = 4;

        /*
        title.setText("Identification & Uploads");
        personal_detail_layout.setVisibility(View.GONE);
        bank_detail_layout.setVisibility(View.GONE);
        identification_layout.setVisibility(View.VISIBLE);
        next_kin_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.GONE);
        success_msg.setVisibility(View.GONE);
        */

        title.setText("Next of Kin");
        personal_detail_layout.setVisibility(View.GONE);
        bank_detail_layout.setVisibility(View.GONE);
        identification_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.VISIBLE);
        required_layout.setVisibility(View.GONE);
        sponsor_layout.setVisibility(View.GONE);
        success_msg.setVisibility(View.GONE);
        submit.setText("Next");

    }

    private void identification(){
        /*
         idVal= idSpinner.getSelectedItem().toString().trim();
         identifNumberVal= identifNumber.getText().toString().trim();
         dateOfExpiryVal= dateOfExpiry.getText().toString().trim();


        if (TextUtils.isEmpty(idVal) || TextUtils.isEmpty(identifNumberVal) || TextUtils.isEmpty(dateOfExpiryVal)) {
            Utility.alertOnly(context, "One of the field is empty", "");
            return;
        }

        if(!Utility.checkDate(dateOfExpiryVal)){
            Utility.alertOnly(context, "Invalid date", "");
            return;
        }

        pageInt = 4;
        title.setText("Next of Kin");
        personal_detail_layout.setVisibility(View.GONE);
        bank_detail_layout.setVisibility(View.GONE);
        identification_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.VISIBLE);
        success_msg.setVisibility(View.GONE);
        */

    }

    private void requiredDoc(){

        if (TextUtils.isEmpty(passPortPath) || TextUtils.isEmpty(proofAddress) || TextUtils.isEmpty(idPath)) {
            Utility.alertOnly(context, "One of the field is empty", "");
            return;
        }
        progress.show();
        completeAcctCreation();
    }

    private void sponsorInfoAction() {

        sponspor_surnameVal = sponspor_surname.getText().toString().trim();
        sponspor_fnameVal = sponspor_fname.getText().toString().trim();
        sponspor_other_nameVal = sponspor_other_name.getText().toString().trim();
        sponspor_residential_addressVal=sponspor_residential_address.getText().toString().trim();
        sponspor_residential_addressVal=sponspor_country.getSelectedItem().toString().trim();
        sponspor_cityVal=sponspor_city.getText().toString().trim();
        sponspor_lgaVal=sponspor_lga.getText().toString().trim();
        sponspor_nationalityVal=sponspor_nationality.getText().toString().trim();
        sponspor_phoneVal=sponspor_phone.getText().toString().trim();
        sponspor_emailVal=sponspor_email.getText().toString().trim();


        if (TextUtils.isEmpty(sponspor_surnameVal) || TextUtils.isEmpty(sponspor_fnameVal) || TextUtils.isEmpty(sponspor_other_nameVal)
                || TextUtils.isEmpty(sponspor_residential_addressVal) || TextUtils.isEmpty(sponspor_residential_addressVal) || TextUtils.isEmpty(sponspor_cityVal)
                || TextUtils.isEmpty(sponspor_lgaVal) || TextUtils.isEmpty(sponspor_nationalityVal) || TextUtils.isEmpty(sponspor_phoneVal)|| TextUtils.isEmpty(sponspor_emailVal)) {
            Utility.alertOnly(context, "One of the field is empty", "");
            return;
        }

        pageInt = 6;

        title.setText("Upload Required Documents");
        personal_detail_layout.setVisibility(View.GONE);
        bank_detail_layout.setVisibility(View.GONE);
        identification_layout.setVisibility(View.GONE);
        next_kin_layout.setVisibility(View.GONE);
        sponsor_layout.setVisibility(View.GONE);

        if(isMinor){
            sponsor_layout_view.setVisibility(View.VISIBLE);
        }else {
            sponsor_layout_view.setVisibility(View.GONE);
        }
        required_layout.setVisibility(View.VISIBLE);
        success_msg.setVisibility(View.GONE);
        submit.setText("Submit");

    }


    @OnClick({R.id.passport_upload,R.id.proof_address_upload,R.id.id_upload,R.id.signature_upload,R.id.birth_upload,R.id.sponsor_upload})

    public void upload(View view){
        int id=view.getId();

        switch (id){

            case R.id.passport_upload: selectPassport();
                break;
            case R.id.proof_address_upload: selectProofAddres();
                break;
            case R.id.id_upload: selectValidID();
                break;
            case R.id.signature_upload:  selectSignature();
                break;

            case R.id.birth_upload:  selectBirthCertificate();
                break;

            case R.id.sponsor_upload:  selectSponsorId();
                break;

                default:

        }
    }

    public void selectPassport(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Passport"),SELECT_PASSPORT);
    }

    public void selectProofAddres(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Proof of Address"),PROOF_ADDRESS);
    }

    public void selectValidID(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Valid ID"),VALID_ID);
    }

    public void selectSignature(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Signature image"),SIGNATURE);
    }

    public void selectBirthCertificate(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Birth Certificate image"),BIRTH_CERT);
    }

    public void selectSponsorId(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Sponsor ID"),SPONSOR);
    }

    Uri passPortUri,proofAddressUri,validIDuri,signatureUri,birthuri,sponsorUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PASSPORT && resultCode == RESULT_OK && data != null && data.getData() != null) {
             passPortUri = data.getData();
            try {
                String path = FileUtilx.getPath(context,passPortUri);
                String dir[]=path.split("/");
                passPortPath=path;
                passportEditText.setText(dir[dir.length-1]);
                //   Toast.makeText(context,"Brochure captured",Toast.LENGTH_SHORT).show();
                // brochurePath=path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

        else   if (requestCode == VALID_ID && resultCode == RESULT_OK && data != null && data.getData() != null) {
             validIDuri = data.getData();
            try {
                String path = FileUtilx.getPath(context,validIDuri);
                String dir[]=path.split("/");
                idPath=path;
                id_EditText.setText(dir[dir.length-1]);
                //   Toast.makeText(context,"Brochure captured",Toast.LENGTH_SHORT).show();
                // brochurePath=path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else   if (requestCode == SIGNATURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
             signatureUri = data.getData();
            try {
                String path = FileUtilx.getPath(context,signatureUri);
                String dir[]=path.split("/");
                signaturePath=path;
                signatureEditText.setText(dir[dir.length-1]);
                //   Toast.makeText(context,"Brochure captured",Toast.LENGTH_SHORT).show();
                // brochurePath=path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else   if (requestCode == BIRTH_CERT && resultCode == RESULT_OK && data != null && data.getData() != null) {
             birthuri = data.getData();
            try {
                String path = FileUtilx.getPath(context,birthuri);
                String dir[]=path.split("/");
                birthCertPath=path;
                birth_EditText.setText(dir[dir.length-1]);
                //   Toast.makeText(context,"Brochure captured",Toast.LENGTH_SHORT).show();
                // brochurePath=path;



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else   if (requestCode == SPONSOR && resultCode == RESULT_OK && data != null && data.getData() != null) {
            sponsorUri = data.getData();
            try {
                String path = FileUtilx.getPath(context,sponsorUri);
                String dir[]=path.split("/");
                sponsorPath=path;
                sponsoridEditText.setText(dir[dir.length-1]);
                //   Toast.makeText(context,"Brochure captured",Toast.LENGTH_SHORT).show();
                // brochurePath=path;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void datePickerAction(final EditText edit_Text){
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
             //   String outPutformat = "yyyy-MMM-dd"; //In which you need put here
                String outPutformat = "dd MMM, yyyy";
                dateformat = new SimpleDateFormat(outPutformat, Locale.US);
                edit_Text.setText(dateformat.format(myCalendar.getTime()));
            }
        };
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.backarrow)
    public void backArrow(){

        switch (pageInt) {

            case 1:  onBackPressed();
                break;

            case 2:

                title.setText("Personal Details");
                personal_detail_layout.setVisibility(View.VISIBLE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                submit.setText("Next");

                break;

            case 3:
                title.setText("Bank Details");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.VISIBLE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                submit.setText("Next");
                break;

            case 4:
                /*
                title.setText("Identification & Uploads");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.VISIBLE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                submit.setText("Next");
                */
                title.setText("Bank Details");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.VISIBLE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                submit.setText("Next");

                break;

            case 5:
                title.setText("Next of Kin");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.VISIBLE);
                required_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                submit.setText("Next");
                break;

            case 6:
                title.setText("Sponsor");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.VISIBLE);
                required_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.GONE);
                submit.setText("Next");

                break;

            case 7:
                title.setText("Identification & Uploads");
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                sponsor_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.VISIBLE);
                success_msg.setVisibility(View.GONE);
                submit.setText("Submit");

                break;

            default:
        }
    }

    private boolean checkPermission() {

        boolean a= ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        boolean b=ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        boolean c=ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED;

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_PHONE_STATE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission is neccessary");
                alertBuilder.setMessage("Permission to device storage is necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AcctSetUpActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE,READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(AcctSetUpActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE,READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        } else {
           openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }

    private int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if(DOBmonth > currentMonth) {
            --age;
        } else if(DOBmonth == currentMonth) {
            if(DOBday > todayDay){
                --age;
            }
        }
        return age;
    }

    private void checkIfCustomerIsMinor(String dob){
        String dateArray[]=dob.split(",");
        String yr=dateArray[dateArray.length-1];

        isMinor=false;

        /*
        if(getAge(Integer.parseInt(yr.trim()),1,1)>18){
            isMinor=false;
        }else {
            isMinor=true;
        }
        */
    }

    private void getParameter(){
        repo.getCreateAccountParameter(new OnApiResponse<CreateAccountParameter>() {
            @Override
            public void onSuccess(CreateAccountParameter data) {
                populateView(data);
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    void populateView(CreateAccountParameter resp){
        ArrayAdapter country_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, resp.getCountry());
        country.setAdapter(country_adapter);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String contry= country.getSelectedItem().toString().trim();
                if(contry.equals("Nigeria")){
                    state_layout.setVisibility(View.VISIBLE);

                    ArrayAdapter state_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, resp.getState());
                    state_country.setAdapter(state_adapter);


                }else{
                    state_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

      //  ArrayAdapter idType_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, resp.getIdType());
      //  idType.setAdapter(idType_adapter);

        ArrayAdapter bank_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, resp.getBank());
        bankName.setAdapter(bank_adapter);

        ArrayAdapter sponsor_country_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, resp.getCountry());
        sponspor_country.setAdapter(sponsor_country_adapter);


        ArrayAdapter validIDs_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, resp.getCountry());
        sponspor_country.setAdapter(sponsor_country_adapter);


    }

    private void completeAcctCreation(){
        CreateAccount req=new CreateAccount();
        req.setAccType("1");
        req.setTitle(user_titleVal);
        req.setGender(genderVal);
        req.setSurname(surnameVal);
        req.setNextOfKinAddress(nextOfKinAddress);
        req.setOtherNames(other_nameVal);
        req.setFirstName(fnameVal);
        req.setEmail(custEmail);
        req.setDateOfBirth(DOB);
        req.setResidentialAddress(custResidential_address);
        req.setCountry(custCountry);
        req.setCity(custCity);
        req.setLga(csutLga);
        req.setPhone(custPhno);
        req.setIdType(idVal);
        req.setIdNumber(identifNumberVal);
        req.setDateOfExpiryVal(dateOfExpiryVal);

        req.setAcctName(acct_nameVal);
        req.setBankAcctNumber(acct_numberVal);
        req.setBankName(bankNameVal);
        req.setBvn(bvnVal);
        req.setCustAid(SharedPref.getUSERID(context));
        req.setReInvest(reInvest);
        req.setNextOfKinName(fullnameVal);
        req.setNextOfKinRelationship(relationshipVal);
        req.setNextOfKinPhone(nextofkin_phonenumberVal);
        req.setNextOfKinEmail(nextofkin_emailVal);

        idVal= idSpinner.getSelectedItem().toString().trim();
        identifNumberVal= identifNumber.getText().toString().trim();
        dateOfExpiryVal= dateOfExpiry.getText().toString().trim();

        if(isMinor) {
            req.setSponsorCountry(sponspor_countryVal);
            req.setSponsorPhone(sponspor_phoneVal);
            req.setSponsorEmail(sponspor_emailVal);
            req.setSponsorFirstName(sponspor_fnameVal);
            req.setSponsorSurname(sponspor_surnameVal);
            req.setSponsorOtherNames(sponspor_other_nameVal);
            req.setSponsorResidentialAddr(sponspor_residential_addressVal);
            req.setSponsorCountry(sponspor_countryVal);

        }
        sendCompleteAccountCreation(req);
    }

    private void sendCompleteAccountCreation(CreateAccount req){

        repo.CompleteAccountCreationByCustomer(Constant.APPID,req, new OnApiResponse<String>() {
            @Override
            public void onSuccess(String data) {

                uploadKYCDoc(data);
            }

            @Override
            public void onFailed(String message) {
            progress.dismiss();
            Utility.alertOnly(context,message,"");
            }
        });

    }

    private void uploadKYCDoc(String kycID){
        List<MultipartBody.Part> files=new ArrayList<>();
        // create part for file (photo, video, ...)
        MultipartBody.Part passport = prepareFilePart("passport", passPortUri);
        files.add(passport);
        // create part for file (photo, video, ...)
        MultipartBody.Part validID = prepareFilePart("validID", validIDuri);
        files.add(validID);

        MultipartBody.Part proofAddr = prepareFilePart("validID", proofAddressUri);
        files.add(proofAddr);

        // create part for file (photo, video, ...)
        MultipartBody.Part signature = prepareFilePart("signature", signatureUri);
        files.add(signature);
/*
        if(isMinor) {
            // create part for file (photo, video, ...)
            MultipartBody.Part birthCertificate = prepareFilePart("birthCertif", birthuri);
            files.add(birthCertificate);

            // create part for file (photo, video, ...)
            MultipartBody.Part sponsorU = prepareFilePart("SponsorPassport", sponsorUri);
            files.add(sponsorU);
        }
        */

        repo.kycUploads(Constant.APPID, kycID, SharedPref.getUSERID(context),files, new OnApiResponse<WebResponse<String>>() {
            @Override
            public void onSuccess(WebResponse<String> data) {
                progress.dismiss();

                title.setVisibility(View.GONE);
                personal_detail_layout.setVisibility(View.GONE);
                bank_detail_layout.setVisibility(View.GONE);
                identification_layout.setVisibility(View.GONE);
                next_kin_layout.setVisibility(View.GONE);
                required_layout.setVisibility(View.GONE);
                success_msg.setVisibility(View.VISIBLE);
                success_checkbox.setChecked(true);
                sponsor_layout.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(context,LoginActivity.class));
                    }
                },5000);
            }

            @Override
            public void onFailed(String message) {
            progress.dismiss();
            Utility.alertOnly(context,message,"");
            }
        });

    }

    private void getValidIDs(){
     repo.getValidIDForm(new OnApiResponse<List<String>>() {
         @Override
         public void onSuccess(List<String> data) {
             listOfValidIDs=data;

             ArrayAdapter validIDs_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listOfValidIDs);
             idSpinner.setAdapter(validIDs_adapter);
         }

         @Override
         public void onFailed(String message) {

             ArrayAdapter validIDs_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listOfValidIDs);
             idSpinner.setAdapter(validIDs_adapter);

         }
     });
    }



    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtilx to get the actual file by uri
       File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

}