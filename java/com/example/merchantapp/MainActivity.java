package com.example.merchantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView wecomeText;
    Button btnLogout;

    private SharedPreferences sharedPreferences;
    public static final String preference_name = "userSession";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userName  = intent.getStringExtra("userName");
        String userPhone = intent.getStringExtra("userPhone");
        String userMail = intent.getStringExtra("userMail");

        wecomeText = findViewById(R.id.welcomeText1);
        wecomeText.setText("Welcome "+userName+"!");

        btnLogout = findViewById(R.id.btnLogout);
        actionListeners();
    }

    private void actionListeners() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getSharedPreferences(preference_name,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.apply();

                Intent intent = new Intent(MainActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });
    }
}