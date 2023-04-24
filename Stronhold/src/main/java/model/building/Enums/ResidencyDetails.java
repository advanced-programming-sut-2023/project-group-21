package model.building.Enums;

import model.building.Residency;

public enum ResidencyDetails {
    SMALL_STONE_GATEHOUSE(BuildingsDetails.SMALL_STONE_GATEHOUSE, 8),
    BIG_STONE_GATEHOUSE(BuildingsDetails.BIG_STONE_GATEHOUSE, 10),
    HOVEL(BuildingsDetails.HOVEL, 8),
    DRAWBRIDGE(BuildingsDetails.DRAWBRIDGE, 0);

    BuildingsDetails buildingsDetails;
    int maxPopularity;

    ResidencyDetails(BuildingsDetails buildingsDetails, int maxPopularity) {
        this.buildingsDetails = buildingsDetails;
        this.maxPopularity = maxPopularity;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getMaxPopularity() {
        return maxPopularity;
    }

    public static ResidencyDetails getResidencyDetailsByBuildingDetails(BuildingsDetails buildingsDetails) {
        for (ResidencyDetails residencyDetails: ResidencyDetails.values())
            if (residencyDetails.buildingsDetails.equals(buildingsDetails)) return residencyDetails;
        return null;
    }
}
