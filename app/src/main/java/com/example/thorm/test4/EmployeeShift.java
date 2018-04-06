package com.example.thorm.test4;


public class EmployeeShift {

    double payAmount;
    String payRate;
    String date;
    String startTime;
    String endTime;
    String duration;
    String commission;

    public EmployeeShift(){

    }

    public void setPayAmount(double d) { payAmount = d; }

    public void setDate(String s) { date = s; }

    public void setStartTime(String s){
        startTime = s;
    }

    public void setEndTime(String s){
        endTime = s;
    }

    public void setDuration(String s) { duration = s; }

    public void setCommission(String s) { commission = s; }

}
