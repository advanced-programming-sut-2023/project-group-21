package model;

import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.building.Residency;
import model.building.Storage;
import model.generalenums.GroundTexture;
import model.generalenums.Resource;
import model.human.Enums.WorkerDetails;
import model.human.Person;
import model.human.Worker;
import model.machine.Machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class Government {
    private final User lord;
    private int foodRate, taxRate, fearRate, popularityRate = 0, religionRate = 0, foodVariety = 0;
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Machine> machines = new ArrayList<>();
    private HashMap<Resource, Integer> resources = new HashMap<>();
    private Building hold;
    private ArrayList<Trade> trades;

    public Government(User lord,Cell cell) {
        Worker myLord = new Worker(WorkerDetails.LORD,this,cell,cell);
        people.add(myLord);
        cell.setExtras(null);
        cell.setGroundTexture(GroundTexture.SOIL);
        resources.put(Resource.GOLD,2000);
        resources.put(Resource.APPLE,30);
        resources.put(Resource.BREAD,60);
        resources.put(Resource.WOOD,70);
        resources.put(Resource.STONE, 70);
        hold = new Building(this,BuildingsDetails.HOLD,cell, null);
        this.lord = lord;
        for(int i1=0;i1<10;i1++){
            people.add(new Person(this));
        }
    }

    public User getLord() {
        return lord;
    }

    public int getPopularity() {
        return people.size();
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void addMachine(Machine machine) {
        machines.add(machine);
    }

    public HashMap<Resource, Integer> getResources() {
        return resources;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
        building.getCell().setBuilding(building);
    }

    public void addTrainedPeople(WorkerDetails workerDetails, Cell cell) {
        for (int i = 0; i < people.size(); i++) {
            if (!(people.get(i) instanceof Worker) && people.get(i).getWorkPlace() == null) {
                people.remove(i);
                break;
            }
        }
        people.add(new Worker(workerDetails, this, cell, cell));
    }

    public void setFoodRate(int foodRate) {
        popularityRate += (foodRate - this.foodRate) * 4;
        this.foodRate = foodRate;
    }

    public void setTaxRate(int taxRate) {
        popularityRate += Game.TaxDetails.getPopularity(taxRate) - Game.TaxDetails.getPopularity(this.taxRate);
        this.taxRate = taxRate;
    }

    public void setFearRate(int fearRate) {
        popularityRate += fearRate - this.fearRate;
        this.fearRate = fearRate;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setReligionRate(boolean check) {
        religionRate += check ? 2 : -2;
    }

    public Building getBuildingByName(String name) {
        for (Building building : buildings)
            if (building.getName().equals(name)) return building;
        return null;
    }

    public Building getHold() {
        return hold;
    }

    public void reduceResources(Resource resource, int count) {
        resources.replace(resource, resources.get(resource) - count);
        if (resources.get(resource) == 0) resources.remove(resource);
    }

    public void addToResource(Resource resource, int amount) {
        if (resources.containsKey(resource))
            resources.replace(resource, resources.get(resource) + amount);
        else
            resources.put(resource, amount);
    }

    private int getLeftFood() {
        int number = 0;
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            Resource resource = entry.getKey();
            Integer amount = entry.getValue();
            if (resource.getResourceKeeper() == BuildingsDetails.GRANARY)
                number += amount;
        }
        return number;
    }



    public void addTrade(Trade createdTrade) {
        trades.add(createdTrade);
    }

    public int getResourceAmount(Resource resource) {
        if (resources.containsKey(resource))
            return resources.get(resource);
        return -1;
    }

    public void removeResource(Resource resource, int amount) {
        if (resources.containsKey(resource)) {
            int newAmount = resources.get(resource) - amount;
            resources.put(resource, newAmount);
            if (newAmount == 0)
                resources.remove(resource);
        }
    }

    public int leftStorage(Resource resource) {
        BuildingsDetails buildingsDetails = resource.getResourceKeeper();
        int capacity = 0;
        for (Building building: buildings)
            if (building.getBuildingsDetails().equals(buildingsDetails))
                capacity += ((Storage) building).getCapacity();
        int full = 0;
        for (Map.Entry<Resource, Integer> entry: resources.entrySet()) {
            if (entry.getKey().getResourceKeeper()!=null&&
                    entry.getKey().getResourceKeeper().equals(buildingsDetails)) full += entry.getValue();
        }
        return capacity - full;
    }

    public void sellSuccessfully(Trade trade) {
        int newGoldAmount = resources.get(Resource.GOLD) - trade.getAmount() * trade.getCost();
        resources.put(Resource.GOLD, newGoldAmount);
    }

    public void buySuccessfully(Trade trade) {
        if (resources.containsKey(trade.getResource())) {
            resources.put(trade.getResource(), resources.get(trade.getResource()) + trade.getAmount());
        }
        int newGoldAmount = resources.get(Resource.GOLD) - trade.getAmount() * trade.getCost();
        resources.put(Resource.GOLD, newGoldAmount);
    }

    public int getGold() {
        return resources.get(Resource.GOLD);
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }
    public void doActionInTurnFirst () {
        int change = 0;
        double tax = Game.TaxDetails.getTax(taxRate);
        if (resources.get(Resource.GOLD) + people.size() * tax < 0) {
            taxRate = 0;
            resources.remove(Resource.GOLD);
        }
        else resources.replace(Resource.GOLD, resources.get(Resource.GOLD) + (int)(resources.get(Resource.GOLD) + people.size() * tax));
        if (resources.get(Resource.GOLD) == 0) resources.remove(Resource.GOLD);

        if (getLeftFood() < Game.FoodRate.getFoodRate(foodRate) * people.size()) {
            removeAllFood();
            setFoodRate(-2);
        }
        int foodNumber = (int) Game.FoodRate.getFoodRate(foodRate);
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            Resource resource = entry.getKey();
            Integer amount = entry.getValue();
            if (resource.getResourceKeeper() == BuildingsDetails.GRANARY) {
                change = min(amount, foodNumber);
                amount -= change;
                foodNumber -= change;
                resources.replace(resource, amount);
                if (amount == 0)
                    resources.remove(resource);
            }
        }
        int variety = getFoodVariety();
        popularityRate += variety - foodVariety;
        foodVariety = variety;
        addPopularity();
    }

    private void addPopularity() {
        int addition = Math.min(calculateLeftPopulationCapacity(), popularityRate);
        for (int i = 1; i <= addition; i++) people.add(new Person(this));
    }

    private int getFoodVariety() {
        int i = 0;
        for (Map.Entry<Resource, Integer> entry: resources.entrySet())
            if (entry.getKey().getResourceKeeper().equals(BuildingsDetails.GRANARY)) i++;
        return i;
    }

    public void deletePerson(Person person){
        people.remove(person);
    }

    private void removeAllFood() {
        ArrayList<Resource> toRemove = new ArrayList<>();
        for (Map.Entry<Resource, Integer> entry: resources.entrySet())
            if (entry.getKey().getResourceKeeper().equals(BuildingsDetails.GRANARY)) toRemove.add(entry.getKey());
        for (Resource resource: toRemove) resources.remove(resource);
    }
    public int calculateScore(){
        int result = 0;
        result += resources.get(Resource.GOLD);
        for (Person person : people)
            if (person instanceof Worker)
                ((Worker) person).getWorkerDetails().getGold();

        return result;
    }

    public boolean checkDefeat(){
        if(people.size()==0 || ((Worker)(people.get(0))).getHitPoint()<=0)
            return true;
        return hold.getHitPoint() <= 0;
    }

    public int calculateLeftPopulationCapacity(){
        int result = 0;
        for(int i1=0;i1<buildings.size();i1++){
            if(buildings.get(i1) instanceof Residency)
                result += ((Residency)buildings.get(i1)).getMaxPopularity();
        }
        result = result - people.size();
        return result;
    }

    public void killAllPeople(){
        while (people.size()>1){
            people.get(0).delete();
            people.remove(0);
        }
    }

    public void removeMachine(Machine machine){
        machines.remove(machine);
    }

    public String showStorage(BuildingsDetails buildingsDetails) {
        StringBuilder string = new StringBuilder();
        if (buildingsDetails == null) {
            for (Map.Entry<Resource, Integer> entry: resources.entrySet())
                string.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
        } else {
            for (Map.Entry<Resource, Integer> entry: resources.entrySet()) {
                if (entry.getKey().getResourceKeeper().equals(buildingsDetails))
                    string.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        return string.toString();
    }

}
