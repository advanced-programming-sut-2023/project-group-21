package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ProductMakerDetails;

import java.util.HashMap;

public class Quarry extends ProductMaker {
    private final int capacity = 12;

    public Quarry(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost, ProductMakerDetails productMakerDetails) {
        super(government, buildingsDetails, hitPoint, cell, cost, productMakerDetails);
    }
}
