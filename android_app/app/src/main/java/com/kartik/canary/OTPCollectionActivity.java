package com.kartik.canary;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.kartik.canary.activity.DialogActivity;
import com.kartik.canary.activity.LoginActivity;
import com.kartik.canary.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OTPCollectionActivity extends AppCompatActivity {

    URL url;
    HttpURLConnection urlConnection;

    @BindView(R.id.otp_text) EditText OTPText;
    @OnClick(R.id.confirmOTP) void confirmOTP() {
        String s = String.valueOf(OTPText.getText());
        if(s.length() != 4) {
            OTPText.setError("Enter valid OTP");
        } else {
            showProgressDialog();
            new CheckOTP(this).execute(getString(R.string.base_url) + "/register/verify?otp=" + s);
        }
    }

    public void showProgressDialog() {
        startActivity(new Intent(getApplicationContext(), DialogActivity.class));
    }

    public void hideProgressDialogAndRedirect(Class mClass) {
        startActivity(new Intent(getApplicationContext(), mClass)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_collection);

        ButterKnife.bind(this);


    }

    @Override
    public void onBackPressed() {

    }

    private class CheckOTP extends AsyncTask<String, Void, String> {

        private Context mContext;
        private CheckOTP(Context mContext) {
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
                hideProgressDialogAndRedirect(OTPCollectionActivity.class);
            } else {
                try {
                    JSONObject obj = new JSONObject(s);
                    if(obj.getString("status").equals("ok")) {
                        hideProgressDialogAndRedirect(MainActivity.class);
                    } else {
                        Toast.makeText(mContext, obj.getString("reason"), Toast.LENGTH_SHORT).show();
                        hideProgressDialogAndRedirect(LoginActivity.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
