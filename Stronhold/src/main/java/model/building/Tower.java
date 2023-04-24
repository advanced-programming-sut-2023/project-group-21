package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.TowerDetails;

public class Tower extends Building {
    TowerDetails towerDetails;
    private boolean isClimbable;

    public Tower(Government government, Cell cell, TowerDetails towerDetails) {
        super(government, towerDetails.getBuildingsDetails(), cell);
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
}
