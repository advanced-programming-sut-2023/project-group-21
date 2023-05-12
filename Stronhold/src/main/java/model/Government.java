package model;

import model.building.Building;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.StorageDetails;
import model.building.Gate;
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
    private int foodRate, taxRate, fearRate, popularityRate = 0, religionRate = 0;
    private ArrayList<Building> buildings;
    private ArrayList<Person> people;
    private ArrayList<Machine> machines;
    private HashMap<Resource, Integer> resources;
    private Building hold;
    private ArrayList<Trade> trades;

    public Government(User lord) {
        this.lord = lord;
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
            if (resource.getResourceKeeper() == StorageDetails.ARMOURY)
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
        StorageDetails storageDetails = resource.getResourceKeeper();
        int capacity = 0;
        for (Building building: buildings)
            if (building instanceof Storage && ((Storage) building).getStorageDetails().equals(storageDetails))
                capacity += storageDetails.getCapacity();
        int full = 0;
        for (Map.Entry<Resource, Integer> entry: resources.entrySet())
            if (entry.getKey().getResourceKeeper().equals(storageDetails)) full += entry.getValue();
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
        //get tax and feed
        int change = 0;
        if (people.size() * taxRate < 0 && ((resources.get(Resource.GOLD) + people.size() * taxRate) < 0))
            taxRate = 0;
        resources.replace(Resource.GOLD, resources.get(Resource.GOLD) + (resources.get(Resource.GOLD) + people.size() * taxRate));
        if (getLeftFood() < Game.FoodRate.getFoodRate(foodRate) * people.size())
            foodRate = -2;
        int foodNumber = (int) Game.FoodRate.getFoodRate(foodRate);
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            Resource resource = entry.getKey();
            Integer amount = entry.getValue();
            if (resource.getResourceKeeper() == StorageDetails.ARMOURY) {
                change = min(amount, foodNumber);
                amount -= change;
                foodNumber -= change;
                resources.replace(resource, amount);
                if (amount == 0)
                    resources.remove(resource);
            }
        }
        int tax = (int) (Game.TaxDetails.getTax(taxRate) * people.size());
        resources.put(Resource.GOLD, resources.get(Resource.GOLD) + tax);
    }

    public void deletePerson(Person person){
        people.remove(person);
    }

}
