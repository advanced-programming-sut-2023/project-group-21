package model.human.Enums;

import model.generalenums.Resource;

import java.util.Arrays;
import java.util.List;

public enum EuropeanSoldiersDetails {
    ARCHER(WorkerDetails.ARCHER, Arrays.asList(Resource.BOW)),
    CROSSBOWMAN(WorkerDetails.CROSSBOWMAN, Arrays.asList(Resource.BOW, Resource.LEATHER_ARMOR)),
    SPEARMAN(WorkerDetails.SPEARMAN, Arrays.asList(Resource.SPEAR)),
    PIKEMAN(WorkerDetails.PIKEMAN, Arrays.asList(Resource.PIKE, Resource.METAL_ARMOR)),
    MACEMAN(WorkerDetails.MACEMAN, Arrays.asList(Resource.MACE, Resource.LEATHER_ARMOR)),
    SWORDSMAN(WorkerDetails.SWORDSMAN, Arrays.asList(Resource.SWORD, Resource.METAL_ARMOR)),
    KNIGHT(WorkerDetails.KNIGHT, Arrays.asList(Resource.SWORD, Resource.METAL_ARMOR, Resource.HORSE));
    private final WorkerDetails workerDetails;
    private final List<Resource> equipments;

    EuropeanSoldiersDetails(WorkerDetails workerDetails, List<Resource> equipments) {
        this.workerDetails = workerDetails;
        this.equipments = equipments;
    }

    public static EuropeanSoldiersDetails getDetailsByWorkerDetails(WorkerDetails workerDetails) {
        for (EuropeanSoldiersDetails soldiersDetails: EuropeanSoldiersDetails.values())
            if (soldiersDetails.workerDetails.equals(workerDetails)) return soldiersDetails;
        return null;
    }

    public List<Resource> getEquipments() {
        return equipments;
    }
}
