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
    private int popularity, foodRate, taxRate, fearRate, popularityRate = 0, religionRate = 0;
    private ArrayList<Building> buildings;
    private ArrayList<Person> people;
    private ArrayList<Machine> machines;
    private HashMap<Resource, Integer> resources;
    private Building castle;

    public Government(User lord) {
        this.lord = lord;
    }

    public User getLord() {
        return lord;
    }

    public int getPopularity() {
        return popularity;
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

    public HashMap<Resource, Integer> getResources() {
        return resources;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
        building.getCell().setBuilding(building);
    }

    public void addTrainedPeople(WorkerDetails workerDetails) {
        for (int i = 0; i < people.size(); i++) {
            if (!(people.get(i) instanceof Worker)) {
                people.remove(i);
                break;
            }
        }
        people.add(new Worker(workerDetails, this, castle.getCell(), castle.getCell()));
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

    public Building getCastle() {
        return castle;
    }

    public void reduceResources(Resource resource, int count) {
        resources.replace(resource, resources.get(resource) - count);
    }

    public int calculateLeftStorageCapacity(Resource resource) {
        int sum = 0, sum2 = 0;
        if (resource == null || resource == Resource.GOLD)
            return -1;
        StorageDetails details = resource.getResourceKeeper();
        if (details == null)
            return -2;
        for (int i1 = 0; i1 < buildings.size(); i1++) {
            if (buildings.get(i1) instanceof Storage && ((Storage) buildings.get(i1)).getStorageDetails() == details) {
                sum += ((Storage) buildings.get(i1)).getCapacity() - ((Storage) buildings.get(i1)).getOccupation(resource.getResourceKeeper());
            }
        }
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            Resource resource1 = entry.getKey();
            Integer amount = entry.getValue();
            if (resource1.getResourceKeeper() == details)
                sum2 += amount;
        }
        return sum - sum2;//it has repaired !
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

    public void doActionInTurnFirst() {
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
            if (resource.getResourceKeeper() == StorageDetails.ARMOURY){
                change = min(amount,foodNumber);
                amount -= change;
                foodNumber -= change;
                resources.replace(resource,amount);
                if(amount ==0)
                    resources.remove(resource);
            }
        }
        int tax = (int) (Game.TaxDetails.getTax(taxRate)*people.size());
        resources.put(Resource.GOLD,resources.get(Resource.GOLD)+tax);
    }

}
