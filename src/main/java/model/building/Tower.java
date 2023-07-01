package model.building;

import ServerConnection.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.TowerDetails;
import model.human.Person;

import java.util.ArrayList;

public class Tower extends Building {
    TowerDetails towerDetails;
    private boolean isClimbable;

    public Tower(Government government, Cell cell, TowerDetails towerDetails, ArrayList<Person> workers) {
        super(government, towerDetails.getBuildingsDetails(), cell, workers);
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
