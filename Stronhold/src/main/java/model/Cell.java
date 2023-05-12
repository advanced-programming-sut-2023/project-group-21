package model;

import controller.GameController;
import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.building.Gate;
import model.generalenums.GroundTexture;
import model.generalenums.Extras;
import model.human.Person;
import model.human.Worker;
import model.machine.Machine;

import java.util.ArrayList;

public class Cell {
    private GroundTexture groundTexture = GroundTexture.SOIL;
    private int distanceOfStart=0;
    private int distanceOfDestination=0;
    private int totalDistance=0;
    private Building building;
    private ArrayList<Machine> machines = new ArrayList<>();
    private Extras extra;
    private ArrayList<Worker> people;
    private int xCoordinates, yCoordinates;
    private boolean hadCross = false,hasOil=false,hasHole=false;
    private  char direction;
    private boolean hasLadder = false;
    public Cell(int xCoordinates, int yCoordinates) {
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        people = new ArrayList<>();
    }
    public void setDistanceOfStart(int distanceOfStart){
        this.distanceOfStart = distanceOfStart;
    }

    private void updateTotalDistance() {
        totalDistance = distanceOfStart + distanceOfDestination;
    }

    public int getDistanceOfDestination() {
        return distanceOfDestination;
    }

    public int getDistanceOfStart() {
        return distanceOfStart;
    }

    public void setDistanceOfDestination(int distanceOfDestination) {
        this.distanceOfDestination = distanceOfDestination;
    }

    public GroundTexture getGroundTexture() {
        return groundTexture;
    }

    public Building getBuilding() {
        return building;
    }

    public ArrayList<Machine> getMachine() {
        return machines;
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
        machines.clear();
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
    public String makeSaveCode(){
        String temp = "";
        if(extra == null)
            temp += "!";
        else
            temp += extra.getSaveCode();
        temp += groundTexture.getSaveCode();
        return temp;
    }

    public int getTotal() {
        return totalDistance;
    }
    public void cross() {
        hadCross = true;
    }
    public String toString() {
        return "(" + xCoordinates + "," + yCoordinates + ") ";
    }

    public boolean isCross() {
        return hadCross;
    }

    public char getDirection() {
        return direction;
    }
    public void setTotal(int total) {
        this.totalDistance = total;
    }
    public void changeDirection(char dir) {
        this.direction = dir;
    }

    public boolean checkCross(char myDirection,Cell anotherCell){//repair
        int lastCellX = 0;
        int lastCellY = 0;
        switch (myDirection){
            case 'n':
                lastCellY = yCoordinates-1;
                lastCellX = xCoordinates;
                break;
            case 's':
                lastCellY = yCoordinates + 1;
                lastCellX = xCoordinates;
                break;
            case 'e':
                lastCellX = xCoordinates-1;
                lastCellY = yCoordinates;
                break;
            case 'w':
                lastCellX = xCoordinates + 1;
                lastCellY = yCoordinates;
                break;
            default:
                lastCellX = xCoordinates;
                lastCellY = yCoordinates;
        }
        if(groundTexture!=GroundTexture.SOIL)
            return false;
        if(building != null){
            if(building.getBuildingsDetails() == BuildingsDetails.WALL && anotherCell.hasLadder)
                return true;
            if(building instanceof Gate && ((Gate)building).checkState())
                return true;
            return false;
        }
        return true;
    }
    public void putLadder(){
        hasLadder = true;
    }

    public boolean checkHasLadder(){
        return hasLadder;
    }

    public void removeLadder(){
        hasLadder = false;
    }
    public void deletePerson(Person person){
        people.remove(person);
    }

    public void addPerson(Person person){
        people.add((Worker) person);
    }

    public void addMachine(Machine machine) {
        machine=machine;
    }

    public boolean doesHaveOil() {
        return hasOil;
    }

    public boolean doesHaveHole() {
        return hasHole;
    }

    public void deleteMachine(){
        this.machine = null;
    }

}
