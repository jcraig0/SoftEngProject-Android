package com.example.thorm.test4;


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

    private String NAME;
    private String ID;
    private boolean active;
    ArrayList<Job> jobs = new ArrayList<>();
    ArrayList<ShiftArrayAdapter> jobsAdapterList = new ArrayList<>();

    public Employee(String name, String id, boolean active){
        this.NAME = name;
        this.ID = id;
        this.active = active;
    }

    public String getName() { return NAME; }
    public String getID() { return ID; }
    public boolean getActive() { return active; }

    public void addJob(String name, String unit, JobType type) {
        this.jobs.add(new Job(name, unit, type));
    }

}
