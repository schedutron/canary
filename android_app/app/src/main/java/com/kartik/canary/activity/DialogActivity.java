package com.kartik.canary.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.kartik.canary.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_dialog);

        setFinishOnTouchOutside(false);

        Intent i = getIntent();
        String title = i.getStringExtra("title");

        if(title != null && !title.equals("")) {
            setTitle(title);
        }
    }
}
