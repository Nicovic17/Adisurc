package com.example.nico.adisurc;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SegrepassData extends AppCompatActivity {

    Button button;
    String nome;
    String password;
    EditText etxt, etxt2;

    TextInputLayout layNome, layPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segrepass_data);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            Toast.makeText(this,"Size max",Toast.LENGTH_LONG).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else
        {
            Toast.makeText(this,"Size not max",Toast.LENGTH_LONG).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if(Build.VERSION.SDK_INT>=21)
        {
            Window window=getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.segrepass));
        }

        final SharedPreferences settings = getSharedPreferences("segrepassData", 0);
        boolean firstRun = settings.getBoolean("firstRun", false);

        //boolean firstRun1=false; //Da eliminare

        if (firstRun == false) {

            AlertDialog.Builder builder = new AlertDialog.Builder(SegrepassData.this);
            builder.setMessage("Inserisci i tuoi dati di accesso Segrepass").setPositiveButton("Chiudi", null)
                    .create()
                    .show();

            button = (Button) findViewById(R.id.btnSalva);
            etxt = (EditText) findViewById(R.id.etxt);
            etxt2 = (EditText) findViewById(R.id.etxt2);
            layNome = (TextInputLayout) findViewById(R.id.layoutetName);
            layPass= (TextInputLayout)findViewById(R.id.layoutetPassword);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    nome = etxt.getText().toString();
                    password = etxt2.getText().toString();

                    if (TextUtils.isEmpty(etxt.getText().toString()) || TextUtils.isEmpty(etxt2.getText().toString())) {

                        Toast.makeText(SegrepassData.this,"Compila tutti i campi",Toast.LENGTH_LONG).show();
                        if(TextUtils.isEmpty(etxt.getText().toString()))//Nome
                        {
                            layNome.setErrorEnabled(true);
                            layNome.setError("Inserisci username");
                        }

                        if(TextUtils.isEmpty(etxt2.getText().toString()))//Password
                        {
                            layPass.setErrorEnabled(true);
                            layPass.setError("Inserisci password");
                        }


                    } else {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("firstRun", true);
                        editor.commit();

                        SharedPreferences.Editor editor2 = settings.edit();
                        editor2.putString("nomeSegrepass", nome);
                        editor2.putString("passwordSegrepass", password);
                        editor2.commit();

                        Intent intent = new Intent(SegrepassData.this, ForumData.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        } else {
            Intent intent = new Intent(SegrepassData.this, ForumData.class);
            startActivity(intent);
            finish();
        }


    }
}
