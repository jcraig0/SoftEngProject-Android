package com.example.thorm.test4;

import java.util.ArrayList;

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
            if (e.ID.equals(id)){
                return e;
            }
        }
        return null;
    }

    public static void createEmployerList(){
        Employer cos420 = new Employer("COS420");
        TestEmployer = cos420;

        cos420.addEmployee(new Employee("Justin", "0"));
        cos420.addEmployee(new Employee("Aaron", "1"));
        cos420.addEmployee(new Employee("Tim","spaceman"));
        cos420.addEmployee(new Employee("Jovon", "2"));
        cos420.addEmployee(new Employee("Tim","tim"));
        cos420.addEmployee(new Employee("Patrick","!"));

        cos420.getEmployeeByID("0").addJob("Project Manager");
        cos420.getEmployeeByID("0").addJob("API Builder");
        cos420.getEmployeeByID("1").addJob("iOS Dev");
        cos420.getEmployeeByID("spaceman").addJob("Odd Jobs");
        cos420.getEmployeeByID("2").addJob("UI & Testing");
        cos420.getEmployeeByID("tim").addJob("Database");
        cos420.getEmployeeByID("!").addJob("Android Dev");
    }

}
