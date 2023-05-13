package controller;

import model.Cell;
import model.Game;
import model.Government;
import model.building.*;
import model.building.Enums.*;
import model.generalenums.GroundTexture;
import model.generalenums.Resource;
import model.human.Assassin;
import model.human.Engineer;
import model.human.Enums.EuropeanSoldiersDetails;
import model.human.Enums.WorkerDetails;
import model.human.Person;
import model.human.Worker;
import model.machine.Machine;
import model.machine.MachineDetails;
import view.message.GameMessage;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.*;
import static java.lang.Math.max;

public class GameController {
    private ArrayList<Cell> path = new ArrayList<>();
    private int xCoordinates, yCoordinates;
    private HashSet<Cell> closed = new HashSet<>();
    private TreeMap<Integer, TreeMap<Integer, ArrayList<Cell>>> opens = new TreeMap<>();
    private HashSet<Cell> openSet = new HashSet<>();
    private Cell[][] map;
    private ArrayList<Government> governments;
    private Building selectedBuilding;
    private Worker selectedWorker;
    private Government currentGovernment;
    private MapController mapController;
    private Map<Engineer, String> pouringOils = new HashMap<>();
    private final List<WorkerDetails> ARCHERS = Arrays.asList(WorkerDetails.ARCHER, WorkerDetails.CROSSBOWMAN, WorkerDetails.ARABIAN_BOW);

    public GameController(ArrayList<Government> governments, Cell[][] map) {
        this.governments = governments;
        this.map = map;
        this.currentGovernment = governments.get(0);
    }

    public String showFoodList() {
        return currentGovernment.showStorage(BuildingsDetails.GRANARY);
    }

