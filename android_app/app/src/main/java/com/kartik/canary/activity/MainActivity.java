package com.kartik.canary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.kartik.canary.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    URL url;
    HttpURLConnection urlConnection;

    @BindView(R.id.toolbarDashboard) Toolbar toolbar;
    @BindView(R.id.toggleSwitch) SwitchCompat toggleSwitch;

    @OnClick(R.id.logout_button) void logOut() {
        FirebaseAuth instance = FirebaseAuth.getInstance();
        if(instance != null) {
            instance.signOut();
            startActivity(new Intent(this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    switchBot(true);

                } else {
                    switchBot(false);
                }
            }
        });

    }

    public void switchBot(boolean status) {

    }

    private class SwitchBot extends AsyncTask<String, Void, String> {

        private Context mContext;
        private SwitchBot(Context mContext) {
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
            } else {
                try {
                    JSONObject obj = new JSONObject(s);
                    if(obj.getString("mode").equals("on")) {
                        Toast.makeText(mContext, obj.getString("Bot switched on"), Toast.LENGTH_SHORT).show();
                        hideProgressDialogAndRedirect(MainActivity.class);
                    } else if(obj.getString("mode").equals("off")) {
                        Toast.makeText(mContext, obj.getString("Bot switched off"), Toast.LENGTH_SHORT).show();
                        hideProgressDialogAndRedirect(MainActivity.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void showProgressDialog() {
        startActivity(new Intent(getApplicationContext(), DialogActivity.class));
    }

    public void hideProgressDialogAndRedirect(Class mClass) {
        startActivity(new Intent(getApplicationContext(), mClass)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
