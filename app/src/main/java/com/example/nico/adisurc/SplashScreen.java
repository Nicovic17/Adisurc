package com.example.nico.adisurc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo;
        Animation animation;

        if(Build.VERSION.SDK_INT>=21)
        {
            Window window=getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.unina));
        }


        logo=(ImageView)findViewById(R.id.imageSplash);

        animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animation.setAnimationListener(this);

        logo.setVisibility(View.VISIBLE);
        logo.startAnimation(animation);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SplashScreen.this, InserimentoDatiGenerali.class);
                SplashScreen.this.startActivity(intent);
                finish();

            }
        });

        Thread timer=new Thread()
        {
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent=new Intent(SplashScreen.this,InserimentoDatiGenerali.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animatione) {



    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
