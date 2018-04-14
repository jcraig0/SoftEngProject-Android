package com.example.thorm.test4;


public class EmployeeShift {

    enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    }

    private String date;
    private Day day;
    private String startTime;
    private String endTime;
    private String amount;
    private boolean visible;
    private boolean saved;

    public EmployeeShift() {
        visible = false;
        saved = false;
    }

    public EmployeeShift(String date, Day day, String startTime, String endTime, String amount, String unit) {
        this.date = date;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        visible = false;
        saved = true;
    }

    public String getDate() { return date; }
    public void setDate(String s){ date = s; }

    public Day getDay() { return day; }
    public void setDay(Day s) { day = s; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String s){ startTime = s; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String s){ endTime = s; }

    public String getAmount() { return amount; }
    public void setAmount(String s) { amount = s; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean s) { visible = s; }

    public boolean isSaved() { return saved; }
    public void setSaved(boolean s) { saved = s; }

}
