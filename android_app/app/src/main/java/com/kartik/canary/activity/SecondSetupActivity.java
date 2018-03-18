package com.kartik.canary.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.kartik.canary.R;
import com.kartik.canary.SetupData;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondSetupActivity extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<>();
    @BindView(R.id.locationEditText) AutoCompleteTextView locationEditText;
    @OnClick(R.id.toFirstSetupFAB) void toFirstSetupFAB() {
        String city = String.valueOf(locationEditText.getText());
        if(!list.contains(city)) {
            locationEditText.setError("Please select a city from the given options.");
        } else {
            SetupData.setLocation(city);
            startActivity(new Intent(this, FirstSetupActivity.class));
        }
    }
    @OnClick(R.id.toThirdSetupFAB) void toThirdSetupFAB() {
        String city = String.valueOf(locationEditText.getText());
        if(!list.contains(city)) {
            locationEditText.setError("Please select a city from the given options.");
        } else {
            SetupData.setLocation(city);
            startActivity(new Intent(this, ThirdSetupActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_second);

        ButterKnife.bind(this);

        if(SetupData.getLocation() != null) {
            locationEditText.setText(SetupData.getLocation());
        }

        list = getArrayFromJson();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        locationEditText.setAdapter(adapter);
    }

    public ArrayList<String> getArrayFromJson() {
        ArrayList<String> arrayList = new ArrayList<>();
        InputStream ins = getResources().openRawResource(getResources().getIdentifier("cities", "raw", getPackageName()));
        try {
            String a = IOUtils.toString(ins);
            JSONArray array = new JSONArray(a);
            for(int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String s = obj.getString("name") +","+obj.getString("subcountry")
                        +","+obj.getString("country");
                arrayList.add(s);
            }
            Log.i("Array", arrayList.toString());
            return arrayList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        toFirstSetupFAB();
    }
}
