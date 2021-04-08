package com.example.merchantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class loginActivity extends AppCompatActivity {

    EditText editText1, editText2;
    Button btnLogin;

    private SharedPreferences sharedPreferences;
    public static final String preference_name = "userSession";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();
        validations();
        actionListeners();
    }

    private void actionListeners() {

        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validations();
            }
        });

        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validations();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = editText1.getText().toString();
                String password = editText2.getText().toString();
                if(validations())
                {
                    authentication(userName,password);
                }
            }
        });
    }

    private boolean validations() {

        String userName = editText1.getText().toString();
        String password = editText2.getText().toString();

        String userNameValidation1 = "^[a-zA-Z]*$";
        String userNameValidation2 = "^[0-9]{1,11}$";
        String  userNameValidation3 = "[a-z]*@[a-z]*.[a-z]*$";

        Pattern pattern1 = Pattern.compile(userNameValidation1);
        Pattern pattern2 = Pattern.compile(userNameValidation2);
        Pattern pattern3 = Pattern.compile(userNameValidation3);

        Matcher matcher1 = pattern1.matcher(userName);
        Matcher matcher2 = pattern2.matcher(userName);
        Matcher matcher3 = pattern3.matcher(userName);

        if(userName.length() == 0 && password.length() != 0) {
            editText1.setError("Kindly fill in this field");
            return false;
        }
        else if(password.length() == 0 && userName.length() != 0) {
            editText2.setError("Kindly fill in this field");
            return false;
        }
        else if(userName.length() == 0 && password.length() == 0)
        {
            editText1.setError("Kindly fill in this field");
            editText2.setError("Kindly fill in this field");
            return false;
        }
        else if(!matcher1.matches())
        {
            if(!matcher2.matches())
            {
                if(!matcher3.matches())
                {
                    editText1.setError("Invalid input");
                    return false;
                }
            }
        }

        return true;
    }

    public void showHidePassword(View view)
    {
        if(view.getId()==R.id.showpassword){

            if(editText2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){

                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Show Password
                editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);

                //Hide Password
                editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void authentication(String userName, String password) {

        RequestToLogin requestToLogin = new RequestToLogin();
        requestToLogin.setUserName(userName);
        requestToLogin.setPassword(password);

        Call<ResponseOfLogin> loginResponseCall = Instantiation.generateCallToAPI().userLogin(requestToLogin);
        loginResponseCall.enqueue(new Callback<ResponseOfLogin>() {
            @Override
            public void onResponse(Call<ResponseOfLogin> call, Response<ResponseOfLogin> response) {

                if(response.isSuccessful())
                {
                    Toast.makeText(loginActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    String userName1 = response.body().getUserNameInfo();
                    String userPhone1 = response.body().getUserContactInfo();
                    String userMail1 = response.body().getUserMailInfo();

                    sharedPreferences = getSharedPreferences(preference_name,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userMail",userName);
                    editor.putString("userpassword",password);
                    editor.putString("userName",userName1);
                    editor.putString("userPhone",userPhone1);
                    editor.apply();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(loginActivity.this,MainActivity.class);
                            intent.putExtra("userName",userName1);
                            intent.putExtra("userPhone",userPhone1);
                            intent.putExtra("userMail",userMail1);
                            startActivity(intent);
                        }
                    },700);
                }
                else{
                    Toast.makeText(loginActivity.this,"Failed to Login",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseOfLogin> call, Throwable t) {

                Toast.makeText(loginActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initWidget() {

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        btnLogin = findViewById(R.id.btnLogin);
    }
}