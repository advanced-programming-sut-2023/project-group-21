package model.human;

import model.Resource;
import model.building.Building;
import model.human.Enums.EuropeanSoldierDetails;
import model.human.Enums.MercenaryDetails;
import model.human.Enums.WorkerDetails;

import java.util.List;

public class EuropeanSoldier extends Mercenary {
    Building workPlace;
    EuropeanSoldierDetails europeanSoldierDetails;

    public EuropeanSoldier(WorkerDetails workerDetails, MercenaryDetails mercenaryDetails, Building workPlace, EuropeanSoldierDetails europeanSoldierDetails) {
        super(workerDetails, mercenaryDetails);
        this.workPlace = workPlace;
        this.europeanSoldierDetails = europeanSoldierDetails;
    }

    public MercenaryDetails getMercenaryDetails() {
        return europeanSoldierDetails.getMercenaryDetails();
    }

    public List<Resource> getRequiredResources() {
        return europeanSoldierDetails.getRequiredResources();
    }
}
