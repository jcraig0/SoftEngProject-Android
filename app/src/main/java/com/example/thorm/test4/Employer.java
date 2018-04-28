package com.example.thorm.test4;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Employer {

<<<<<<< HEAD
    public static Employer employer;
=======

    public static Employer currentEmployer;

>>>>>>> ICH-Brarnch
    String NAME;
    String ID;
    ArrayList<Employee> employees = new ArrayList<>();

    public Employer(String name){
        this.NAME = name;
    }

    public void addEmployee(Employee e){
        this.employees.add(e);
    }

    public Employee getEmployeeByID(String id){

        for (int i = 0; i < this.employees.size(); i++){
            Employee e = this.employees.get(i);
            if (e.getID().equals(id)){
                return e;
            }
        }
        return null;
    }

<<<<<<< HEAD
    public static void createEmployerList(){
        Employer cos420 = new Employer("COS420");
        employer = cos420;
=======
    public static void createEmployerList(JSONArray jArray){
        Employer temp = new Employer("ThisEmployer");
        currentEmployer = temp;

        for (int i = 0; i < jArray.length(); i++){

            try {

                JSONObject curr = jArray.getJSONObject(i);
                temp.addEmployee(new Employee((curr)));

            }catch(JSONException e){
                temp.addEmployee(new Employee(null));
            }
        }
    }

>>>>>>> ICH-Brarnch

/*
    public static void createTestEmployerList(){

        cos420.getEmployeeByID("0").addJob("Project Manager", "hours", Employee.JobType.BOTH);
        cos420.getEmployeeByID("0").addJob("API Builder", "hours", Employee.JobType.AMOUNT);
        cos420.getEmployeeByID("1").addJob("iOS Dev", "dollars", Employee.JobType.AMOUNT);
        cos420.getEmployeeByID("spaceman").addJob("Odd Jobs", "dollars", Employee.JobType.AMOUNT);
        cos420.getEmployeeByID("2").addJob("UI & Testing", "hours", Employee.JobType.STARTEND);
        cos420.getEmployeeByID("tim").addJob("Database", "dollars", Employee.JobType.AMOUNT);
        cos420.getEmployeeByID("!").addJob("Android Dev", null, Employee.JobType.STARTEND);

        cos420.getEmployeeByID("0").jobs.get(0).shifts.add(new EmployeeShift("2018-04-09", "09:00:00", "17:00:00", "3.33"));
        cos420.getEmployeeByID("0").jobs.get(1).shifts.add(new EmployeeShift("2018-04-10", null, null, "3.33"));
        cos420.getEmployeeByID("!").jobs.get(0).shifts.add(new EmployeeShift("2018-04-11", "08:00:00", "12:00:00", null));
    }

    public static void refreshEmployerList() {
        Employer newEmployer = new Employer("COS420");

        for (Employee e : employer.employees) {
            newEmployer.addEmployee(new Employee(e.getName(), e.getID(), e.getActive()));
            Employee newE = newEmployer.getEmployeeByID(e.getID());
            for (Employee.Job j : e.jobs) {
                newE.addJob(j.getName(), j.getUnit(), j.getType());
                Employee.Job newJ = newE.jobs.get(newE.jobs.size()-1);
                for (EmployeeShift s : j.shifts) {
                    newJ.shifts.add(new EmployeeShift(dateToStr(s.getDate(),true), dateToStr(s.getStartTime(),false),
                                    dateToStr(s.getEndTime(), false), s.getAmount()));
                }
            }
        }

        employer = newEmployer;
    }

    static public String dateToStr(Date s, boolean date) {
        if (s != null) {
            SimpleDateFormat f;
            if (date)
                f = new SimpleDateFormat("yyyy-MM-dd");
            else
                f = new SimpleDateFormat("HH:mm:ss");
            return f.format(s);
        }
        else return null;
    }
*/
}
