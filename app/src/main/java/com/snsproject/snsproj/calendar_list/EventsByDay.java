package com.snsproject.snsproj.calendar_list;

import java.util.Calendar;
import java.util.List;

public class EventsByDay {
    private String name;
    private int day;

    public String getWeek(int week) {
        switch (week) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周天";
            default:
                return "周一";
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private int month;
    private int year;
    private List<EventByItem> eventByItemList;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<EventByItem> getEventByItemList() {
        return eventByItemList;
    }

    public void setEventByItemList(List<EventByItem> eventByItemList) {
        this.eventByItemList = eventByItemList;
    }
    private int index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public EventsByDay(List<EventByItem> eventByItemList, int index, int day) {
        this.eventByItemList = eventByItemList;
        this.index = index;
        this.month = index % 12;
        this.year = index / 12 + 2016;
        this.day = day;
    }
    public EventsByDay(int year,int month,int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortKey() {
        Calendar currentCC = Calendar.getInstance();
        int currentYear = currentCC.get(Calendar.YEAR);
        int cc;
        if (currentYear == this.year) {
            cc = 'A' + month;
        } else {
            cc = 'A' + month + 12;
        }
        return String.valueOf((char) cc);

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EventsByDay) {
            EventsByDay eventsByDay = (EventsByDay) o;
            if (eventsByDay.getYear() == this.year && eventsByDay.getMonth() == this.month && eventsByDay.getDay() == this.day) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.year * 10000 + this.month * 100 + this.day;
    }

    public static String getParallMonth(String title) {
        int key = title.toCharArray()[0];
        int month = (key - 'A' + 1) % 12;
        if (month == 0) {
            month = 12;
        }
        return (month + "月");
    }

}
