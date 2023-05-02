package view;

import controller.GameController;
import model.Government;
import model.User;
import view.commands.GameMenuCommand;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private User user;
    private Government government;
    private final GameController gameController;
    private final ArrayList<Government> governments;
    public GameMenu(User user,ArrayList<Government> governments){
        this.governments = governments;
        this.user =user;
        this.government = governments.get(0);
        gameController = new GameController(governments,null);
    }

    public void run(Scanner scanner){
        String line;
        Matcher matcher;
        while (true){
            line = scanner.nextLine();
            if(line.equals("back")||checkWin())
                break;
            else if((GameMenuCommand.getMatcher(line,GameMenuCommand.SHOW_FACTORS))!=null)
                printPopularityFactors();
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SHOW_POPULARITY))!=null)
                showPopularity();
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SET_FOOD_RATE))!=null)
                setFoodRate(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SHOW_FOOD_LIST))!=null)
                showFoodList();
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SET_TAX))!=null)
                checkSetTaxRate(matcher);
            else if(GameMenuCommand.getMatcher(line,GameMenuCommand.SHOW_TAX_RATE)!=null)
                showTaxRate();
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.DROP_BUILDING))!=null)
                dropBuilding(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.GO_TO_SHOP))!=null)
                goToShop(matcher,scanner);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SELECT_BUILDING))!=null)
                chooseBuilding(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SHOW_FOOD_RATE))!=null)
                showFoodRate();
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.CREATE_UNIT))!=null)
                checkCreateUnit(matcher);
            else if(GameMenuCommand.getMatcher(line,GameMenuCommand.REPAIR)!=null)
                repairBuilding();
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.SELECT_UNIT))!=null)
                chooseUnit(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.MOVE_UNIT))!=null)
                moveUnitTo(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.CHANGE_STATE))!=null)
                setMyTroop(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.ATTACK))!=null)
                attackEnemy(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.ATTACK2))!=null)
                attackXY(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.POUR_OIL))!=null)
                pourOil(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.DIG_TUNNEL))!=null)
                digTunnel(matcher);
            else if((matcher = GameMenuCommand.getMatcher(line,GameMenuCommand.DISBAND_UNIT))!=null)
                disbandUnit();
            else if(GameMenuCommand.getMatcher(line,GameMenuCommand.NEXT_TURN) != null)
                nextTurn();
            else if(GameMenuCommand.getMatcher(line,GameMenuCommand.GO_TO_TRADE)!=null)
                goToTradeMenu(scanner);
            else
                System.out.println("invalid format!");

        }
    }

    private boolean checkWin(){
        return false;
    }
    private void printPopularityFactors(){
        System.out.println("repair");
    }
    private void goToTradeMenu(Scanner scanner){
        TradeMenu tradeMenu = new TradeMenu();
        tradeMenu.run(scanner,gameController.getCurrentGovernment());
    }
    private void goToShop(Matcher matcher,Scanner scanner){
       ShopMenu shopMenu = new ShopMenu(government);
       shopMenu.run(scanner);
    }

    private void  showPopularity(){
        System.out.println("your popularity is :"+government.getPopularity());
    }

    private  void showFoodList(){

    }

    private void setFoodRate(Matcher matcher){
        int rate = Integer.parseInt(matcher.group("rate"));

    }

    private void showFoodRate(){

    }

    private void checkSetTaxRate(Matcher matcher){
        int rate = Integer.parseInt(matcher.group("rate"));
    }
    private void showTaxRate(){
        System.out.println("your tax rate is :"+government.getTaxRate());
    }

    private void dropBuilding(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
    }

    private void chooseBuilding(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
    }

    private void checkCreateUnit(Matcher matcher){
        String type = matcher.group("type");
        int count  = Integer.parseInt(matcher.group("count"));
    }
    private void repairBuilding(){

    }

    private void chooseUnit(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
    }

    private void moveUnitTo(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
    }
    private void setMyTroop(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String state = matcher.group("state");
    }

    private void attackEnemy(Matcher matcher){
        String enemy = matcher.group("enemy");
    }

    private void attackXY(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
    }
    private void pourOil(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
    }

    private void digTunnel(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
    }
    private void makeSiegeUnit(Matcher matcher){

    }

    private void disbandUnit(){

    }

    private void nextTurn(){
        int n = governments.indexOf(user);
        n = (n+1) % governments.size();
        this.user = governments.get(n).getLord();
        gameController.nextTurn();
        gameController.setGovernment(governments.get(n));
    }

    private void setTextureUnit(Matcher matcher){

    }

    private void clearElement(Matcher matcher){

    }

    private void dropRock(Matcher matcher){

    }

    private void dropTree(Matcher matcher){

    }

    private void dropUnits(Matcher matcher){

    }

}
