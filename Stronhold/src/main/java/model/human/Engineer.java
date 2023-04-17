package model.human;

import model.building.Building;
import model.human.Enums.WorkerDetails;
import model.machine.Machine;

public class Engineer extends Worker {
    Building building;
    Machine machine;
    public Engineer(WorkerDetails workerDetails) {
        super(workerDetails);
    }

    public Building getBuilding() {
        return building;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
