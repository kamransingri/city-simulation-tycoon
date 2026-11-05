package com.example.template;

import javafx.scene.control.ListView;

import java.util.ArrayList;

public class RandomEvents {

    private long startTime;
    private String breakingNews;

    public RandomEvents(){
        startTime = System.nanoTime();
    }

    public void RandomEventChooser(ArrayList<Building> buildings, ArrayList<Person> people){
        breakingNews = "";
        int randNum = (int)(Math.random()*2);
        if(randNum == 0){
            earthquake(buildings);
            breakingNews = "An Earthquake has hit the city!!!";
        }
        if(randNum == 1){
            alienInvasion(people);
            breakingNews = "Aliens have invaded and are taking people with them!!!";
        }
    }
    public void earthquake(ArrayList<Building> buildings){
        for(Building building: buildings){
            int damage = (int)(Math.random()*50) + 1;
            building.damageBuilding(damage);
        }
    }

    public void alienInvasion(ArrayList<Person> people){
        for(int i = people.size()-1; i>=0; i--){
            int randNum = (int)(Math.random()*10) +1;
            if(randNum<=3){
                ((Apartment)people.get(i).getHome()).removePerson();
                people.remove(i);
            }
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        startTime = System.nanoTime();
    }

    public String getBreakingNews(){
        return breakingNews;
    }
}
