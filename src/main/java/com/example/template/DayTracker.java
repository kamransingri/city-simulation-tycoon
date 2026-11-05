package com.example.template;
import javafx.scene.control.Label;

public class DayTracker {

    private String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private String currentDay;
    String dayOrNight = "Day";
    private int dayCount;
    private int startDay;
    private long dayTimer;



    public DayTracker(){
        startDay = (int)(Math.random()*daysOfWeek.length);
        currentDay = daysOfWeek[startDay];
        dayTimer = System.nanoTime();
    }

    public String getDay(){
        return currentDay;
    }

    public void changeDay(){
        dayCount++;
        currentDay = daysOfWeek[(dayCount + startDay)%7];
    }

    public long getDayTimer(){
        return dayTimer;
    }

    public void setDayTimer(){
        dayTimer = System.nanoTime();
    }

    public void changeNightDay(){
        if(dayOrNight.equals("Day")){
            dayOrNight = "Night";
        }
        else{
            dayOrNight = "Day";
            changeDay();
        }
    }

    public String getDayOrNight(){
        return dayOrNight;
    }
}
