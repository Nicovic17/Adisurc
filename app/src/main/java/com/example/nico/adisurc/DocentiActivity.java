package com.example.nico.adisurc;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DocentiActivity extends AppCompatActivity {

    WebView wv;

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

        });

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setInitialScale(1);
        wv.getSettings().setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDomStorageEnabled(true);
        wv.loadUrl("https://www.docenti.unina.it/#!/search");

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
