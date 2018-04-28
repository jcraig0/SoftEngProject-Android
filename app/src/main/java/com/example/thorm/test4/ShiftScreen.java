package com.example.thorm.test4;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class ShiftScreen extends AppCompatActivity {

    ListView shiftsLV;
    ArrayList<String> jobs = new ArrayList<>();
    ArrayList<ShiftArrayAdapter> jobsAdapterList = new ArrayList<>();
    final Employee CURRENT_EMPLOYEE = Employee.selectedEmployee;
    int activeJob = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(CURRENT_EMPLOYEE.getName());

        jobsAdapterList = CURRENT_EMPLOYEE.jobsAdapterList;
        for (Employee.Job j : CURRENT_EMPLOYEE.jobs) {
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
                    Snackbar.make(view, "Changes saved to "+CURRENT_EMPLOYEE.getName()+", "+jobs.get(activeJob)+".", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else
                    Snackbar.make(view, "Data format error in "+CURRENT_EMPLOYEE.getName()+", "+jobs.get(activeJob)+".", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

    }

    public boolean writeShiftInfo() {
        int index = 0;
        for (EmployeeShift s : jobsAdapterList.get(activeJob).shifts) {
            if (!s.isToDelete()) {
                View child = shiftsLV.getChildAt(index);
                EditText date = child.findViewById(R.id.date);
                EditText startTime = child.findViewById(R.id.startTime);
                EditText endTime = child.findViewById(R.id.endTime);
                EditText amount = child.findViewById(R.id.amount);

                String dateStr = date.getText().toString();
                String startTimeStr = startTime.getText().toString();
                String endTimeStr = endTime.getText().toString();
                String amountStr = amount.getText().toString();

                Date dateObj, startTimeObj = null, endTimeObj = null;
                try { dateObj = new SimpleDateFormat("M/d/y").parse(dateStr); }
                catch (ParseException e) { return false; }

                if (CURRENT_EMPLOYEE.jobs.get(activeJob).getType() != Employee.JobType.AMOUNT) {
                    if ((startTimeObj = toTwentyFourHr(startTimeStr))==null || (endTimeObj = toTwentyFourHr(endTimeStr))==null)
                        return false;
                }
                else if (CURRENT_EMPLOYEE.jobs.get(activeJob).getType() != Employee.JobType.STARTEND) {
                    if (Double.parseDouble(amountStr) <= 0)
                        return false;
                }

                s.setDate(dateObj);
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(dateObj);
                s.setDay(c.get(java.util.Calendar.DAY_OF_WEEK)-1);
                s.setStartTime(startTimeObj);
                s.setEndTime(endTimeObj);
                s.setAmount(amountStr);
            }
            else
                CURRENT_EMPLOYEE.jobs.get(activeJob).shifts.remove(s);

            s.setSaved(!s.isToDelete());
            index++;
        }
        return true;
    }

    public Date toTwentyFourHr(String time) {
        if (time.equals("")) return null;
        else {
            int hour, minute, index;
            String currStr = "";

            for (index = 0; time.charAt(index) != ':'; index++)
                currStr += time.charAt(index);
            hour = Integer.parseInt(currStr);
            index++;
            currStr = "";
            for (; time.charAt(index) != ' '; index++)
                currStr += time.charAt(index);
            minute = Integer.parseInt(currStr);
            index++;
            if (time.charAt(index) == 'P' && hour != 12)
                hour += 12;
            if (time.charAt(index) == 'A' && hour == 12)
                hour = 0;

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            return c.getTime();
        }
    }

    public void deleteShift(View view) {
        ViewGroup parent = (ViewGroup) view.getParent().getParent();
        parent.setVisibility(View.GONE);

        ListView list = (ListView) parent.getParent().getParent();
        int index = list.indexOfChild((View) parent.getParent());
        jobsAdapterList.get(activeJob).shifts.get(index).setToDelete(true);
    }

    public void dateClick(View view) {
        final EditText dateText = (EditText) view;
        final View parent = (View) view.getParent();
        final SimpleDateFormat f = new SimpleDateFormat("M/d/y");
        Calendar c = Calendar.getInstance();
        try {
            Date d = f.parse(dateText.getText().toString());
            if (d != null)
                c.setTime(d);
        }
        catch (ParseException e) {}

        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                dateText.setText(f.format(c.getTime()));
                RadioGroup days = parent.findViewById(R.id.daygroup);
                days.check(days.getChildAt(c.get(Calendar.DAY_OF_WEEK)-1).getId());
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        picker.show();
    }

    public void dayClick(View view) {
        RadioGroup days = (RadioGroup) view.getParent();
        int index = days.indexOfChild(days.findViewById(days.getCheckedRadioButtonId()));

        EditText dateText = ((ViewGroup) days.getParent()).findViewById(R.id.date);
        SimpleDateFormat f = new SimpleDateFormat("M/d/y");
        try {
            Date d = f.parse(dateText.getText().toString());
            if (d != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.set(Calendar.DAY_OF_WEEK, index+1);
                dateText.setText(f.format(c.getTime()));
            }
        }
        catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert")
                    .setMessage("Date format is invalid.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    });
            builder.create().show();
        }
    }

    public void timeClick(View view) {
        final EditText timeText = (EditText) view;
        final Calendar c = Calendar.getInstance();
        Date d = toTwentyFourHr(timeText.getText().toString());
        if (d != null)
            c.setTime(d);
        else {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
        }

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                Date d = c.getTime();

                timeText.setText(ShiftArrayAdapter.toTwelveHr(d));
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),false);
        dialog.show();
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
                startActivity(new Intent(this, EmployeeScreen.class));
                return true;
            case R.id.signout:
                startActivity(new Intent(this, LogInScreen.class));
                return true;
            case R.id.about:
                AboutScreen.parentActivity = 1;
                startActivity(new Intent(this, AboutScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EmployeeScreen.class));
    }

}
