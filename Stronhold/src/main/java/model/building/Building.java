package model.building;

import model.Cell;
import model.Government;
import model.generalenums.Resource;
import model.building.Enums.BuildingsDetails;
import model.human.Person;

import java.util.ArrayList;
import java.util.Map;

public class Building {
    private Government government;
    private BuildingsDetails buildingsDetails;
    private int hitPoint;
    private Cell cell;

    private ArrayList<Person> workers = new ArrayList<>();
    public Building(Government government, BuildingsDetails buildingsDetails, Cell cell, ArrayList<Person> workers) {
        this.government = government;
        this.buildingsDetails = buildingsDetails;
        this.hitPoint = buildingsDetails.getMaxHitPoints();
        this.cell = cell;
        this.workers = workers;
        for (Person person: workers)
            person.setWorkPlace(this);
    }

    public Government getGovernment() {
        return government;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Cell getCell() {
        return cell;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getMaxHitPoint() {
        return buildingsDetails.getMaxHitPoints();
    }

    public Map<Resource, Integer> getRequiredResource() {
        return buildingsDetails.getRequiredResource();
    }

    public String getName() {
        return getBuildingsDetails().name();
    }

    public void repairHitPoint(){
        hitPoint=buildingsDetails.getMaxHitPoints();
    }

    public ArrayList<Person> getWorkers() {
        return workers;
    }

    public int getRequiredWorkersCount() {
        return buildingsDetails.getWorkersCount();
    }
}
