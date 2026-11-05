package com.example.template;

import java.util.ArrayList;

public class Building {
    private ArrayList<Location> locations = new ArrayList<>();
    private boolean visible;
    private int upgradeCost = 40;
    private long damageTimer;
    private long durability;
    private int condition = 100;
    private int maxCondition = 100;
    private boolean underConstruction = false;


    //Food available could go here if it is not tied to a specific building

    public Building(){

    }
    public Building(int x, int y, String color,boolean visible, long durability){
        locations.add(new Location(x,y,color));
        this.visible = visible;
        damageTimer = System.nanoTime();
        this.durability = durability;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public long getDamageTimer(){
        return damageTimer;
    }

    public void setDamageTimer() {
        damageTimer = System.nanoTime();
    }

    public long getDurability(){
        return durability;
    }

    public void changeCondition(){
        condition--;
    }

    public int getCondition(){
        return condition;
    }

    public void damageBuilding(int damage){
        condition -= damage;
    }

    public int getMaxCondition(){
        return maxCondition;
    }

    public void fixBuilding(){
        if(condition<maxCondition){
            condition += 1;
        }
    }

    public boolean getUnderConstruction(){
        return underConstruction;
    }

    public void changeUnderConstruction(boolean beingWorkedOn){
        underConstruction = beingWorkedOn;
    }

    public void upgradeBuilding(){
        upgradeCost += 40;
        maxCondition += 20;
    }

    public int getUpgradeCost(){
        return upgradeCost;
    }





}
