package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;

import java.util.HashMap;

public class Building {
    private Government government;
    private BuildingsDetails buildingsDetails;
    private int hitPoint;
    private Cell cell;
    private HashMap<Resource, Integer> cost;

    public Government getGovernment() {
        return government;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Cell getCell() {
        return cell;
    }

    public HashMap<Resource, Integer> getCost() {
        return cost;
    }
    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getMaxHitPoints() {
        return buildingsDetails.getMaxHitPoints();
    }

    public HashMap<Resource, Integer> getRequiredResource() {
        return buildingsDetails.getRequiredResource();
    }

    public Building(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost) {
        this.government = government;
        this.buildingsDetails = buildingsDetails;
        this.hitPoint = hitPoint;
        this.cell = cell;
        this.cost = cost;
    }
}
