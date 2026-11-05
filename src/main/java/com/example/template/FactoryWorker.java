package com.example.template;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class FactoryWorker extends Person{
    private int maxResourceCapacity = 5;
    private int currentResourceCapacity = 0;
    private boolean fullCapacity = false;
    public FactoryWorker(int x, int y,Building connectedApartment, Building connectedWorkBuilding ){
        super(x,y, "0AB075", connectedApartment, connectedWorkBuilding);
    }

    public void movePerson(Button[][] btn, ArrayList<Building> buildings){
        if(findClosestResource(buildings) != null && !fullCapacity){
            if(movetoHomeOrWork(btn, findClosestResource(buildings))){
                if(((ResourceArea)findClosestResource(buildings)).takeResource()){
                    currentResourceCapacity++;
                }
                if(currentResourceCapacity == maxResourceCapacity || !((ResourceArea)findClosestResource(buildings)).takeResource()){
                    fullCapacity = true;
                }
            }
        }
        else if(fullCapacity){
            if(movetoHomeOrWork(btn, getWorkBuilding())){
                ((Factory)getWorkBuilding()).addResources();
                currentResourceCapacity--;
                if(currentResourceCapacity == 0){
                    fullCapacity = false;
                }
            }
        }
    }

    public Building findClosestResource(ArrayList<Building> buildings){
        boolean foundBuilding = false;
        int numSquares = 1;
        ArrayList<Building> possibleLocations = new ArrayList<>();
        while(!foundBuilding){
            for (int i = 0; i < buildings.size(); i++) {
                if (getWorkBuilding().getLocations().getFirst().getX() - numSquares <= buildings.get(i).getLocations().getFirst().getX()
                        && getWorkBuilding().getLocations().getFirst().getX() + numSquares >= buildings.get(i).getLocations().getFirst().getX()
                        && getWorkBuilding().getLocations().getFirst().getY() - numSquares <= buildings.get(i).getLocations().getFirst().getY()
                        && getWorkBuilding().getLocations().getFirst().getY() + numSquares >= buildings.get(i).getLocations().getFirst().getY()
                        && buildings.get(i).isVisible() && buildings.get(i) instanceof ResourceArea){
                    possibleLocations.add(buildings.get(i));
//                    System.out.println(buildings.get(i).getLocations().getFirst().getX() + ", " + buildings.get(i).getLocations().getFirst().getY());
                    foundBuilding = true;
                }
            }

            numSquares++;
            if(numSquares>40){
                foundBuilding = true;
            }
        }
        if(possibleLocations.size()>0){
            return possibleLocations.get((int)(Math.random()*possibleLocations.size()));
        }
        return null;
    }
}
