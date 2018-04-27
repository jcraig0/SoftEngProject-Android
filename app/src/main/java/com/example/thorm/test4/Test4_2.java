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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Test4_2 extends AppCompatActivity {

    final Employer currentEmployer = Employer.currentEmployer;
    ArrayList<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4_2);
        setTitle("Employee List");

        employees = makeAdapter(currentEmployer.employees);

        final ListView employeeList = findViewById(R.id.employeeLV);
        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Employee.selectedEmployee = employees.get(position);
                Intent toPayActivity = new Intent(Test4_2.this, PayActivity.class);
                startActivity(toPayActivity);
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
            item.put("line2", "ID: "+ e.getID());
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
                String[] orders = "Show All,Show Active Only,Sort by Job".split(",");
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

                                makeAdapter(newList);
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                builder.create().show();
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
