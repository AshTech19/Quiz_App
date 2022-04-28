package com.project.quizapp1.auth.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.project.quizapp1.MainActivity;
import com.project.quizapp1.R;

public class RegisterFragment extends Fragment {

    private EditText username, email, password, confirm_password, security;
    private Button registerBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.register_fragment, container, false);
        init(view);
        registerBtn.setOnClickListener(v -> validate());


        return view;
    }


    private void validate(){
        String user_name = username.getText().toString();
        String e_mail = email.getText().toString();
        String pass_word = password.getText().toString();
        String confirm_pass = confirm_password.getText().toString();
        String security_code = security.getText().toString();

        if(!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(e_mail) && !TextUtils.isEmpty(pass_word) && !TextUtils.isEmpty(security_code)){
            if(e_mail.contains("@")){
                if(!user_name.contains(" ")){
                    if(pass_word.equals(confirm_pass)){
                        startRegistration(user_name, e_mail, pass_word, security_code);
                    } else {
                        Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Username must not contain space", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Email not valid", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Please check the fields and type again", Toast.LENGTH_LONG).show();
        }
    }

    private void startRegistration(String user_name,String e_mail, String pass_word, String security_code){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERNAME", user_name);
        editor.putString("EMAIL", e_mail);
        editor.putString("PASSWORD", pass_word);
        editor.putString("SECURITY CODE", security_code);
        editor.putBoolean("LOGGED_IN", true);
        editor.apply();
        startMainActivity();
    }
    private void startMainActivity(){
        MainActivity.start(getActivity());
        getActivity().finish();
    }

    private void init(View view){
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirm_password = view.findViewById(R.id.confirm_password);
        security = view.findViewById(R.id.security);
        registerBtn = view.findViewById(R.id.btnRegister);
    }
}
