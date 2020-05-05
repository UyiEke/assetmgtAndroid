package com.coronationmb.activityModule.activityModule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements LogOutListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);
        ((App)getApplication()).registerSessionListener(this);
        ((App)getApplication()).startUserSession();
    }

    @Override
    public void onSessionLogout() {


        BaseActivity.this.runOnUiThread(new Runnable() {
            public void run() {



                AlertDialog.Builder builder1 = new AlertDialog.Builder(BaseActivity.this);
                builder1.setMessage("Your App Session has timed out due to inactivity").setTitle("");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();


                                finish();
                                startActivity(new Intent(BaseActivity.this,LoginActivity.class));

                            }
                        });
                AlertDialog alert11 = builder1.create();

                if(!(BaseActivity.this).isFinishing()) {
                    alert11.show();
                }



            }
        });




    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        ((App)getApplication()).onUserInteraction();

    }
}
