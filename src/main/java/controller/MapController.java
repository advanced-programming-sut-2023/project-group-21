package controller;

import model.Cell;
import model.Game;
import model.User;
import model.generalenums.Extras;
import model.generalenums.GroundTexture;
import view.message.MapMessages;

import java.util.ArrayList;

public class MapController {
    public MapController() {
        System.out.println("new object!");
    }

    private int size, xCoordinates, yCoordinates;
    private Cell[][] map;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    private int countHold = 0;
    private ArrayList<Cell> myHolds = new ArrayList<>();
    private boolean initState = false;

    public boolean isInitState() {
        return initState;
    }

    public MapMessages initializeMap(int size, boolean behave) {
        initState = true;
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
        initState = true;
        myHolds = new ArrayList<>();
        int size = myMap.size();
        map = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                map[i][j] = new Cell(i, j);
        }
        if (size != 200 && size != 400)
            return null;
        for (String s : myMap)
            if (s.length() != (size * 2))
                return null;
        for (int i1 = 0; i1 < size; i1++) {
            for (int i2 = 0, i = 0; i < (myMap.get(i1).length() / 2); i2 += 2, i++) {
                Extras extra = null;
                if (myMap.get(i1).charAt(i2) != '!')
                    extra = Extras.getExtrasByCode(String.valueOf(myMap.get(i1).charAt(i2)));
                if (extra == Extras.HOLD)
                    myHolds.add(map[i1][i]);
                map[i1][i].setExtras(extra);
                GroundTexture groundTexture;
                groundTexture = GroundTexture.getTextureBySaveCode((String.valueOf(myMap.get(i1).charAt(i2 + 1))));
                map[i1][i].setGroundTexture(groundTexture);
            }
        }
        return MapMessages.SUCCESS;
    }

    public String showMap(int x, int y) {
        xCoordinates = x;
        yCoordinates = y;
        return Game.showMap(x, y, map);
    }

    public Cell[][] showMapForGui(int x, int y) {
        return Game.showMapForGui(x, y, map);
    }

    public Cell[][] showMapGui(int x, int y) {
        return Game.showMapForGui(x, y, map);
    }

    public Cell[][] moveMapGui(String direction) {
        return Game.moveMapForGui(direction, map);
    }

    public String moveMap(String changes) {
        return Game.moveMap(changes, map);
    }

    public MapMessages dropTree(int x, int y, String type) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return MapMessages.OUT_OF_INDEX;
        Extras tree = Extras.getExtrasByName(type);
        if (tree == null) return MapMessages.NO_TREE;
        map[x - 1][y - 1].setExtras(tree);
        return MapMessages.SUCCESS;
    }

    public MapMessages dropRock(int x, int y, String direction) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return MapMessages.OUT_OF_INDEX;
        if (direction.length() > 1) return MapMessages.INVALID_FORMAT;
        if (!Game.directions.contains(direction)) return MapMessages.INVALID_DIRECTION;
        if (direction.equals("r"))
            direction = String.valueOf(Game.directions.charAt((int) System.currentTimeMillis() % 4));
        Extras stone = Extras.getExtrasByName(direction);
        if (stone == null) return MapMessages.NO_STONE;
        map[x][y].setExtras(stone);
        return MapMessages.SUCCESS;
    }

    public MapMessages setTexture(int x, int y, String type) {
        GroundTexture texture = GroundTexture.getTextureByName(type);
        if (map[x][y].getExtra() != null) return MapMessages.UNABLE;
        if (x >= map.length || y >= map.length) return MapMessages.OUT_OF_INDEX;
        if (texture == null) return MapMessages.NO_TEXTURE;
        map[x][y].setGroundTexture(texture);
        return MapMessages.SUCCESS;
    }

    public MapMessages setTexture(int x1, int x2, int y1, int y2, String type) {
        if (x1 > x2 || y1 > y2) return MapMessages.INVALID_NUMBER;
        if (x1 < 0 || x1 >= map.length || y1 < 0 || y1 > map.length) return MapMessages.OUT_OF_INDEX;
        if (x2 >= map.length || y2 > map.length) return MapMessages.OUT_OF_INDEX;
        GroundTexture texture = GroundTexture.getTextureByName(type);
        if (texture == null) return MapMessages.NO_TEXTURE;
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                map[i][j].setExtras(null);
                map[i][j].setGroundTexture(texture);
            }
        }
        return MapMessages.SUCCESS;
    }

    public MapMessages clear(int x, int y) {
        if (x >= map.length || x < 0 || y >= map.length || y <= 1) return MapMessages.OUT_OF_INDEX;
        try {
            myHolds.remove(map[x][y]);
            map[x][y].clear();
            return MapMessages.SUCCESS;
        } catch (ArrayIndexOutOfBoundsException e) {
            return MapMessages.OUT_OF_INDEX;
        }
    }

    public String showDetails(int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return "out of index!!";
        try {
            return map[x - 1][y - 1].showDetails();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "out of index";
        }
    }

    public MapMessages setExtra(int x, int y, Extras extras) {
        if (x >= map.length || x < 0 || y >= map.length || y < 0)
            return MapMessages.OUT_OF_INDEX;
        if (extras == null)
            return MapMessages.NO_TEXTURE;
        if (map[x][y].getGroundTexture() != GroundTexture.SOIL) return MapMessages.UNABLE;
        if (map[x][y].getExtra() != null)
            return MapMessages.UNABLE_TO_PUT_EXTRA;
        map[x][y].setExtras(extras);
        if (extras == Extras.HOLD)
            myHolds.add(map[x][y]);
        return MapMessages.SUCCESS;
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
        if (x < 0 || x >= map.length || y < 0 || y >= map.length)
            return MapMessages.OUT_OF_INDEX;
        if (map[x][y].getExtra() != null)
            return MapMessages.UNABLE_TO_PUT_HOLD;
        if (map[x][y].getBuilding() != null)
            return MapMessages.UNABLE_TO_PUT_HOLD;
        if (map[x][y].getGroundTexture() != GroundTexture.SOIL)
            return MapMessages.UNABLE_TO_PUT_HOLD;
        if (countHold >= 8)
            return MapMessages.TOO_MANY_HOLD;
        countHold++;
        map[x][y].setExtras(Extras.HOLD);
        myHolds.add(map[x][y]);
        return MapMessages.SUCCESS;
    }

    public Cell[][] getMap() {
        return map;
    }

    public void saveMap() {
        if (user != null)
            saveMap(user.getUserName());
        else
            saveMap("default");
    }

    public ArrayList<Cell> getMyHolds() {
        return myHolds;
    }

    public void loadMapNormal() {
        if (map != null)
            return;
        if (user != null && FileController.checkExistenceOfMap(user.getUserName()))
            initializeMap(FileController.loadMap(user.getUserName()));
        else
            initializeMap(400,true);
    }
}