package controller;

import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.human.Enums.WorkerDetails;
import model.human.Worker;
import model.machine.Machine;
import model.machine.MachineDetails;

public class Command {
    WorkerDetails workerDetails;
    Worker worker;
    BuildingsDetails buildingsDetails;
    Building building;
    String name, direction;
    MachineDetails machineDetail;
    int x, y, soldierCount;

    public Command(String name, BuildingsDetails buildingsDetails, int x, int y) {
        this.name = name;
        this.buildingsDetails = buildingsDetails;
        this.x = x;
        this.y = y;
    }

    public Command(String name, WorkerDetails workerDetails, int soldierCount) {
        this.workerDetails = workerDetails;
        this.name = name;
        this.soldierCount = soldierCount;
    }

    public Command(String name, Building building) {
        this.building = building;
        this.name = name;
    }

    public Command(String name, Worker worker, int x, int y) {
        this.name = name;
        this.worker = worker;
        this.x = x;
        this.y = y;
    }

    public Command(String name, Worker worker, String direction) {
        this.worker = worker;
        this.name = name;
        this.direction = direction;
    }

    public Command(String name, Worker worker, int x, int y, String direction) {
        this.worker = worker;
        this.name = name;
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public Command(String name, MachineDetails machineDetail, int x, int y) {
        this.name = name;
        this.machineDetail = machineDetail;
        this.x = x;
        this.y = y;
    }
}
