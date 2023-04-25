package model.human;

import model.Resource;
import model.building.Building;
import model.human.Enums.WorkerDetails;

import java.util.List;

public class Worker extends Person {
    WorkerDetails workerDetails;

    public Worker(WorkerDetails workerDetails) {
        this.workerDetails = workerDetails;
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

    public List<Resource> getWeapon() {
        return workerDetails.getWeapon();
    }

    public List<Resource> getArmor() {
        return workerDetails.getArmor();
    }
}
