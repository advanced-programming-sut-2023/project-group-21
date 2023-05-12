package controller;

import model.Cell;
import model.Game;
import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.building.Gate;
import model.building.Tower;
import model.generalenums.Extras;
import model.generalenums.GroundTexture;
import model.human.Person;
import model.human.Worker;
import view.message.MapMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.*;

public class MapController {
    private int size, xCoordinates, yCoordinates;
    private Cell[][] map;
    private int countHold = 0;
    private ArrayList<Cell> myHolds = new ArrayList<>();

    public MapMessages initializeMap(int size, boolean behave) {
        if (map != null && behave)
            return null;
        map = new Cell[size][size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                map[i][j] = new Cell(i, j);
        }
        return MapMessages.SUCCESS;
    }


    public MapMessages initializeMap(ArrayList<String> myMap) {
        myHolds = new ArrayList<>();
        int size = myMap.size();
        if (size != 200 && size != 400)
            return null;
        for (String s : myMap)
            if (s.length() != (size * 2))
                return null;
        map = new Cell[size][size];
        for (int i1 = 0; i1 < size; i1++) {
            for (int i2 = 0; i2 < myMap.get(i1).length(); i2 += 2) {
                Extras extra = null;
                if (myMap.get(i1).charAt(i2) == '!')
                    extra = Extras.getExtrasByCode(String.valueOf(myMap.get(i1).charAt(i2)));
                if(extra == Extras.HOLD)
                    myHolds.add(map[i1][i2]);
                map[i1][i2].setExtras(extra);
                GroundTexture groundTexture;
                groundTexture = GroundTexture.getTextureBySaveCode((String.valueOf(myMap.get(i1).charAt(i2 + 1))));
                map[i1][i2].setGroundTexture(groundTexture);
            }
        }
        return MapMessages.SUCCESS;
    }

    public String showMap(int x, int y) {
        xCoordinates = x;
        yCoordinates = y;
        if (x > map.length || x < 1 || y > map.length || y < 1) return null;
        StringBuilder output = new StringBuilder();
        if(x>map.length || x<=0 ||y>map.length||y<=0)
            return "out of index!";
        boolean hasPerson = false;
        int yMax = min(y + 4, size) - 1, yMin = max(y - 4, 1) - 1, xMax = min(x + 4, size) - 1, xMin = max(x - 4, 1) - 1;
        for (int j = yMin; j <= yMax; j++) {
            for (int i = xMin; i <= xMax; i++) {
                output.append(map[i][j].getGroundTexture().getColor());
                Building building = map[i][j].getBuilding();
                for (Person person : map[i][j].getPeople()) {
                    if (person instanceof Worker && ((Worker) person).getDestination().equals(((Worker) person).getPosition())) {
                        hasPerson = true;
                        break;
                    }
                }
                if (hasPerson) output.append("S");
                else if (building != null && (building instanceof Tower || building.getBuildingsDetails().equals(BuildingsDetails.WALL) ||
                        building instanceof Gate)) output.append("W");
                else if (building != null) output.append("B");
                else if (map[i][j].getExtra() != null && !Game.directions.contains(map[i][j].getExtra().getName()))
                    output.append("T");
                else if (map[i][j].getExtra() != null)
                    output.append("R");
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
        if (xCoordinates + xChange > map.length || xCoordinates + xChange <= 0 || yCoordinates + yChange > map.length || yCoordinates + yChange <= 0)
            return "";
        xCoordinates += xChange;
        yCoordinates += yChange;
        return showMap(xCoordinates, yCoordinates);
    }

    public MapMessages dropTree(int x, int y, String type) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return null;
        Extras tree = Extras.getExtrasByName(type);
        if (tree == null) return MapMessages.NO_TREE;
        map[x - 1][y - 1].setExtras(tree);
        return MapMessages.SUCCESS;
    }

    public MapMessages dropRock(int x, int y, String direction) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return null;
        if (direction.length() > 1) return MapMessages.INVALID_FORMAT;
        if (!Game.directions.contains(direction)) return MapMessages.INVALID_DIRECTION;
        if (direction.equals("r")) direction = String.valueOf(Game.directions.charAt((int) System.currentTimeMillis() % 4));
        Extras stone = Extras.getExtrasByName(direction);
        if (stone == null) return MapMessages.NO_STONE;
        map[x - 1][y - 1].setExtras(stone);
        return MapMessages.SUCCESS;
    }

    public MapMessages setTexture(int x, int y, String type) {
        GroundTexture texture = GroundTexture.getTextureByName(type);
        if(x<0||x>=map.length||y<0||y>=map.length) return MapMessages.OUT_OF_INDEX;
        if (texture == null) return MapMessages.NO_TEXTURE;
        map[x - 1][y - 1].setGroundTexture(texture);
        return MapMessages.SUCCESS;
    }

    public MapMessages setTexture(int x1, int x2, int y1, int y2, String type) {
        if (x1 >= x2 || y1 >= y2) return MapMessages.INVALID_NUMBER;
        if(x1<0||x1>=map.length||y1<0||y1>map.length) return MapMessages.OUT_OF_INDEX;
        if(x1<0||x1>=map.length||y1<0||y1>map.length) return MapMessages.OUT_OF_INDEX;
        GroundTexture texture = GroundTexture.getTextureByName(type);
        if (texture == null) return MapMessages.NO_TEXTURE;
        for (int i = x1 - 1; i < x2; i++) {
            for (int j = y1 - 1; j < y2; j++) map[i][j].setGroundTexture(texture);
        }
        return MapMessages.SUCCESS;
    }

    public MapMessages clear(int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return null;
        try {
            map[x - 1][y - 1].clear();
            return MapMessages.SUCCESS;
        } catch (ArrayIndexOutOfBoundsException e) {
            return MapMessages.OUT_OF_INDEX;
        }
    }

    public String showDetails(int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return null;
        try {
            return map[x - 1][y - 1].showDetails();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "out of index";
        }
    }

    public void saveMap(String username) {
        String temp = "";
        for (int i1 = 0; i1 < map.length; i1++) {
            for (int i2 = 0; i2 < map[i1].length; i2++)
                temp += map[i1][i2].makeSaveCode();
            if (i1 != map.length - 1)
                temp += "\n";
        }
        FileController.saveMap(username, temp);
    }

    public void loadMap(String username) {
        if (!FileController.checkExistenceOfMap(username))
            return;
        ArrayList<String> savedMap = FileController.loadMap(username);
        if (savedMap == null)
            return;
        initializeMap(savedMap);
    }

    public Cell[][] getMapByUsername(String username) {
        loadMap(username);
        return map;
    }

    public MapMessages setHold(int x, int y) {
        if(x<0||x>=map.length||y<0||y>=map.length)
            return MapMessages.OUT_OF_INDEX;
        if (map[x][y].getExtra() != null)
            return MapMessages.UNABLE_TO_PUT_HOLD;
        if(map[x][y].getBuilding()!=null)
            return MapMessages.UNABLE_TO_PUT_HOLD;
        if(map[x][y].getGroundTexture()!=GroundTexture.SOIL)
            return MapMessages.UNABLE_TO_PUT_HOLD;
        if(countHold>=8)
            return MapMessages.TOO_MANY_HOLD;
        countHold++;
        map[x][y].setExtras(Extras.HOLD);
        myHolds.add(map[x][y]);
        return MapMessages.SUCCESS;
    }

    public Cell[][] getMap() {
        return map;
    }

    public ArrayList<Cell> getMyHolds(){
        return myHolds;
    }
}