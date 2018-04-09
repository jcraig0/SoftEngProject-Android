package com.example.thorm.test4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ShiftArrayAdapter extends ArrayAdapter<EmployeeShift> {
    ArrayList<EmployeeShift> shifts = new ArrayList<>();

    public ShiftArrayAdapter(Context context){
        super(context, 0);
    }

    @Override
    public void add(EmployeeShift object){
        super.add(object);
        shifts.add(object);
    }

    @Override
    public int getCount(){
        return this.shifts.size();
    }

    @Override
    public EmployeeShift getItem(int position){
        return this.shifts.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_pay, parent, false);

        TextView shiftNumber = convertView.findViewById(R.id.shiftNum);
        shiftNumber.setText("Shift ".concat(String.valueOf(position+1)));

        TextView payRate = convertView.findViewById(R.id.payRate);
        payRate.setText(shifts.get(position).getPayRate());

        LinearLayout layout = convertView.findViewById(R.id.shiftLayout);
        if (position % 2 == 1)
            layout.setBackgroundColor(Color.rgb(240,240,255));
        else
            layout.setBackgroundColor(0);

        return convertView;
    }

}
