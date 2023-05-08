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
import model.machine.Machine;
import model.machine.MachineDetails;
import view.message.GameMessage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Math.abs;

public class GameController {
    private ArrayList<Cell> path = new ArrayList<>();
    private HashSet<Cell> closed = new HashSet<>();
    private TreeMap<Integer, TreeMap<Integer, ArrayList<Cell>>> opens = new TreeMap<>();
    private HashSet<Cell> openSet = new HashSet<>();
    private Cell[][] map;
    private ArrayList<Government> governments;
    private Building selectedBuilding;
    private Worker selectedWorker;
    private Government currentGovernment;
    private ArrayList<Command> commands = new ArrayList<>();
    private MapController mapController;

    public GameMessage showFactor() {
        return GameMessage.FACTORS;
    }

    public GameController(ArrayList<Government> governments, Cell[][] map) {
        this.governments = governments;
        this.map = map;
        this.currentGovernment = governments.get(0);
    }

    public String showFoodList() {
        StringBuilder list = new StringBuilder();
        Storage storage = null;
        for (Building building : currentGovernment.getBuildings())
            if (building instanceof Storage && building.getBuildingsDetails().equals(BuildingsDetails.GRANARY))
                storage = (Storage) building;
        if (storage == null) return "";
        for (Map.Entry<Resource, Integer> entry : storage.getAvailableResources().entrySet())
            list.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        return list.toString();
    }

