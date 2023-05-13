package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ContainerDetails;
import model.generalenums.Resource;
import model.human.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Storage extends Building {
    private ContainerDetails containerDetails;

    public Storage(Government government, Cell cell, ContainerDetails containerDetails, ArrayList<Person> workers) {
        super(government, containerDetails.getBuildingsDetails(), cell, workers);
        this.containerDetails = containerDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return containerDetails.getBuildingsDetails();
    }

    public ContainerDetails getcontainerDetails(){
        return containerDetails;
    }

    public int getCapacity() {
        return containerDetails.getCapacity();
    }

}
