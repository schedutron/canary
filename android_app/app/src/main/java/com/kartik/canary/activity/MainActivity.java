package com.kartik.canary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.kartik.canary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbarDashboard) Toolbar toolbar;
    @OnClick(R.id.logout_button) void logOut() {
        FirebaseAuth instance = FirebaseAuth.getInstance();
        if(instance != null) {
            instance.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);



    }
}
