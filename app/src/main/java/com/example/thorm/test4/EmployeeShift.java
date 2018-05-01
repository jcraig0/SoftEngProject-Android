package com.example.thorm.test4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeShift {

    private Date date;
    private int day;
    private Date startTime;
    private Date endTime;
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

        if (startTime != null && endTime != null) {
            f = new SimpleDateFormat("HH:mm:ss");
            try { this.startTime = f.parse(startTime); }
            catch (ParseException e) {}
            try { this.endTime = f.parse(endTime); }
            catch (ParseException e) {}
        }
        this.amount = amount;
        visible = false;
        saved = true;
        toDelete = false;
    }

    public String getStringDate(){
        return String.format("%4d-%02d-%02d", date.getYear()+1900, date.getMonth()+1, date.getDate());
    }

    public Date getDate() {
         return date;
    }

    public void setDate(Date s){ date = s; }

    public int getDay() { return day; }
    public void setDay(int s) { day = s; }

    public Date getStartTime() { return startTime; }

    public String getStartTimeString(){
        return String.format("%02d:%02d:%s", startTime.getHours(), startTime.getMinutes(), "00");
    }

    public void setStartTime(Date s){ startTime = s; }

    public Date getEndTime() { return endTime; }

    public String getEndTimeString(){
        return String.format("%02d:%02d:%s", endTime.getHours(), endTime.getMinutes(), "00");
    }
    public void setEndTime(Date s){ endTime = s; }

    public String getAmount() { return amount; }
    public void setAmount(String s) { amount = s; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean s) { visible = s; }

    public boolean isSaved() { return saved; }
    public void setSaved(boolean s) { saved = s; }

    public boolean isToDelete() { return toDelete; }
    public void setToDelete(boolean s) { toDelete = s; }

}
