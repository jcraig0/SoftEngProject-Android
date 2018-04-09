package com.example.thorm.test4;


import java.util.ArrayList;

public class Employee {

    public static Employee selectedEmployee = null;

    String NAME;
    String ID;
    ArrayList<String> jobs = new ArrayList<>();
    ArrayList<EmployeeShift> shifts = new ArrayList<>();

    public Employee(String name, String id){
        this.NAME = name;
        this.ID = id;
    }

    public void addJob(String jobName){
        this.jobs.add(jobName);
    }


}
