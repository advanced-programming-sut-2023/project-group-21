package view;

import controller.GameController;
import model.Cell;
import model.Government;
import model.User;
import view.commands.GameMenuCommand;
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
        while (gameController.checkEndGame()) {
            line = scanner.nextLine();
            if (line.equals("back") || checkWin())
                break;
            else if ((GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_FACTORS)) != null)
                printPopularityFactors();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_POPULARITY)) != null)
                showPopularity();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SET_FOOD_RATE)) != null)
                setFoodRate(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_FOOD_LIST)) != null)
                showFoodList();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SET_TAX)) != null)
                checkSetTaxRate(matcher);
            else if (GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_TAX_RATE) != null)
                showTaxRate();
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.GO_TO_SHOP)) != null)
                goToShop(matcher, scanner);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SELECT_BUILDING)) != null)
                chooseBuilding(matcher);
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.SHOW_FOOD_RATE)) != null)
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
            else if ((matcher = GameMenuCommand.getMatcher(line, GameMenuCommand.DISBAND_UNIT)) != null)
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
            else if(true)
                checkShowResource(matcher);
            else
                System.out.println("invalid format!");

        }
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
        System.out.println("repair");
    }

    private void goToTradeMenu(Scanner scanner) {
        TradeMenu tradeMenu = new TradeMenu();
        tradeMenu.run(scanner, gameController.getCurrentGovernment());
    }

    private void goToShop(Matcher matcher, Scanner scanner) {
        ShopMenu shopMenu = new ShopMenu(government);
        shopMenu.run(scanner);
    }

    private void showPopularity() {
        System.out.println("your popularity is :" + government.getPopularity());
    }

    private void showFoodList() {
        System.out.println(gameController.showFoodRate());
    }

    private void setFoodRate(Matcher matcher) {
        int rate = Integer.parseInt(matcher.group("rate"));
        gameController.setFoodRate(rate);

    }

    private void showFoodRate() {
        System.out.println(gameController.showFoodRate());
    }

    private void checkSetTaxRate(Matcher matcher) {
        int rate = Integer.parseInt(matcher.group("rate"));
        GameMessage gameMessage = gameController.setTaxRate(rate);
        System.out.println(gameMessage.toString());
    }

    private void showTaxRate() {
        System.out.println("your tax rate is :" + gameController.showFoodRate());
    }

    private void dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
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
        String type = matcher.group("type");
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

    private void attackEnemy(Matcher matcher) {//ask
        String enemy = matcher.group("enemy");
        System.out.println("repair" + enemy);
    }

    private void attackXY(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        GameMessage gameMessage = gameController.attack(x, y);
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
        String name = matcher.group("name");
        GameMessage gameMessage = gameController.checkBuildSiegeEquipment(name);
        System.out.println(gameMessage);
    }

    private void disbandUnit() {
        gameController.disbandUnit();
        System.out.println("success!");
    }

    private void nextTurn() {
        gameController.nextTurn();
        System.out.println("now " + government.getLord().getUserName() + " is playing!");
    }

    private void clearElement(Matcher matcher) {

    }

    private void dropRock(Matcher matcher) {

    }

    private void dropUnits(Matcher matcher) {

    }

}
