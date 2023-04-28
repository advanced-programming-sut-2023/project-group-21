package model.human;

import model.Cell;
import model.human.Enums.WorkerDetails;

public class Worker extends Person {
    private String state;
    WorkerDetails workerDetails;
    Cell position;
    Cell destination;

    public Worker(WorkerDetails workerDetails, Cell position, Cell destination) {
        this.workerDetails = workerDetails;
        this.position = position;
        this.destination = destination;
        state = "standing";
    }

    public String getName() {
        return workerDetails.getName();
    }

    public int getMaxHitPoint() {
        return workerDetails.getMaxHitPoint();
    }

    public int getDamage() {
        return workerDetails.getDamage();
    }

    public int getDefense() {
        return workerDetails.getDefense();
    }

    public int getSpeed() {
        return workerDetails.getSpeed();
    }

    public int getRange() {
        return workerDetails.getRange();
    }

    public Cell getPosition() {
        return position;
    }

    public Cell getDestination() {
        return destination;
    }

    public void setDestination(Cell destination) {
        this.destination = destination;
    }

    public void setState(String state) {
        this.state = state;
    }


}
