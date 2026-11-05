package com.example.template;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class HelloController {
    public Label lblCityGDP, lblBuildingCondition, lblDayOfWeek, lblDayOrNight, lblUserFeedback;
    public ListView lstShopBuildings, lstGarageInfo, lstCityNews, lstRegBuildingInfo;
    public Tab tabBuildingInfo, tabGarageInfo;
    public TabPane tabPaneGameOptions;
    private Button[][] btn = new Button[30][15];
    //can change
    private ArrayList<Building>  buildings = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();
    private Building selectedBuilding;
    private boolean[] selected = new boolean[3];
    private Overlord overlord = new Overlord();
    private RandomEvents randomEvents;
    private DayTracker dayTracker = new DayTracker();
    @FXML
    private GridPane playBoard;

    public void initialize(){
        handleSetup();
        setUpShop();
        addStartingBuildings();
    }

    public void handleSetup() {
        playBoard.setGridLinesVisible(true);
        playBoard.setVisible(true);
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j] = new Button();
                btn[i][j].setPrefSize(40,40);
                playBoard.add(btn[i][j],i,j);
            }
        }
        EventHandler z = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int y = GridPane.getRowIndex(((Button) event.getSource()));
                int x = GridPane.getColumnIndex(((Button) event.getSource()));
                System.out.println("x:" + x);
                System.out.println("y:" + y);
                addPieces(x,y);
                getObject(x,y);


            }
        };

        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j].setOnAction(z);
            }
        }

    }



    public void addStartingBuildings(){
        buildings.add(new ResourceArea(0,0));
        buildings.add(new ResourceArea(0, btn[0].length-1));
        buildings.add(new ResourceArea(btn.length-1,0));
        buildings.add(new ResourceArea(btn.length-1, btn[0].length-1));
        buildings.add(new Factory(5,5));
        for (Building building: buildings){
            System.out.println(building.getLocations().getFirst().getX() + ", " + building.getLocations().getFirst().getY());
        }
    }

    public void setUpShop(){
        lstShopBuildings.getItems().clear();
        lstShopBuildings.getItems().add("Factory:  $50");
        lstShopBuildings.getItems().add("Apartment: $60");
        lstShopBuildings.getItems().add("Construction Facility: $70");

    }

    public void getSelectedShopItem(){
        for(int i = 0; i<selected.length; i++){
            selected[i] = false;
        }
        selected[lstShopBuildings.getSelectionModel().getSelectedIndex()] = true;
    }

    public void addPieces(int x, int y){
        if(selected[0] && btn[x][y].getStyle().equals("-fx-background-color: #ffffff; ") && overlord.getMoney()>=50){
            buildings.add(new Factory(x,y));
            overlord.changeMoney(-50);
        }
        else if(selected[1] && btn[x][y].getStyle().equals("-fx-background-color: #ffffff; ") && overlord.getMoney()>=60){
            buildings.add(new Apartment(x,y));
            overlord.changeMoney(-60);
        }
        else if(selected[2] && btn[x][y].getStyle().equals("-fx-background-color: #ffffff; ") && overlord.getMoney()>=70){
            buildings.add(new ConstructionFacility(x,y));
            overlord.changeMoney(-70);
        }
        else{
            lblUserFeedback.setText("Not enough money or something already there");
        }
    }

    public void getObject(int x, int y){
        for(int i = 0; i<buildings.size(); i++){
            if(buildings.get(i).getLocations().getFirst().getX() == x && buildings.get(i).getLocations().getFirst().getY() == y){
                selectedBuilding = buildings.get(i);
                showBuildingInformation();

            }
        }
    }

    public void showBuildingInformation(){
        tabPaneGameOptions.getSelectionModel().select(tabBuildingInfo);
        lstRegBuildingInfo.getItems().clear();
        lstRegBuildingInfo.getItems().add(" Max Condition: " + selectedBuilding.getMaxCondition());
        lstRegBuildingInfo.getItems().add(" Current Condition: " + selectedBuilding.getCondition());
        if(selectedBuilding instanceof Apartment){
            lstRegBuildingInfo.getItems().add("Max people / Num Rooms: " + ((Apartment)selectedBuilding).getNumRooms());
            lstRegBuildingInfo.getItems().add("Rooms taken: " + ((Apartment)selectedBuilding).getTotalPeople());
        }
        if(selectedBuilding instanceof Factory){
            lstRegBuildingInfo.getItems().add("Amount produced: " + ((Factory)selectedBuilding).getAmntProduced());
            lstRegBuildingInfo.getItems().add("Resources to use: " + ((Factory)selectedBuilding).getResourcesAvailable());
            lstRegBuildingInfo.getItems().add("Product per Resource : " + ((Factory)selectedBuilding).getResourceEfficiency());
        }
        if(selectedBuilding instanceof ConstructionFacility){
            lstRegBuildingInfo.getItems().add("Work Radius: " + ((ConstructionFacility)selectedBuilding).getWorkRadius());
        }
        lstRegBuildingInfo.getItems().add("Upgrade Cost " + selectedBuilding.getUpgradeCost());
    }

    public void updateScreen(){
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j].setStyle("-fx-background-color: #ffffff; ");
            }
        }

        for (Person person: people){
            for(Location loc: person.getLocations()){
                String temp = "-fx-background-color: #" + loc.getColor() + "; ";
                btn[loc.getX()][loc.getY()].setStyle(temp);

            }


        }
        for (Building building: buildings){
            for(Location loc: building.getLocations()){
                //all the locations have a color attributed to them
                //All the buildings and animals have access to their locations which means from there we can get the color
                String temp = "-fx-background-color: #" + loc.getColor() + "; ";
                if(building.isVisible()){
                    btn[loc.getX()][loc.getY()].setStyle(temp);
                }

            }


        }
        lblCityGDP.setText("Money/GDP: $" + overlord.getMoney());
        lblDayOfWeek.setText("Day: " + dayTracker.getDay());
        lblDayOrNight.setText("Time: " + dayTracker.getDayOrNight());

    }


    public void handleStartAnimation(ActionEvent actionEvent) {
        randomEvents = new RandomEvents();
        new AnimationTimer(){
            @Override
            public void handle(long now) {
            if (buildings.size()>0){
                for(int i = buildings.size()-1; i>=0; i--){
                    if(!(buildings.get(i) instanceof ResourceArea)){
                        if(now - buildings.get(i).getDamageTimer() > buildings.get(i).getDurability() && !buildings.get(i).getUnderConstruction()){
                            buildings.get(i).changeCondition();
                            buildings.get(i).setDamageTimer();
                        }
                    }
                    if(buildings.get(i) instanceof Factory){
                        if(now - ((Factory) buildings.get(i)).getProductionTimer() > ((Factory) buildings.get(i)).getProductionInterval()){
                            ((Factory) buildings.get(i)).produceProduct();
                            ((Factory) buildings.get(i)).setProductionTimer();
                        }
                    }
                    if(buildings.get(i).getCondition()<=0){
                        if(buildings.get(i) instanceof Apartment){
                            for(int j = people.size()-1; j>=0; ){
                                if(people.get(j).getHome().equals(buildings.get(i))){
                                    if(getEmptyApartment() != null && !getEmptyApartment().equals(buildings.get(i))){
                                        people.get(j).changeHome(getEmptyApartment());
                                    }
                                    else{
                                        people.remove(j);
                                    }
                            }

                            }
                        }
                        else{
                            for(int j = people.size()-1; j>=0; j--){
                                if(people.get(j).getHome().equals(buildings.get(i))){
                                    ((Apartment)people.get(j).getHome()).removePerson();
                                    people.remove(j);
                                }
                            }
                        }
                        buildings.remove(i);

                    }
                }
            }

            if(people.size()>0){
                for (int i = people.size()-1; i >= 0; i--) {
                    if(now - people.get(i).getStartTime() > 500000000){
                        if(dayTracker.getDayOrNight().equals("Day") && people.get(i).getAtHome()){
                            if(people.get(i).movetoHomeOrWork(btn, people.get(i).getWorkBuilding())){
                                people.get(i).setAtHome(false);
                            }
                        }
                        if(dayTracker.getDayOrNight().equals("Night") && !people.get(i).getAtHome()){
                            if(people.get(i).movetoHomeOrWork(btn, people.get(i).getHome())){
                                people.get(i).setAtHome(true);
                            }
                        }
                        if(people.get(i) instanceof ConstructionWorker && dayTracker.getDayOrNight().equals("Day") && !people.get(i).getAtHome()){
                            System.out.println("Move Construction worker");
                            ((ConstructionWorker) people.get(i)).movePerson("", btn, buildings);
                        }
                        if(people.get(i) instanceof FactoryWorker && dayTracker.getDayOrNight().equals("Day") && !people.get(i).getAtHome()){
                            ((FactoryWorker)people.get(i)).movePerson(btn, buildings);
                        }
                        people.get(i).setStartTime();
                    }

                }
            }


            if(now - dayTracker.getDayTimer() > 20000000000.0){
                dayTracker.changeNightDay();
                dayTracker.setDayTimer();
                setResourceSpots();
                for(int i = 0; i<buildings.size(); i++){
                    if(buildings.get(i) instanceof Factory){
                        ((Factory) buildings.get(i)).sellProduct(overlord);
                    }
                }
            }
            if(now - randomEvents.getStartTime() > 80000000000.0){
                //Every 2 days
                randomEvents.RandomEventChooser(buildings, people);
                if(!randomEvents.getBreakingNews().isEmpty()){
                    lstCityNews.getItems().add(randomEvents.getBreakingNews());
                }
                randomEvents.setStartTime();
            }

                updateScreen();
            }

        }.start();
    }

    public void handleHireWorker(ActionEvent actionEvent) {
        if(overlord.getMoney()>=20){
            if(getEmptyApartment()!=null){
                if(selectedBuilding instanceof Factory){
                    people.add(new FactoryWorker(selectedBuilding.getLocations().getFirst().getX(), selectedBuilding.getLocations().getFirst().getY(), getEmptyApartment(), selectedBuilding));
                }
                else if(selectedBuilding instanceof ConstructionFacility){
                    people.add(new ConstructionWorker(selectedBuilding.getLocations().getFirst().getX(), selectedBuilding.getLocations().getFirst().getY(), getEmptyApartment(), selectedBuilding));
                }
                ((Apartment)getEmptyApartment()).addPerson();
                overlord.changeMoney(-20);
            }
            else{
                lblUserFeedback.setText("There is not enough apartment space for new workers");
            }
        }
        else{
            lblUserFeedback.setText("Not Enough Money for a worker!");
        }


    }

    public Building getEmptyApartment(){
        for(int i = 0; i<buildings.size(); i++){
            if(buildings.get(i) instanceof Apartment){
                if(((Apartment)buildings.get(i)).getTotalPeople() < ((Apartment)buildings.get(i)).getNumRooms()){
                    return buildings.get(i);
                }
            }
        }
        return null;
    }

    public void upgradeBuilding(){
        if(overlord.getMoney()>=selectedBuilding.getUpgradeCost()){
            overlord.changeMoney(-selectedBuilding.getUpgradeCost());
            selectedBuilding.upgradeBuilding();
        }
        else{
            lblUserFeedback.setText("Not Enough money!");
        }
    }

    public void setResourceSpots(){
        ArrayList<Building> resources = new ArrayList<>();
        for(Building building: buildings){
            if(building instanceof ResourceArea){
                resources.add(building);
                ((ResourceArea) building).replenishResources();
            }
        }
        int numResources = 0;
        if(dayTracker.getDay().equals("Saturday") || dayTracker.getDay().equals("Sunday")){
            numResources += (int)(Math.random()*2);
        }
        else{
            numResources += (int)(Math.random()*2) + 1;
        }
        if(dayTracker.getDayOrNight().equals("Day")){
            numResources += (int)(Math.random()*2) + 1;
        }
        else{
            numResources += (int)(Math.random()*2);
        }

        for(Building resource: resources){
            resource.setVisible(false);
        }

        while(numResources>0){
            int randIndex = (int)(Math.random()*resources.size());
            if(!resources.get(randIndex).isVisible()){
                resources.get(randIndex).setVisible(true);
                numResources--;
            }
        }
    }
}
