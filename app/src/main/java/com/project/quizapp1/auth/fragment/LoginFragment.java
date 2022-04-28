package com.project.quizapp1.auth.fragment;

import static android.content.Context.MODE_PRIVATE;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.project.quizapp1.MainActivity;
import com.project.quizapp1.R;
import com.project.quizapp1.auth.ForgotPasswordActivity;

public class LoginFragment extends Fragment {
    private EditText email, password;
    private TextView reset_pw;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.login_fragment, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        final Boolean logged_in = sharedPreferences.getBoolean("LOGGED_IN",false);
        final String required_username = sharedPreferences.getString("USERNAME", "DEFAULT_USERNAME");
        final String required_email = sharedPreferences.getString("EMAIL","DEFAULT_EMAIL");
        final String required_password = sharedPreferences.getString("PASSWORD","DEFAULT_PASSWORD");

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        Button loginBtn = view.findViewById(R.id.btnLogin);
        TextView reset_pw = view.findViewById(R.id.reset);

        reset_pw.setOnClickListener(v-> forgot_password());
        loginBtn.setOnClickListener(v -> validate(required_username,required_email,required_password));
        return view;
    }

    private void forgot_password(){
        ForgotPasswordActivity.start(getActivity());
    }

    private void validate(String required_username,String required_email, String required_password){
        String e_mail = email.getText().toString();
        String pass_word = password.getText().toString();

        if(!TextUtils.isEmpty(e_mail) && !TextUtils.isEmpty(pass_word)){
            if(e_mail.contains("@")){
                isEmailLogin(e_mail,pass_word,required_email,required_password);
            } else {
                isUsernameLogin(e_mail,pass_word,required_username,required_password);
            }
        } else {
            Toast.makeText(getActivity(), "Please check the fields", Toast.LENGTH_LONG).show();
        }
    }

    private void isUsernameLogin(String user_name, String pass_word, String required_username, String required_password){
        if(user_name.equals(required_username) && pass_word.equals(required_password)){
            sharedPreferences.edit().putBoolean("LOGGED_IN", false).apply();
            startMainActivity();
        } else {
            Toast.makeText(getActivity(),"Username or password is incorrect",Toast.LENGTH_LONG).show();
        }
    }

    private void isEmailLogin(String e_mail, String pass_word, String required_email, String required_password){
        if(e_mail.equals(required_email) && pass_word.equals(required_password)){
            sharedPreferences.edit().putBoolean("LOGGED_IN", false).apply();
            startMainActivity();
        } else {
            Toast.makeText(getActivity(),"Email Address or password is incorrect",Toast.LENGTH_LONG).show();
        }
    }

    private void startMainActivity(){
        MainActivity.start(getActivity());
        getActivity().finish();
    }

}
