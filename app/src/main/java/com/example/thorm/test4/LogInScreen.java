package com.example.thorm.test4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;

public class LogInScreen extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ICHelper.ICH = new ICHelper(this);
        ICHelper.employeeJArray = new ICHelper.EmployeeJSONArray();
        EmployeeScreen.firstLoad = false;

        intent = new Intent(this, EmployeeScreen.class);
        findViewById(R.id.oops).setVisibility(View.INVISIBLE);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = ((EditText) findViewById(R.id.usernameET)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordET)).getText().toString();

                ICHelper.ICH.loginRequest(userName, password);
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute("5");
        }});
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0])*1000;
                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            JSONArray arrayValue = ICHelper.employeeJArray.getValue();
            if (arrayValue == null)
                findViewById(R.id.oops).setVisibility(View.VISIBLE);
            else {
                findViewById(R.id.oops).setVisibility(View.INVISIBLE);
                Employer.createEmployerList(arrayValue);
                startActivity(intent);
            }
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LogInScreen.this,
                    "Log-In Attempt",
                    "Logging in. Please wait...");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            Log.d("WaitTime" ,text[0]);

        }
    }

    @Override
    public void onBackPressed() {}

}

