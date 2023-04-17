package controller;

import view.message.GameMessage;

public class GameMenuController {
    public GameMessage showFactor(){
        return null;
    }

    public String showFoodList(){
        return "repair";
    }

    public GameMessage SetFoodRate(int newRate){
        return null;
    }

    public GameMessage setTax(int tax){
        return null;
    }

    public String showFoodRate(){
        return "repair";
    }
    public String showTaxRate(){
        return "repair";
    }

    public GameMessage setFear(int fearRate){
        return null;
    }

    public GameMessage DropBuilding(int x,int y,String type){
        return null;
    }
    public GameMessage selectBuilding(int x,int y){
        return null;
    }

    public GameMessage makeTroop(String type,int count){
        return null;
    }

    public GameMessage repiar(){
        return null;
    }

    public GameMessage selectUnit(int x,int y){
        return null;
    }
    public GameMessage moveUnit(int x,int y){
    return null;
    }

    public GameMessage patrolUnti(int x1,int y1,int x2,int y2){
        return null;
    }
    public GameMessage setStateUnit(int x,int y,String mode){
        return null;
    }

    public GameMessage attackToEnemy(String enemyUsername){
        return null;
    }

    public GameMessage attack(int x1,int y1,int x2,int y2){
        return null;
    }

    public GameMessage pourOil(char direction){
        return null;
    }

    public GameMessage dig(int x,int y){
        return null;
    }
    public GameMessage buildSiegeEquipment(String name){
        return null;
    }

    public GameMessage disbandUnit(){
        return null;
    }

    public void nextTurn(){
        updateBuilding();
        updateTroops();
        updateStorage();
    }

    private void updateStorage(){

    }

    private void updateTroops(){

    }

    private void updateBuilding(){

    }


}
