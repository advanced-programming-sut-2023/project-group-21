package model.building.Enums;

public enum StorageDetails {
    ARMOURY(BuildingsDetails.ARMOURY, 50),
    GRANARY(BuildingsDetails.GRANARY, 60),
    STOCKPILE(BuildingsDetails.STOCKPILE, 100);
    BuildingsDetails buildingsDetails;
    int capacity;

    StorageDetails(BuildingsDetails buildingsDetails, int capacity) {
        this.buildingsDetails = buildingsDetails;
        this.capacity = capacity;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getCapacity() {
        return capacity;
    }

    public static StorageDetails getStorageDetailsByBuildingDetails(BuildingsDetails buildingsDetails) {
        for (StorageDetails storageDetails: StorageDetails.values())
            if (storageDetails.buildingsDetails.equals(buildingsDetails)) return storageDetails;
        return null;
    }
}
