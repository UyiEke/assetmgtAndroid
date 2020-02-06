package com.coronationmb.activityModule.activityModule;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.ui.onboarding.OnboardingFragment;
import com.coronationmb.activityModule.activityModule.ui.onboarding.OnboardingFragment22;
import com.coronationmb.adapter.OnboardingViewAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnboardingActivity extends AppCompatActivity implements OnboardingFragment.OnFragmentInteractionListener,
        OnboardingFragment22.OnFragmentInteractionListener {

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tablayout)
    TabLayout tablayout;

    Context context;

    @BindView(R.id.h1)
    public TextView h1;

    @BindView(R.id.h2)
    public TextView h2;


    @BindView(R.id.login_lay)
    public LinearLayout login_lay;


    @BindView(R.id.create_acct_lay)
    public LinearLayout create_acct_lay;

    int acctTpe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        context=OnboardingActivity.this;
        ButterKnife.bind(this);

        acctTpe=1;

        initUI();
    }

    public void initUI(){
        OnboardingViewAdapter adapter=new OnboardingViewAdapter(getSupportFragmentManager(),context);
        adapter.addFragment(OnboardingFragment.newInstance(R.drawable.splash_screen_1, "The smartest way to grow your finance"),"");
        adapter.addFragment(OnboardingFragment.newInstance(R.drawable.splash_screen_2,"Grow together with a joint account"),"");
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager,true);
        tablayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        tablayout.bringToFront();

        viewpager.setOffscreenPageLimit(2);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:
                        h1.setText("The smartest way to grow");
                        h2.setText("your finances");
                        acctTpe=1;
                        break;
                    case 1:
                        h1.setText("Grow together with a joint");
                        h2.setText("account");
                        acctTpe=2;
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.login_lay,R.id.create_acct_lay})
    public void buttonAction(View view){
        int id=view.getId();

        switch (id){

            case R.id.login_lay: startActivity(new Intent(context,LoginActivity.class));
                break;

            case R.id.create_acct_lay:
                Intent intent=new Intent(context,SignUpActivity.class);
                if(acctTpe==2){
                    intent.putExtra("group",2);
                }
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
