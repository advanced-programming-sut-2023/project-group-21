package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.ResidencyDetails;
import model.human.Person;

import java.util.ArrayList;

public class Gate extends Residency {
    private boolean direction;
    private boolean isOpen;

    public Gate(Government government, Cell cell, ResidencyDetails residencyDetails, ArrayList<Person> workers, boolean direction, boolean isOpen) {
        super(government, cell, residencyDetails, workers);
        this.direction = direction;
        this.isOpen = isOpen;
    }

    public void setGate(boolean state) {
        isOpen = state;
    }
    public boolean checkState(){
        return isOpen;
    }
}
