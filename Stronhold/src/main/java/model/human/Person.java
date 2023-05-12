package model.human;

import model.Government;
import model.building.Building;

public class Person {
    int hitPoint;
    Government government;
    Building workPlace;

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
