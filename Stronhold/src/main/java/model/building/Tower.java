package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.TowerDetails;
import model.human.Mercenary;

import java.util.HashMap;
import java.util.List;

public class Tower extends Building {
    TowerDetails towerDetails;
    private boolean isClimbable;

    public Tower(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost, TowerDetails towerDetails) {
        super(government, buildingsDetails, hitPoint, cell, cost);
        this.towerDetails = towerDetails;
        isClimbable = false;
    }

    public BuildingsDetails getBuildingsDetails() {
        return towerDetails.getBuildingsDetails();
    }

    public int getFireRange() {
        return towerDetails.getFireRange();
    }

    public int getDefenseRange() {
        return towerDetails.getDefenseRange();
    }

    public void setClimbable(boolean climbable) {
        isClimbable = climbable;
    }

    public boolean isClimbable() {
        return isClimbable;
    }

    public List<Mercenary> getSoldiers() {
        return towerDetails.getSoldiers();
    }
}
