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

    ArrayList<Job> jobs = new ArrayList<>();
    ArrayList<ShiftArrayAdapter> jobsAdapterList = new ArrayList<>();

    private JSONObject employeeData;

    public Employee(JSONObject j){
        if (j == null){
            this.employeeData = null;
        }else {
            this.employeeData = j;
        }
    }

    public String getName() {
        try {
            String name = String.format("%s %s %s", employeeData.get("firstname").toString(),
                    employeeData.get("middlename").toString(), employeeData.get("lastname").toString());
            return name;
        }catch(JSONException e){
            return "Error";
        }
    }

    public String getID() {
        try {
            return employeeData.get("employer_employee_ID").toString();
        }catch(JSONException e){
            return "Error";
        }
    }
    public boolean getActive() {
        try {
            String temp = employeeData.get("active").toString();
            if (temp.equals("Active")){
                return true;
            }else{
                return false;
            }
        }catch(JSONException e){
            return false;
        }
    }

    public JSONObject getData() { return employeeData; }

    public void addJob(String name, String unit, JobType type) {
        this.jobs.add(new Job(name, unit, type));
    }

}
