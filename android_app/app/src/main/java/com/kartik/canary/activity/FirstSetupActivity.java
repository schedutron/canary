package com.kartik.canary.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.kartik.canary.R;
import com.kartik.canary.SecondSetupActivity;
import com.kartik.canary.SetupData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstSetupActivity extends AppCompatActivity {

    EditText positiveResponse, negativeResponse;

    @OnClick(R.id.firstFAB) void goToNextStep() {
        SetupData.setPositiveResponse(String.valueOf(positiveResponse.getText()));
        SetupData.setNegativeResponse(String.valueOf(negativeResponse.getText()));
        Intent toSecondSetup = new Intent(this, SecondSetupActivity.class);
        startActivity(toSecondSetup);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_first);

        ButterKnife.bind(this);

        positiveResponse = findViewById(R.id.positive_response_text_view);
        negativeResponse = findViewById(R.id.negative_response_text_view);

    }
}
