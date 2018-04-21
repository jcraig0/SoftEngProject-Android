package com.example.thorm.test4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShiftArrayAdapter extends ArrayAdapter<EmployeeShift> {
    Employee.Job job;
    ArrayList<EmployeeShift> shifts = new ArrayList<>();

    public ShiftArrayAdapter(Context context, Employee.Job job){
        super(context, 0);
        this.job = job;
        shifts = job.shifts;
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
        EmployeeShift shift = shifts.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_pay, parent, false);

            if (!shift.isVisible()) {
                readShiftInfo(position, convertView);
                shift.setVisible(true);
            }
        }

        TextView shiftNumber = convertView.findViewById(R.id.shiftNum);
        shiftNumber.setText("Shift ".concat(String.valueOf(position+1)));

        TextView unit = convertView.findViewById(R.id.unit);
        unit.setText(job.getUnit());

        LinearLayout layout = convertView.findViewById(R.id.shiftLayout);
        if (position % 2 == 1)
            layout.setBackgroundColor(Color.rgb(240,255,240));
        else
            layout.setBackgroundColor(0);

        LinearLayout startend = convertView.findViewById(R.id.startEndRow);
        LinearLayout amount = convertView.findViewById(R.id.amountRow);
        if (job.getUnit() != null)
            startend.setVisibility(View.GONE);
        else
            amount.setVisibility(View.GONE);

        return convertView;
    }

    public boolean readShiftInfo(int pos, View view) {
        EditText date = view.findViewById(R.id.date);
        RadioGroup days = view.findViewById(R.id.daygroup);
        EditText startTime = view.findViewById(R.id.startTime);
        EditText endTime = view.findViewById(R.id.endTime);
        EditText amount = view.findViewById(R.id.amount);
        TextView unit = view.findViewById(R.id.unit);

        EmployeeShift s = shifts.get(pos);
        if (s.getDate() != null) {
            date.setText(new SimpleDateFormat("M/d/y").format(s.getDate()));
            days.check(days.getChildAt(s.getDay()).getId());
        }
        startTime.setText(s.getStartTime());
        endTime.setText(s.getEndTime());
        amount.setText(s.getAmount());
        unit.setText(job.getUnit());

        return true;
    }

}
