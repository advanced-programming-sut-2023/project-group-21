package model;

import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.building.Gate;
import model.building.Tower;
import model.generalenums.GroundTexture;
import model.human.Person;
import model.human.Worker;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Game {
    private static final int mapSizeSmall = 8;
    private static ArrayList<TradeRequest> tradeRequests=new ArrayList<>();
    private static final ArrayList<User> users = new ArrayList<>();
    public static final String directions = "nwesr";
    private static int xCoordinates = 10, yCoordinates = 10;
    public static final List<String> SLOGANS = Arrays.asList("Peace Through Strength",
            "Better to die than to be a coward",
            "Whatever It Takes",
            "Deeds, Not Words",
            "Be All You Can Be",
            "Always Ready",
            "Fortune favours the brave",
            "Loyalty, duty, honor",
            "An Army of One",
            "The harder the conflict, the more glorious the triumph",
            "The only easy day was yesterday"
    );
    public static void addTradeRequest(TradeRequest request){
        tradeRequests.add(request);
    }

    public static ArrayList<TradeRequest> getTradeRequests() {
        return tradeRequests;
    }

    private static final ArrayList<Trade> trades = new ArrayList<>();

    public static final String[] SECURITY_QUESTION = {"what is your favorite food ?", "what is your father s name ?",
            "what is your oldest sibling's first name?"};
    public static final List<GroundTexture> UNPASSABLE = Arrays.asList(GroundTexture.ROCK, GroundTexture.RIVER,
            GroundTexture.SMALL_LAKE, GroundTexture.BIG_LAKE, GroundTexture.SEA);

    public static void addTrade(Trade createdTrade) {
        trades.add(createdTrade);
    }

    public static Trade getTradeById(int id) {
        for (Trade trade : trades) {
            if (trade.getId() == id)
                return trade;
        }
        return null;
    }

    public static void removeTrade(Trade tradeById) {
        trades.remove(tradeById);
    }

    public static enum TaxDetails {
        A(-3, -1, 7),
        B(-2, -0.8, 5),
        C(-1, -0.6, 3),
        D(0, 0, 1),
        E(1, 0.6, -2),
        F(2, 0.8, -4),
        G(3, 1, -6),
        H(4, 1.2, -8),
        I(5, 1.4, -12),
        J(6, 1.6, -16),
        K(7, 1.8, -20),
        L(8, 2, -24);
        final int number;
        final int popularityRate;
        final double tax;

        TaxDetails(int number, double tax, int popularityRate) {
            this.number = number;
            this.tax = tax;
            this.popularityRate = popularityRate;
        }

        public static double getTax(int number) {
            for (TaxDetails taxDetail : TaxDetails.values()) if (taxDetail.number == number) return taxDetail.tax;
            return 20;
        }

        public static int getPopularity(int number) {
            for (TaxDetails taxDetail : TaxDetails.values())
                if (taxDetail.number == number) return taxDetail.popularityRate;
            return 20;
        }
    }

    public static enum FoodRate {
        A(-2, 0, -8),
        B(-1, 0.5, -4),
        C(0, 1, 0),
        D(1, 1.5, 4),
        E(2, 2, 8);
        final int number;
        final int popularityRate;
        final double foodRate;

        FoodRate(int number, double foodRate, int popularityRate) {
            this.number = number;
            this.popularityRate = popularityRate;
            this.foodRate = foodRate;
        }

        public static double getFoodRate(int number) {
            for (FoodRate foodRate1 : FoodRate.values()) if (foodRate1.number == number) return foodRate1.foodRate;
            return 0;
        }

        public static int getPopularity(int number) {
            for (FoodRate foodRate1 : FoodRate.values())
                if (foodRate1.number == number) return foodRate1.popularityRate;
            return 0;
        }
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) if (user.getUserName().equals(username)) return user;
        return null;
    }

    public static ArrayList<Trade> getTrades() {
        return trades;
    }

    public static String showMap(int x, int y, Cell[][] map) {
        xCoordinates = x;
        yCoordinates = y;
        if (x > map.length || x < 1 || y > map.length || y < 1) return "out of index!";
        StringBuilder output = new StringBuilder();
        boolean hasPerson = false;
        int yMax = min(y + 4, map.length) - 1, yMin = max(y - 4, 1) - 1, xMax = min(x + 4, map.length) - 1, xMin = max(x - 4, 1) - 1;
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
                hasPerson = false;
            }
            output.append("\033[0m").append("\n");
        }
        return output.toString();
    }

    public static Cell[][] showMapForGui(int x, int y, Cell[][] map) {
        xCoordinates = x;
        yCoordinates = y;
        if (map == null || x > map.length || x < 1 || y > map.length || y < 1)
            return null;
        if (x > map.length - mapSizeSmall)
            x = map.length - mapSizeSmall;
        if (y > map.length - mapSizeSmall)
            y = map.length - mapSizeSmall;
        x--;y--;
        Cell[][] result = new Cell[mapSizeSmall][mapSizeSmall];
        for (int i1 = x, j1 = 0; i1 < mapSizeSmall + x; i1++, j1++)
            for (int i2 = y, j2 = 0; i2 < mapSizeSmall + y; i2++, j2++)
                result[j1][j2] = map[i1][i2];
        return result;
    }

    public static Cell[][] moveMapForGui(String direction,Cell[][] map){
        switch (direction){
            case "u":
                if(yCoordinates+1<=map.length-mapSizeSmall)
                    yCoordinates++;
                break;
            case "d":
                if(yCoordinates-1>=1)
                    yCoordinates--;
                break;
            case "r":
                if(xCoordinates+1<=map.length)
                    xCoordinates++;
                break;
            case "l":
                if(xCoordinates-1>=1)
                    yCoordinates--;
        }
        return showMapForGui(xCoordinates,yCoordinates,map);
    }

    public static String moveMap(String changes, Cell[][] map) {
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
        return showMap(xCoordinates, yCoordinates, map);
    }

    public String showDetails(int x, int y, Cell[][] map) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return "out of index!!";
        try {
            return map[x - 1][y - 1].showDetails();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "out of index";
        }
    }
}