    public GameMessage SetFoodRate(int newRate) {
        if (newRate > 5 || newRate < -5) return GameMessage.INVALID_FOOD_RATE;
        currentGovernment.setFoodRate(newRate);
        return GameMessage.SUCCESS;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public GameMessage setTax(int tax) {//repair
        currentGovernment.setTaxRate(tax);
        return GameMessage.SUCCESS;
    }

    public int showFoodRate() {
        return currentGovernment.getFoodRate();
    }

    public int showTaxRate() {
        return currentGovernment.getTaxRate();
    }

    public GameMessage setFear(int fearRate) {
        if (fearRate > 5 || fearRate < -5) return GameMessage.INVALID_FEAR_RATE;
        currentGovernment.setFearRate(fearRate);
        return GameMessage.SUCCESS;
    }

    public GameMessage checkDropBuilding(int x, int y, String type) {
        BuildingsDetails buildingsDetails = BuildingsDetails.getBuildingDetailsByName(type);
        if (x > 200 || x < 1 || y > 200 || y < 1) return GameMessage.OUT_OF_RANGE;
        if (buildingsDetails == null) return GameMessage.NO_BUILDING;
        if (map[x - 1][y - 1].getBuilding() != null) return GameMessage.ALREADY_BUILDING;
        BuildingsDetails.BuildingType buildingType = buildingsDetails.getBuildingType();
        if (buildingType.equals(BuildingsDetails.BuildingType.PRODUCT_MAKER))
            if (!textureMatches(buildingsDetails, x, y)) return GameMessage.FAILURE1;
        for (Map.Entry<Resource, Integer> entry : buildingsDetails.getRequiredResource().entrySet())
            if (currentGovernment.getResources().get(entry.getKey()) < entry.getValue())
                return GameMessage.NOT_ENOUGH_RESOURCE;
        for (Map.Entry<Resource, Integer> entry : buildingsDetails.getRequiredResource().entrySet())
            currentGovernment.reduceResources(entry.getKey(), entry.getValue());
        commands.add(new Command("drop building", buildingsDetails, x, y));
        return GameMessage.SUCCESS;
    }

    private GameMessage dropBuilding(int x, int y, BuildingsDetails buildingsDetails) {
        BuildingsDetails.BuildingType buildingType = buildingsDetails.getBuildingType();
        switch (buildingType) {
            case PRODUCT_MAKER:
                currentGovernment.addBuilding(new ProductMaker(currentGovernment, map[x - 1][y - 1],
                        ProductMakerDetails.getProductMakerDetailsByBuildingDetails(buildingsDetails)));
                break;
            case STORAGE:
                currentGovernment.addBuilding(new Storage(currentGovernment, map[x - 1][y - 1],
                        StorageDetails.getStorageDetailsByBuildingDetails(buildingsDetails)));
                break;
            case RESIDENCY:
                currentGovernment.addBuilding(new Residency(currentGovernment, map[x - 1][y - 1],
                        ResidencyDetails.getResidencyDetailsByBuildingDetails(buildingsDetails)));
                break;
            case GATE:
                currentGovernment.addBuilding(new Gate(currentGovernment, map[x - 1][y - 1],
                        ResidencyDetails.getResidencyDetailsByBuildingDetails(buildingsDetails), true, false));
                break;
            case WEAPON_PRODUCTION:
                currentGovernment.addBuilding(new WeaponProduction(currentGovernment, map[x - 1][y - 1],
                        ProductMakerDetails.getProductMakerDetailsByBuildingDetails(buildingsDetails)));
                break;
            case STABLE:
                currentGovernment.addBuilding(new Stable(currentGovernment, BuildingsDetails.STABLE, map[x - 1][y - 1]));
                break;
            case TRAP:
                currentGovernment.addBuilding(new Trap(currentGovernment, buildingsDetails, map[x - 1][y - 1]));
                break;
            case QUARRY:
                currentGovernment.addBuilding(new Quarry(currentGovernment, map[x - 1][y - 1]));
            default:
                currentGovernment.addBuilding(new Building(currentGovernment, buildingsDetails, map[x - 1][y - 1]));
        }
        return GameMessage.SUCCESS;
    }

    private boolean textureMatches(BuildingsDetails buildingsDetails, int x, int y) {
        Cell cell = map[x - 1][y - 1];
        return (!buildingsDetails.getName().equals("quarry") || cell.getGroundTexture() == GroundTexture.STONE) &&
                (!buildingsDetails.getName().equals("iron mine") || cell.getGroundTexture() == GroundTexture.IRON) &&
                ((!buildingsDetails.getName().contains("farm") && !buildingsDetails.getName().equals("apple orchard")) ||
                        (cell.getGroundTexture() == GroundTexture.GRASS && cell.getGroundTexture() == GroundTexture.DENSE_MEADOW)) &&
                (!buildingsDetails.getName().equals("pitch rig") || cell.getGroundTexture() == GroundTexture.PLAIN);
    }

    public GameMessage selectBuilding(int x, int y) {
        selectedBuilding = map[x - 1][y - 1].getBuilding();
        if (selectedBuilding == null) return GameMessage.NO_BUILDING_TO_SELECT;
        return GameMessage.NO_BUILDING_TO_SELECT;
    }

    public GameMessage checkMakeTroop(String type, int count) {
        if (selectedBuilding == null) return GameMessage.NO_SELECTED_BUILDING;
        WorkerDetails worker = WorkerDetails.getWorkerDetailsByName(type);
        BuildingsDetails.BuildingType buildingType = selectedBuilding.getBuildingsDetails().getBuildingType();
        if (worker == null) return GameMessage.ANOTHER_PURPOSE;
        if (!buildingType.equals(BuildingsDetails.BuildingType.TROOP_TRAINER)) return GameMessage.FAILURE4;
        EuropeanSoldiersDetails europeanSoldiers = EuropeanSoldiersDetails.getDetailsByWorkerDetails(worker);
        if (!worker.getTrainerBuilding().equals(selectedBuilding.getBuildingsDetails())) return GameMessage.FAILURE5;
        if (getNumberOfPeasants() < count) return GameMessage.FAILURE5;
        if (worker.getGold() * count >
                ((Storage) (currentGovernment.getBuildingByName("stockpile"))).getAvailableResources().get(Resource.GOLD))
            return GameMessage.FAILURE5;
        if (europeanSoldiers != null) {
            for (Resource equipment : europeanSoldiers.getEquipments())
                if (currentGovernment.getResources().get(equipment) < 1)
                    return GameMessage.NOT_ENOUGH_RESOURCE;
            for (Resource equipment : europeanSoldiers.getEquipments())
                currentGovernment.reduceResources(equipment, 1);
        }
        commands.add(new Command("drop unit", worker, count));
        return GameMessage.SUCCESS;
    }

    private void makeTroop(WorkerDetails workerDetails, int count) {
        for (int i = 0; i < count; i++) currentGovernment.addTrainedPeople(workerDetails);
    }

    public int getNumberOfPeasants() {
        int count = 0;
        for (Person person : currentGovernment.getPeople())
            if (!(person instanceof Worker)) count++;
        return count;
    }

    public GameMessage repair() {
        if (selectedBuilding.getHitPoint() == selectedBuilding.getMaxHitPoint()) return GameMessage.REPAIR;
        double ratio = ((double) selectedBuilding.getHitPoint()) / ((double) selectedBuilding.getMaxHitPoint());
        for (Map.Entry<Resource, Integer> entry : selectedBuilding.getRequiredResource().entrySet()) {
            if (currentGovernment.getResources().get(entry.getKey()) < (int) Math.floor(ratio * entry.getValue()))
                return GameMessage.NOT_ENOUGH_RESOURCE;
        }
        for (Map.Entry<Resource, Integer> entry : selectedBuilding.getRequiredResource().entrySet())
            currentGovernment.reduceResources(entry.getKey(), (int) Math.floor(ratio * entry.getValue()));
        commands.add(new Command("repair", selectedBuilding));
        return GameMessage.SUCCESS;
    }

    public GameMessage selectUnit(int x, int y) {
        if (x > 200 || x < 1 || y > 200 || y < 1) return GameMessage.OUT_OF_RANGE;
        if (map[x - 1][y - 1].getPeople().size() == 0) return GameMessage.NO_PEOPLE_TO_SELECT;
        selectedWorker = map[x - 1][y - 1].getPeople().get(0);
        return GameMessage.SUCCESS;
    }

    public GameMessage moveUnit(int x, int y) {
        if (x > 200 || x < 1 || y > 200 || y < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedWorker == null) return GameMessage.NO_SELECTED_UNIT;
        int x1 = selectedWorker.getPosition().getxCoordinates();
        int y1 = selectedWorker.getPosition().getyCoordinates();
        if (Game.UNPASSABLE.contains(map[x - 1][y - 1].getGroundTexture()) || map[x - 1][y - 1].getExtra() != null)
            return GameMessage.UNREACHABLE_CELL;
        if ((abs(x - x1) + abs(y - y1)) > selectedWorker.getRange()) return GameMessage.UNREACHABLE_DISTANCE;
        selectedWorker.setDestination(map[x - 1][y - 1]);
        return GameMessage.SUCCESS;
    }

    public GameMessage patrolUnit(int x1, int y1, int x2, int y2) {
        if (x1 > 200 || x1 < 1 || y1 > 200 || y1 < 1) return GameMessage.OUT_OF_RANGE;
        if (x2 > 200 || x2 < 1 || y2 > 200 || y2 < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedWorker == null) return GameMessage.NO_SELECTED_UNIT;
        selectedWorker.setPatrolMovement(x1, y1, x2, y2);
        return GameMessage.SUCCESS;
    }

    public GameMessage setStateUnit(int x, int y, String mode) {
        if (map[x - 1][y - 1].getPeople().isEmpty()) return GameMessage.NO_PEOPLE_TO_SELECT;
        if (!(mode.equals("offensive") || mode.equals("defensive") || mode.equals("standing")))
            return GameMessage.INVALID_STATE;
        map[x - 1][y - 1].getPeople().get(0).setState(mode);
        return GameMessage.SUCCESS;
    }

    public GameMessage attack(int x, int y) {
        if (x > 200 || x < 1 || y > 200 || y < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedWorker == null || selectedWorker instanceof Engineer) return GameMessage.NO_SELECTED_UNIT;
        Worker enemy = null;
        for (Person person : map[x - 1][y - 1].getPeople()) {
            if (person instanceof Worker && !person.getGovernment().equals(selectedWorker.getGovernment()))
                enemy = (Worker) person;
        }
        if (enemy == null) return GameMessage.NO_ENEMY_TO_FIGHT;
        selectedWorker.setEnemy(enemy);
        return GameMessage.SUCCESS;
    }

    public GameMessage checkArcherAttack(int x, int y) {
        if (x > 200 || x < 1 || y > 200 || y < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedWorker == null) return GameMessage.NO_SELECTED_UNIT;
        String name = selectedWorker.getName();
        if (!(name.equals("archer") || name.equals("crossbowman") || name.equals("archer bow")))
            return GameMessage.NO_SUITABLE;
        if (getDistance(x, y) > selectedWorker.getRange()) return GameMessage.OUT_OF_RANGE;
        commands.add(new Command("attack", selectedWorker, x, y));
        return GameMessage.SUCCESS;
    }

    private int getDistance(int x, int y) {
        int a = abs(selectedWorker.getPosition().getxCoordinates() - x);
        int b = abs(selectedWorker.getPosition().getyCoordinates() - y);
        return a + b;
    }

    public GameMessage checkPourOil(String direction) {
        if (!(selectedWorker instanceof Engineer)) return GameMessage.NO_SUITABLE;
        if (!((Engineer) selectedWorker).getWorkplace().getBuildingsDetails().equals(BuildingsDetails.OIL_SMELTER))
            return GameMessage.FAILURE3;
        if (!((Engineer) selectedWorker).hasOil()) return GameMessage.NO_OIL;
        if (direction.length() > 1) return GameMessage.INVALID_DIRECTION;
        if (!(Game.directions.contains(direction))) return GameMessage.INVALID_DIRECTION;
        commands.add(new Command("pour oil", selectedWorker, direction));
        return GameMessage.SUCCESS;
    }

    public GameMessage checkDigTunnel(int x, int y, String direction) {
        if (!selectedWorker.getName().equals("tunneler")) return GameMessage.SELECT_TUNNELER;
        if (x > 200 || x < 1 || y > 200 || y < 1) return GameMessage.OUT_OF_RANGE;
        if (direction.length() > 1) return GameMessage.INVALID_DIRECTION;
        if (!Game.directions.contains(direction)) return GameMessage.INVALID_DIRECTION;
        if (!checkTunnel(x, y, direction)) return GameMessage.FAILURE2;
        commands.add(new Command("dig tunnel", selectedWorker, x, y, direction));
        return GameMessage.SUCCESS;
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
            if (horizontal) cell = map[x + i * dir][y];
            else cell = map[x][y + i * dir];
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

    public GameMessage checkBuildSiegeEquipment(String name) {
        ArrayList<Engineer> engineers = new ArrayList<>();
        if (!(selectedWorker instanceof Engineer)) return null;
        MachineDetails machineDetail = MachineDetails.getMachineDetailsByName(name);
        if (machineDetail == null) return null;
        if (numberOfEngineers() < machineDetail.getEngineersNeeded()) return null;
        currentGovernment.addBuilding(new Building(currentGovernment, BuildingsDetails.SIEGE_TENT, selectedWorker.getPosition()));
        for (Worker person : selectedWorker.getPosition().getPeople()) {
            if(person instanceof Engineer&& !((Engineer) person).hasMachine())
                engineers.add((Engineer) person);
            if(engineers.size()>=machineDetail.getEngineersNeeded())
                break;
        }
        if(engineers.size()<machineDetail.getEngineersNeeded())
            return null;
        for (Engineer engineer : engineers) {
            engineer.giveHimMachine(true);
        }
//        for (int i = 0; i < currentGovernment.getPopularity(); i++)
//            if (currentGovernment.getPeople().get(i) instanceof Engineer &&
//                    !(currentGovernment.getPeople().get(i).equals(selectedWorker)))
//                engineers.add((Engineer) currentGovernment.getPeople().get(i));
//        for (Engineer engineer : engineers) engineer.setDestination(selectedWorker.getPosition());
        commands.add(new Command("build equipment", machineDetail, selectedWorker.getPosition().getxCoordinates(),
                selectedWorker.getPosition().getyCoordinates(),engineers));
        return null;
    }

    private int numberOfEngineers() {
        int engineers = 0;
        for (Person person : currentGovernment.getPeople())
            if (person instanceof Engineer) engineers++;
        return engineers;
    }

    public GameMessage disbandUnit() {
        selectedWorker.setDestination(currentGovernment.getBuildingByName("castle").getCell());
        return null;
    }


    public void setGovernment(Government government) {
        this.currentGovernment = government;
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    private boolean checkHasPath(int x1, int y1, int x2, int y2) {
        return false;
    }

    private int calculateDistance(int x1, int y1, int x2, int y2) {
        return abs(x1 - x2) + abs(y1 - y2);
    }

    public void suggestPath(int x1, int y1, int x2, int y2) {
        if (x1 >= map.length || x1 < 0 || x2 >= map.length || x2 < 0 || y1 >= map.length || y1 < 0 || y2 >= map.length || y2 < 0)
            return;
        if (x1 == x2 && y1 == y2)
            return;
    }

    private boolean checkValidity(int x1, int y1, int x2, int y2) {
        if (x1 >= map.length || x1 < 0 || x2 >= map.length || x2 < 0 || y1 >= map.length || y1 < 0 || y2 >= map.length
                || y2 < 0)
            return false;
        return true;
    }

    private void checkCell(int x1, int y1, int distanceOfStart, int distanceOfDestination, char dir) {
        if (!checkValidity(x1, y1, x1, y1))
            return;
        if (closed.contains(map[x1][y1])) {
            return;
        }
        map[x1][y1].changeDirection(dir);
        int total = distanceOfStart + distanceOfDestination;
        map[x1][y1].setDistanceOfStart(distanceOfStart);
        map[x1][y1].setTotal(total);
        openSet.add(map[x1][y1]);
        if (opens.get(total) == null) {
            opens.put(total, new TreeMap<Integer, ArrayList<Cell>>());
        }
        if (opens.get(total).get(distanceOfStart) == null) {
            opens.get(total).put(distanceOfStart, new ArrayList<Cell>());
        }
        opens.get(total).get(distanceOfStart).add(map[x1][y1]);
    }

    private void find2Path(int x1, int y1, int x2, int y2, int distancesOfStart) {
        int totalDistance = abs(x1 - x2) + abs(y1 - y2) + distancesOfStart;
        int endDistance = abs(x1 - x2) + abs(y1 - y2);
        if (x1 >= map.length || x1 < 0 || x2 >= map.length || x2 < 0 || y1 >= map.length || y1 < 0 || y2 >= map.length
                || y2 < 0)
            return;
        if (!map[x1][y1].checkCross(map[x1][y1].getDirection()))
            return;
        map[x1][y1].cross();
        if (map[x1][y1].getTotal() == 0 || map[x1][y1].getTotal() >= totalDistance) {
            map[x1][y1].setDistanceOfStart(distancesOfStart);
            map[x1][x2].setTotal(totalDistance);
        }
        if (closed.contains(map[x1][x2]))
            return;
        if (x1 == x2 && y1 == y2)
            return;
        openSet.remove(map[x1][y1]);
        closed.add(map[x1][y1]);
        if (opens.get(totalDistance) == null)
            return;
        if (opens.get(totalDistance).get(distancesOfStart) == null)
            return;
        opens.get(totalDistance).get(distancesOfStart).remove(map[x1][y1]);
        checkCell(x1 + 1, y1, distancesOfStart + 1, abs(x1 + 1 - x2) + abs(y1 - y2), 'e');
        checkCell(x1 - 1, y1, distancesOfStart + 1, abs(x1 - 1 - x2) + abs(y1 - y2), 'w');
        checkCell(x1, y1 + 1, distancesOfStart + 1, abs(x1 - x2) + abs(y1 + 1 - y2), 'n');
        checkCell(x1, y1 - 1, distancesOfStart + 1, abs(x1 - x2) + abs(y1 - 1 - y2), 'e');
    }

    private void callOtherFunction(int x1, int y1, int x, int y) {
        checkCell(x1, y1, 0, abs(x1 - x) + abs(y1 - y), 'a');
        while (opens.size() != 0 && map[x][y].getDirection() == 'a') {
            Integer i = opens.firstKey();
            if (i == null)
                return;
            TreeMap<Integer, ArrayList<Cell>> treeMap = opens.get(i);
            if (treeMap == null)
                return;
            Integer i2 = treeMap.firstKey();
            if (i2 == null)
                return;
            ArrayList<Cell> myCells = treeMap.get(i2);
            if (myCells == null || myCells.size() == 0) {
                treeMap.remove(myCells);
            } else {
                Cell cell = myCells.get(0);
                myCells.remove(0);
                find2Path(cell.getxCoordinates(), cell.getyCoordinates(), x, y, cell.getDistanceOfStart());
                if (myCells.size() == 0)
                    treeMap.remove(i2);
                if (treeMap.size() == 0)
                    opens.remove(i);

            }
        }
        decodePath(x,y);
    }

    private void decodePath(int x2, int y2) {
        path = new ArrayList<>();
        if (!checkValidity(y2, y2, x2, y2))
            return;
        if (map[x2][y2].getDirection() == 'a')
            return;
        path.add(map[x2][y2]);
        while (map[x2][y2].getDirection() != 'a') {
            char dir = map[x2][y2].getDirection();
            switch (dir) {
                case 'n':
                    y2--;
                    break;
                case 's':
                    y2++;
                    break;
                case 'w':
                    x2++;
                    break;
                case 'e':
                    x2--;
                    break;
            }
            path.add(map[x2][y2]);
        }
    }

    public void nextTurn() {
        doCommands();
        updateTroops();
        currentGovernment.doActionInTurnFirst();
        clearDeadSoldiers();
    }

    //moving

    public void attackInRange(Worker soldier){
        int x=soldier.getPosition().getxCoordinates();
        int y=soldier.getPosition().getyCoordinates();
        int range=0;
        if(soldier.getState().equals("standing"))
            range=soldier.getRange();
        else if (soldier.getState().equals("defensive"))
            range= soldier.getRange()+1;
        else if(soldier.getState().equals("attacking"))
            range=soldier.getRange()+ soldier.getSpeed();
        for(int b=0;b<=range;b++) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    for (Worker person : map[i - 1][j - 1].getPeople()) {
                        if (!person.getGovernment().equals(soldier.getGovernment())&&person.getEnemy()!=null)
                            soldier.setEnemy(person);
                    }
                }
            }
        }
    }
    public void clearDeadSoldiers(){
        for (Government government : governments) {
            for (Person person : government.getPeople()) {
                if (person.getHitPoint() <= 0) {
                    person.delete();
                }
            }
        }
    }

