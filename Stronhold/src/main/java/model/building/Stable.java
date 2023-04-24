package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;

public class Stable extends Building {
    private final int CAPACITY = 4, RATE = 1;
    public Stable(Government government, BuildingsDetails buildingsDetails, Cell cell) {
        super(government, buildingsDetails, cell);
    }

}
