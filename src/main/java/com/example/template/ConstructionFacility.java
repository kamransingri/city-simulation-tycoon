package com.example.template;

public class ConstructionFacility extends Building{
    private int workRadius = 6;

    public ConstructionFacility(int x, int y){
        super(x,y, "F8A229", true, 4000000000L);
    }

    public int getWorkRadius(){
        return workRadius;
    }

    public void upgradeBuilding(){
        super.upgradeBuilding();
        workRadius++;
    }
}
