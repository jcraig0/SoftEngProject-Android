package com.example.thorm.test4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Employer.createEmployerList();

        final Intent intent = new Intent(this, Test4_2.class);

        final String username = "1", password = "2", companycode = "3";
        findViewById(R.id.oops).setVisibility(View.INVISIBLE);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameText = ((EditText) findViewById(R.id.usernameET)).getText().toString();
                String passwordText = ((EditText) findViewById(R.id.passwordET)).getText().toString();
                String companycodeText = ((EditText) findViewById(R.id.companyNameET)).getText().toString();

                if (usernameText.equals(username) && passwordText.equals(password) && companycodeText.equals(companycode)) {
                    startActivity(intent);
                    findViewById(R.id.oops).setVisibility(View.INVISIBLE);
                }
                else
                    findViewById(R.id.oops).setVisibility(View.VISIBLE);

                /*
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, "localhost/server/public/authenticate", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        userName.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userName.setText(error.toString());
                    }
                });

                queue.add(jsonObjectRequest);
                */
            }
        });
    }
}
