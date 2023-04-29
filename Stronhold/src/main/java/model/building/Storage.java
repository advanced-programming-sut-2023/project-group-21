package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.StorageDetails;
import model.generalenums.Resource;

import java.util.HashMap;
import java.util.Map;

public class Storage extends Building {
    private StorageDetails storageDetails;
    private Map<Resource, Integer> availableResources = new HashMap<>();

    public Storage(Government government, Cell cell, StorageDetails storageDetails) {
        super(government, storageDetails.getBuildingsDetails(), cell);
        this.storageDetails = storageDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return storageDetails.getBuildingsDetails();
    }

    public int getCapacity() {
        return storageDetails.getCapacity();
    }

    public int getOccupation(StorageDetails details){
        int sum = 0;
        for (Map.Entry<Resource, Integer> entry : availableResources.entrySet()) {
            Resource resource = entry.getKey();
            Integer amount = entry.getValue();
            if(resource.getResourceKeeper() == details)
                sum += amount;
        }
        return sum;
    }

    public Map<Resource, Integer> getAvailableResources() {
        return availableResources;
    }
}
