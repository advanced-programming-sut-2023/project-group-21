package model.building.Enums;

import model.human.Enums.MercenaryDetails;
import model.human.Enums.WorkerDetails;
import model.human.Mercenary;

import java.util.Arrays;
import java.util.List;

public enum TowerDetails {
    TOWER_DETAILS(BuildingsDetails.BUILDINGS_DETAILS, 0, 0, Arrays.asList(new Mercenary(WorkerDetails.WORKER_DETAILS, MercenaryDetails.MERCENARY_DETAILS)));
    BuildingsDetails buildingsDetails;
    int fireRange, defenseRange;
    List<Mercenary> soldiers;
    TowerDetails(BuildingsDetails buildingsDetails, int fireRange, int defenseRange, List<Mercenary> soldiers){}

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefenseRange() {
        return defenseRange;
    }

    public List<Mercenary> getSoldiers() {
        return soldiers;
    }
}
