package com.example.thorm.test4;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Employee {
    enum JobType {
        STARTEND, AMOUNT, BOTH
    }

    public class Job {
        JobType type;
        JSONObject Data;
        ArrayList<EmployeeShift> shifts;

        public Job(JSONObject d) {
            this.Data = d;
            shifts = new ArrayList<>();

            try {
                if (Data.get("UOM").toString().equals("dollars")){
                    type = JobType.AMOUNT;
                }else{
                    type = JobType.STARTEND;
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        public String getName() {
            try {
                return this.Data.get("title").toString();
            }catch(JSONException e){
                return "Error";
            }
        }

        public String getUnit() {
            try {
                return this.Data.get("UOM").toString();
            }catch(JSONException e){
                return "Error";
            }
        }

        public JobType getType() {
            return type;
        }

        public String getID(){
            try{
                return this.Data.get("job_id").toString();
            }catch(JSONException e){
                return "Error";
            }
        }
    }

    public static Employee selectedEmployee = null;

    private JSONObject employeeData;
    ArrayList<Job> jobs = new ArrayList<>();
    ArrayList<ShiftArrayAdapter> jobsAdapterList = new ArrayList<>();

    public Employee(JSONObject j){
        if (j == null){
            this.employeeData = null;
        }else {
            this.employeeData = j;
        }
    }

    public String getName() {
        try {
            String middleName = employeeData.get("middlename").toString();
            if (middleName.equals("null"))
                middleName = " ";
            else
                middleName = " "+middleName+" ";

            String name = String.format("%s%s%s", employeeData.get("firstname").toString(),
                    middleName, employeeData.get("lastname").toString());
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

    public String getEmployeeID(){
        try{
            return employeeData.get("employee_id").toString();
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

    public void addJob(JSONObject job) {
        this.jobs.add(new Job(job));
    }

    public Job getJobAt(int x){
        return this.jobs.get(x);
    }

}
