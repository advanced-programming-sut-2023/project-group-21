package ServerConnection;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.generalenums.Extras;
import model.generalenums.GroundTexture;
import model.human.Person;
import model.human.Worker;
import model.machine.Machine;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Cell implements Serializable {
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
    private static final String MY_PATH_EXTRA = "file:" + (new File("").getAbsolutePath()) +
            "/src/main/resources/ExtraImage/";
    private static final String MY_PATH_BUILDING = "file:" + (new File("").getAbsolutePath()) +
            "/src/main/resources/buildingImage/";

    public Cell(int xCoordinates, int yCoordinates) {
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
        details.append(("(" + xCoordinates + "," + yCoordinates + ")\n"));
        details.append("Texture: ").append(getGroundTexture().getName()).append("\n");
        if (extra != null)
            details.append("extra: ").append(extra.getName()).append("\n");
        if (building != null)
            details.append("Building: ").append(building.getName()).append(" | hitpoint: ").
                    append(building.getHitPoint()).append("\n");
        if (building != null && building.getBuildingsDetails() == BuildingsDetails.HOLD)
            details.append("government: ").append(building.getGovernment().getLord().getUserName());
        if (!people.isEmpty()) {
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
        details.append("\n");
        for (Machine machine : machines) {
            details.append(machine.getName()).append(": ").append(machine.getGovernment().getLord().getUserName());
            details.append(" | ").append(machine.getHitPoint()).append("\n");
        }
        if (!people.isEmpty())
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

//        System.out.println("check cross is called");
        if (this.building != null || extra != null || this.groundTexture != GroundTexture.SOIL) {
//            System.out.println(xCoordinates + " ::::: " + yCoordinates);
            return false;
        }
//        System.out.println(xCoordinates + " :::::: " + yCoordinates);
        return true;//i should fix it

//        if (this.building == null) {
//            return groundTexture != GroundTexture.PETROL && groundTexture != GroundTexture.BIG_LAKE
//                    && groundTexture != GroundTexture.ROCK && groundTexture != GroundTexture.SMALL_LAKE &&
//                    groundTexture != GroundTexture.RIVER && groundTexture != GroundTexture.SEA && extra == null;
//        }
//        //repair
//        if (state == 'n') {
//            if (groundTexture != GroundTexture.SOIL || extra != null)
//                return false;
//                if (building.getBuildingsDetails() == BuildingsDetails.WALL && anotherCell.hasLadder)
//                    return true;
//                return building instanceof Gate && ((Gate) building).checkState();
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
//        return building instanceof Tower
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


    public void addPeople(Worker worker) {
        people.add(worker);
    }

    public String toString2() {
        return "(" + (xCoordinates + 1) + "," + (yCoordinates + 1) + ")";
    }

    public Label toLabel(int xShow, int yShow, int size) {
        return getLabel(xShow, yShow, size);
    }

    public Label toLabel(int xShow, int yShow, int sizeX, int sizeY) {//for some corner tile
        Label label = new Label();
        label.setStyle("-fx-background-color: " + groundTexture.getRGB() + ";-fx-border-color: black;");
        label.setPrefWidth(sizeX);
        label.setPrefHeight(sizeY);
        label.setLayoutX(xShow);
        label.setLayoutY(yShow);
        label.setAlignment(Pos.CENTER);
        return label;
    }


    public void setExtra(Extras extra) {
        this.extra = extra;
    }

    private Label getLabel(int xShow, int yShow, int size) {
        Label label;
        if (extra != null) {
            ImageView iv = new ImageView(MY_PATH_EXTRA + extra.getImagePath());
            iv.setFitHeight((double) size / 2);
            iv.setFitWidth((double) size / 2);
            label = new Label(null, iv);
        } else if (building != null) {
            ImageView buildingImage = new ImageView(
                    new Image(Cell.class.getResource(building.getBuildingsDetails().getImagePath()).toExternalForm()));
            buildingImage.setFitHeight((double) size / 2);
            buildingImage.setFitWidth((double) size / 2);
            if (!people.isEmpty()) {
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(buildingImage);
                for (Worker worker: people) {
                    if (worker.getWorkerDetails().getImagePath() == null) continue;
                    ImageView workerImage = new ImageView(
                            new Image(Cell.class.getResource(worker.getWorkerDetails().getImagePath()).toExternalForm()));
                    workerImage.setFitHeight((double) size / 2);
                    workerImage.setFitWidth((double) size / 2);
                    stackPane.getChildren().add(workerImage);
                }
                label = new Label(null, stackPane);
            } else label = new Label(null, buildingImage);
        } else if (!people.isEmpty()) {
            StackPane stackPane = new StackPane();
            for (Worker worker: people) {
                if (worker.getWorkerDetails().getImagePath() == null) continue;
                ImageView workerImage = new ImageView(
                        new Image(Cell.class.getResource(worker.getWorkerDetails().getImagePath()).toExternalForm()));
                workerImage.setFitHeight((double) size / 2);
                workerImage.setFitWidth((double) size / 2);
                stackPane.getChildren().add(workerImage);
            }
            label = new Label(null, stackPane);
        } else label = new Label();

        label.setStyle("-fx-background-color: " + groundTexture.getRGB() + ";-fx-border-color: black;");
        label.setPrefWidth(size);
        label.setPrefHeight(size);
        label.setLayoutX(xShow);
        label.setLayoutY(yShow);
        label.setAlignment(Pos.CENTER);
        return label;
    }
}
