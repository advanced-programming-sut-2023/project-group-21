package controller;

import model.Cell;
import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.building.Gate;
import model.building.Tower;
import model.generalenums.Extras;
import model.generalenums.GroundTexture;
import model.human.Person;
import model.human.Worker;
import view.message.MapMessages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.*;

public class MapController {
    private final String directions = "nwesr";
    private int size, xCoordinates, yCoordinates;
    private Cell[][] map;

    private MapMessages initializeMap(int size) {
        map = new Cell[size][size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                map[i][j] = new Cell(i, j);
        }
        return null;
    }

    public String showMap(int x, int y) {
        xCoordinates = x;
        yCoordinates = y;
        StringBuilder output = new StringBuilder();
        boolean hasPerson = false;
        if (x < 1 || x > 200 || y < 1 || y > 200) return null;
        int yMax = min(y + 2, size) - 1, yMin = max(y - 2, 0) - 1, xMax = min(x + 2, size) - 1, xMin = max(x - 2, 0) - 1;
        for (int j = yMin; j <= yMax; j++) {
            for (int i = xMin; i <= xMax; i++) {
                output.append(map[i][j].getGroundTexture().getColor());
                Building building = map[i][j].getBuilding();
                for (Person person: map[i][j].getPeople()) {
                    if (person instanceof Worker && ((Worker) person).getDestination().equals(((Worker) person).getPosition())) {
                        hasPerson = true;
                        break;
                    }
                }
                if (hasPerson) output.append("S");
                else if (building != null && (building instanceof Tower || building.getBuildingsDetails().equals(BuildingsDetails.WALL) ||
                        building instanceof Gate)) output.append("W");
                else if (building != null) output.append("B");
                else if (map[i][j].getExtra() != null && !directions.contains(map[i][j].getExtra().getName())) output.append("T");
                else output.append(" ");
            }
            output.append("\033[0m").append("\n");
        }
        return output.toString();
    }

    public String moveMap(String changes) {
        int xChange = 0, yChange = 0, number;
        String direction;
        Matcher matcher = Pattern.compile("\\S+[ \\d+]*").matcher(changes);
        while (matcher.find()) {
            String[] split = matcher.group().split(" ");
            direction = split[0];
            if (split.length < 2) number = 1;
            else number = Integer.parseInt(split[1]);
            switch (direction) {
                case "up" -> yChange -= number;
                case "down" -> yChange += number;
                case "left" -> xChange -= number;
                case "right" -> xChange += number;
                default -> {
                    return "";
                }
            }
        }
        if (xCoordinates + xChange > 200 || xCoordinates + xChange <= 0 || yCoordinates + yChange > 200 || yCoordinates + yChange <= 0)
            return "";
        xCoordinates += xChange;
        yCoordinates += yChange;
        return showMap(xCoordinates, yCoordinates);
    }
    public MapMessages dropTree(int x,int y, String type){
        Extras tree = Extras.getExtrasByName(type);
        if (tree == null) return null;
        map[x][y].setExtras(tree);
        return null;
    }

    public MapMessages dropRock(int x,int y, String direction){
        if (direction.length() > 1) return null;
        if (!directions.contains(direction)) return null;
        Extras stone = Extras.getExtrasByName(direction);
        if (stone == null) return null;
        map[x-1][y-1].setExtras(stone);
        return null;
    }

    public MapMessages setTexture(int x,int y,String type){
        GroundTexture texture = GroundTexture.getTextureByName(type);
        if (texture == null) return null;
        map[x-1][y-1].setGroundTexture(texture);
        return null;
    }

    public MapMessages setTexture(int x1,int x2,int y1,int y2,String type){
        if (x1 >= x2 || y1 >= y2) return null;
        GroundTexture texture = GroundTexture.getTextureByName(type);
        if (texture == null) return null;
        for (int i = x1-1; i <x2; i++) {
            for (int j = y1-1; j < y2; j++) map[i][j].setGroundTexture(texture);
        }
        return null;
    }

    public MapMessages clear(int x,int y){
        map[x-1][y-1].clear();
        return null;
    }

    public String showDetails(int x, int y) {
        return map[x-1][y-1].showDetails();
    }
}
