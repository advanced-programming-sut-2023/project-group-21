package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.ResidencyDetails;

public class Gate extends Residency {
    private boolean direction;
    private boolean isOpen;

    public Gate(Government government, Cell cell, ResidencyDetails residencyDetails, boolean direction, boolean isOpen) {
        super(government, cell, residencyDetails);
        this.direction = direction;
        this.isOpen = isOpen;
    }
    public boolean checkState(){
        return isOpen;
    }
    public void openGate() {
        isOpen = true;
    }
}
