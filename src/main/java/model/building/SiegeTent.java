package model.building;

import ServerConnection.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.human.Engineer;
import model.machine.MachineDetails;

import java.util.ArrayList;

public class SiegeTent extends Building {
    MachineDetails machineToMake;
    ArrayList<Engineer> engineers;

    public SiegeTent(Government government, BuildingsDetails buildingsDetails, Cell cell, MachineDetails machineToMake, ArrayList<Engineer> engineers) {
        super(government, buildingsDetails, cell, null);
        this.machineToMake = machineToMake;
        this.engineers = engineers;
    }

    public MachineDetails getMachineToMake() {
        return machineToMake;
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }
}
