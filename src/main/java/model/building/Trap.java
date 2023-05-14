package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.human.Person;

import java.util.ArrayList;

public class Trap extends Building {
    private boolean isLit = false;
    private String name;

    public Trap(Government government, BuildingsDetails buildingsDetails, Cell cell, ArrayList<Person> workers) {
        super(government, buildingsDetails, cell, workers);
        isLit = false;
    }

    public void setOnFire(boolean lit) {
        isLit = lit;
    }
}
