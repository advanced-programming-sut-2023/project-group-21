package model.machine;

public enum MachineDetails {
    MACHINE_DETAILS(0,0,0,0,0);

    int damage, defense, engineersNeeded, fireRange, speed;
    MachineDetails(int damage, int defense, int engineersNeeded, int fireRange, int speed){}

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
}
