package com.example.thorm.test4;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Employee {
    enum JobType {
        STARTEND, AMOUNT, BOTH
    }

    public class Job {
        private String name;
        private String unit;
        private JobType type;
        ArrayList<EmployeeShift> shifts;

        public Job(String name, String unit, JobType type) {
            this.name = name;
            this.unit = unit;
            this.type = type;
            shifts = new ArrayList<>();
        }

        public String getName() { return name; }
        public String getUnit() { return unit; }
        public JobType getType() { return type; }
    }

    public static Employee selectedEmployee = null;

    private String name;
    private String ID;
    private boolean active;
    private JSONObject employeeData;

    ArrayList<Job> jobs = new ArrayList<>();
    ArrayList<ShiftArrayAdapter> jobsAdapterList = new ArrayList<>();

    public Employee(JSONObject j){
        if (j == null)
            employeeData = null;
        else
            employeeData = j;

        if (employeeData != null) {
            try {
                name = String.format("%s %s %s", employeeData.get("firstname").toString(),
                        employeeData.get("middlename").toString(), employeeData.get("lastname").toString());
            }catch(JSONException e){}

            try {
                ID = employeeData.get("employer_employee_ID").toString();
            }catch(JSONException e){}

            try {
                String temp = employeeData.get("active").toString();
                if (temp.equals("Active"))
                    active = true;
                else
                    active = false;
            }catch(JSONException e){}
        }
    }

    public String getName() { return name; }
    public String getID() { return ID; }
    public boolean getActive() { return active; }
    public JSONObject getData() { return employeeData; }

    public void addJob(String name, String unit, JobType type) {
        this.jobs.add(new Job(name, unit, type));
    }

}
