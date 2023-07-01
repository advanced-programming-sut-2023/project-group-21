package model.building;

import ServerConnection.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ResidencyDetails;
import model.human.Person;

import java.util.ArrayList;

public class Residency extends Building {
    ResidencyDetails residencyDetails;
    private int popularity;

    public Residency(Government government, Cell cell, ResidencyDetails residencyDetails, ArrayList<Person> workers) {
        super(government, residencyDetails.getBuildingsDetails(), cell, workers);
        this.residencyDetails = residencyDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return residencyDetails.getBuildingsDetails();
    }

    public int getMaxPopularity() {
        return residencyDetails.getMaxPopularity();
    }
}
