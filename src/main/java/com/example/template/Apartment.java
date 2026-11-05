package com.example.template;

import java.util.ArrayList;

public class Apartment extends Building {
    private int numRooms;
    private int totalPeople = 0;

    public Apartment(int x,  int y){
        super(x,y,"C8541E", true, 3000000000L);
        numRooms = (int)(Math.random()*5) + 5;
    }

    public int getNumRooms(){
        return numRooms;
    }

    public int getTotalPeople(){
        return totalPeople;
    }

    public void addPerson(){
        totalPeople++;
    }

    public void removePerson(){
        totalPeople--;
    }

    public void upgradeBuilding(){
        super.upgradeBuilding();
        numRooms++;
    }

}