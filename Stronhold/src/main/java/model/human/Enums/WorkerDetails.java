package model.human.Enums;

import model.building.Enums.BuildingsDetails;

public enum WorkerDetails {
    ARCHER("archer", BuildingsDetails.BARRACKS, 20, 2, 2, 4, 4, 12),
    CROSSBOWMAN("crossbowman", BuildingsDetails.BARRACKS, 30, 2, 3, 2, 3, 20),
    SPEARMAN("spearman", BuildingsDetails.BARRACKS, 25, 3, 1, 3, 1, 8),
    PIKEMAN("pikeman", BuildingsDetails.BARRACKS, 35, 3, 4, 2, 1, 20),
    MACEMAN("maceman", BuildingsDetails.BARRACKS, 30, 4, 3, 3, 0, 20),
    SWORDSMAN("swordsman", BuildingsDetails.BARRACKS, 40, 4, 1, 1, 0, 40),
    KNIGHT("knight", BuildingsDetails.BARRACKS, 30, 5, 4, 5, 1, 40),
    TUNNELER("tunneler", BuildingsDetails.BARRACKS, 20, 2, 1, 4, 0, 30),
    LADDERMAN("ladderman", BuildingsDetails.ENGINEERS_GUILD, 15, 0, 1, 4, 0, 4),
    ENGINEER("engineer", BuildingsDetails.ENGINEERS_GUILD, 15, 0, 1, 3, 0, 30),
    BLACK_MONK("black monk", BuildingsDetails.CATHEDRAL, 30, 3, 3, 2, 0, 10),
    ARABIAN_BOW("archer bow", BuildingsDetails.MERCENARY_POST, 20, 2, 2, 4, 4, 75),
    SLAVE("slave", BuildingsDetails.MERCENARY_POST, 15, 1, 1, 4, 0, 5),
    SLINGER("slinger", BuildingsDetails.MERCENARY_POST, 20, 4, 1, 4, 2, 12),
    ASSASSIN("assassin", BuildingsDetails.MERCENARY_POST, 30, 3, 3, 3, 0, 60),
    HORSE_ARCHER("horse archer", BuildingsDetails.MERCENARY_POST, 30, 2, 3, 5, 5, 80),
    ARABIAN_SWORDSMAN("arabian swordsman", BuildingsDetails.MERCENARY_POST, 35, 4, 4, 5, 0, 80),
    FIRE_THROWER("fire thrower", BuildingsDetails.MERCENARY_POST, 25, 4, 2, 5, 3, 100);
    private String name;
    private BuildingsDetails trainerBuilding;
    private int maxHitPoint, damage, defense, speed, range, gold;

    WorkerDetails(String name,BuildingsDetails BuildingsDetails, int maxHitPoint, int damage, int defense, int speed, int range, int gold){}

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

    public static WorkerDetails getWorkerDetailsByName(String name) {
        for (WorkerDetails workerDetails: WorkerDetails.values())
            if (workerDetails.name.equals(name)) return workerDetails;
        return null;
    }

    public BuildingsDetails getTrainerBuilding() {
        return trainerBuilding;
    }

    public int getGold() {
        return gold;
    }
}
