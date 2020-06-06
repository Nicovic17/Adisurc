package com.example.nico.adisurc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //************DRAWER*****************************
    private DrawerLayout drawerLayout;
    //***********************************************

    //*************GRID VIEW IMAGE*******************
    GridView androidGridView;

    Integer[] imageIDs = {
            R.drawable.adisu, R.drawable.segrepasslogo, R.drawable.forum, R.drawable.docenti, R.drawable.mail
    };
    //***********************************************

    //Button adisu,segrepass,forum,docenti,salva,mail;
    Button elimina;
    TextView txt; //Mostra nome + password
    //EditText etxt, etxt2; //Ins nome + password


    //*************DATI SEGREPASS**********************

    String nomeSegrepass;
    String passwordSegrepass;

    //*************************************************

    //*************DATI FORUM**********************

    String nomeForum;
    String passwordForum;

    //*************************************************

    //*************DATI MAIL**********************

    String nomeMail;
    String passwordMail;

    //*************************************************

    //**************SHARED PREF*************************

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String PASSWORD = "password";

    private String text, password;

    //**************************************************


    /*public void saveData() {
        String nome = etxt.getText() + "";
        String password = etxt2.getText() + "";
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, nome);
        editor.putString(PASSWORD, password);

        editor.apply();
    }*/

   /* public void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        password = sharedPreferences.getString(PASSWORD, "");
    }*/

    /*public void updateViews() {

        //txt.setText(text + password);

    }*/


    //DRAWER API
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //****************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=21)
        {
            Window window=getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.unina));
        }

        //******************DRAWER************************************

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //************************************************************


        //*******************DATI SEGREPASS***************************
        SharedPreferences sharedPreferences = getSharedPreferences("datiUtente", MODE_PRIVATE);
        nomeSegrepass = sharedPreferences.getString("nomeUtente", "nomeUtente");
        passwordSegrepass = sharedPreferences.getString("passwordUtente", "passwordUtente");
        //************************************************************


        //******************DATI FORUM*********************************
        SharedPreferences sharedPreferencesForum = getSharedPreferences("forumData", MODE_PRIVATE);
        nomeForum = sharedPreferencesForum.getString("nomeUtente", "nomeUtente");
        passwordForum = sharedPreferencesForum.getString("passwordUtente", "passwordUtente");
        //*************************************************************

        //******************DATI MAIL*********************************
        SharedPreferences sharedPreferencesMail = getSharedPreferences("mailData", MODE_PRIVATE);
        nomeMail = sharedPreferencesMail.getString("nomeUtente", "nomeUtente");
        passwordMail = sharedPreferencesMail.getString("passwordUtente", "passwordUtente");
        //*************************************************************


        //******************CHECK CONNESSIONE**************************+
        if (isOnline()) {
            Toast.makeText(MainActivity.this, "Connesso", Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("NESSUNA CONNESSIONE AD INTERNET").setNegativeButton("Indietro", null)
                    .create()
                    .show();
        }
        //***************************************************************

        //******************GRID VIEW**********************************
        androidGridView = (GridView) findViewById(R.id.gridview_android_example);
        ImageAdapterGridView adapterGridView = new ImageAdapterGridView(this);
        try {
            androidGridView.setAdapter(adapterGridView);

            androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View v, int position, long id) {

                    if (position + 1 == 1) {

                        Intent intent = new Intent(MainActivity.this, AdisuActivity.class);
                        startActivity(intent);

                    } else if (position + 1 == 2) {

                        Intent intent = new Intent(MainActivity.this, Segrepass.class);
                        intent.putExtra("nome", nomeSegrepass);
                        intent.putExtra("password", passwordSegrepass);
                        startActivity(intent);

                    } else if (position + 1 == 3) {

                        Intent intent = new Intent(MainActivity.this, ForumActivity.class);
                        intent.putExtra("nome", nomeForum);
                        intent.putExtra("password", passwordForum);
                        startActivity(intent);

                    } else if (position + 1 == 4) {

                        Intent intent = new Intent(MainActivity.this, DocentiActivity.class);
                        startActivity(intent);

                    } else if (position + 1 == 5) {

                        Intent intent = new Intent(MainActivity.this, MailActivity2.class);
                        intent.putExtra("nome", nomeMail);
                        intent.putExtra("password", passwordMail);
                        startActivity(intent);

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        //*************************************************************


        /*salva=(Button)findViewById(R.id.btnSalva);
        etxt = (EditText) findViewById(R.id.etxt);
        etxt2 = (EditText) findViewById(R.id.etxt2);*/
        elimina = (Button) findViewById(R.id.btnDelete);

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("segrepassData", MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();

                SharedPreferences sharedPreferencesForum = getSharedPreferences("forumData", MODE_PRIVATE);
                sharedPreferencesForum.edit().clear().commit();

                SharedPreferences sharedPreferencesMail = getSharedPreferences("mailData", MODE_PRIVATE);
                sharedPreferencesMail.edit().clear().commit();

            }
        });


    }

    //********************DRAWER*******************************

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_adisurc:
                Intent intent = new Intent(MainActivity.this, AdisuActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_segrepass:
                Intent intentSegrepass = new Intent(MainActivity.this, Segrepass.class);
                intentSegrepass.putExtra("nome", nomeSegrepass);
                intentSegrepass.putExtra("password", passwordSegrepass);
                startActivity(intentSegrepass);
                break;

            case R.id.nav_forum:
                Intent intentF = new Intent(MainActivity.this, ForumActivity.class);
                intentF.putExtra("nome", nomeForum);
                intentF.putExtra("password", passwordForum);
                startActivity(intentF);
                break;

            case R.id.nav_docenti:
                Intent intentD = new Intent(MainActivity.this, DocentiActivity.class);
                startActivity(intentD);
                break;

            case R.id.nav_mail:
                Intent intentM = new Intent(MainActivity.this, MailActivity2.class);
                intentM.putExtra("nome", nomeMail);
                intentM.putExtra("password", passwordMail);
                startActivity(intentM);
                break;

            case R.id.nav_reset:
                SharedPreferences sharedPreferences = getSharedPreferences("segrepassData", MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();

                SharedPreferences sharedPreferencesForum = getSharedPreferences("forumData", MODE_PRIVATE);
                sharedPreferencesForum.edit().clear().commit();

                SharedPreferences sharedPreferencesMail = getSharedPreferences("mailData", MODE_PRIVATE);
                sharedPreferencesMail.edit().clear().commit();

                Toast.makeText(this,"Dati resettati. Reinserirli al prossimo accesso.",Toast.LENGTH_LONG).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    //*********************************************************
    //********************GRID VIEW*****************************
    public class ImageAdapterGridView extends BaseAdapter {

        private Context mContext;

        public ImageAdapterGridView(Context c) {
            this.mContext = c;
        }


        @Override
        public int getCount() {
            return imageIDs.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }

            mImageView.setImageResource(imageIDs[position]);

            return mImageView;
        }
    }
    //**********************************************************************************


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
