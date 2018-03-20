package com.parse.starter.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.parse.starter.R;

public class WebViewArtistaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String postUrl;
    private WebView webView;
    private ProgressBar progressBar;
    private String artistaNome, linkURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_artista);

        final Intent intent = getIntent();
        artistaNome = intent.getStringExtra("artista");
        linkURL = intent.getStringExtra("link");

        //configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_webView);
        toolbar.setTitle(artistaNome);
        toolbar.setTitleTextColor(R.color.preta);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);


        setSupportActionBar(toolbar);


        webView = (WebView) findViewById(R.id.webView);
        // initializing toolbar


        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(linkURL);

       /* webView.setWebViewClient(new WebViewClient(){
                @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){

                    return true;
                }
        });
*/

        webView.setHorizontalScrollBarEnabled(false);
    }


}
