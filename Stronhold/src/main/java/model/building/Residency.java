package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ResidencyDetails;

import java.util.HashMap;

public class Residency extends Building {
    ResidencyDetails residencyDetails;
    private int popularity;

    public Residency(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost, ResidencyDetails residencyDetails) {
        super(government, buildingsDetails, hitPoint, cell, cost);
        this.residencyDetails = residencyDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return residencyDetails.getBuildingsDetails();
    }

    public int getMaxPopularity() {
        return residencyDetails.getMaxPopularity();
    }
}
