package model.building;

import ServerConnection.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.human.Person;

import java.util.ArrayList;

public class Stable extends Building {
    private final int CAPACITY = 4, RATE = 1;
    private int availableHorses;
    public Stable(Government government, BuildingsDetails buildingsDetails, Cell cell, ArrayList<Person> workers) {
        super(government, buildingsDetails, cell, workers);
    }

    public int getAvailableHorses() {
        return availableHorses;
    }

    public void addHorse() {
        if (availableHorses < 4) availableHorses++;
    }
}
