package model.building.Enums;

import model.Resource;

import java.util.Arrays;
import java.util.List;

public enum StorageDetails {
    STORAGE_DETAILS(BuildingsDetails.BUILDINGS_DETAILS, Arrays.asList(Resource.RESOURCE), 0);
    BuildingsDetails buildingsDetails;
    List<Resource> availableResources;
    int capacity;

    StorageDetails(BuildingsDetails buildingsDetails, List<Resource> availableResources, int capacity) {};

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public List<Resource> getAvailableResources() {
        return availableResources;
    }

    public int getCapacity() {
        return capacity;
    }
}
