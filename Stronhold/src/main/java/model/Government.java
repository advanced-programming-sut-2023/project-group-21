package model;

import model.building.Building;
import model.building.Gate;
import model.generalenums.GroundTexture;
import model.generalenums.Resource;
import model.human.Person;
import model.machine.Machine;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User lord;
    private int popularity, foodRate, taxRate, fearRate, popularityRate = 0, religionRate = 0;
    private ArrayList<Building> buildings;
    private ArrayList<Person> people;
    private ArrayList<Machine> machines;
    private HashMap<Resource, Integer> resources;

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

    public void addBuilding(Building building){
        buildings.add(building);
        building.getCell().setBuilding(building);
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
        for (Building building: buildings)
            if (building.getName().equals(name)) return building;
        return null;
    }
}
