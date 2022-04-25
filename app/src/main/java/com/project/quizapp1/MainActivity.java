package com.project.quizapp1;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.project.quizapp1.auth.Authentication;

public class MainActivity extends AppCompatActivity {
    public static void start(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private Button btnCreateQuiz, btnStartQuiz, btnLogout;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        btnCreateQuiz.setOnClickListener(v -> {
            createQuiz();
            finish();
        });

        btnStartQuiz.setOnClickListener(v -> {
            startQuiz();
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            sp.edit().putBoolean("LOGGED_IN",false).apply();
            startLogin();
            finish();
        });
    }

    private void createQuiz(){

        QuizCreation.start(this);
    }

    private void startQuiz(){
        QuizActivity.start(this);
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        btnLogout = findViewById(R.id.logout);
        btnCreateQuiz = findViewById(R.id.createQuiz);
        btnStartQuiz = findViewById(R.id.startQuiz);
        setSupportActionBar(toolbar);
        setSystemBarColor(this, R.color.colorPrimary);

        sp = getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);
        String username = sp.getString("USERNAME", null);
        if(username != null){
            toolbar.setTitle("Welcome " + username);
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        Window window = act.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(act.getResources().getColor(color));
    }

    private void startLogin(){
        Authentication.start(this);
    }
}