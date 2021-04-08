package com.example.merchantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class splashScreen extends AppCompatActivity {

    ImageView logo;

    private SharedPreferences sharedPreferences;
    public static final String preference_name = "userSession";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initWidget();
        logo.animate().rotation(360).setDuration(2000);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences(preference_name,0);
                String getEmail = sharedPreferences.getString("userMail","");
                String getPassword = sharedPreferences.getString("userpassword","");
                String getName = sharedPreferences.getString("userName","");
                String getPhone = sharedPreferences.getString("userPhone","");

                Intent intent;
                if(getEmail.equals("") && getPassword.equals(""))
                {
                    intent = new Intent(splashScreen.this,loginActivity.class);
                }
                else
                {
                    intent = new Intent(splashScreen.this,MainActivity.class);
                    intent.putExtra("userName",getName);
                    intent.putExtra("userMail",getEmail);
                    intent.putExtra("userPhone",getPhone);
                }
                startActivity(intent);
                finish();
            }
        },4000);


    }

    private void initWidget() {

        logo = findViewById(R.id.logoImg);
    }
}