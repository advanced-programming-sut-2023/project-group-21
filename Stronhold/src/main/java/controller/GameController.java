package controller;

import model.Cell;
import model.Government;
import model.building.*;
import model.building.Enums.*;
import model.generalenums.GroundTexture;
import model.generalenums.Resource;
import model.human.Enums.WorkerDetails;
import model.human.Person;
import model.human.Worker;
import view.message.GameMessage;

import java.util.ArrayList;
import java.util.Map;

public class GameController {
    private Cell[][] map = new Cell[200][200];
    private ArrayList<Government> governments;
    private Building selectedBuilding;
    private Government currentGovernment;
    public GameMessage showFactor(){
        return null;
    }

    public String showFoodList(){
        StringBuilder list = new StringBuilder();
        Storage storage = null;
        for (Building building: currentGovernment.getBuildings())
            if (building instanceof Storage && building.getBuildingsDetails().equals(BuildingsDetails.GRANARY)) storage = (Storage) building;
        if (storage == null) return "";
        for (Map.Entry<Resource, Integer> entry: storage.getAvailableResources().entrySet())
            list.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        return list.toString();
    }

    public GameMessage SetFoodRate(int newRate){
        if (newRate > 5 || newRate < -5) return null;
        currentGovernment.setFoodRate(newRate);
        return null;
    }

    public GameMessage setTax(int tax){
        currentGovernment.setTaxRate(tax);
        return null;
    }

    public int showFoodRate(){
        return currentGovernment.getFoodRate();
    }
    public int showTaxRate(){
        return currentGovernment.getTaxRate();
    }

    public GameMessage setFear(int fearRate){
        currentGovernment.setFearRate(fearRate);
        return null;
    }

    public GameMessage DropBuilding(int x,int y,String type){
        BuildingsDetails buildingsDetails = BuildingsDetails.getBuildingDetailsByName(type);
        if (x > 200 || x < 1 || y > 200 || y < 1) return null;
        if (buildingsDetails == null) return null;
        if (map[x-1][y-1].getBuilding() != null) return null;
        BuildingsDetails.BuildingType buildingType = buildingsDetails.getBuildingType();
        switch (buildingType) {
            case PRODUCT_MAKER:
                if (!textureMatches(buildingsDetails, x, y)) return null;
                currentGovernment.addBuilding(new ProductMaker(currentGovernment, map[x-1][y-1],
                        ProductMakerDetails.getProductMakerDetailsByBuildingDetails(buildingsDetails)));
                break;
            case STORAGE:
                currentGovernment.addBuilding(new Storage(currentGovernment, map[x-1][y-1],
                        StorageDetails.getStorageDetailsByBuildingDetails(buildingsDetails)));
            case TROOP_TRAINER:
                currentGovernment.addBuilding(new TroopTrainer(currentGovernment, map[x-1][y-1],
                        TroopTrainerDetails.getTroopTrainerDetailsByBuildingDetails(buildingsDetails)));
            case RESIDENCY:
                currentGovernment.addBuilding(new Residency(currentGovernment, map[x-1][y-1],
                        ResidencyDetails.getResidencyDetailsByBuildingDetails(buildingsDetails)));
            case GATE:
                currentGovernment.addBuilding(new Gate(currentGovernment, map[x-1][y-1],
                        ResidencyDetails.getResidencyDetailsByBuildingDetails(buildingsDetails), true, false));
            case WEAPON_PRODUCTION:
                currentGovernment.addBuilding(new WeaponProduction(currentGovernment, map[x-1][y-1],
                        ProductMakerDetails.getProductMakerDetailsByBuildingDetails(buildingsDetails)));
            default:
                currentGovernment.addBuilding(new Building(currentGovernment, buildingsDetails, map[x-1][y-1]));
        }
        return null;
    }

    private boolean textureMatches(BuildingsDetails buildingsDetails, int x, int y) {
        Cell cell = map[x-1][y-1];
        return (!buildingsDetails.getName().equals("quarry") || cell.getGroundTexture() == GroundTexture.STONE) &&
                (!buildingsDetails.getName().equals("iron mine") || cell.getGroundTexture() == GroundTexture.IRON) &&
                ((!buildingsDetails.getName().contains("farm") && !buildingsDetails.getName().equals("apple orchard")) ||
                        (cell.getGroundTexture() == GroundTexture.GRASS && cell.getGroundTexture() == GroundTexture.DENSE_MEADOW)) &&
                (!buildingsDetails.getName().equals("pitch rig") || cell.getGroundTexture() == GroundTexture.PLAIN);
    }
    public GameMessage selectBuilding(int x,int y){
        selectedBuilding = map[x-1][y-1].getBuilding();
        if (selectedBuilding == null) return null;
        return null;
    }

    public GameMessage makeTroop(String type,int count){
        WorkerDetails worker = WorkerDetails.getWorkerDetailsByName(type);
        if (getNumberOfPeasants() < count) return null;
        if (worker.getGold() * count > )
        if (worker == null) return null;
        if (! (selectedBuilding instanceof TroopTrainer)) return null;
        if (!worker.getTrainerBuilding().equals(((TroopTrainer) selectedBuilding).getTroopTrainerDetails())) return null;
        ((TroopTrainer) selectedBuilding).addToQueue(worker, count,
                map[selectedBuilding.getCell().getxCoordinates()+1][selectedBuilding.getCell().getyCoordinates()]);
        return null;
    }

    public int getNumberOfPeasants() {
        int count = 0;
        for (Person person: currentGovernment.getPeople())
            if (!(person instanceof Worker)) count++;
        return count;
    }

    public GameMessage repair(){
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
