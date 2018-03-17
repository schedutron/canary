package com.kartik.canary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondSetupActivity extends AppCompatActivity {

    @BindView(R.id.locationEditText) AutoCompleteTextView locationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_second);

        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getArrayFromJson());
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
                String s = obj.getString("name") +", "+obj.getString("subcountry")
                        +", "+obj.getString("country");
                arrayList.add(s);
            }
            Log.i("Array", arrayList.toString());
            return arrayList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
