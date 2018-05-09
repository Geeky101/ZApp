package com.justinmutsito.zapp.util;

public class Event {
    private String details;
    private String location;
    private int startHour, startMinute, endHour, endMinute;
    private int day;
    private String month;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public Event(String details, String location, int startHour, int startMinute, int endHour, int endMinute, int day, String month) {
        this.details = details;
        this.location = location;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.day = day;
        this.month = month;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public int getMonthIndex() {
        return TimeFormatter.formatMonth(getMonth());
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public String getStartTime(){
        return TimeFormatter.formatTime(getStartHour(),getStartMinute());
    }
    public String getFinishTime(){
        return TimeFormatter.formatTime(getEndHour(),getEndMinute());
    }

    public String getDate(){
        return TimeFormatter.formatDate(getDay(),getMonthIndex());
    }
}
