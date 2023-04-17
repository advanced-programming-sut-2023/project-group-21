package model;

import model.building.Building;
import model.human.Person;
import model.machine.Machine;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User lord;
    private int popularity, foodRate, taxRate, fearRate;
    private ArrayList<Building> buildings;
    private ArrayList<Person> people;
    private ArrayList<Machine> machines;
    private HashMap<Resource, Integer> resources;

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

    public void addResource(Resource resource, int amount){}
}
