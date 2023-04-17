package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.TroopTrainerDetails;
import model.human.Person;

import java.util.HashMap;
import java.util.List;

public class TroopTrainer extends Building {
    TroopTrainerDetails troopTrainerDetails;

    public TroopTrainer(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost, TroopTrainerDetails troopTrainerDetails) {
        super(government, buildingsDetails, hitPoint, cell, cost);
        this.troopTrainerDetails = troopTrainerDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return troopTrainerDetails.getBuildingsDetails();
    }

    public List<Person> getTroopsTrained() {
        return troopTrainerDetails.getTroopsTrained();
    }

    public int getRate() {
        return troopTrainerDetails.getRate();
    }
}