    public String showPopularityFactors() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1. Food Variety: ").append(currentGovernment.getFoodVariety()).append("\n");
        stringBuilder.append("2. Food Rate: ").append(currentGovernment.getFoodRate());
        stringBuilder.append(" | Food Count: ").append(currentGovernment.getLeftFood()).append("\n");
        stringBuilder.append("3. Tax Rate: ").append(currentGovernment.getTaxRate()).append("\n");
        stringBuilder.append("4. Fear Rate: ").append(currentGovernment.getFearRate()).append("\n\n");
        stringBuilder.append("Popularity Rate: ").append(currentGovernment.getPopularityRate());
        return stringBuilder.toString();
    }

    public GameMessage setFoodRate(int newRate) {
        if (newRate > 2 || newRate < -2) return GameMessage.INVALID_FOOD_RATE;
        currentGovernment.setFoodRate(newRate);
        return GameMessage.SUCCESS;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public GameMessage setTaxRate(int tax) {
        if (tax > 8 || tax < -3) return GameMessage.INVALID_TEX_RATE;
        currentGovernment.setTaxRate(tax);
        return GameMessage.SUCCESS;
    }

    public int showFoodRate() {
        return currentGovernment.getFoodRate();
    }

    public int showTaxRate() {
        return currentGovernment.getTaxRate();
    }

    public GameMessage setFearRate(int fearRate) {
        if (fearRate > 5 || fearRate < -5) return GameMessage.INVALID_FEAR_RATE;
        currentGovernment.setFearRate(fearRate);
        return GameMessage.SUCCESS;
    }

    public GameMessage checkDropBuilding(int x, int y, String type) {
        BuildingsDetails buildingsDetails = BuildingsDetails.getBuildingDetailsByName(type);
        if (x > map.length || x < 1 || y > map.length || y < 1) return GameMessage.OUT_OF_RANGE;
        if (buildingsDetails == null) return GameMessage.NO_BUILDING;
        if (map[x - 1][y - 1].getExtra() != null) return GameMessage.OCCUPIED;
        if (map[x - 1][y - 1].getBuilding() != null) return GameMessage.ALREADY_BUILDING;
        BuildingsDetails.BuildingType buildingType = buildingsDetails.getBuildingType();
        if (buildingType.equals(BuildingsDetails.BuildingType.PRODUCT_MAKER))
            if (!textureMatches(buildingsDetails, x, y)) return GameMessage.BAD_TEXTURE;
        if (getNumberOfPeasants() < buildingsDetails.getWorkersCount()) return GameMessage.NOT_ENOUGH_PEOPLE;
        if (buildingType.equals(BuildingsDetails.BuildingType.OIL_SMELTER)) {
            boolean hasEngineer = false;
            for (Worker worker: map[x-1][y-1].getPeople()) {
                if (worker instanceof Engineer && !((Engineer) worker).hasOil() && !((Engineer) worker).hasMachine() &&
                    ((Engineer) worker).getWorkplace() == null) {
                    hasEngineer =true;
                    break;
                }
            }
            if (!hasEngineer) return GameMessage.NO_ENGINEERS;
        }
        if (buildingsDetails.getRequiredResource() != null) {
            for (Map.Entry<Resource, Integer> entry : buildingsDetails.getRequiredResource().entrySet())
                if (currentGovernment.getResources().get(entry.getKey()) < entry.getValue())
                    return GameMessage.NOT_ENOUGH_RESOURCE;
            for (Map.Entry<Resource, Integer> entry : buildingsDetails.getRequiredResource().entrySet())
                currentGovernment.reduceResources(entry.getKey(), entry.getValue());
        }
        dropBuilding(x, y, buildingsDetails);
        return GameMessage.SUCCESS;
    }
    public String showDetails(int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return "out of index!!";
        try {
            return map[x - 1][y - 1].showDetails();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "out of index";
        }
    }
    private void dropBuilding(int x, int y, BuildingsDetails buildingsDetails) {
        BuildingsDetails.BuildingType buildingType = buildingsDetails.getBuildingType();
        ArrayList<Person> persons = new ArrayList<>();
        int number = buildingsDetails.getWorkersCount();
        for (Person person: currentGovernment.getPeople()) {
            if (!(person instanceof Worker) && person.getWorkPlace() == null) {
                if (number-- == 0) break;
                persons.add(person);
            }
        }
        switch (buildingType) {
            case PRODUCT_MAKER:
                currentGovernment.addBuilding(new ProductMaker(currentGovernment, map[x - 1][y - 1],
                        ProductMakerDetails.getProductMakerDetailsByBuildingDetails(buildingsDetails), persons));
                break;
            case STORAGE:
                currentGovernment.addBuilding(new Storage(currentGovernment, map[x - 1][y - 1],
                        ContainerDetails.getContainerByBuilding(buildingsDetails), persons));
                break;
            case GATE:
                currentGovernment.addBuilding(new Gate(currentGovernment, map[x - 1][y - 1],
                        ResidencyDetails.getResidencyDetailsByBuildingDetails(buildingsDetails), persons, true, false));
                break;
            case RESIDENCY:
                currentGovernment.addBuilding(new Residency(currentGovernment, map[x - 1][y - 1],
                        ResidencyDetails.getResidencyDetailsByBuildingDetails(buildingsDetails), persons));
                break;
            case WEAPON_PRODUCTION:
                currentGovernment.addBuilding(new WeaponProduction(currentGovernment, map[x - 1][y - 1],
                        ProductMakerDetails.getProductMakerDetailsByBuildingDetails(buildingsDetails), persons));
                break;
            case STABLE:
                currentGovernment.addBuilding(new Stable(currentGovernment, BuildingsDetails.STABLE, map[x - 1][y - 1], persons));
                break;
            case TRAP:
                currentGovernment.addBuilding(new Trap(currentGovernment, buildingsDetails, map[x - 1][y - 1], persons));
                break;
            case QUARRY:
                currentGovernment.addBuilding(new Quarry(currentGovernment, map[x - 1][y - 1], persons));
            case TOWER:
                currentGovernment.addBuilding(new Tower(currentGovernment, map[x-1][y-1],
                        TowerDetails.getTowerDetailsByBuildingDetails(buildingsDetails), persons));
            default:
                currentGovernment.addBuilding(new Building(currentGovernment, buildingsDetails, map[x - 1][y - 1], persons));
        }

    }

    private boolean textureMatches(BuildingsDetails buildingsDetails, int x, int y) {
        Cell cell = map[x - 1][y - 1];
        return (!buildingsDetails.getName().equals("quarry") || cell.getGroundTexture().equals(GroundTexture.STONE)) &&
                (!buildingsDetails.getName().equals("iron mine") || cell.getGroundTexture().equals(GroundTexture.IRON)) &&
                ((!buildingsDetails.getName().contains("farm") && !buildingsDetails.getName().equals("apple orchard")) ||
                        (cell.getGroundTexture().equals(GroundTexture.GRASS) || cell.getGroundTexture().equals(GroundTexture.DENSE_MEADOW))) &&
                (!buildingsDetails.getName().equals("pitch rig") || cell.getGroundTexture().equals(GroundTexture.PLAIN));
    }

    public GameMessage selectBuilding(int x, int y) {
        selectedBuilding = map[x - 1][y - 1].getBuilding();
        if (selectedBuilding == null) return GameMessage.NO_BUILDING_TO_SELECT;
        if (!selectedBuilding.getGovernment().equals(currentGovernment)) return GameMessage.NOT_YOUR_BUILDING;
        return GameMessage.SUCCESS;
    }

    public GameMessage checkMakeTroop(String type, int count, int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedBuilding == null) return GameMessage.NO_SELECTED_BUILDING;
        if (map[x-1][y-1].getBuilding() != null) return GameMessage.ALREADY_BUILDING;
        WorkerDetails worker = WorkerDetails.getWorkerDetailsByName(type);
        BuildingsDetails.BuildingType buildingType = selectedBuilding.getBuildingsDetails().getBuildingType();
        if (worker == null) return GameMessage.NO_SUCH_UNIT;
        if (!buildingType.equals(BuildingsDetails.BuildingType.TROOP_TRAINER)) return GameMessage.BAD_BUILDING;
        EuropeanSoldiersDetails europeanSoldiers = EuropeanSoldiersDetails.getDetailsByWorkerDetails(worker);
        if (!worker.getTrainerBuilding().equals(selectedBuilding.getBuildingsDetails())) return GameMessage.NO_SUITABLE_BUILDING;
        if (getNumberOfPeasants() < count) return GameMessage.NOT_ENOUGH_PEOPLE;
        if (worker.getGold() * count >
                currentGovernment.getResources().get(Resource.GOLD))
            return GameMessage.MONEY_PROBLEM;
        if (europeanSoldiers != null) {
            for (Resource equipment : europeanSoldiers.getEquipments())
                if (currentGovernment.getResources().get(equipment) < 1)
                    return GameMessage.NOT_ENOUGH_RESOURCE;
            for (Resource equipment : europeanSoldiers.getEquipments())
                currentGovernment.reduceResources(equipment, 1);
        }
        for (int i = 0; i < count; i++) currentGovernment.addTrainedPeople(worker, map[x - 1][y - 1]);
        return GameMessage.SUCCESS;
    }

    public int getNumberOfPeasants() {
        int count = 0;
        for (Person person : currentGovernment.getPeople())
            if (!(person instanceof Worker) && person.getWorkPlace() == null) count++;
        return count;
    }

    public int getPopularity() {
        return currentGovernment.getPopularity();
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
        selectedBuilding.repairHitPoint();
        return GameMessage.SUCCESS;
    }

    public GameMessage selectUnit(int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return GameMessage.OUT_OF_RANGE;
        if (map[x - 1][y - 1].getPeople().size() == 0) return GameMessage.NO_PEOPLE_TO_SELECT;
        selectedWorker = map[x - 1][y - 1].getPeople().get(0);
        return GameMessage.SUCCESS;
    }

    public GameMessage moveUnit(int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedWorker == null) return GameMessage.NO_SELECTED_UNIT;
        if (map[x - 1][y - 1].getBuilding() != null && !(map[x - 1][y -1].getBuilding() instanceof Tower))
            return null;
        int x1 = selectedWorker.getPosition().getxCoordinates();
        int y1 = selectedWorker.getPosition().getyCoordinates();
        if (Game.UNPASSABLE.contains(map[x - 1][y - 1].getGroundTexture()) || map[x - 1][y - 1].getExtra() != null)
            return GameMessage.UNREACHABLE_CELL;
        if ((abs(x - x1) + abs(y - y1)) > selectedWorker.getRange()) return GameMessage.UNREACHABLE_DISTANCE;
        if (selectedWorker.isOnTower()) selectedWorker.setOnTower(false);
        selectedWorker.setDestination(map[x - 1][y - 1]);
        return GameMessage.SUCCESS;
    }

    public GameMessage patrolUnit(int x1, int y1, int x2, int y2) {
        if (x1 > map.length || x1 < 1 || y1 > map.length || y1 < 1) return GameMessage.OUT_OF_RANGE;
        if (x2 > map.length || x2 < 1 || y2 > map.length || y2 < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedWorker == null) return GameMessage.NO_SELECTED_UNIT;
        selectedWorker.setPatrolMovement(x1 - 1, y1 - 1, x2 - 1, y2 - 1);
        return GameMessage.SUCCESS;
    }

    public GameMessage setStateUnit(int x, int y, String mode) {
        if (map[x - 1][y - 1].getPeople().isEmpty()) return GameMessage.NO_PEOPLE_TO_SELECT;
        if (!(mode.equals("offensive") || mode.equals("defensive") || mode.equals("standing")))
            return GameMessage.INVALID_STATE;
        map[x - 1][y - 1].getPeople().get(0).setState(mode);
        return GameMessage.SUCCESS;
    }
    private void startMove(){
        for(int i1=0;i1<governments.size();i1++){
            for(int i2=0;i2<governments.get(i1).getPeople().size();i2++){
                if(governments.get(i1).getPeople().get(i2) instanceof Worker &&
                        ((Worker) governments.get(i1).getPeople().get(i2)).getDestination()!=
                                ((Worker) governments.get(i1).getPeople().get(i2)).getPosition()){
                    Worker tempWorker = ((Worker) governments.get(i1).getPeople().get(i2));
                    callOtherFunction(tempWorker.getPosition().getxCoordinates(),tempWorker.getPosition().getyCoordinates(),
                            tempWorker.getDestination().getxCoordinates(),tempWorker.getDestination().getyCoordinates(),'n');
                    doTheMove(tempWorker,path);
                }
            }
        }
    }
    public GameMessage attack(int x, int y) {
        if (x > map.length || x < 1 || y > map.length || y < 1) return GameMessage.OUT_OF_RANGE;
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
        if (x > map.length || x < 1 || y > map.length || y < 1) return GameMessage.OUT_OF_RANGE;
        if (selectedWorker == null) return GameMessage.NO_SELECTED_UNIT;
        String name = selectedWorker.getName();
        if (!(name.equals("archer") || name.equals("crossbowman") || name.equals("archer bow")))
            return GameMessage.NO_SUITABLE;
        if (getDistance(x, y) > selectedWorker.getRange()) return GameMessage.OUT_OF_RANGE;
        Worker enemy = null;
        for (Person person : map[x - 1][y - 1].getPeople()) {
            if (person instanceof Worker && !person.getGovernment().equals(selectedWorker.getGovernment()))
                enemy = (Worker) person;
        }
        if (enemy == null) return GameMessage.NO_ENEMY_TO_FIGHT;
        selectedWorker.setEnemy(enemy);
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
            return GameMessage.BAD_WORK;
        if (!((Engineer) selectedWorker).hasOil()) return GameMessage.NO_OIL;
        if (direction.length() > 1) return GameMessage.INVALID_DIRECTION;
        if (!(Game.directions.contains(direction))) return GameMessage.INVALID_DIRECTION;
        pouringOils.put((Engineer) selectedWorker, direction);
        return GameMessage.SUCCESS;
    }

    public GameMessage checkDigTunnel(int x, int y, String direction) {
        if (!selectedWorker.getName().equals("tunneler")) return GameMessage.SELECT_TUNNELER;
        if (x > map.length || x < 1 || y > map.length || y < 1) return GameMessage.OUT_OF_RANGE;
        if (direction.length() > 1) return GameMessage.INVALID_DIRECTION;
        if (!Game.directions.contains(direction)) return GameMessage.INVALID_DIRECTION;
        if (!checkTunnel(x, y, direction)) return GameMessage.CANT_TUNNEL;

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
        if (!(selectedWorker instanceof Engineer)) return GameMessage.ENGINEER_NEEDED;
        MachineDetails machineDetail = MachineDetails.getMachineDetailsByName(name);
        if (machineDetail == null) return GameMessage.NO_SUCH_CAR_EXIST;
        if (numberOfEngineers() < machineDetail.getEngineersNeeded()) return GameMessage.NOT_ENOUGH_ENGINEER;
        for (Worker person : selectedWorker.getPosition().getPeople()) {
            if(person instanceof Engineer&& !((Engineer) person).hasMachine())
                engineers.add((Engineer) person);
            if(engineers.size()>=machineDetail.getEngineersNeeded())
                break;
        }
        if(engineers.size()<machineDetail.getEngineersNeeded())
            return GameMessage.NOT_ENOUGH_ENGINEER;
        for (Engineer engineer : engineers)
            engineer.giveHimMachine(true);
        currentGovernment.addBuilding(new Building(currentGovernment, BuildingsDetails.SIEGE_TENT, selectedWorker.getPosition(), null));
        return GameMessage.SUCCESS;
    }

    private int numberOfEngineers() {
        int engineers = 0;
        for (Person person : currentGovernment.getPeople())
            if (person instanceof Engineer) engineers++;
        return engineers;
    }

    public GameMessage disbandUnit() {
        selectedWorker.setDestination(currentGovernment.getBuildingByName("hold").getCell());
        return GameMessage.SUCCESS;
    }


    public void setGovernment(Government government) {
        this.currentGovernment = government;
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }


    private int calculateDistance(int x1, int y1, int x2, int y2) {
        return abs(x1 - x2) + abs(y1 - y2);
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

    private void find2Path(int x1, int y1, int x2, int y2, int distancesOfStart,char state) {
        int totalDistance = abs(x1 - x2) + abs(y1 - y2) + distancesOfStart;
        int endDistance = abs(x1 - x2) + abs(y1 - y2);
        if (x1 >= map.length || x1 < 0 || x2 >= map.length || x2 < 0 || y1 >= map.length || y1 < 0 || y2 >= map.length
                || y2 < 0)
            return;
        int lastCellX = 0;
        int lastCellY = 0;
        switch (map[x1][y1].getDirection()) {
            case 'n':
                lastCellY = y1 - 1;
                lastCellX = x1;
                break;
            case 's':
                lastCellY = y1 + 1;
                lastCellX = x1;
                break;
            case 'e':
                lastCellX = x1 - 1;
                lastCellY = y1;
                break;
            case 'w':
                lastCellX = x1 + 1;
                lastCellY = y1;
                break;
            default:
                lastCellX = x1;
                lastCellY = y1;
        }
        if (!map[x1][y1].checkCross(map[x1][y1].getDirection(), map[lastCellX][lastCellY],state))
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

    private void callOtherFunction(int x1, int y1, int x, int y,char state) {
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
                find2Path(cell.getxCoordinates(), cell.getyCoordinates(), x, y, cell.getDistanceOfStart(),state);
                if (myCells.size() == 0)
                    treeMap.remove(i2);
                if (treeMap.size() == 0)
                    opens.remove(i);

            }
        }
        decodePath(x, y);
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
            System.out.println(map[x2][y2].toString() + map[x2][y2].getDirection());
            path.add(map[x2][y2]);
        }
    }

    public void nextTurn() {
        buildEquipments();
        updateStorage();
        startMove();
        for (Map.Entry<Engineer, String> entry: pouringOils.entrySet()) pourOil(entry.getValue(), entry.getKey());
        machineAttack();
        updateBuildings();
        updateTroops();
        clearDeadSoldiers();
        int index = governments.indexOf(currentGovernment);
        if (currentGovernment.checkDefeat()) {
            int score = currentGovernment.calculateScore();
            FileController.modifyScore(currentGovernment.getLord().getUserName(), score);
            currentGovernment.killAllPeople();
            governments.remove(currentGovernment);
            currentGovernment = governments.get(index % governments.size());
        }
        else currentGovernment = governments.get((index + 1) % governments.size());
        currentGovernment.doActionInTurnFirst();
    }

    private void machineAttack() {
        for (Government government: governments) {
            for (Machine machine: government.getMachines()) {
                if (machine.getMachineDetails().equals(MachineDetails.SIEGE_TOWER) ||
                        machine.getMachineDetails().equals(MachineDetails.PORTABLE_SHIELD)) continue;
                findBuilding(machine).getDamaged(machine.getDamage());
            }
        }
    }

    private Building findBuilding(Machine machine) {
        Cell cell = machine.getCell();
        int distance = 10000;
        Building selected = null;
        for (Government government: governments) {
            if (government.equals(machine.getGovernment())) continue;
            for (Building building: government.getBuildings()) {
                if (building.getBuildingsDetails().equals(BuildingsDetails.HOLD)) continue;
                if (machine.getMachineDetails().equals(MachineDetails.BATTERING_RAM) &&
                        (!(building instanceof Gate) && !(building instanceof Tower))) continue;
                int d = calculateDistance(building.getCell().getxCoordinates(), building.getCell().getyCoordinates(),
                        machine.getCell().getxCoordinates(), machine.getCell().getyCoordinates());
                if (d < distance) {
                    distance = d;
                    selected = building;
                }
            }
        }
        return selected;
    }

    private void updateBuildings() {
        for (Government government: governments) {
            for (Building building: government.getBuildings()) {
                if (building.getHitPoint() <= 0 && building.getHitPoint() > -900) removeBuilding(building);
                if (building.getWorkers().size() < building.getRequiredWorkersCount()) {
                    for (Person person: currentGovernment.getPeople()) {
                        if (!(person instanceof Worker) && person.getWorkPlace() == null) {
                            building.getWorkers().add(person);
                            if (building.getWorkers().size() == building.getRequiredWorkersCount()) break;
                        }
                    }
                }
            }
        }
    }


    public void clearDeadSoldiers() {
        for (Government government : governments) {
            for (Person person : government.getPeople()) {
                if (person.getHitPoint() <= 0) {
                    person.delete();
                    if (person.getWorkPlace() != null)
                        person.getWorkPlace().getWorkers().remove(person);
                }
            }
        }
    }

    public void doTheMove(Worker person, ArrayList<Cell> way) {//put ladder & remove ladder
        //the array is from last to first
        int length = way.size();
        Cell cell = map[0][0];//fake initialize
        for (int i = 1; i <= person.getSpeed() && i < length; i++) {
            way.get(length - i).deletePerson(person);
            if (way.get(length - 1 - i).doesHaveHole() || way.get(length - 1 - i).doesHaveOil())
                person.getDamaged(40);
            if (person.getHitPoint() > 0) {
                cell = way.get(length - 1 - i);
                way.get(length - 1 - i).addPerson(person);
            }
            way.remove(length - i);
            //check if goes well..{the changes in the cell and the hitpoint of the person
        }
        if (person.getWorkerDetails() == WorkerDetails.LADDERMAN) {
            ArrayList<Cell> neighbours = this.getNeighbours(cell);
            for (int i1 = 0; i1 < neighbours.size(); i1++)
                if (neighbours.get(i1).getBuilding().getBuildingsDetails() == BuildingsDetails.WALL)
                    cell.putLadder();
        }
        if (person.getWorkerDetails() == WorkerDetails.SPEARMAN || person.getWorkerDetails() == WorkerDetails.MACEMAN) {
            if (cell.checkHasLadder())
                cell.removeLadder();
        }
    }

    private void updateStorage() {
        for (Government government : governments) {
            for (Building building : government.getBuildings()) {
                if (building instanceof ProductMaker productMaker) {
                    if (((ProductMaker) building).getConsumingProduct() != null) {
                        if (currentGovernment.getResources().containsKey(productMaker.getConsumingProduct()) &&
                            productMaker.getWorkers().size() == productMaker.getRequiredWorkersCount()) {
                            currentGovernment.reduceResources(((ProductMaker) building).getConsumingProduct(), 1);
                            for (Resource resource: ((ProductMaker) building).getProducts())
                                currentGovernment.addToResource(resource,
                                        Math.min(((ProductMaker) building).getRate(), currentGovernment.leftStorage(resource)));
                        }
                    }
                    else if (productMaker.getWorkers().size() == productMaker.getRequiredWorkersCount()){
                        for (Resource resource : ((ProductMaker) building).getProducts())
                            currentGovernment.addToResource(resource,
                                    Math.min(((ProductMaker) building).getRate(), currentGovernment.leftStorage(resource)));
                    }
                    for (Resource resource : ((ProductMaker) building).getProducts())
                        currentGovernment.addToResource(resource,
                                Math.min(((ProductMaker) building).getRate(), currentGovernment.leftStorage(resource)));
                }
                if (building instanceof WeaponProduction &&
                        currentGovernment.getResources().containsKey(((WeaponProduction) building).getConsumingProduct())) {
                    Resource create = ((WeaponProduction) building).getCurrentWeapon();
                    currentGovernment.addToResource(create, Math.min(((WeaponProduction) building).getRate(),
                            currentGovernment.leftStorage(create)));
                }
                if (building instanceof Quarry) ((Quarry) building).addToCapacity(3);
            }
            addStone();
        }
    }

    private void addStone() {
        int quarryStone = 0, oxStone = 0, addition;
        for (Building building: currentGovernment.getBuildings()) {
            if (building instanceof Quarry) quarryStone += ((Quarry) building).getCapacity();
            if (building instanceof OxTether) oxStone += ((OxTether) building).capacity;
        }
        int addStone = Math.min(quarryStone, oxStone);
        for (Building building: currentGovernment.getBuildings()) {
            if (building instanceof Quarry) {
                addition = Math.min(currentGovernment.leftStorage(Resource.STONE),
                        Math.min(((Quarry) building).getCapacity(), addStone));
                addStone -= addition;
                if (addStone == 0) break;
                ((Quarry) building).addToCapacity(addition * -1);
                currentGovernment.addToResource(Resource.STONE, addition);
            }
        }
    }

    private void updateTroops() {
        for (Government government: governments) {
            for (Person person : government.getPeople()) {
                if (!(person instanceof Worker worker)) continue;
                worker.setOnTower(worker.getPosition().equals(worker.getDestination()) &&
                        worker.getPosition().getBuilding() instanceof Tower);
                if (worker.getName().equals("slave") && worker.getPosition().equals(worker.getDestination()) &&
                        worker.getPosition().getBuilding() != null &&
                        worker.getPosition().getBuilding().getName().equals("pitch ditch"))
                    ((Trap) worker.getPosition().getBuilding()).setOnFire(true);
                if (worker instanceof Engineer && worker.getPosition().equals(worker.getDestination()) &&
                        worker.getPosition().getBuilding().getName().equals("oil smelter"))
                    ((Engineer) worker).setHasOil(true);
                if (worker instanceof Assassin && checkOtherTroop((Assassin) worker)) ((Assassin) worker).expose();
                damage(worker);
            }
            clearDeadSoldiers();
        }
    }

    private boolean checkOtherTroop(Assassin assassin) {
        Government government = assassin.getGovernment();
        Cell cell = assassin.getPosition();
        for (Worker worker: cell.getPeople())
            if (!worker.getGovernment().equals(government)) return true;
        return false;
    }

    private void damage(Worker worker) {
        Worker enemy;
        int range = worker.isOnTower() ? worker.getRange()*2 : worker.getRange();
        int hitDamage = worker.getDamage();
        if(worker.getEnemy() != null && isEnemyInRange(worker)) {
            int defenseRate = worker.getEnemy().getDefense();
            if (worker.getEnemy() instanceof Engineer && ((Engineer) worker.getEnemy()).getMachine() != null) {
                Machine machine = ((Engineer) worker.getEnemy()).getMachine();
                machine.getDamaged(Math.max(hitDamage - machine.getDefense(), 0));
            }
            else if(hitDamage>defenseRate)
                worker.getEnemy().getDamaged(hitDamage-defenseRate);
        }
        else if((enemy = findRandomEnemy(worker))!=null){
            int dis = calculateDistance(worker.getPosition().getxCoordinates(), worker.getPosition().getyCoordinates(),
                    enemy.getPosition().getxCoordinates(), enemy.getPosition().getyCoordinates());
            if (worker.getState().equals("offensive"))
                if (dis <= Math.min(30, range * 2)) worker.setDestination(enemy.getPosition());
            if ( dis > range) return;
            if (enemy instanceof Engineer && ((Engineer) enemy).getMachine() != null) {
                Machine machine = ((Engineer) enemy).getMachine();
                machine.getDamaged(hitDamage - machine.getDefense());
            }
            int defenseRate= enemy.getDefense();
            enemy.getDamaged(Math.max(hitDamage-defenseRate, 0));
            if (worker.getState().equals("defensive")) worker.setDestination(enemy.getPosition());
        }
    }

    private Worker findRandomEnemy(Worker worker) {
        int distance = 10000;
        Worker selected = null;
        for (Government government: governments) {
            if (government.equals(worker.getGovernment())) continue;
            for (Person person: government.getPeople()) {
                if (!(person instanceof Worker)) continue;
                if (person instanceof Assassin && ((Assassin) person).isHidden()) continue;
                int d = calculateDistance(worker.getPosition().getxCoordinates(), worker.getPosition().getyCoordinates(),
                        ((Worker) person).getPosition().getxCoordinates(), ((Worker) person).getPosition().getyCoordinates());
                if (((Worker) person).isOnTower()) {
                    Tower tower = ((Tower) ((Worker) person).getPosition().getBuilding());
                    d += tower.getDefenseRange();
                }
                if (d < distance) {
                    distance = d;
                    selected = (Worker) person;
                }
            }
        }
        return selected;
    }

    private boolean isEnemyInRange(Person person) {
        int x1=((Worker)person).getEnemy().getPosition().getxCoordinates();
        int y1=((Worker)person).getEnemy().getPosition().getyCoordinates();
        int x2=((Worker) person).getPosition().getxCoordinates();
        int y2=((Worker) person).getPosition().getyCoordinates();
        int distance=calculateDistance(x1,y1,x2,y2);
        return distance <= ((Worker) person).getRange();
    }
    private void buildEquipments() {
        for (Building building: currentGovernment.getBuildings()) {
            if (building instanceof SiegeTent) {
                currentGovernment.addMachine(new Machine(((SiegeTent) building).getMachineToMake(), currentGovernment,
                        building.getCell(), ((SiegeTent) building).getEngineers()));
                for (Engineer engineer: ((SiegeTent) building).getEngineers())
                    engineer.setMachine(currentGovernment.getMachines().get(currentGovernment.getMachines().size()-1));
                removeBuilding(building);
            }
        }
    }

    public void pourOil(String direction, Engineer engineer) {
        int x= engineer.getPosition().getxCoordinates();
        int y= engineer.getPosition().getyCoordinates();
        switch (direction) {
            case "n" -> {
                for (int i = y - 1; i >= y - 3; i--) {
                    for (Worker person : map[x][i].getPeople()) person.getDamaged(5);
                    if (i == 0) break;
                }
            }
            case "s" -> {
                for (int i = y + 1; i <= y + 3; i++) {
                    for (Worker person : map[x][i].getPeople()) person.getDamaged(5);
                    if (i == map.length - 1) break;
                }
            }
            case "e" -> {
                for (int i = x + 1; i <= x + 3; i++) {
                    for (Worker person : map[x][i].getPeople()) person.getDamaged(5);
                    if (i == map.length - 1) break;
                }
            }
            case "w" -> {
                for (int i = x - 1; i >= x - 3; i--) {
                    for (Worker person : map[x][i].getPeople()) person.getDamaged(5);
                    if (i == 0) break;
                }
            }
        }
        engineer.setHasOil(false);
        engineer.setDestination(currentGovernment.getBuildingByName("oil smelter").getCell());
    }

    private void removeBuilding(Building building) {
        building.getGovernment().getBuildings().remove(building);
        building.getCell().setBuilding(null);
        if (building.getWorkers() != null) {
            for (Person person: building.getWorkers())
                person.setWorkPlace(null);
        }
    }
    public ArrayList<Cell> getNeighbours(Cell cell) {
        if (cell == null)
            return null;
        ArrayList<Cell> results = new ArrayList<>();
        int x = cell.getxCoordinates();
        int y = cell.getyCoordinates();
        if ((x + 1) < map.length && (x + 1) >= 0)
            results.add(map[x + 1][y]);
        if ((x - 1) < map.length && (x - 1) >= 0)
            results.add(map[x - 1][y]);
        if ((y - 1) < map.length && (y - 1) >= 0)
            results.add(map[x][y - 1]);
        if ((y + 1) < map.length && (y + 1) >= 0)
            results.add(map[x][y + 1]);
        return results;
    }

    public boolean checkEndGame() {
        if (governments.size() == 0)
            return false;
        return true;
    }

    public GameMessage openOrCloseGate(String state) {
        if (!(selectedBuilding instanceof Gate)) return GameMessage.NO_SELECTED_BUILDING;
        switch (state){
            case "open":{
                ((Gate) selectedBuilding).setGate(true);
                return GameMessage.SUCCESS;
            }
            case "close":{
                ((Gate) selectedBuilding).setGate(false);
                return GameMessage.SUCCESS;
            }
            default:{
                return GameMessage.INVALID_STATE;
            }
        }
    }


    public String getState(){
        if(selectedBuilding == null ||(!(selectedBuilding instanceof  WeaponProduction)))
            return "mistake in selecting building!";
        return "current product is :"+((WeaponProduction)selectedBuilding).getCurrentProduct().getName();
    }
    public GameMessage checkMoveEquipments(int x1,int y1,int x2,int y2){
        if (x1 > map.length || x1 < 1 || y1 > map.length || y1 < 1) return GameMessage.OUT_OF_RANGE;
        if (x2 > map.length || x2 < 1 || y2 > map.length || y2 < 1) return GameMessage.OUT_OF_RANGE;
        Machine myMachine = null;
        for(int i1 = 0;i1<map[x1][y1].getMachine().size();i1++){
            if(map[x1-1][y1-1].getMachine().get(i1).getSpeed()==0)
                myMachine = map[x1][y1].getMachine().get(i1);
        }
        if(myMachine == null)
            return GameMessage.UNABLE_TO_MOVE;
        Cell cell1;
        calculateDistance(x1 - 1,y1 - 1,x2 - 1,y2 - 1);
        if (x1 > map.length || x1 < 1 || y1 > map.length || y1 < 1) return GameMessage.OUT_OF_RANGE;
        if (x2 > map.length || x2 < 1 || y2 > map.length || y2 < 1) return GameMessage.OUT_OF_RANGE;
        Machine machine = null;
        for(int i1=0;i1<map[x1][y1].getMachine().size();i1++){
            if(map[x1][y1].getMachine().get(i1).getSpeed()!=0) machine = map[x1][y1].getMachine().get(i1);
        }
        if(machine == null) return GameMessage.UNABLE_TO_MOVE;
        Cell cell2;
        calculateDistance(x1,y1,x2,y2);
        int length = path.size();
        for (int i = 1; i <= machine.getSpeed() && i < length; i++){
            path.get(length - i).deleteMachine(machine);
            if (path.get(length - 1 - i).doesHaveHole() || path.get(length - 1 - i).doesHaveOil())
                machine.getDamaged(100);
            if (machine.getHitPoint()>0) {
                cell1 = path.get(length - 1 - i);
                path.get(length - 1 - i).addMachine(machine);
                machine.setCell(path.get(length - 1 + i));
            }
            if(machine.getHitPoint()<0){
                for(int i1=0;i1<machine.getEngineers().size();i1++){
                    machine.getEngineers().get(i1).delete();
                    machine.getEngineers().remove(i1);
                }
                currentGovernment.removeMachine(machine);
            }
            path.remove(length - i);
        }
        cell2 = machine.getCell();
        for(int i2=0;i2<machine.getEngineers().size();i2++){
            machine.getEngineers().get(i2).transport(cell2);
        }
        return GameMessage.SUCCESS;
    }
    public GameMessage switchProduct(){
        if(selectedBuilding == null)
            return GameMessage.NO_SELECTED_BUILDING;
        if(!(selectedBuilding instanceof  WeaponProduction))
            return GameMessage.NO_SUITABLE_BUILDING;
        WeaponProduction productMaker = (WeaponProduction) selectedBuilding;
        if(selectedBuilding.getBuildingsDetails()!=BuildingsDetails.POLETURNER &&
        selectedBuilding.getBuildingsDetails() != BuildingsDetails.FLETCHER &&
        selectedBuilding.getBuildingsDetails() != BuildingsDetails.BLACKSMITH)
            return GameMessage.NO_SUITABLE_BUILDING;
        productMaker.switch1();
        return GameMessage.SUCCESS;
    }

    public String getResourceName(String name){
        if(name == null)
            return currentGovernment.showStorage(null);
        BuildingsDetails buildingsDetails = BuildingsDetails.getBuildingDetailsByName(name);
        ContainerDetails containerDetails = ContainerDetails.getContainerByBuilding(buildingsDetails);
        if(containerDetails==null)
            return "Storage name is invalid";
        return currentGovernment.showStorage(buildingsDetails);
    }

    public String showMap(int x, int y) {
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
                    if (person instanceof Worker) {
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

    public String moveMap(String changes) {
        int xChange = 0, yChange = 0, number;
        String direction;
        Matcher matcher = Pattern.compile("\\S+[ \\d+]*").matcher(changes);
        while (matcher.find()) {
            String[] split = matcher.group().split(" ");
            direction = split[0].trim();
            if (split.length < 2) number = 1;
            else number = Integer.parseInt(split[1]);
            switch (direction) {
                case "up" -> yChange -= number;
                case "down" -> yChange += number;
                case "left" -> xChange -= number;
                case "right" -> xChange += number;
                default -> {
                    return "wrong direction";
                }
            }
        }
        if (xCoordinates + xChange > map.length || xCoordinates + xChange <= 0 || yCoordinates + yChange > map.length || yCoordinates + yChange <= 0)
            return "out of bounds";
        xCoordinates += xChange;
        yCoordinates += yChange;
        return showMap(xCoordinates, yCoordinates);
    }
    public boolean checkWin(){
        return governments.size()<=1;
    }
}
