package model.human.Enums;

import model.Resource;

import java.util.Arrays;
import java.util.List;

public enum WorkerDetails {
    WORKER_DETAILS("0", 0, 0, 0, 0, 0, Arrays.asList(Resource.RESOURCE), Arrays.asList(Resource.RESOURCE));
    String name;
    int maxHitPoint, damage, defense, speed, range;
    List<Resource> weapon;
    List<Resource> armor;
    WorkerDetails(String name,int maxHitPoint, int damage, int defense, int speed, int range, List<Resource> weapon, List<Resource> armor){}

    public String getName() {
        return name;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public List<Resource> getWeapon() {
        return weapon;
    }

    public List<Resource> getArmor() {
        return armor;
    }
}
