package model.building.Enums;

public enum ContainerDetails {
    STOCKPILE(BuildingsDetails.STOCKPILE, 100),
    ARMOURY(BuildingsDetails.ARMOURY, 50),
    GRANARY(BuildingsDetails.GRANARY, 60),
    STABLE(BuildingsDetails.STABLE, 4);

    private final BuildingsDetails buildingsDetails;
    private final int capacity;

    ContainerDetails(BuildingsDetails buildingsDetails, int capacity) {
        this.buildingsDetails = buildingsDetails;
        this.capacity = capacity;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getCapacity() {
        return capacity;
    }

    public static ContainerDetails getContainerByBuilding(BuildingsDetails buildingsDetails) {
        for (ContainerDetails containerDetails: ContainerDetails.values()) {
            if (containerDetails.buildingsDetails.equals(buildingsDetails)) return containerDetails;
        }
        return null;
    }
}
