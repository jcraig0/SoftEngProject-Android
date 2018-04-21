package com.example.thorm.test4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeShift {

    private Date date;
    private int day;
    private String startTime;
    private String endTime;
    private String amount;
    private boolean visible;
    private boolean saved;
    private boolean toDelete;

    public EmployeeShift() {
        visible = saved = toDelete = false;
    }

    public EmployeeShift(String date, String startTime, String endTime, String amount) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try { this.date = f.parse(date); }
        catch (ParseException e) {}
        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        day = c.get(Calendar.DAY_OF_WEEK)-1;

        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        visible = false;
        saved = true;
        toDelete = false;
    }

    public Date getDate() { return date; }
    public void setDate(Date s){ date = s; }

    public int getDay() { return day; }
    public void setDay(int s) { day = s; }

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

    public boolean isToDelete() { return toDelete; }
    public void setToDelete(boolean s) { toDelete = s; }

}
