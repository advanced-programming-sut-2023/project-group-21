package model.building.Enums;

import model.generalenums.Resource;

import java.util.Map;

public enum BuildingsDetails {
    HOLD("hold", null, 100, null, 0),
    SMALL_STONE_GATEHOUSE("small stone gatehouse", BuildingType.GATE, 35, null, 0),
    BIG_STONE_GATEHOUSE("big stone gatehouse", BuildingType.GATE, 45, Map.of(Resource.STONE, 20), 0),
    DRAWBRIDGE("drawbridge", BuildingType.GATE, 35, Map.of(Resource.WOOD, 10), 0),
    LOOKOUT_TOWER("lookout tower", BuildingType.TOWER, 45, Map.of(Resource.STONE, 10),0),
    PERIMETER_TOWER("perimeter tower", BuildingType.TOWER, 40, Map.of(Resource.STONE, 10),0),
    TURRET("turret", BuildingType.TOWER, 50, Map.of(Resource.STONE, 15),0),
    SQUARE_TOWER("square tower", BuildingType.TOWER, 40, Map.of(Resource.STONE, 35),0),
    ROUND_TOWER("round tower", BuildingType.TOWER, 45, Map.of(Resource.STONE, 40),0),
    ARMOURY("armoury", BuildingType.STORAGE, 20, Map.of(Resource.WOOD, 5),0),
    BARRACKS("barracks", BuildingType.TROOP_TRAINER, 25, Map.of(Resource.STONE, 15),0),
    MERCENARY_POST("mercenary post", BuildingType.TROOP_TRAINER, 25, Map.of(Resource.WOOD, 10),0),
    ENGINEERS_GUILD("engineers guild", BuildingType.TROOP_TRAINER, 15, Map.of(Resource.WOOD, 10, Resource.GOLD, 100),0),
    TANNERS_GUILD("tanners guild", BuildingType.TROOP_TRAINER, 15, Map.of(Resource.WOOD, 5), 0),
    KILLING_PIT("killing pit", BuildingType.TRAP, -1000, Map.of(Resource.WOOD, 6),0),
    INN("inn", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 20, Resource.GOLD, 100), 1),
    MILL("mill", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 20), 3),
    IRON_MINE("iron mine", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 20), 2),
    MARKETPLACE("marketplace", null, 15, Map.of(Resource.WOOD, 5), 1),
    OX_TETHER("ox tether", BuildingType.OX_TETHER, 10, Map.of(Resource.WOOD, 5), 1),
    PITCH_RIG("pitch rig", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 20), 1),
    QUARRY("quarry", BuildingType.QUARRY, 20, Map.of(Resource.WOOD, 20), 3),
    STOCKPILE("stockpile", BuildingType.STORAGE, 10, null,0),
    WOOD_CUTTER("wood cutter", BuildingType.PRODUCT_MAKER, 8, Map.of(Resource.WOOD, 3), 1),
    HOVEL("hovel", BuildingType.RESIDENCY, 10, Map.of(Resource.WOOD, 6),0),
    CHURCH("church", null, 5, Map.of(Resource.GOLD, 250),0),
    CATHEDRAL("cathedral", BuildingType.TROOP_TRAINER, 6, Map.of(Resource.GOLD, 1000),0),
    ARMOURER("armourer", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.GOLD, 100, Resource.WOOD, 20), 1),
    BLACKSMITH("blacksmith", BuildingType.WEAPON_PRODUCTION, 30, Map.of(Resource.GOLD, 100, Resource.WOOD, 20), 1),
    FLETCHER("fletcher", BuildingType.WEAPON_PRODUCTION, 30, Map.of(Resource.GOLD, 100, Resource.WOOD, 20), 1),
    POLETURNER("poleturner", BuildingType.WEAPON_PRODUCTION, 35, Map.of(Resource.GOLD, 100, Resource.WOOD, 10), 1),
    OIL_SMELTER("oil smelter", BuildingType.OIL_SMELTER, 20, Map.of(Resource.IRON, 10, Resource.GOLD, 100), 1),
    PITCH_DITCH("pitch ditch", BuildingType.TRAP, -1000, null,0),
    CAGED_WAR_DOGS("caged war dogs", BuildingType.CAGED_WAR_DOGS, 10, Map.of(Resource.GOLD, 100, Resource.WOOD, 10),0),
    SIEGE_TENT("siege tent", BuildingType.SIEGE_TENT, 8, null,0),
    STABLE("stable", BuildingType.STABLE, 20, Map.of(Resource.WOOD, 20, Resource.GOLD, 400),0),
    APPLE_ORCHARD("apple orchard", BuildingType.PRODUCT_MAKER, 30, Map.of(Resource.WOOD, 5), 1),
    DAIRY_FARM("dairy farm", BuildingType.PRODUCT_MAKER, 35, Map.of(Resource.WOOD, 10), 1),
    HOPS_FARM("hops farm", BuildingType.PRODUCT_MAKER, 30, Map.of(Resource.WOOD, 15), 1),
    HUNTERS_HUT("hunters hut", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 5), 1),
    WHEAT_FARM("wheat farm", BuildingType.PRODUCT_MAKER, 25, Map.of(Resource.WOOD, 15), 1),
    BAKERY("bakery", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 10), 1),
    BREWERY("brewery", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 10), 1),
    GRANARY("granary", BuildingType.STORAGE, 25, Map.of(Resource.WOOD, 5),0),
    WALL("wall", null, 20, null,0),
    DITCH("ditch", null, -1000, null,0);
    public enum BuildingType {
        PRODUCT_MAKER, GATE, STORAGE, TOWER, TROOP_TRAINER, QUARRY, SIEGE_TENT, STABLE, TRAP, WEAPON_PRODUCTION, OIL_SMELTER, CAGED_WAR_DOGS, RESIDENCY, OX_TETHER
    }
    private final String name;
    private final BuildingType buildingType;
    private final int maxHitPoints, workersCount;
    private final Map<Resource, Integer> requiredResource;

    BuildingsDetails(String name, BuildingType buildingType, int maxHitPoints, Map<Resource, Integer> requiredResource, int workersCount) {
        this.name = name;
        this.buildingType = buildingType;
        this.maxHitPoints = maxHitPoints;
        this.workersCount = workersCount;
        this.requiredResource = requiredResource;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public Map<Resource, Integer> getRequiredResource() {
        return requiredResource;
    }

    public static BuildingsDetails getBuildingDetailsByName(String name) {
        for (BuildingsDetails buildingsDetails: BuildingsDetails.values())
            if (buildingsDetails.name.equals(name)) return buildingsDetails;
        return null;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }

    public int getWorkersCount() {
        return workersCount;
    }
}
