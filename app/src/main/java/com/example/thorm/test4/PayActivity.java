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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        final Spinner s = findViewById(R.id.jobSelectionBT);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this, R.layout.text_view, jobs);
        s.setAdapter(sAdapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = jobs.indexOf(s.getSelectedItem().toString());
                shiftsLV.setAdapter(jobsAdapterList.get(pos));
                activeJob = pos;
                for (EmployeeShift sh : jobsAdapterList.get(pos).shifts) {
                    if (!sh.isSaved())
                        jobsAdapterList.get(activeJob).shifts.remove(sh);
                    else
                        sh.setVisible(false);
                }
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
                    Snackbar.make(view, "Data format error in "+currentEmployee.getName()+", "+jobs.get(activeJob), Snackbar.LENGTH_LONG)
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

            Date dateObj;
            try { dateObj = new SimpleDateFormat("M/d/y").parse(dateStr); }
            catch (ParseException e) { return false; }

            if (currentEmployee.jobs.get(activeJob).getUnit() == null) {
                String match = "\\s*([1-9]|10|11|12)(\\:[0-5][0-9]\\s*(am|pm|AM|PM|a\\.m\\.|p\\.m\\.|A\\.M\\.|P\\.M\\.))?\\s*";
                if (!startTimeStr.matches(match) || !endTimeStr.matches(match))
                    return false;
            }
            else {
                if (Double.parseDouble(amountStr) <= 0)
                    return false;
            }

            s.setDate(dateObj);
            s.setDay(days.indexOfChild(days.findViewById(days.getCheckedRadioButtonId())));
            s.setStartTime(startTimeStr);
            s.setEndTime(endTimeStr);
            s.setAmount(amountStr);
            s.setSaved(!s.isToDelete());

            index++;
        }
        return true;
    }

    public void deleteShift(View view) {
        ViewGroup parent = (ViewGroup) view.getParent().getParent();
        parent.setVisibility(View.GONE);

        ListView list = (ListView) parent.getParent().getParent();
        int index = list.indexOfChild((View) parent.getParent());
        jobsAdapterList.get(activeJob).shifts.get(index).setToDelete(true);
    }

    /*
    public void dateClick(View view) {
        final EditText e = (EditText) view;
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                Date d = null;
                SimpleDateFormat f = new SimpleDateFormat("M/d/y");
                try { d = f.parse(e.getText().toString()); }
                catch (ParseException e) {}
                if (d != null) {
                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.setTime(d);
                    RadioGroup days = ((ViewGroup) e.getParent()).findViewById(R.id.daygroup);
                    days.check(java.util.Calendar.DAY_OF_WEEK);
                }
            }
        });
    }

    public void dayClick(View view) {
        RadioGroup days = (RadioGroup) view.getParent();
        int index = days.indexOfChild(days.findViewById(days.getCheckedRadioButtonId()));

        EditText dateText = ((ViewGroup) days.getParent()).findViewById(R.id.date);
        Date d = null;
        SimpleDateFormat f = new SimpleDateFormat("M/d/y");
        try { d = f.parse(dateText.getText().toString()); }
        catch (ParseException e) {}
        if (d != null) {
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(d);
            c.set(java.util.Calendar.DAY_OF_WEEK, index+1);
            dateText.setText(f.format(c));
        }
    }
    */

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
