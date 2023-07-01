package model.human;

import model.Government;
import model.building.Building;

import java.io.Serializable;

public class Person implements Serializable {
    int hitPoint = 15;
    Government government;
    Building workPlace = null;

    public Person(Government government) {
        this.government = government;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Government getGovernment() {
        return government;
    }

    public void delete() {
        government.deletePerson(this);
    }

    public void setWorkPlace(Building workPlace) {
        this.workPlace = workPlace;
    }

    public Building getWorkPlace() {
        return workPlace;
    }


}
