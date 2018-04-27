package com.example.thorm.test4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class EmployeeScreen extends AppCompatActivity {

    Employer currentEmployer;
    ArrayList<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4_2);
        setTitle("Employee List");

        currentEmployer = Employer.employer;
        employees = makeAdapter(currentEmployer.employees);

        final ListView employeeList = findViewById(R.id.employeeLV);
        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Employee.selectedEmployee = employees.get(position);
                startActivity(new Intent(EmployeeScreen.this, ShiftScreen.class));
            }
        });
    }

    public ArrayList<Employee> makeAdapter(ArrayList<Employee> employees) {
        ArrayList<Employee> sorted = new ArrayList<>(employees);
        Collections.sort(sorted, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getName().compareTo(e2.getName());
            }
        });

        ArrayList<HashMap<String,String>> hash = new ArrayList<>();
        for (Employee e : sorted) {
            HashMap<String,String> item = new HashMap<>();
            item.put("line1", e.getName());
            item.put("line2", "ID: "+e.getID());
            hash.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, hash, R.layout.linear_layout, new String[]{"line1","line2"}, new int[]{R.id.textView1, R.id.textView2});
        ListView employeeList = findViewById(R.id.employeeLV);
        employeeList.setAdapter(adapter);
        return sorted;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order:
                String[] orders = "Show All,Show Active Only,Show by Job".split(",");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Action")
                       .setItems(orders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                employees = makeAdapter(currentEmployer.employees);
                                break;
                            case 1:
                                ArrayList<Employee> newList = new ArrayList<>(currentEmployer.employees);
                                for (int j=0; j < newList.size(); j++) {
                                    Employee e = newList.get(j);
                                    if (!e.getActive()) {
                                        newList.remove(e); j--; }
                                }
                                employees = makeAdapter(newList);
                                break;
                            case 2:
                                filterByJob();
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            case R.id.signout:
                startActivity(new Intent(this, LogInScreen.class));
                return true;
            case R.id.about:
                startActivity(new Intent(this, AboutScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void filterByJob() {
        HashSet<String> allJobs = new HashSet<String>();
        for (Employee e : currentEmployer.employees) {
            for (Employee.Job j : e.jobs)
                allJobs.add(j.getName());
        }
        final String[] jobs = allJobs.toArray(new String[0]);
        Arrays.sort(jobs);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Job Name").setItems(jobs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ArrayList<Employee> newList = new ArrayList<>(currentEmployer.employees);
                String choice = jobs[i];
                for (int j=0; j < newList.size(); j++) {
                    Employee e = newList.get(j);
                    boolean present = false;
                    for (Employee.Job k : e.jobs) {
                        if (k.getName().equals(choice))
                            present = true;
                    }
                    if (!present) {
                        newList.remove(e); j--; }
                }
                employees = makeAdapter(newList);
            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LogInScreen.class));
    }

}
