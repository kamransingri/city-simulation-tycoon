package com.example.template;

import java.util.ArrayList;

public class ResourceArea extends Building{
    private int currentResource = 50;
    private int maxResource = 50;
    public ResourceArea(int x, int y){
        super(x, y, "338537", true, 100);
    }

    public int getMaxResource(){
        return maxResource;
    }

    public boolean takeResource(){
        if(currentResource>0){
            currentResource--;
            return true;
        }
        return false;
    }

    public void replenishResources(){
        currentResource = maxResource;
    }
}
