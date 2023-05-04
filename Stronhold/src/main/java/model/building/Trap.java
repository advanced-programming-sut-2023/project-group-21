package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;

public class Trap extends Building {
    private boolean isLit = false;
    private String name;

    public Trap(Government government, BuildingsDetails buildingsDetails, Cell cell) {
        super(government, buildingsDetails, cell);
        isLit = false;
    }

    public void setOnFire(boolean lit) {
        isLit = lit;
    }
}
