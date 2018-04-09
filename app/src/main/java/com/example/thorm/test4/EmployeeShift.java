package com.example.thorm.test4;


public class EmployeeShift {

    private double payAmount;
    private String payRate;
    private String date;
    private String startTime;
    private String endTime;
    private String duration;
    private String commission;

    public EmployeeShift(){

    }

    public double getPayAmount() { return payAmount; }
    public void setPayAmount(double d) { payAmount = d; }

    public String getDate() { return date; }
    public void setDate(String s) { date = s; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String s){
        startTime = s;
    }

    public String getEndTime() { return endTime; }
    public void setEndTime(String s){
        endTime = s;
    }

    public String getDuration() { return duration; }
    public void setDuration(String s) { duration = s; }

    public String getCommission() { return commission; }
    public void setCommission(String s) { commission = s; }

}
