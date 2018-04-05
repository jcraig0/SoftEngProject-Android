package com.example.thorm.test4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Employer.createEmployerList();

        final Intent intent = new Intent(this, Test4_2.class);

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userName = (EditText) findViewById(R.id.usernameET);
                EditText password = (EditText) findViewById(R.id.passwordET);
                EditText companyCode = (EditText) findViewById(R.id.passwordET);

                startActivity(intent);
            }
        });
    }
}
