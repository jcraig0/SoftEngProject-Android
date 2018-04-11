package com.example.thorm.test4;


public class EmployeeShift {

    enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    }

    private Day day;
    private String week;
    private String startTime;
    private String endTime;
    private String quantity;

    public EmployeeShift() {

    }

    public EmployeeShift(Day day, String week, String startTime, String endTime, String quantity) {
        this.day = day;
        this.week = week;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
    }

    public Day getDay() { return day; }
    public void setDay(Day s) { day = s; }

    public String getWeek() { return week; }
    public void setWeek(String s){
        week = s;
    }

    public String getStartTime() { return startTime; }
    public void setStartTime(String s){
        startTime = s;
    }

    public String getEndTime() { return endTime; }
    public void setEndTime(String s){
        endTime = s;
    }

    public String getQuantity() { return quantity; }
    public void setQuantity(String s) { quantity = s; }

}
