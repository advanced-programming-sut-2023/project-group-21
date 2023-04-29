package controller;

import model.Cell;
import model.Game;
import model.Government;
import model.building.*;
import model.building.Enums.*;
import model.generalenums.GroundTexture;
import model.generalenums.Resource;
import model.human.Engineer;
import model.human.Enums.EuropeanSoldiersDetails;
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
    private Worker selectedWorker;
    private Government currentGovernment;
    private ArrayList<Command> commands = new ArrayList<>();
    public GameMessage showFactor(){
        return null;
    }

    public GameController(ArrayList<Government> governments,Cell[][] map){
        this.governments = governments;
        this.map = map;
        this.currentGovernment = governments.get(0);
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

    public GameMessage checkDropBuilding(int x, int y, String type) {
        BuildingsDetails buildingsDetails = BuildingsDetails.getBuildingDetailsByName(type);
        if (x > 200 || x < 1 || y > 200 || y < 1) return null;
        if (buildingsDetails == null) return null;
        if (map[x-1][y-1].getBuilding() != null) return null;
        BuildingsDetails.BuildingType buildingType = buildingsDetails.getBuildingType();
        if (buildingType.equals(BuildingsDetails.BuildingType.PRODUCT_MAKER))
            if (!textureMatches(buildingsDetails, x, y)) return null;
        for (Map.Entry<Resource, Integer> entry: buildingsDetails.getRequiredResource().entrySet())
            if (currentGovernment.getResources().get(entry.getKey()) < entry.getValue()) return null;
        for (Map.Entry<Resource, Integer> entry: buildingsDetails.getRequiredResource().entrySet())
            currentGovernment.reduceResources(entry.getKey(), entry.getValue());
        commands.add(new Command("drop building", buildingsDetails, x, y));
        return null;
    }

    private GameMessage dropBuilding(int x,int y, BuildingsDetails buildingsDetails){
        BuildingsDetails.BuildingType buildingType = buildingsDetails.getBuildingType();
        switch (buildingType) {
            case PRODUCT_MAKER:
                currentGovernment.addBuilding(new ProductMaker(currentGovernment, map[x-1][y-1],
                        ProductMakerDetails.getProductMakerDetailsByBuildingDetails(buildingsDetails)));
                break;
            case STORAGE:
                currentGovernment.addBuilding(new Storage(currentGovernment, map[x-1][y-1],
                        StorageDetails.getStorageDetailsByBuildingDetails(buildingsDetails)));
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

    public GameMessage checkMakeTroop(String type,int count){
        if (selectedBuilding == null) return null;
        WorkerDetails worker = WorkerDetails.getWorkerDetailsByName(type);
        BuildingsDetails.BuildingType buildingType = selectedBuilding.getBuildingsDetails().getBuildingType();
        if (worker == null) return null;
        if (!buildingType.equals(BuildingsDetails.BuildingType.TROOP_TRAINER)) return null;
        EuropeanSoldiersDetails europeanSoldiers = EuropeanSoldiersDetails.getDetailsByWorkerDetails(worker);
        if (!worker.getTrainerBuilding().equals(selectedBuilding.getBuildingsDetails())) return null;
        if (getNumberOfPeasants() < count) return null;
        if (worker.getGold() * count >
                ((Storage) (currentGovernment.getBuildingByName("stockpile"))).getAvailableResources().get(Resource.GOLD))
            return null;
        if (europeanSoldiers != null) {
            for (Resource equipment: europeanSoldiers.getEquipments())
                if (currentGovernment.getResources().get(equipment) < 1)
                    return null;
            for (Resource equipment: europeanSoldiers.getEquipments())
                currentGovernment.reduceResources(equipment, 1);
        }
        commands.add(new Command("drop unit", worker, count));
        return null;
    }

    private void makeTroop(WorkerDetails workerDetails, int count) {
        for (int i = 0; i < count; i++) currentGovernment.addTrainedPeople(workerDetails);
    }

    public int getNumberOfPeasants() {
        int count = 0;
        for (Person person: currentGovernment.getPeople())
            if (!(person instanceof Worker)) count++;
        return count;
    }

    public GameMessage repair() {
        if (selectedBuilding.getHitPoint() == selectedBuilding.getMaxHitPoint()) return null;
        double ratio = ((double) selectedBuilding.getHitPoint()) / ((double) selectedBuilding.getMaxHitPoint());
        for (Map.Entry<Resource, Integer> entry: selectedBuilding.getRequiredResource().entrySet()) {
            if (currentGovernment.getResources().get(entry.getKey()) < (int) Math.floor(ratio * entry.getValue()))
                return null;
        }
        for (Map.Entry<Resource, Integer> entry: selectedBuilding.getRequiredResource().entrySet())
            currentGovernment.reduceResources(entry.getKey(), (int) Math.floor(ratio * entry.getValue()));
        commands.add(new Command("repair", selectedBuilding));
        return null;
    }

    public GameMessage selectUnit(int x,int y){
        if (x > 200 || x < 1 || y > 200 || y < 1) return null;
        if (map[x-1][y-1].getPeople().size() == 0) return null;
        selectedWorker = map[x-1][y-1].getPeople().get(0);
        return null;
    }
    public GameMessage moveUnit(int x,int y){
        if (x > 200 || x < 1 || y > 200 || y < 1) return null;
        if (selectedWorker == null) return null;
        int x1 = selectedWorker.getPosition().getxCoordinates();
        int y1 = selectedWorker.getPosition().getyCoordinates();
        if (Game.UNPASSABLE.contains(map[x-1][y-1].getGroundTexture()) || map[x-1][y-1].getExtra() != null)
            return null;
        if ((Math.abs(x - x1) + Math.abs(y - y1)) > selectedWorker.getRange()) return null;
        selectedWorker.setDestination(map[x-1][y-1]);
        return null;
    }

    public GameMessage patrolUnit(int x1,int y1,int x2,int y2) {
        if (x1 > 200 || x1 < 1 || y1 > 200 || y1 < 1) return null;
        if (x2 > 200 || x2 < 1 || y2 > 200 || y2 < 1) return null;
        if (selectedWorker == null) return null;
        selectedWorker.setPatrolMovement(x1, y1, x2, y2);
        return null;
    }
    public GameMessage setStateUnit(int x,int y,String mode){
        if (map[x-1][y-1].getPeople().isEmpty()) return null;
        if (!(mode.equals("offensive") || mode.equals("defensive") || mode.equals("standing"))) return null;
        map[x-1][y-1].getPeople().get(0).setState(mode);
        return null;
    }

    public GameMessage attack(int x, int y) {
        if (x > 200 || x < 1 || y > 200 || y < 1) return null;
        if (selectedWorker == null || selectedWorker instanceof Engineer) return null;
        Worker enemy = null;
        for (Person person: map[x-1][y-1].getPeople()) {
            if (person instanceof Worker && !person.getGovernment().equals(selectedWorker.getGovernment()))
                enemy = (Worker) person;
        }
        if (enemy == null) return null;
        selectedWorker.setEnemy(enemy);
        return null;
    }

    public GameMessage checkArcherAttack(int x, int y) {
        if (x > 200 || x < 1 || y > 200 || y < 1) return null;
        if (selectedWorker == null) return null;
        String name = selectedWorker.getName();
        if (!(name.equals("archer") || name.equals("crossbowman") || name.equals("archer bow"))) return null;
        if (getDistance(x, y) > selectedWorker.getRange()) return null;
        commands.add(new Command("attack", selectedWorker, x, y));
        return null;
    }

    private int getDistance(int x, int y) {
        int a = Math.abs(selectedWorker.getPosition().getxCoordinates() - x);
        int b = Math.abs(selectedWorker.getPosition().getyCoordinates() - y);
        return a + b;
    }

    public GameMessage checkPourOil(String direction){
        if (!(selectedWorker instanceof Engineer)) return null;
        if (!((Engineer) selectedWorker).getWorkplace().getBuildingsDetails().equals(BuildingsDetails.OIL_SMELTER))
            return null;
        if (!((Engineer) selectedWorker).hasOil()) return null;
        if (direction.length() > 1) return null;
        if (!(Game.directions.contains(direction))) return null;
        commands.add(new Command("pour oil", selectedWorker, direction));
        return null;
    }

    public GameMessage checkDigTunnel(int x,int y, String direction){
        if (!selectedWorker.getName().equals("tunneler")) return null;
        if (x > 200 || x < 1 || y > 200 || y < 1) return null;
        if (direction.length() > 1) return null;
        if (!Game.directions.contains(direction)) return null;
        if (!checkTunnel(x, y, direction)) return null;
        commands.add(new Command("dig tunnel", selectedWorker, x, y, direction));
        return null;
    }

    private boolean checkTunnel(int x, int y, String direction) {
        Cell cell;
        boolean horizontal = true;
        int dir = 0;
        switch (direction) {
            case "w" -> dir = -1;
            case "e" -> dir = 1;
            case "n" -> {
                horizontal = false;
                dir = -1;
            }
            case "s" -> {
                horizontal = false;
                dir = 1;
            }
        }
        for (int i = 0; i < 6; i++) {
            if (horizontal) cell = map[x+i*dir][y];
            else cell = map[x][y+i*dir];
            if (cell.getBuilding().getBuildingsDetails().getBuildingType().equals(BuildingsDetails.BuildingType.TRAP) ||
                    cell.getBuilding().getName().equals("ditch")) return false;
            if (cell.getBuilding().getBuildingsDetails().getBuildingType().equals(BuildingsDetails.BuildingType.TOWER)) {
                String name = cell.getBuilding().getName();
                if (name.equals("perimeter tower") || name.equals("square tower") || name.equals("round tower"))
                    return false;
            }
        }
        return true;
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

    public void setGovernment(Government government){
        this.currentGovernment = government;
    }

    private void updateStorage(){

    }

    private void updateTroops(){

    }

    private void updateBuilding(){

    }


}
