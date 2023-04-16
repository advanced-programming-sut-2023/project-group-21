package model.human.Enums;

import model.Resource;

import java.util.Arrays;
import java.util.List;

public enum EuropeanSoldierDetails {
    EUROPEAN_SOLDIER_DETAILS(MercenaryDetails.MERCENARY_DETAILS, Arrays.asList(Resource.RESOURCE));
    MercenaryDetails mercenaryDetails;
    List<Resource> requiredResources;
    EuropeanSoldierDetails(MercenaryDetails mercenaryDetails, List<Resource> requiredResources){}

    public MercenaryDetails getMercenaryDetails() {
        return mercenaryDetails;
    }

    public List<Resource> getRequiredResources() {
        return requiredResources;
    }
}
