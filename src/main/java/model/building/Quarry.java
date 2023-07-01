package model.building;

import ServerConnection.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.human.Person;

import java.util.ArrayList;

public class Quarry extends Building {
    public final int maxCapacity = 24;
    private int capacity;

    public Quarry(Government government, Cell cell, ArrayList<Person> workers) {
        super(government, BuildingsDetails.QUARRY, cell, workers);
    }

    public int getCapacity() {
        return capacity;
    }

    public void addToCapacity(int number) {
        capacity += Math.min(maxCapacity - capacity, number);
    }
}
