package model.building;

import model.Cell;
import model.Government;
import model.generalenums.Resource;
import model.building.Enums.BuildingsDetails;

import java.util.Map;

public class Building {
    private Government government;
    private BuildingsDetails buildingsDetails;
    private int hitPoint;
    private Cell cell;
    private boolean isWrecked = false;
    public Building(Government government, BuildingsDetails buildingsDetails, Cell cell) {
        this.government = government;
        this.buildingsDetails = buildingsDetails;
        this.hitPoint = buildingsDetails.getMaxHitPoints();
        this.cell = cell;
    }

    public Government getGovernment() {
        return government;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Cell getCell() {
        return cell;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getHitPoints() {
        return buildingsDetails.getMaxHitPoints();
    }

    public Map<Resource, Integer> getRequiredResource() {
        return buildingsDetails.getRequiredResource();
    }

    public String getName() {
        return getBuildingsDetails().name();
    }

    public boolean isWrecked() {
        return isWrecked;
    }
}
