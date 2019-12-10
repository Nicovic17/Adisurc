package com.example.nico.adisurc;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MailActivity2 extends AppCompatActivity {

    WebView wv;

    private String nome,password;

    @Override
    public void onBackPressed() {


        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nome=getIntent().getStringExtra("nome");
        password=getIntent().getStringExtra("password");

        //****************JAVASCRIPT**********************

        final String js="javascript: function E() {var us=document.getElementById('IDToken1'); us.value='"+nome+"'; " +
                "var ps=document.getElementById('IDToken2'); ps.value='"+password+"';}E();";

        //************************************************

        wv = new WebView(this);
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
        });

        //****************Non apre browser esterno***************************
        wv.setWebViewClient(new WebViewClient() {

            //****Per i certificati SSL self-signed*************************
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();


            }
            //**************************************************************

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(Build.VERSION.SDK_INT>19)
                {

                    view.evaluateJavascript(js, null);

                }

            }

        });

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setInitialScale(1);
        wv.getSettings().setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDomStorageEnabled(true);
        wv.loadUrl("https://www.ssignon.unina.it/openam/UI/Login");

        //****************IMPLEMENTAZIONE DOWNLOAD*******************************
        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(s));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(),"Downloading",Toast.LENGTH_LONG).show();
            }
        });
        //***********************************************************************



        wv.setInitialScale(0);
        wv.requestFocus();
        wv.requestFocusFromTouch();
        setContentView(wv);
    }
}
