package com.example.thorm.test4;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Employee {

    public class Job {
        private boolean isAmount;
        JSONObject data;
        ArrayList<EmployeeShift> shifts;

        public Job(JSONObject d) {
<<<<<<< HEAD
            this.data = d;
            shifts = new ArrayList<>();
=======
            this.Data = d;
            shifts = new ArrayList<EmployeeShift>();
>>>>>>> parent of f4b020f... Finish merge

            try {
                isAmount = data.get("UOM").toString().equals("Dollars");
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        public String getName() {
            try {
                return this.data.get("title").toString();
            }catch(JSONException e){
                return "Error";
            }
        }

        public String getUnit() {
            try {
                return this.data.get("UOM").toString();
            }catch(JSONException e){
                return "Error";
            }
        }

        public boolean isAmount() {
            return isAmount;
        }

        public String getID(){
            try{
                return this.data.get("job_id").toString();
            }catch(JSONException e){
                return "Error";
            }
        }
    }

    public static Employee selectedEmployee = null;

<<<<<<< HEAD
    JSONObject employeeData;
=======
    private String name;
    private String ID;
    private boolean active;
    private JSONObject employeeData;

>>>>>>> parent of f4b020f... Finish merge
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

<<<<<<< HEAD
=======
    public String getEmployeeID(){
        try{
            return employeeData.get("employee_id").toString();
        }catch(JSONException e){
            return "Error";
        }
    }
>>>>>>> MVP-Branch
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

    public void addJob(JSONObject job) {
        this.jobs.add(new Job(job));
    }

    public Job getJobAt(int x){
        return this.jobs.get(x);
    }

}
