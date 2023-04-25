package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.StorageDetails;

import java.util.HashMap;
import java.util.List;

public class Storage extends Building {
    StorageDetails storageDetails;

    public Storage(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost, StorageDetails storageDetails) {
        super(government, buildingsDetails, hitPoint, cell, cost);
        this.storageDetails = storageDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return storageDetails.getBuildingsDetails();
    }

    public List<Resource> getAvailableResources() {
        return storageDetails.getAvailableResources();
    }

    public int getCapacity() {
        return storageDetails.getCapacity();
    }
}
