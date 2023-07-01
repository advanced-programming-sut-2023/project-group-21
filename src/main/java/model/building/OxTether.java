package model.building;

import ServerConnection.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.human.Person;

import java.util.ArrayList;

public class OxTether extends Building {
    public final int capacity = 12;

    public OxTether(Government government, BuildingsDetails buildingsDetails, Cell cell, ArrayList<Person> workers) {
        super(government, buildingsDetails, cell, workers);
    }
}
