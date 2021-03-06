package com.example.thorm.test4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutScreen extends AppCompatActivity {

    static int parentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("About");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.order).setVisible(false);
        menu.findItem(R.id.about).setVisible(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (parentActivity == 0)
                    startActivity(new Intent(this, EmployeeScreen.class));
                else
                    startActivity(new Intent(this, ShiftScreen.class));
                return true;
            case R.id.signout:
                startActivity(new Intent(this, LogInScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (parentActivity == 0)
            startActivity(new Intent(this, EmployeeScreen.class));
        else
            startActivity(new Intent(this, ShiftScreen.class));
    }
}
