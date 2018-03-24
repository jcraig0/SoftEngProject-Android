package com.example.thorm.test4;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

public class Test4_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4_2);
        getApplication().setTheme(R.style.DarkTheme);

        String[] myArray = "Aaron,Justin,Tim,Timothy,Patrick,Jovon".split(",");
        final ListView employeeList = (ListView) findViewById(R.id.employeeLV);
        final ArrayAdapter employeeAdaptor = new ArrayAdapter(this, R.layout.text_view, myArray);

        employeeList.setAdapter(employeeAdaptor);

        final Intent toMainActivity = new Intent(this, MainActivity.class);

        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView t = (TextView) view;
                t.setText(String.valueOf(position));

            }
        });

        String[] test32 = "D,E,F,G".split(",");
        ArrayAdapter test1 = new ArrayAdapter(this, R.layout.text_view, test32 );
        final Spinner spin = (Spinner) findViewById(R.id.sortingSpinner);
        spin.setAdapter(test1);
        spin.setDropDownVerticalOffset(100);




        final Button b = (Button) findViewById(R.id.testButtonB);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu menu = new PopupMenu(Test4_2.this, v);

                menu.getMenu().add("Hello");
                menu.getMenu().getItem(0).setIcon(android.R.drawable.arrow_down_float);

                menu.getMenu().add("World");
                menu.getMenu().getItem(1).setIcon(android.R.drawable.arrow_up_float);

                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        b.setText(item.getTitle());
                        b.setBackground(item.getIcon());

                        return false;
                    }
                });


            }
        });


    }
}
