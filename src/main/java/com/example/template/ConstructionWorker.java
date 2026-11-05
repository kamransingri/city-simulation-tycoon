package com.example.template;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class ConstructionWorker extends Person{
    boolean fixing = false;

    public ConstructionWorker(int x, int y, Building connectedApartment, Building connectedWorkBuilding){
        super(x,y,"BDF829", connectedApartment, connectedWorkBuilding);
    }

    public void movePerson(String type, Button[][] btn, ArrayList<Building> buildings){
        if(findBrokenBuildings(buildings) != null){
            Building brokenBuilding = findBrokenBuildings(buildings);
            if(movetoHomeOrWork(btn, brokenBuilding)){
                brokenBuilding.fixBuilding();
                if(brokenBuilding.getCondition()>=brokenBuilding.getMaxCondition()/2){
                    brokenBuilding.changeUnderConstruction(false);
                }
            }
        }
    }

    public Building findBrokenBuildings(ArrayList<Building> buildings){
        boolean foundBuilding = false;
        int numSquares = 1;
        ArrayList<Building> possibleLocations = new ArrayList<>();
        while(!foundBuilding){
            for (int i = 0; i < buildings.size(); i++) {
                if (getWorkBuilding().getLocations().getFirst().getX() - numSquares <= buildings.get(i).getLocations().getFirst().getX()
                        && getWorkBuilding().getLocations().getFirst().getX() + numSquares >= buildings.get(i).getLocations().getFirst().getX()
                        && getWorkBuilding().getLocations().getFirst().getY() - numSquares <= buildings.get(i).getLocations().getFirst().getY()
                        && getWorkBuilding().getLocations().getFirst().getY() + numSquares >= buildings.get(i).getLocations().getFirst().getY()
                        && buildings.get(i).isVisible() && buildings.get(i).getCondition()<=buildings.get(i).getMaxCondition()/2){
                    possibleLocations.add(buildings.get(i));
                    System.out.println(buildings.get(i).getLocations().getFirst().getX() + ", " + buildings.get(i).getLocations().getFirst().getY());
                    foundBuilding = true;
                }
            }

            numSquares++;
            if(numSquares>((ConstructionFacility)getWorkBuilding()).getWorkRadius()){
                foundBuilding = true;
            }
        }
        if(possibleLocations.size()>0){
            return possibleLocations.get((int)(Math.random()*possibleLocations.size()));
        }
        return null;
    }


}
