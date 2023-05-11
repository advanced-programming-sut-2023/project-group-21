package controller;

import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.human.Engineer;
import model.human.Enums.WorkerDetails;
import model.human.Worker;
import model.machine.MachineDetails;

import java.util.ArrayList;

public class Command {
    private WorkerDetails workerDetails;
    private Worker worker;
    private BuildingsDetails buildingsDetails;
    private Building building;
    private String name, direction;
    private MachineDetails machineDetail;
    private ArrayList<Engineer> engineers=new ArrayList<>();
    private int x, y, soldierCount;

    public WorkerDetails getWorkerDetails() {
        return workerDetails;
    }

    public Worker getWorker() {
        return worker;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public Building getBuilding() {
        return building;
    }

    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public MachineDetails getMachineDetail() {
        return machineDetail;
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSoldierCount() {
        return soldierCount;
    }

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

    public Command(String name, MachineDetails machineDetail, int x, int y, ArrayList<Engineer> engineers) {
        this.name = name;
        this.machineDetail = machineDetail;
        this.x = x;
        this.y = y;
        //is the syntax of copying the arraylist OK??
        this.engineers=engineers;
    }
}
