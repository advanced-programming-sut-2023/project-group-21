package model.machine;

public enum MachineDetails {
    PORTABLE_SHIELD("portable shield", 0, 15, 0, 0, 15),
    BATTERING_RAM("battering ram", 25, 0, 4, 1, 15),
    SIEGE_TOWER("siege tower", 0, 0, 4, 0, 10),
    CATAPULT("catapult", 20, 0, 2, 20, 10),
    TREBUCHET("trebuchet", 35, 0, 3, 15, 0),
    FIRE_BALLISTA("fire ballista", 30, 0, 2, 20, 10);

    private String name;
    private int damage, defense, engineersNeeded, fireRange, speed;
    MachineDetails(String name, int damage, int defense, int engineersNeeded, int fireRange, int speed){
        this.name = name;
        this.damage = damage;
        this.defense = defense;
        this.engineersNeeded = engineersNeeded;
        this.fireRange = fireRange;
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getEngineersNeeded() {
        return engineersNeeded;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public static MachineDetails getMachineDetailsByName(String name) {
        for (MachineDetails machineDetail: MachineDetails.values())
            if (machineDetail.getName().equals(name)) return machineDetail;
        return null;
    }
}
