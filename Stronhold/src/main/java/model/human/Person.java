package model.human;

import model.Government;

public class Person {
    int hitPoint;
    Government government;

    public Person(Government government) {
        this.government = government;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Government getGovernment() {
        return government;
    }
}
