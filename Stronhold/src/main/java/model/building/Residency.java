package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ResidencyDetails;

public class Residency extends Building {
    ResidencyDetails residencyDetails;
    private int popularity;

    public Residency(Government government, Cell cell, ResidencyDetails residencyDetails) {
        super(government, residencyDetails.getBuildingsDetails(), cell);
        this.residencyDetails = residencyDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return residencyDetails.getBuildingsDetails();
    }

    public int getMaxPopularity() {
        return residencyDetails.getMaxPopularity();
    }
}
