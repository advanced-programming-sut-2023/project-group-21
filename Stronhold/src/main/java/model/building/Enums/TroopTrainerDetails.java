package model.building.Enums;

import model.human.Person;

import java.util.Arrays;
import java.util.List;

public enum TroopTrainerDetails {
    TROOP_TRAINER_DETAILS(BuildingsDetails.BUILDINGS_DETAILS, Arrays.asList(new Person()), 0);
    BuildingsDetails buildingsDetails;
    List<Person> troopsTrained;
    int rate;
    TroopTrainerDetails(BuildingsDetails buildingsDetails, List<Person> troopsTrained,int rate){}

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public List<Person> getTroopsTrained() {
        return troopsTrained;
    }

    public int getRate() {
        return rate;
    }
}
