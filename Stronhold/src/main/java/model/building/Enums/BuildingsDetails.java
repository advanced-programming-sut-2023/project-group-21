package model.building.Enums;

import model.Resource;

import java.util.HashMap;

public enum BuildingsDetails {
    BUILDINGS_DETAILS(0, HashMap.newHashMap(0));
    int maxHitPoints;
    HashMap<Resource, Integer> requiredResource;
    BuildingsDetails(int maxHitPoints, HashMap<Resource, Integer> requiredResource){}

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public HashMap<Resource, Integer> getRequiredResource() {
        return requiredResource;
    }
}
