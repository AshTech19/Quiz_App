package com.project.quizapp1.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.project.quizapp1.MainActivity;
import com.project.quizapp1.R;
import com.project.quizapp1.auth.fragment.LoginFragment;
import com.project.quizapp1.auth.fragment.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

public class Authentication extends AppCompatActivity {
    private static final String[] pageTitle = {"REGISTER", "LOG IN"};

    public static void start(Context context){
        Intent intent = new Intent(context,Authentication.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("");
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout_form);
        ViewPager viewPager = findViewById(R.id.viewpager_form);
        AuthenticationAdapter adapter = new AuthenticationAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class AuthenticationAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public AuthenticationAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            fragments.add(new RegisterFragment());
            fragments.add(new LoginFragment());
        }
        @Override
        public Fragment getItem(int position){
            return fragments.get(position);
        }

        @Override
        public int getCount(){
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int arrayPos){
            return pageTitle[arrayPos];
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        checkForLogin();
    }

    private void checkForLogin(){
        final SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        final boolean logged_in = sharedPreferences.getBoolean("LOGGED_IN",false);

        if (logged_in){
            startMainActivity();
        }
    }

    private void startMainActivity() {
        MainActivity.start(Authentication.this);
        finish();
    }
}
