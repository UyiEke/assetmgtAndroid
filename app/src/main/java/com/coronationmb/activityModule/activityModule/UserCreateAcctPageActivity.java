package com.coronationmb.activityModule.activityModule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coronationmb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCreateAcctPageActivity extends AppCompatActivity {

    Context context;

    @BindView(R.id.individual)
    Button individual;

    @BindView(R.id.joint)
    Button joint;

    @BindView(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acct);
        ButterKnife.bind(this);
        this.context = UserCreateAcctPageActivity.this;
        initUI();
    }

    public void initUI(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.individual)
    public void individualAction(){
        Intent intent=new Intent(context,SignUpActivity.class);

        intent.putExtra("type",1); // 1-individual account, 2-joint account
        startActivity(intent);
    }


    @OnClick(R.id.joint)
    public void jointAction(){
        Intent intent=new Intent(context,SignUpActivity.class);

        intent.putExtra("type",2); // 1-individual account, 2-joint account
        startActivity(intent);
    }

    @OnClick(R.id.login)
    public void loginAction(){

        startActivity(new Intent(context,LoginActivity.class));

    }


}
