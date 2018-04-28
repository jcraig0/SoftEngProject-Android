package com.example.thorm.test4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, EmployeeScreen.class);

        final String username = "1", password = "2";
        findViewById(R.id.oops).setVisibility(View.INVISIBLE);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = ((EditText) findViewById(R.id.usernameET)).getText().toString();
                String passwordText = ((EditText) findViewById(R.id.passwordET)).getText().toString();

                if (usernameText.equals(username) && passwordText.equals(password)) {
                    Employer.createEmployerList();
                    startActivity(intent);
                    findViewById(R.id.oops).setVisibility(View.INVISIBLE);
                }
                else
                    findViewById(R.id.oops).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {}

}
