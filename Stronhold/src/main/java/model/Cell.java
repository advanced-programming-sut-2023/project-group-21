package model;

import model.building.Building;
import model.human.Person;
import model.machine.Machine;

import java.util.ArrayList;

public class Cell {
    GroundTexture groundTexture;
    int xCoordinates, yCoordinates;
    Building building;
    Machine machine;
    ArrayList<Person> people;

    public GroundTexture getGroundTexture() {
        return groundTexture;
    }

    public int getXCoordinates() {
        return xCoordinates;
    }

    public int getYCoordinates() {
        return yCoordinates;
    }

    public Building getBuilding() {
        return building;
    }

    public Machine getMachine() {
        return machine;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public String showDetails(){
        return null;
    }
}
