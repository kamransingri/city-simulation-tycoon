package com.example.template;

import javafx.scene.control.Button;

import java.lang.annotation.Retention;

import java.util.ArrayList;

public class Person {
    private ArrayList<Location> locations = new ArrayList<>();
    private long startTime;
    private Building connectedWorkBuilding;
    private Building connectedApartment;
    private int health = 100;
    private boolean atHome = false;
    private int adder;
    private boolean canMove = true;
    private ArrayList<Location> pathTaken = new ArrayList<>();

    public Person(int x, int y,String color, Building connectedApartment, Building connectedWorkBuilding) {
        locations.add(new Location(x, y, color));
        this.connectedApartment = connectedApartment;
        this.connectedWorkBuilding = connectedWorkBuilding;
        startTime = System.nanoTime();
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void moveRandom(int numColumns, int numRows){
        int currentx=locations.getFirst().getX();
        int currenty=locations.get(0).getY();
        int ydirection = (int)(Math.random()*3);
        int xdirection = (int)(Math.random()*3);
        if (xdirection ==0){
            currentx = currentx+1;
        }else if(xdirection==1){
            currentx = currentx-1;
        }
        if (ydirection ==0){
            currenty = currenty+1;
        }else if(ydirection==1){
            currenty = currenty-1;
        }
        if(currentx < numColumns && currentx >-1 && currenty < numRows && currenty >-1){
            locations.getFirst().setX(currentx);
            locations.getFirst().setY(currenty);
        }

    }

    public boolean movetoHomeOrWork(Button[][] btn, Building targetDestination){
        int currentx =locations.getFirst().getX();
        int currenty =locations.getFirst().getY();

        if(getLocations().getFirst().getX() < targetDestination.getLocations().getFirst().getX()){
            currentx++;
        }
        if(getLocations().getFirst().getX() > targetDestination.getLocations().getFirst().getX()){
            currentx--;
        }
//
        if(getLocations().getFirst().getY() < targetDestination.getLocations().getFirst().getY()){
            currenty++;
        }
        if(getLocations().getFirst().getY() > targetDestination.getLocations().getFirst().getY()){
            currenty--;
        }
//

        if((btn[currentx][currenty].getStyle().equals("-fx-background-color: #ffffff; ") || checkForTarget(currentx, currenty, targetDestination)) && checkNewPath(currentx, currenty)){
            locations.getFirst().setX(currentx);
            locations.getFirst().setY(currenty);
            pathTaken.add(new Location(currentx, currenty, "ffffff"));
        }
        else if((btn[locations.getFirst().getX()][currenty].getStyle().equals("-fx-background-color: #ffffff; ")  || checkForTarget(locations.getFirst().getX(), currenty, targetDestination)) && checkNewPath(locations.getFirst().getX(), currenty)){
            locations.getFirst().setY(currenty);
            pathTaken.add(new Location(locations.getFirst().getX(), currenty, "ffffff"));
        }
        else if((btn[currentx][locations.getFirst().getY()].getStyle().equals("-fx-background-color: #ffffff; ") || checkForTarget(currentx, locations.getFirst().getY(), targetDestination))&& checkNewPath(currentx, locations.getFirst().getY())){
            locations.getFirst().setY(currentx);
            pathTaken.add(new Location(currentx, locations.getFirst().getY(), "ffffff"));
        }
        else if(checkForClosedPath(targetDestination, btn, currentx, currenty) != null){
            if(findClosestPath(checkForClosedPath(targetDestination, btn, currentx, currenty), btn) != null){
                System.out.println(checkForClosedPath(targetDestination, btn, currentx, currenty));
                System.out.println(findClosestPath(checkForClosedPath(targetDestination, btn, currentx, currenty), btn));
                moveToClosestPath(findClosestPath(checkForClosedPath(targetDestination, btn, currentx, currenty), btn));
            }

        }

        if(locations.getFirst().getX() == targetDestination.getLocations().getFirst().getX() && locations.getFirst().getY() == targetDestination.getLocations().getFirst().getY()){
            pathTaken.clear();
            return true;
        }

        return false;


    }

    public boolean checkNewPath(int x, int y){
        if(pathTaken.size()>0){
            if(pathTaken.getFirst().getX() == x && pathTaken.getFirst().getY() == y){
                return false;
            }
        }
        return true;
    }

    public boolean checkForTarget(int x, int y, Building targetDestination){
        return (x == targetDestination.getLocations().getFirst().getX() && y == targetDestination.getLocations().getFirst().getY());
    }

    public String checkForClosedPath(Building targetDestination, Button[][] btn, int x, int y){
        if(x < targetDestination.getLocations().getFirst().getX() && !btn[x+1][y].getStyle().equals("-fx-background-color: #ffffff; ") && !checkForTarget(x+1, y, targetDestination)){
            return "Right";
        }
        if(x > targetDestination.getLocations().getFirst().getX() && !btn[x-1][y].getStyle().equals("-fx-background-color: #ffffff; ") && !checkForTarget(x-1, y, targetDestination)){
            return "Left";
        }
        if(y < targetDestination.getLocations().getFirst().getY() && !btn[x][y+1].getStyle().equals("-fx-background-color: #ffffff; ") && !checkForTarget(x, y+1, targetDestination)){
            return "Down";
        }
        if(y > targetDestination.getLocations().getFirst().getY() && !btn[x][y-1].getStyle().equals("-fx-background-color: #ffffff; ")&& !checkForTarget(x, y-1, targetDestination)){
            return "Up";
        }
        return null;
    }
    public String findClosestPath(String blockedPath, Button[][] btn){
        boolean done = false;
        int x = getLocations().getFirst().getX();
        int y = getLocations().getFirst().getY();
        adder = 1;
        if (blockedPath.equals("Right") || blockedPath.equals("Left")){
            while(!done){
                if(btn[x][y+adder].getStyle().equals("-fx-background-color: #ffffff; ") && checkNewPath(x, y+adder)){
                    return "Down";
                }
                else if (btn[x][y-adder].getStyle().equals("-fx-background-color: #ffffff; ") && checkNewPath(x, y-adder)){
                    return "Up";
                }
                adder++;
            }
        }
        else{
            while(!done){
                if(btn[x+adder][y+1].getStyle().equals("-fx-background-color: #ffffff; ") && checkNewPath(x+adder, y+1)){
                    return "Right";
                }
                else if (btn[x-adder][y+1].getStyle().equals("-fx-background-color: #ffffff; ")&& checkNewPath(x-adder, y+1)){
                    return "Left";
                }
                adder++;
            }
        }
        return null;
    }

    public void moveToClosestPath(String direction){
        int x = locations.getFirst().getX();
        int y = locations.getFirst().getY();
        if(direction.equals("Right")){
            getLocations().getFirst().setX(x+1);
            pathTaken.add(new Location(locations.getFirst().getX(), y, "ffffff"));
        }
        else if(direction.equals("Left")){
            getLocations().getFirst().setX(x-1);
            pathTaken.add(new Location(locations.getFirst().getX(), y, "ffffff"));
        }
        else if(direction.equals("Up")){
            getLocations().getFirst().setY(y-1);
            pathTaken.add(new Location(x, locations.getFirst().getY(), "ffffff"));
        }
        else if(direction.equals("Down")){
            getLocations().getFirst().setY(y+1);
            pathTaken.add(new Location(x, locations.getFirst().getY(), "ffffff"));
        }
    }

    public boolean getAtHome(){
        return atHome;
    }

    public void setAtHome(boolean home){
        atHome = home;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        startTime = System.nanoTime();
    }

    public Building getHome(){
        return connectedApartment;
    }

    public Building getWorkBuilding(){
        return connectedWorkBuilding;
    }

    public void changeHome(Building newApartment){
        connectedApartment = newApartment;
    }
}
