package model.machine;

import model.Cell;
import model.Government;
import model.human.Engineer;
import model.human.Enums.WorkerDetails;

import java.util.ArrayList;

public class Machine {
    private MachineDetails machineDetails;
    private Government government;
    private Cell cell;
    int hitPoint = 0;
    private ArrayList<Engineer> engineers;

    public Machine(MachineDetails machineDetails, Government government, Cell cell, ArrayList<Engineer> engineers) {
        this.machineDetails = machineDetails;
        this.government = government;
        this.cell = cell;
        cell.addMachine(this);
        this.engineers = engineers;
        for (Engineer engineer: engineers)
            hitPoint += engineer.getHitPoint();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

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

    public void getDamaged(int damage) {
        hitPoint -= damage;
    }

    public ArrayList<Engineer> getEngineers(){
        return engineers;
    }

    public int getHitPoint(){
        return hitPoint;
    }

    public MachineDetails getMachineDetails() {
        return machineDetails;
    }

    public String getName() {
        return machineDetails.getName();
    }
}
