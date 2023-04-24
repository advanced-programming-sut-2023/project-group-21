package model.building.Enums;

import model.human.Person;

import java.util.Arrays;
import java.util.List;

public enum TroopTrainerDetails {
    CATHEDRAL(BuildingsDetails.CATHEDRAL, 1),
    BARRACKS(BuildingsDetails.BARRACKS, 3),
    MERCENARY_POST(BuildingsDetails.MERCENARY_POST, 4),
    ENGINEERS_GUILD(BuildingsDetails.ENGINEERS_GUILD, 2);
    BuildingsDetails buildingsDetails;
    int rate;
    TroopTrainerDetails(BuildingsDetails buildingsDetails, int rate){}

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getRate() {
        return rate;
    }

    public static TroopTrainerDetails getTroopTrainerDetailsByBuildingDetails(BuildingsDetails buildingsDetails) {
        for (TroopTrainerDetails troopTrainerDetails: TroopTrainerDetails.values())
            if (troopTrainerDetails.buildingsDetails.equals(buildingsDetails)) return troopTrainerDetails;
        return null;
    }
}
