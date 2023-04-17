package model.building.Enums;

public enum ResidencyDetails {
    RESIDENCY(BuildingsDetails.BUILDINGS_DETAILS, 0);

    BuildingsDetails buildingsDetails;
    int maxPopularity;
    ResidencyDetails(BuildingsDetails buildingsDetails, int maxPopularity){}

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getMaxPopularity() {
        return maxPopularity;
    }
}
