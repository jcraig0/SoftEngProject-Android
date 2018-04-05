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
import java.util.HashMap;

public class Test4_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4_2);
        setTitle("Employee List");

        final Employer currentEmployer = Employer.TestEmployer;

        ArrayList<HashMap<String,String>> hash = new ArrayList<>();
        for (Employee e : currentEmployer.employees) {
            HashMap<String,String> item = new HashMap<>();
            item.put("line1", e.NAME);
            item.put("line2", "ID: "+e.ID);
            hash.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, hash, R.layout.linear_layout, new String[]{"line1","line2"}, new int[]{R.id.textView1, R.id.textView2});

        final ListView employeeList = (ListView) findViewById(R.id.employeeLV);
        employeeList.setAdapter(adapter);

        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Employee.selectedEmployee = currentEmployer.employees.get(position);

                Intent toPayActivity = new Intent(Test4_2.this, PayActivity.class);
                startActivity(toPayActivity);
            }
        });
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
                String[] orders = "Alphabetical,Alphabetical (Reverse),Unfilled First".split(",");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Order")
                       .setItems(orders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
