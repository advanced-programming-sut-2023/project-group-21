package model;

import model.building.Building;
import model.generalenums.GroundTexture;
import model.generalenums.Extras;
import model.human.Person;
import model.human.Worker;
import model.machine.Machine;

import java.util.ArrayList;

public class Cell {
    private GroundTexture groundTexture = GroundTexture.SOIL;
    private Building building;
    private Machine machine;
    private Extras extra;
    private ArrayList<Worker> people;
    private int xCoordinates, yCoordinates;

    public Cell(int xCoordinates, int yCoordinates) {
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
    }
    public GroundTexture getGroundTexture() {
        return groundTexture;
    }

    public Building getBuilding() {
        return building;
    }

    public Machine getMachine() {
        return machine;
    }

    public ArrayList<Worker> getPeople() {
        return people;
    }

    public String showDetails(){
        StringBuilder details = new StringBuilder();
        details.append("Texture: ").append(getGroundTexture().getName()).append("\n");
        if (building != null)
            details.append("Building: ").append(building.getName()).append("\n");
        if (people.size() != 0) {
            details.append("People: ").append("\n");
            for (int i = 0; i < people.size(); i++) {
                boolean isRepeated = false;
                for (int j = 0; j < i; j++) {
                    if (people.get(j).getName().equals(people.get(i).getName())) {
                        isRepeated = true;
                        break;
                    }
                }
                if (isRepeated) continue;
                details.append(people.get(i));
                int number = 1;
                for (int j = i + 1; j < people.size(); j++) if (people.get(j).getName().equals(people.get(i).getName())) number++;
                details.append(" ").append(number).append("\n");
            }
        }
        details.append("All People: ").append(people.size());
        return details.toString();
    }

    public void setGroundTexture(GroundTexture groundTexture) {
        this.groundTexture = groundTexture;
    }
    public void setExtras(Extras extra) {
        this.extra = extra;
    }

    public Extras getExtra() {
        return extra;
    }

    public void clear() {
        building = null;
        machine = null;
        extra = null;
        people.clear();
        setGroundTexture(GroundTexture.SOIL);
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getxCoordinates() {
        return xCoordinates;
    }

    public int getyCoordinates() {
        return yCoordinates;
    }
}
