package model;

import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private int distanceOfStart = 0;
    private int distanceOfDestination = 0;
    private int totalDistance = 0;
    private Building building;
    private final ArrayList<Machine> machines = new ArrayList<>();
    private Extras extra;
    private final ArrayList<Worker> people;
    private final int xCoordinates;
    private final int yCoordinates;
    private boolean hadCross = false;
    private final boolean hasOil = false;
    private final boolean hasHole = false;
    private char direction = 'a';
    private boolean hasLadder = false;

    public Cell(int xCoordinates, int yCoordinates) {
        if (xCoordinates%6==0)
            groundTexture = GroundTexture.DENSE_MEADOW;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        people = new ArrayList<>();
    }

    public void setDistanceOfStart(int distanceOfStart) {
        this.distanceOfStart = distanceOfStart;
    }

    public void refreshDirection() {
        this.direction = 'a';
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

    public String showDetails() {
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
            for (Worker person : people) {
                details.append(person.getName()).append(": ").append(person.getGovernment().getLord().getUserName());
                details.append(" | ").append(person.getHitPoint());
                if (person.getEnemy() != null)
                    details.append(" | enemy: ").append(person.getEnemy().getPosition().toString());
                details.append("\n");
            }
        }
        if (machines != null) {
            details.append("\n");
            for (Machine machine : machines) {
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

    public String makeSaveCode() {
        String temp = "";
        if (extra == null)
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

    public boolean checkCross(char myDirection, Cell anotherCell, char state) {
        return true;
//        if (this.building == null) {
//            return groundTexture != GroundTexture.PETROL && groundTexture != GroundTexture.BIG_LAKE
//                    && groundTexture != GroundTexture.ROCK && groundTexture != GroundTexture.SMALL_LAKE &&
//                    groundTexture != GroundTexture.RIVER && groundTexture != GroundTexture.SEA && extra == null;
//        }
//        //repair
//        if (state == 'n') {
//            if (groundTexture != GroundTexture.SOIL || extra != null)
//                return false;
//            if (building != null) {
//                if (building.getBuildingsDetails() == BuildingsDetails.WALL && anotherCell.hasLadder)
//                    return true;
//                return building instanceof Gate && ((Gate) building).checkState();
//            }
//            return true;
//        }
//        if (state == 'a') {
//            if (groundTexture != GroundTexture.SOIL || extra != null)
//                return false;
//            if (building != null) {
//                if (building.getBuildingsDetails() == BuildingsDetails.WALL)
//                    return true;
//                return building instanceof Gate && ((Gate) building).checkState();
//            }
//            if (groundTexture != GroundTexture.SOIL || extra != null)
//                return false;
//            if ((building.getBuildingsDetails().equals(BuildingsDetails.SQUARE_TOWER) ||
//                    building.getBuildingsDetails().equals(BuildingsDetails.ROUND_TOWER)))
//                return true;
//        }
//        if (groundTexture != GroundTexture.SOIL || extra != null)
//            return false;
//        return building != null && building instanceof Tower
//                && (building.getBuildingsDetails() == BuildingsDetails.SQUARE_TOWER ||
//                building.getBuildingsDetails() == BuildingsDetails.ROUND_TOWER);
    }

    public void putLadder() {
        hasLadder = true;
    }

    public boolean checkHasLadder() {
        return hasLadder;
    }

    public void removeLadder() {
        hasLadder = false;
    }

    public void deletePerson(Person person) {
        people.remove(person);
    }

    public void addPerson(Person person) {
        people.add((Worker) person);
    }

    public void addMachine(Machine machine) {
        machines.add(0, machine);
    }

    public boolean doesHaveOil() {
        return hasOil;
    }

    public boolean doesHaveHole() {
        return hasHole;
    }

    public void deleteMachine(Machine machine) {
        machines.remove(machine);
    }

    private double normal(String input) {
        if (input.length() != 2)
            return 0;
        double result = 0;
        if (Character.isDigit(input.charAt(0)))
            result = Integer.parseInt(String.valueOf(input.charAt(0))) * 16;
        else
            result = (input.charAt(0) - 'a' + 10) * 16;
        if (Character.isDigit(input.charAt(1)))
            result += (Integer.parseInt(String.valueOf(input.charAt(1))));
        else
            result += (input.charAt(0) - 'a' + 10);
        return result / 256.1;
    }

    public void addPeople(Worker worker) {
        people.add(worker);
    }

    public String toString2() {
        return "(" + (xCoordinates + 1) + "," + (yCoordinates + 1) + ")";
    }

    public Label toLabel(int xShow, int yShow,int size) {
        String rgb = groundTexture.getRGB();
        Color BLACK = new Color(1, 0.0, 0.0, 0.0);
        double red = normal(rgb.substring(1, 3));
        double blue = normal(rgb.substring(3, 5));
        double green = normal(rgb.substring(5));
        Paint paint = new Color(0.99, red, green, blue);
        Label label = getLabel(xShow, yShow);
        return label;
    }

    private Label getLabel(int xShow, int yShow) {
        Label label = new Label(xCoordinates+"   "+yCoordinates);
        label.setStyle("-fx-background-color: " + groundTexture.getRGB() + ";");
        label.setPrefWidth(100);
        label.setPrefHeight(100);
        label.setLayoutX(xShow);
        label.setLayoutY(yShow);
        label.setAlignment(Pos.CENTER);
        return label;
    }
}
