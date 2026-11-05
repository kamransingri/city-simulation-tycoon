package com.example.template;

public class Factory extends Building{
    private long productionTimer;
    private long productionInterval = 1000000000;
    private int amntProduced = 0;
    private int resourcesAvailable = 0;
    private int resourceEfficiency = 1;


    public Factory(int x, int y){
        super(x,y, "918C85", true, 2000000000);
        productionTimer = System.nanoTime();
    }

    public void setProductionTimer(){
        productionTimer = System.nanoTime();
    }

    public long getProductionTimer(){
        return productionTimer;
    }

    public long getProductionInterval(){
        return productionInterval;
    }

    public void produceProduct(){
        if(resourcesAvailable>0){
            amntProduced += resourceEfficiency;
            resourcesAvailable--;
        }

    }

    public void sellProduct(Overlord overlord){
        overlord.changeMoney(amntProduced);
        amntProduced = 0;
    }

    public void addResources(){
        resourcesAvailable++;
    }

    public int getAmntProduced(){
        return amntProduced;
    }

    public void upgradeBuilding(){
        super.upgradeBuilding();
        productionInterval -= 50000000;
        resourceEfficiency ++;

    }

    public int getResourcesAvailable(){
        return resourcesAvailable;
    }

    public int getResourceEfficiency(){
        return resourceEfficiency;
    }



}
