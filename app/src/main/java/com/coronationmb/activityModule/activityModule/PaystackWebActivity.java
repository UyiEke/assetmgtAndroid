package com.coronationmb.activityModule.activityModule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coronationmb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class PaystackWebActivity extends BaseActivity {

    Context context;
    @BindView(R.id.paystackWebview)
    WebView webview;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

  //  @BindView(R.id.backarrow)
   // ImageView backarrow;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_paystack_web);
        ButterKnife.bind(this);
        this.context = PaystackWebActivity.this;
        url=getIntent().getStringExtra("PAYMENT_URL");
        initUI();
    }

    private void initUI(){
     //   Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        webview.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webSettings.setPluginState(WebSettings.PluginState.ON);


        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                Log.d("doUpdateVisited: ",url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


                Log.d("onPageStarted: ",url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.d("onPageFinished: ",url);

            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);


                if(url.contains("http://midware.coronationam.com")){

                    loadDashBoard();

                }

                Log.d("onLoadResource: ",url);
            }
        });


    }


    public void loadDashBoard(){
        startActivity(new Intent(PaystackWebActivity.this,DashboardActivity.class));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
