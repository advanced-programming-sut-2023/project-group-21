package model.machine;

import model.Cell;
import model.Government;

public class Machine {
    MachineDetails machineDetails;
    Government government;
    Cell cell;

    public Government getGovernment() {
        return government;
    }

    public Cell getCell() {
        return cell;
    }

    public int getDamage() {
        return machineDetails.getDamage();
    }

    public int getDefense() {
        return machineDetails.getDefense();
    }

    public int getEngineersNeeded() {
        return machineDetails.getEngineersNeeded();
    }

    public int getFireRange() {
        return machineDetails.getFireRange();
    }

    public int getSpeed() {
        return machineDetails.getSpeed();
    }
}