    public void doTheMove(Worker person,ArrayList<Cell> way){
            //the array is from last to first
        int length= way.size();
        for(int i=1;i<=person.getSpeed();i++) {
            way.get(length-i).deletePerson(person);
            if(way.get(length- 1- i).doesHaveHole()||way.get(length- 1- i).doesHaveOil())
                person.getDamaged(10);
            if(person.getHitPoint()>0)
                way.get(length- 1- i).addPerson(person);
            way.remove(length-i);
            //check if goes well..{the changes in the cell and the hitpoint of the person
        }
    }

    private void updateStorage() {

    }

    private void updateTroops() {
        //moving
        for (Government government : governments) {
            for (Person person : government.getPeople()) {
                //doTheMove(person,Morteza);
            }
        }
        for (Person person : currentGovernment.getPeople()) {
            damage(person);
        }
        clearDeadSoldiers();
    }

    private void damage(Person person) {
        int hitDamage=((Worker)person).getDamage();
        if(isEnemyInRange(person)==true) {
            int defenseRate=((Worker)person).getEnemy().getDefense();
            if(hitDamage>defenseRate)
                ((Worker) person).getEnemy().getDamaged(hitDamage-defenseRate);
        }
        else if(((Worker)findRandomEnemy(person))!=null){
            int defenseRate=((Worker)findRandomEnemy(person)).getDefense();
            ((Worker)findRandomEnemy(person)).getDamaged(hitDamage-defenseRate);
        }
    }

