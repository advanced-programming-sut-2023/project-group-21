package view;


import controller.GameController;
import controller.OtherController;
import model.Cell;
import model.Government;
import model.User;
import view.commands.GameMenuCommand;
import view.commands.MapCommand;
import view.message.GameMessage;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final User user;
    private Government government;
    private final GameController gameController;
    private final ArrayList<Government> governments;
    private MapMenu mapMenu;

    public GameMenu(User user, ArrayList<Government> governments, Cell[][] map) {
        this.governments = governments;
        this.user = user;
        this.government = governments.get(0);
        gameController = new GameController(governments, map);
    }

    public void run(Scanner scanner) {
        String line;
        Matcher matcher;
        System.out.println("You are in game menu.");
        while (gameController.checkEndGame()) {
            line = scanner.nextLine();
            if (line.equals("back") || gameController.checkWin())
                break;
            else if ((GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_FACTORS)) != null)
                printPopularityFactors();
            else if ((GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_POPULARITY)) != null)
                showPopularity();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SET_FOOD_RATE)) != null)
                setFoodRate(matcher);
            else if ((GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_FOOD_LIST)) != null)
                showFoodList();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SET_TAX)) != null)
                checkSetTaxRate(matcher);
            else if (GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_TAX_RATE) != null)
                showTaxRate();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if ((GameMenuCommand.getMatcher(line, GameMenuCommand.GO_TO_SHOP)) != null)
                goToShop(scanner);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SELECT_BUILDING)) != null)
                chooseBuilding(matcher);
            else if ((GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_FOOD_RATE)) != null)
                showFoodRate();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.CREATE_UNIT)) != null)
                checkCreateUnit(matcher);
            else if (GameMenuCommand.getMatcher(line, GameMenuCommand.REPAIR) != null)
                repairBuilding();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SELECT_UNIT)) != null)
                chooseUnit(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.MOVE_UNIT)) != null)
                moveUnitTo(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.CHANGE_STATE)) != null)
                setMyTroop(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.ATTACK)) != null)
                attackEnemy(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.ATTACK2)) != null)
                attackXY(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.POUR_OIL)) != null)
                pourOil(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.DIG_TUNNEL)) != null)
                digTunnel(matcher);
            else if ((GameMenuCommand.getMatcher(line, GameMenuCommand.DISBAND_UNIT)) != null)
                disbandUnit();
            else if (GameMenuCommand.getMatcher(line, GameMenuCommand.NEXT_TURN) != null)
                nextTurn();
            else if (GameMenuCommand.getMatcher(line, GameMenuCommand.GO_TO_TRADE) != null)
                goToTradeMenu(scanner);
            else if((matcher=GameMenuCommand.getMatcher(line,GameMenuCommand.PATROL))!=null)
                patrol(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.MOVE_UNIT))!=null)
                checkMoveEquipment(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.MAKE__))!=null)
                makeSiegeUnit(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SWITCH))!= null)
                switch1();
            else if((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_RESOURCE)) != null)
                checkShowResource(matcher);
            else if((matcher = MapCommand.getMatcher(line,MapCommand.SHOW_DETAIL))!=null)
                showDetails(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SET_FEAR_RATE))!=null)
                setFearRate(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.CHANGE_STATE))!=null)
                changeState(matcher);
            else if ((matcher = MapCommand.getMatcher(line,MapCommand.MOVE_MAP))!=null)
                checkMove(matcher);
            else if ((matcher = MapCommand.getMatcher(line,MapCommand.SHOW_MAP))!=null)
                showMap(matcher);
            else if(GameMenuCommand.getMatcher(line,GameMenuCommand.CALCULATE_UNEMPLOYMENT)!=null)
                showUnemployed();
            else
                System.out.println("invalid format!");
        }
        System.out.println("Game ended.");
    }

    private void showUnemployed(){
        System.out.println("number of unemployed is: "+gameController.getNumberOfPeasants());
    }

    private void showMap(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        System.out.println(gameController.showMap(x, y));
    }
    private void checkMove(Matcher matcher) {
        String left = matcher.group("left");
        String mapShow = gameController.moveMap(left);
        System.out.println(mapShow);
    }
    private void changeState(Matcher matcher){
        String state  = matcher.group("state");
        gameController.openOrCloseGate(state);
    }
    private void setFearRate(Matcher matcher){
        try {
            int fearRate = Integer.parseInt(matcher.group("rate"));
            GameMessage message = gameController.setFearRate(fearRate);
            System.out.println(message.toString());
        }catch (NumberFormatException exception){
            System.out.println("enter integer!");
        }
    }
    private void showDetails(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        System.out.println(gameController.showDetails(x, y));
    }
    public void setMapMenu(MapMenu mapMenu) {
        this.mapMenu = mapMenu;
    }

    public void checkMoveEquipment(Matcher matcher){
        int x1 = Integer.parseInt(matcher.group("x1"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        GameMessage message = gameController.checkMoveEquipments(x1,y1,x2,y2);
        System.out.println(message);
    }

    private void checkShowResource(Matcher matcher){
        String resourceName = matcher.group("resource");
        System.out.println(gameController.getResourceName(resourceName));
    }
    public void patrol(Matcher matcher){
        int x1 = Integer.parseInt(matcher.group("x1"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        GameMessage gameMessage = gameController.patrolUnit(x1,y1,x2,y2);
        System.out.println(gameMessage.toString());
    }
    private boolean checkWin() {
        return false;
    }

    private void switch1(){
        GameMessage gameMessage = gameController.switchProduct();
        System.out.println(gameMessage.toString());
    }

    private void printPopularityFactors() {
        System.out.println(gameController.showPopularityFactors());
    }

    private void goToTradeMenu(Scanner scanner) {
        TradeMenu tradeMenu = new TradeMenu();
        tradeMenu.run(scanner, gameController.getCurrentGovernment());
    }

    private void goToShop(Scanner scanner) {
        ShopMenu shopMenu = new ShopMenu(government);
        shopMenu.run(scanner);
    }

    private void showPopularity() {
        System.out.println("your popularity is :" + gameController.getPopularity());
    }

    private void showFoodList() {
        System.out.println(gameController.showFoodList());
    }

    private void setFoodRate(Matcher matcher) {
        int rate = Integer.parseInt(matcher.group("rate"));
        System.out.println(gameController.setFoodRate(rate).toString());
    }

    private void showFoodRate() {
        System.out.println("Food Rate: " + gameController.showFoodRate());
    }

    private void checkSetTaxRate(Matcher matcher) {
        int rate = Integer.parseInt(matcher.group("rate"));
        GameMessage gameMessage = gameController.setTaxRate(rate);
        System.out.println(gameMessage.toString());
    }

    private void showTaxRate() {
        System.out.println("your tax rate is :" + gameController.showTaxRate());
    }

    private void dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = OtherController.myTrim(matcher.group("type"));
        GameMessage gameMessage = gameController.checkDropBuilding(x, y, type);
        System.out.println(gameMessage.toString());
    }

    private void chooseBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        GameMessage gameMessage = gameController.selectBuilding(x, y);
        System.out.println(gameMessage);
    }

    private void checkCreateUnit(Matcher matcher) {
        String type = OtherController.myTrim(matcher.group("type"));
        int count = Integer.parseInt(matcher.group("count"));
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        GameMessage gameMessage=gameController.checkMakeTroop(type,count,x,y);
        System.out.println(gameMessage.toString());
    }

    private void repairBuilding() {
        GameMessage gameMessage = gameController.repair();
        System.out.println(gameMessage.toString());
    }

    private void chooseUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        GameMessage gameMessage = gameController.selectUnit(x, y);
        System.out.println(gameMessage.toString());
    }

    private void moveUnitTo(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        GameMessage gameMessage = gameController.moveUnit(x, y);
        System.out.println(gameMessage.toString());
    }

    private void setMyTroop(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String state = matcher.group("state");
        GameMessage gameMessage = gameController.setStateUnit(x, y, state);
        System.out.println(gameMessage.toString());
    }

    private void attackEnemy(Matcher matcher) {
        try {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            gameController.attack(x,y);
        }catch (NumberFormatException exception){
            System.out.println("please enter an integer!");
        }
    }

    private void attackXY(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        GameMessage gameMessage = gameController.checkArcherAttack(x,y);
        System.out.println(gameMessage);
    }

    private void pourOil(Matcher matcher) {
        String dir1 = matcher.group("direction");
        GameMessage gameMessage = gameController.checkPourOil(dir1);
        System.out.println(gameMessage.toString());
    }

    private void digTunnel(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String direction = matcher.group("direction");
        GameMessage message = gameController.checkDigTunnel(x,y,direction);
        System.out.println(message.toString());
    }

    private void makeSiegeUnit(Matcher matcher) {
        String name = OtherController.myTrim(matcher.group("name"));
        GameMessage gameMessage = gameController.checkBuildSiegeEquipment(name);
        System.out.println(gameMessage);
    }

    private void disbandUnit() {
        gameController.disbandUnit();
        System.out.println("success!");
    }

    private void nextTurn() {
        gameController.nextTurn();
        System.out.println("now " + gameController.getCurrentGovernment().getLord().getUserName() + " is playing!");
    }


}
