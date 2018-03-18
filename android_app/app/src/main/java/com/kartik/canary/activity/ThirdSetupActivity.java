package com.kartik.canary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kartik.canary.OTPCollectionActivity;
import com.kartik.canary.R;
import com.kartik.canary.SetupData;
import com.kartik.canary.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThirdSetupActivity extends AppCompatActivity {

    URL url;
    HttpURLConnection urlConnection;
    TinyDB tinyDB;

    @BindView(R.id.phoneNumber) TextInputEditText phoneNumberView;
    @OnClick(R.id.doneFAB) void done() {
        String s = String.valueOf(phoneNumberView.getText());
        if(s.length() != 10) {
            phoneNumberView.setError("Enter a valid 10 digit mobile number.");
        } else {
            SetupData.setPhoneNumber(s);
            communicateWithServer();
        }
    }
    @OnClick(R.id.toSecondSetupFAB) void toSecondSetupFAB() {
        String s = String.valueOf(phoneNumberView.getText());
        if(s.length() != 10) {
            phoneNumberView.setError("Enter a valid 10 digit mobile number.");
        } else {
            SetupData.setPhoneNumber(s);
            startActivity(new Intent(this, SecondSetupActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_setup);

        ButterKnife.bind(this);

        tinyDB = new TinyDB(this);

        if(SetupData.getPhoneNumber() != null) {
            phoneNumberView.setText(SetupData.getPhoneNumber());
        }
    }

    void communicateWithServer() {
        showProgressDialog();
        String token = tinyDB.getString("token");
        String secret = tinyDB.getString("secret");
        String s = "/register?pos_text="+SetupData.getPositiveResponse()
                +"&neg_text="+SetupData.getNegativeResponse()
                +"&location="+SetupData.getLocation()
                +"&mobile="+SetupData.getLocation()+
                "&access_token="+token+"&access_secret="+secret;
        new PushData(this).execute(getString(R.string.base_url) + s);
    }

    @Override
    public void onBackPressed() {
        toSecondSetupFAB();
    }

    public void showProgressDialog() {
        startActivity(new Intent(getApplicationContext(), DialogActivity.class));
    }

    public void hideProgressDialogAndRedirect(Class mClass) {
        startActivity(new Intent(getApplicationContext(), mClass)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    private class PushData extends AsyncTask<String, Void, String> {

        private Context mContext;
        private PushData(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                String result = "";
                int data = reader.read();
                char current;

                Log.i("Getting", "true");

                while(data != -1) {

                    current = (char) data;
                    result += current;
                    data = reader.read();

                }
                Log.i("JSON", result);
                return result;



            } catch (java.io.IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null) {
                Toast.makeText(mContext, "Request failed", Toast.LENGTH_SHORT).show();
                //TODO Verify working of snackbar
            } else {
                try {
                    JSONObject obj = new JSONObject(s);
                    if(obj.getString("status").equals("ok")) {
                        hideProgressDialogAndRedirect(OTPCollectionActivity.class);
                    } else {
                        Toast.makeText(mContext, obj.getString("reason"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}


