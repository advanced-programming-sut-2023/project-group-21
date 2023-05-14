package model;

import controller.GameController;
import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.building.Gate;
import model.building.Tower;
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
    private char direction = 'a';
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
            details.append("Building: ").append(building.getName()).append(" | hitpoint: ").append(building.getHitPoint()).append("\n");
        if (people.size() != 0) {
//            details.append("People: ").append("\n");
//            for (int i = 0; i < people.size(); i++) {
//                boolean isRepeated = false;
//                for (int j = 0; j < i; j++) {
//                    if (people.get(j).getName().equals(people.get(i).getName())) {
//                        isRepeated = true;
//                        break;
//                    }
//                }
//                if (isRepeated) continue;
//                details.append(people.get(i));
//                int number = 1;
//                for (int j = i + 1; j < people.size(); j++) if (people.get(j).getName().equals(people.get(i).getName())) number++;
//                details.append(" ").append(number).append("\n");
//            }
            for (Worker person: people) {
                details.append(person.getName()).append(": ").append(person.getGovernment().getLord().getUserName());
                details.append(" | ").append(person.getHitPoint());
                if (person.getEnemy() != null) details.append(" | enemy: ").append(person.getEnemy().getPosition().toString());
                details.append("\n");
            }
        }
        if (machines != null) {
            details.append("\n");
            for (Machine machine: machines) {
                details.append(machine.getName()).append(": ").append(machine.getGovernment().getLord().getUserName());
                details.append(" | ").append(machine.getHitPoint()).append("\n");
            }
        }
        details.append("Number of all People: ").append(people.size());
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
        return "(" + (xCoordinates + 1) + "," + (yCoordinates + 1) + ") ";
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

    public boolean checkCross(char myDirection,Cell anotherCell,char state){
        //repair
        if(state == 'n') {
            if (groundTexture != GroundTexture.SOIL || extra != null)
                return false;
            if (building != null) {
                if (building.getBuildingsDetails() == BuildingsDetails.WALL && anotherCell.hasLadder)
                    return true;
                if (building instanceof Gate && ((Gate) building).checkState())
                    return true;
                return false;
            }
            return true;
        }
        if(state == 'a') {
            if (groundTexture != GroundTexture.SOIL || extra != null)
                return false;
            if (building != null) {
                if (building.getBuildingsDetails() == BuildingsDetails.WALL)
                    return true;
                if (building instanceof Gate && ((Gate) building).checkState())
                    return true;
                return false;
            }
            if (groundTexture != GroundTexture.SOIL || extra != null)
                return false;
            if(building != null
                    && (building.getBuildingsDetails().equals(BuildingsDetails.SQUARE_TOWER) ||
                    building.getBuildingsDetails().equals(BuildingsDetails.ROUND_TOWER)))
            return true;
        }
        if (groundTexture != GroundTexture.SOIL || extra != null)
            return false;
        if(building != null && building instanceof Tower
                && (((Tower)building).getBuildingsDetails()==BuildingsDetails.SQUARE_TOWER ||
                ((Tower)building).getBuildingsDetails() == BuildingsDetails.ROUND_TOWER))
            return true;
        return false;
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
        machines.add(0,machine);
    }

    public boolean doesHaveOil() {
        return hasOil;
    }

    public boolean doesHaveHole() {
        return hasHole;
    }

    public void deleteMachine(Machine machine){
        machines.remove(machine);
    }

    public void addPeople(Worker worker) {
        people.add(worker);
    }

    public void refreshDirection() {
        this.direction = 'a';
    }
}