    private Person findRandomEnemy(Person person){
        int range=((Worker)person).getRange();
        for(int b=0;b<=range;b++) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    for (Worker worker : map[i - 1][j - 1].getPeople()) {
                        if (!person.getGovernment().equals(worker.getGovernment()))
                            return worker;
                    }
                }
            }
        }
        return null;
    }
    private boolean isEnemyInRange(Person person){
        int x1=((Worker)person).getEnemy().getPosition().getxCoordinates();
        int y1=((Worker)person).getEnemy().getPosition().getyCoordinates();
        int x2=((Worker) person).getPosition().getxCoordinates();
        int y2=((Worker) person).getPosition().getyCoordinates();
        int distance=calculateDistance(x1,y1,x2,y2);
        if(distance<=((Worker)person).getRange())
            return true;
        return false;
    }
    private void doCommands() {
        for (Command command : commands) {
            switch (command.name){
                case "repair":
                    command.building.repairHitpoint();
                    break;
                case "drop building":
                    Building building=new Building(currentGovernment,command.buildingsDetails,map[command.x][command.y]);
                    map[command.x][command.y].setBuilding(building);
                    //call it
                    break;
                case "drop unit":
                    int x=command.worker.getPosition().getxCoordinates();
                    int y=command.worker.getPosition().getyCoordinates();
                    //map[x][y].addPeople
                    //add to the government
                    //consequences???
                    break;
                case "attack":
                    command.worker.setDestination(map[command.x][command.y]);
                    break;
                case "pour oil":
                    pourOil(command);
                    break;
                case "build equipment":
                    //repair: build a machine with passing the machine details to the constructor
                    Machine machine=new Machine();
                    for (Engineer engineer : command.engineers) {
                        engineer.setMachine(machine);
                    }
                    machine.getCell().addMachine(machine);
//                case "the rest":??
            }
        }
    }

    public void pourOil(Command command)
    {
        String direction=command.direction;
        int x=selectedWorker.getPosition().getxCoordinates();
        int y=selectedWorker.getPosition().getyCoordinates();
        //check the logic of direction
        switch (direction){
            case "n":
                y++;
                break;
            case "s":
                y--;
                break;
            case "e":
                x++;
                break;
            case "w":
                x--;
                break;
        }
        for (Worker person : map[x][y].getPeople()) {
            person.getDamaged(5);
        }
        //give the nearest  oil smelter
        //send him there
    }


    //to do: finish the commands processing with all considerations
    //update troop but what written
    //update and consider all the government factors
    //what check archer attack does and what consequences it have while next turn
    //pour oil
    //dig tunnel
}
