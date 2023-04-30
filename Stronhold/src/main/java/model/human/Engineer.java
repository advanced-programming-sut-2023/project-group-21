package model.human;

import model.Cell;
import model.Government;
import model.building.Building;
import model.human.Enums.WorkerDetails;
import model.machine.Machine;

public class Engineer extends Worker {
    private Building workplace;
    private Machine machine;
    private boolean hasOil;

    public Engineer(WorkerDetails workerDetails, Government government, Cell position, Cell destination) {
        super(workerDetails, government, position, destination);
    }

    public Building getWorkplace() {
        return workplace;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setBuilding(Building building) {
        this.workplace = building;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }

    public boolean hasOil() {
        return hasOil;
    }
}
