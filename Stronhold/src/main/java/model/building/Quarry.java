package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ProductMakerDetails;

public class Quarry extends Building {
    private final int capacity = 12;

    public Quarry(Government government, Cell cell) {
        super(government, BuildingsDetails.QUARRY, cell);
    }

}
