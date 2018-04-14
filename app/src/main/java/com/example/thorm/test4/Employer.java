package com.example.thorm.test4;

import java.util.ArrayList;
import com.example.thorm.test4.EmployeeShift.Day;

public class Employer {


    public static Employer TestEmployer;

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

    public static void createEmployerList(){
        Employer cos420 = new Employer("COS420");
        TestEmployer = cos420;

        cos420.addEmployee(new Employee("Justin", "0", true));
        cos420.addEmployee(new Employee("Aaron", "1", true));
        cos420.addEmployee(new Employee("Tim","spaceman", true));
        cos420.addEmployee(new Employee("Jovon", "2", false));
        cos420.addEmployee(new Employee("Tim","tim", false));
        cos420.addEmployee(new Employee("Patrick","!", false));

        cos420.getEmployeeByID("0").addJob("Project Manager", "hours");
        cos420.getEmployeeByID("0").addJob("API Builder", "hours");
        cos420.getEmployeeByID("1").addJob("iOS Dev", "dollars");
        cos420.getEmployeeByID("spaceman").addJob("Odd Jobs", "dollars");
        cos420.getEmployeeByID("2").addJob("UI & Testing", "hours");
        cos420.getEmployeeByID("tim").addJob("Database", "dollars");
        cos420.getEmployeeByID("!").addJob("Android Dev", null);

        cos420.getEmployeeByID("0").jobs.get(0).shifts.add(new EmployeeShift("4/9/2018", Day.MONDAY, null, null, "3.33", "hours"));
        cos420.getEmployeeByID("0").jobs.get(0).shifts.add(new EmployeeShift("4/10/2018", Day.TUESDAY, null, null, "3.33", "hours"));
        cos420.getEmployeeByID("!").jobs.get(0).shifts.add(new EmployeeShift("4/11/2018", Day.WEDNESDAY, "8:00 AM", "12:00 PM", null, null));
    }

}
