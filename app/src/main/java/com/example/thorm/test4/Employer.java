package com.example.thorm.test4;

import java.util.ArrayList;
import com.example.thorm.test4.EmployeeShift.Day;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Employer {


    public static Employer currentEmployer;

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


/*
    public static void createTestEmployerList(){

        cos420.getEmployeeByID("0").addJob("Project Manager", "hours");
        cos420.getEmployeeByID("0").addJob("API Builder", "hours");
        cos420.getEmployeeByID("1").addJob("iOS Dev", "dollars");
        cos420.getEmployeeByID("spaceman").addJob("Odd Jobs", "dollars");
        cos420.getEmployeeByID("2").addJob("UI & Testing", "hours");
        cos420.getEmployeeByID("tim").addJob("Database", "dollars");
        cos420.getEmployeeByID("!").addJob("Android Dev", "dollars");

        cos420.getEmployeeByID("0").jobs.get(0).shifts.add(new EmployeeShift(Day.MONDAY, "4/9/2018", null, null, "3.33"));
    }
*/
}
