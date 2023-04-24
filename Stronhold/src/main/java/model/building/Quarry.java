package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.ProductMakerDetails;

public class Quarry extends ProductMaker {
    private final int capacity = 12;

    public Quarry(Government government, Cell cell, ProductMakerDetails productMakerDetails) {
        super(government, cell, productMakerDetails);
    }

}
