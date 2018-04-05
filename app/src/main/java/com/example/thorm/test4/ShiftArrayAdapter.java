package com.example.thorm.test4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class ShiftArrayAdapter extends ArrayAdapter<EmployeeShift> {
    ArrayList<EmployeeShift> shifts = new ArrayList<EmployeeShift>();


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

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_pay, parent, false);
        }

        TextView shiftNumber = convertView.findViewById(R.id.shiftNum);
        shiftNumber.setText("Shift ".concat(String.valueOf(position+1)));

        //Input for this method
        //EditText shiftStart = convertView.findViewById(R.id.shiftStartET);
        //EditText shiftEnd = convertView.findViewById(R.id.shiftStartET);

        return convertView;
    }

}
