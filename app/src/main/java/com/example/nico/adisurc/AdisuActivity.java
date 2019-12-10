package com.example.nico.adisurc;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;

public class AdisuActivity extends AppCompatActivity {

    WebView wv;

    private String nome;

    //**********************************************************************************************



    @Override
    public void onBackPressed(){


        if(wv.canGoBack())
        {
            wv.goBack();
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nome=getIntent().getStringExtra("nome");

        //****************JAVASCRIPT**********************

        final String js="javascript: function E() { f0=document.forms[0]; f0['q'].value='"+nome+"';}E();";

        //************************************************


        wv=new WebView(this);
        wv.setWebChromeClient(new WebChromeClient(){
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
        wv.setWebViewClient(new WebViewClient(){

            //****Per i certificati SSL self-signed*************************
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            //**************************************************************


            //********************JAVASCRIPT********************************
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(Build.VERSION.SDK_INT>19)
                {

                    view.evaluateJavascript(js, null);

                }


            }

            //****************************************************************
        });


        //*******************************************************************

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setInitialScale(1);
        wv.getSettings().setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDomStorageEnabled(true);
        wv.loadUrl("https://adisurc-ol.dirittoallostudio.it/apps/V3.1/sol/public/");

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

      //  wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        /*String username="RMMSTN97C03M289N";
        String password="Lcdshop97";*/

       /* final String js="javascript: function E() {setTimeout(function(){ var divs=document.querySelectorAll('.btn-primary'); divs[1].click();},4000);" +
                "setTimeout(function(){alert('Compilo..'); " +
                "f0=document.forms[0];" +
                "f0['p_username'].value='"+username+"';" +
                "f0['p_password'].value='"+password+"'; },6000);}E()";*/

       /* if(Build.VERSION.SDK_INT>19)
            wv.loadUrl("https://adisurc-ol.dirittoallostudio.it/apps/V3.1/sol/public/");*/


        /*wv.setWebViewClient(new WebViewClient(){

            public void onPageFinished(WebView view, String url){

                super.onPageFinished(view,url);

                /*if(Build.VERSION.SDK_INT>19)
                {

                    view.evaluateJavascript(js, null);

                }

            }



        });*/
    }

