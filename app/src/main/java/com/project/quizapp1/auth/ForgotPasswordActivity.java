package com.project.quizapp1.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.quizapp1.MainActivity;
import com.project.quizapp1.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    public static void start(Context context){
        Intent intent = new Intent(context,ForgotPasswordActivity.class);
        context.startActivity(intent);
    }
    private EditText email, security_code,new_password;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        sharedPreferences = this.getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        final Boolean logged_in = sharedPreferences.getBoolean("LOGGED_IN",false);
        final String required_email = sharedPreferences.getString("EMAIL","DEFAULT_EMAIL");
        final String required_security_code = sharedPreferences.getString("SECURITY CODE", "DEFAULT_CODE");


        email = findViewById(R.id.email);
        security_code = findViewById(R.id.security);
        new_password = findViewById(R.id.new_password);
        Button resetBtn = findViewById(R.id.btnReset);

        resetBtn.setOnClickListener(v -> validate(required_email,required_security_code));
    }

    private void validate(String required_email, String required_security_code){
        String e_mail = email.getText().toString();
        String security = security_code.getText().toString();
        String update_password = new_password.getText().toString();

        if(!TextUtils.isEmpty(e_mail) && !TextUtils.isEmpty(security) && !TextUtils.isEmpty(update_password)){
            if(e_mail.contains("@")){
                resetPassword(e_mail,security,required_email,required_security_code,update_password);
            } else {
                Toast.makeText(this, "Please enter the correct Email", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please check the fields", Toast.LENGTH_LONG).show();
        }
    }

    private void resetPassword(String e_mail, String security, String required_email, String required_security_code,String update_password){
        if(e_mail.equals(required_email) && security.equals(required_security_code)){
            sharedPreferences.edit().putBoolean("LOGGED_IN", false).apply();
            sharedPreferences.edit().putString("PASSWORD", update_password).apply();
            startActivity();
        } else {
            Toast.makeText(this,"Email Address and Security Code do not match",Toast.LENGTH_LONG).show();
        }
    }

    private void startActivity(){
        Authentication.start(this);
        this.finish();
    }
}
