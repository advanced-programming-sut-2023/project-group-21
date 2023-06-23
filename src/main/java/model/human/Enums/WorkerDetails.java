package model.human.Enums;

import model.building.Enums.BuildingsDetails;

public enum WorkerDetails {
    ARCHER("archer", BuildingsDetails.BARRACKS, 20, 2, 2, 20, 20, 12,
            "/Game Images/Troops/Archer.png"),
    CROSSBOWMAN("crossbowman", BuildingsDetails.BARRACKS, 30, 2, 3, 10, 15, 20,
            "/Game Images/Troops/CrossbowMan.png"),
    SPEARMAN("spearman", BuildingsDetails.BARRACKS, 25, 3, 1, 15, 1, 8,
            "/Game Images/Troops/Spearman.png"),
    PIKEMAN("pikeman", BuildingsDetails.BARRACKS, 35, 3, 4, 10, 1, 20,
            "/Game Images/Troops/Pikeman.png"),
    MACEMAN("maceman", BuildingsDetails.BARRACKS, 30, 4, 3, 15, 0, 20,
            "/Game Images/Troops/Maceman.png"),
    SWORDSMAN("swordsman", BuildingsDetails.BARRACKS, 40, 4, 1, 5, 0, 40,
            "/Game Images/Troops/Swordsman.png"),
    KNIGHT("knight", BuildingsDetails.BARRACKS, 30, 5, 4, 25, 1, 40,
            "/Game Images/Troops/Knight.png"),
    TUNNELER("tunneler", BuildingsDetails.TANNERS_GUILD, 20, 2, 1, 20, 0, 30,
            null),
    LADDERMAN("ladderman", BuildingsDetails.ENGINEERS_GUILD, 15, 0, 1, 20, 0, 4,
            "/Game Images/Troops/Ladderman.png"),
    ENGINEER("engineer", BuildingsDetails.ENGINEERS_GUILD, 15, 0, 1, 15, 0, 30,
            "/Game Images/Troops/Engineer.png"),
    BLACK_MONK("black monk", BuildingsDetails.CATHEDRAL, 30, 3, 3, 10, 0, 10,
            "/Game Images/Troops/BlackMonk.png"),
    ARABIAN_BOW("archer bow", BuildingsDetails.MERCENARY_POST, 20, 2, 2, 20, 20, 75,
            "/Game Images/Troops/ArabianBow.png"),
    SLAVE("slave", BuildingsDetails.MERCENARY_POST, 15, 1, 1, 20, 0, 5,
            "/Game Images/Troops/Slave.png"),
    SLINGER("slinger", BuildingsDetails.MERCENARY_POST, 20, 4, 1, 20, 7, 12,
            "/Game Images/Troops/Slinger.png"),
    ASSASSIN("assassin", BuildingsDetails.MERCENARY_POST, 30, 3, 3, 15, 0, 60,
            "/Game Images/Troops/Assassin.png"),
    HORSE_ARCHER("horse archer", BuildingsDetails.MERCENARY_POST, 30, 2, 3, 25, 5, 80,
            "/Game Images/Troops/HorseArcher.png"),
    ARABIAN_SWORDSMAN("arabian swordsman", BuildingsDetails.MERCENARY_POST, 35, 4, 4, 25, 0, 80,
            "/Game Images/Troops/ArabianSwordsman.png"),
    FIRE_THROWER("fire thrower", BuildingsDetails.MERCENARY_POST, 25, 4, 2, 20, 3, 100,
            "/Game Images/Troops/FireThrower.png"),
    LORD("lord",BuildingsDetails.HOLD,200,0,0,0,0,0,
            null);
    private final String name, imagePath;
    private final BuildingsDetails trainerBuilding;
    private final int maxHitPoint;
    private final int damage;
    private final int defense;
    private final int speed;
    private final int range;
    private final int gold;

    WorkerDetails(String name,BuildingsDetails BuildingsDetails, int maxHitPoint, int damage, int defense, int speed, int range, int gold, String imagePath){
        this.name = name;
        trainerBuilding = BuildingsDetails;
        this.maxHitPoint = maxHitPoint;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;
        this.range = range;
        this.gold = gold;
        this.imagePath = imagePath;
    }

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

    public String getImagePath() {
        return imagePath;
    }
}
