package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;

import java.util.HashMap;

public class Trap extends Building {
    private boolean isLit = false;

    public Trap(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost) {
        super(government, buildingsDetails, hitPoint, cell, cost);
        isLit = false;
    }

    public void setOnFire(boolean lit) {
        isLit = lit;
    }
}
