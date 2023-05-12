package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ProductMakerDetails;
import model.human.Person;

import java.util.ArrayList;

public class Quarry extends Building {
    private final int capacity = 12;

    public Quarry(Government government, Cell cell, ArrayList<Person> workers) {
        super(government, BuildingsDetails.QUARRY, cell, workers);
    }

}
