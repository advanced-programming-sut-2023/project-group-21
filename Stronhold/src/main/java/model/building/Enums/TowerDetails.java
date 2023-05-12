package model.building.Enums;

public enum TowerDetails {
    LOOKOUT_TOWER(BuildingsDetails.LOOKOUT_TOWER, 8, 1, false),
    PERIMETER_TOWER(BuildingsDetails.PERIMETER_TOWER, 6, 3, false),
    TURRET(BuildingsDetails.TURRET, 6, 3, false),
    SQUARE_TOWER(BuildingsDetails.SQUARE_TOWER, 7, 3, true),
    ROUND_TOWER(BuildingsDetails.ROUND_TOWER,  7, 3, true);
    private final BuildingsDetails buildingsDetails;
    private final int fireRange, defenseRange;
    private final boolean canStandMachines;

    TowerDetails(BuildingsDetails buildingsDetails, int fireRange, int defenseRange, boolean canStandMachines) {
        this.buildingsDetails = buildingsDetails;
        this.fireRange = fireRange;
        this.defenseRange = defenseRange;
        this.canStandMachines = canStandMachines;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefenseRange() {
        return defenseRange;
    }

    public static TowerDetails getTowerDetailsByBuildingDetails(BuildingsDetails buildingsDetails) {
        for (TowerDetails towerDetails: TowerDetails.values())
            if (towerDetails.buildingsDetails.equals(buildingsDetails)) return towerDetails;
        return null;
    }

    public boolean CanStandMachines() {
        return canStandMachines;
    }
}
