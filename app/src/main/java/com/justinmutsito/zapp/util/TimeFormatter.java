package com.justinmutsito.zapp.util;

/**
 * Created by justin on 8/26/17.
 */

public class TimeFormatter {

    public static String formatDate(int date, int month) {
        String theDate;
        switch (month) {
            case 0: {
                theDate = date + " January";
                break;
            }
            case 1: {
                theDate = date + " February";
                break;
            }
            case 2: {
                theDate = date + " March";
                break;
            }
            case 3: {
                theDate = date + " April";
                break;
            }
            case 4: {
                theDate = date + " May";
                break;
            }
            case 5: {
                theDate = date + " June";
                break;
            }
            case 6: {
                theDate = date + " July";
                break;
            }
            case 7: {
                theDate = date + " August";
                break;
            }
            case 8: {
                theDate = date + " September";
                break;
            }
            case 9: {
                theDate = date + " October";
                break;
            }
            case 10: {
                theDate = date + " November";
                break;
            }
            default: {
                theDate = date + " December";
                break;
            }
        }

        return theDate;

    }


    public static String formatTime(int hour, int minute) {
        String time;

        if (hour >= 10) {
            if (minute >= 10) {
                time = hour + ":" + minute;
            } else {
                time = hour + ":0" + minute;
            }
        } else {
            if (minute >= 10) {
                time = "0" + hour + ":" + minute;
            } else {
                time = "0" + hour + ":0" + minute;
            }
        }

        if (hour >= 12) {
            time += " PM";
        } else {
            time += " AM";
        }
        return time;

    }

    public static int[] decodeTime(String time) {
        char[] splitTime = time.toCharArray();
        String[] timeArray = {splitTime[0] + "", splitTime[1] + "", splitTime[3] + "", splitTime[4] + ""};

        int hour, minute;
        hour = (Integer.parseInt(timeArray[0]) * 10) + (Integer.parseInt(timeArray[1]));
        minute = (Integer.parseInt(timeArray[2]) * 10) + (Integer.parseInt(timeArray[3]));

        return new int[]{hour, minute};

    }

    public static String formatMinutes(int min, int sec) {
        String time;

        if (min >= 10) {
            if (sec >= 10) {
                time = min + ":" + sec;
            } else {
                time = min + ":0" + sec;
            }
        } else {
            if (sec >= 10) {
                time = "0" + min + ":" + sec;
            } else {
                time = "0" + min + ":0" + sec;
            }
        }


        return time;

    }

    public static String formatMonth(int index) {
        String month;

        switch (index) {
            case 0: {
                return  " January";
            }
            case 1: {
                return  " February";

            }
            case 2: {
                return  " March";
            }
            case 3: {
                return  " April";
            }
            case 4: {
                return  " May";
            }
            case 5: {
                return  " June";
            }
            case 6: {
                return  " July";
            }
            case 7: {
                return  " August";
            }
            case 8: {
                return  " September";

            }
            case 9: {
                return  " October";
            }
            case 10: {
                return  " November";
            }
            default: {
                return " December";

            }
        }


    }


    public static int formatMonth(String month) {


        switch (month.toLowerCase()) {
            case "january": {
                return 0;
            }
            case "february": {
                return 1;
            }
            case "march": {
                return 2;

            }
            case "april": {
                return 3;
            }
            case "may": {
                return 4;
            }
            case "june": {
                return 5;
            }
            case "july": {
                return 6;
            }
            case "august": {
                return 7;
            }
            case "september": {
                return 8;
            }
            case "october": {
                return 9;
            }
            case "november": {
                return 10;
            }
            default: {
                return 11;
            }
        }

    }

    public static String formatDay(int day) {
        String today;

        switch (day) {
            case 1: {
                today = "Sunday";
                break;
            }
            case 2: {
                today = "Monday";
                break;
            }
            case 3: {
                today = "Tuesday";
                break;
            }
            case 4: {
                today = "Wednesday";
                break;
            }
            case 5: {
                today = "Thursday";
                break;
            }
            case 6: {
                today = "Friday";
                break;
            }
            default: {
                today = "Saturday";
                break;
            }
        }

        return today;
    }


    public static String getMonth(int index) {
        String month;

        switch (index) {
            case 0: {
                month = " January";
                break;
            }
            case 1: {
                month = " February";
                break;
            }
            case 2: {
                month = " March";
                break;
            }
            case 3: {
                month = " April";
                break;
            }
            case 4: {
                month = " May";
                break;
            }
            case 5: {
                month = " June";
                break;
            }
            case 6: {
                month = " July";
                break;
            }
            case 7: {
                month = " August";
                break;
            }
            case 8: {
                month = " September";
                break;
            }
            case 9: {
                month = " October";
                break;
            }
            case 10: {
                month = " November";
                break;
            }
            default: {
                month = " December";
            }
        }

        return month;
    }
}
