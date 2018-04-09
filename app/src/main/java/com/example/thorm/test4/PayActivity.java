package com.example.thorm.test4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    ListView shiftsLV;
    ArrayList<String> jobsList = new ArrayList<>();
    ArrayList<ShiftArrayAdapter> jobsAdapterList = new ArrayList<>();

    int activeJob = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Employee currentEmployee = Employee.selectedEmployee;
        setTitle(currentEmployee.NAME);

        for (String s: currentEmployee.jobs) {
            jobsList.add(s);
            jobsAdapterList.add(new ShiftArrayAdapter(this));
        }

        final Spinner s = findViewById(R.id.jobSelectionBT);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this, R.layout.text_view, jobsList);
        s.setAdapter(sAdapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = jobsList.indexOf(s.getSelectedItem().toString());
                shiftsLV.setAdapter(jobsAdapterList.get(pos));
                activeJob = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                s.setSelection(0);
            }
        });

        shiftsLV = findViewById(R.id.shiftsLV);
        shiftsLV.setAdapter(jobsAdapterList.get(activeJob));

        Button addShift = findViewById(R.id.addShiftBT);
        addShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobsAdapterList.get(activeJob).add(new EmployeeShift());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // How to access a text view in the list
                EditText e = shiftsLV.getChildAt(0).findViewById(R.id.payAmount);

                Snackbar.make(view, "Changes saved to "+currentEmployee.NAME, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.order).setVisible(false);
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
                Intent toEmployees = new Intent(this, Test4_2.class);
                startActivity(toEmployees);
                return true;
            case R.id.signout:
                Intent toMainActivity = new Intent(this, MainActivity.class);
                startActivity(toMainActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
