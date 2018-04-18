package com.example.thorm.test4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.ArrayList;
import com.example.thorm.test4.EmployeeShift.*;

public class PayActivity extends AppCompatActivity {

    ListView shiftsLV;
    ArrayList<String> jobs = new ArrayList<>();
    ArrayList<ShiftArrayAdapter> jobsAdapterList = new ArrayList<>();
    final Employee currentEmployee = Employee.selectedEmployee;
    int activeJob = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(currentEmployee.getName());

        jobsAdapterList = currentEmployee.jobsAdapterList;
        for (Employee.Job j : currentEmployee.jobs) {
            jobs.add(j.getName());
            jobsAdapterList.add(new ShiftArrayAdapter(this, j));
        }

        shiftsLV = findViewById(R.id.shiftsLV);
        for (EmployeeShift s : jobsAdapterList.get(activeJob).shifts) {
            if (!s.isSaved())
                jobsAdapterList.get(activeJob).shifts.remove(s);
            else
                s.setVisible(false);
        }

        final Spinner s = findViewById(R.id.jobSelectionBT);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this, R.layout.text_view, jobs);
        s.setAdapter(sAdapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = jobs.indexOf(s.getSelectedItem().toString());
                shiftsLV.setAdapter(jobsAdapterList.get(pos));
                activeJob = pos;
                for (EmployeeShift s : jobsAdapterList.get(pos).shifts)
                    s.setVisible(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                s.setSelection(0);
            }
        });

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
                boolean write = writeShiftInfo();

                if (write)
                    Snackbar.make(view, "Changes saved to "+currentEmployee.getName()+", "+jobs.get(activeJob), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else
                    Snackbar.make(view, "Data error in "+currentEmployee.getName()+", "+jobs.get(activeJob), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

    }

    public boolean writeShiftInfo() {
        int index = 0;
        for (EmployeeShift s : jobsAdapterList.get(activeJob).shifts) {
            View child = shiftsLV.getChildAt(index);
            EditText date = child.findViewById(R.id.date);
            RadioGroup days = child.findViewById(R.id.daygroup);
            EditText startTime = child.findViewById(R.id.startTime);
            EditText endTime = child.findViewById(R.id.endTime);
            EditText amount = child.findViewById(R.id.amount);

            String dateStr = date.getText().toString();
            String startTimeStr = startTime.getText().toString();
            String endTimeStr = endTime.getText().toString();
            String amountStr = amount.getText().toString();

            /*
            if (!dateStr.matches(""))
                return false;
            if (currentEmployee.jobs.get(activeJob).getUnit() == null) {
                if (!startTimeStr.matches(""))
                    return false;
                if (!endTimeStr.matches(""))
                    return false;
            }
            else {
                if (!amountStr.matches(""))
                    return false;
            }
            */

            s.setDate(dateStr);
            s.setDay(Day.values()[days.indexOfChild(days.findViewById(days.getCheckedRadioButtonId()))]);
            s.setStartTime(startTimeStr);
            s.setEndTime(endTimeStr);
            s.setAmount(amountStr);
            s.setSaved(true);

            index++;
        }
        return true;
    }

    public void deleteShift(View view) {
        ViewGroup l = (ViewGroup) view.getParent().getParent();
        l.setVisibility(View.GONE);
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
                finish();
                return true;
            case R.id.signout:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.about:
                startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
