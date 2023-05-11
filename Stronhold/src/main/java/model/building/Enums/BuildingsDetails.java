package model.building.Enums;

import model.generalenums.Resource;

import java.util.Map;

public enum BuildingsDetails {
    HOLD("castle", null, 100, null),
    SMALL_STONE_GATEHOUSE("small stone gatehouse", BuildingType.GATE, 35, null),
    BIG_STONE_GATEHOUSE("big stone gatehouse", BuildingType.GATE, 45, Map.of(Resource.STONE, 20)),
    DRAWBRIDGE("drawbridge", BuildingType.GATE, 35, Map.of(Resource.WOOD, 10)),
    LOOKOUT_TOWER("lookout tower", BuildingType.TOWER, 45, Map.of(Resource.STONE, 10)),
    PERIMETER_TOWER("perimeter tower", BuildingType.TOWER, 40, Map.of(Resource.STONE, 10)),
    TURRET("turret", BuildingType.TOWER, 50, Map.of(Resource.STONE, 15)),
    SQUARE_TOWER("square tower", BuildingType.TOWER, 40, Map.of(Resource.STONE, 35)),
    ROUND_TOWER("round tower", BuildingType.TOWER, 45, Map.of(Resource.STONE, 40)),
    ARMOURY("armoury", BuildingType.STORAGE, 20, Map.of(Resource.WOOD, 5)),
    BARRACKS("barracks", BuildingType.TROOP_TRAINER, 25, Map.of(Resource.STONE, 15)),
    MERCENARY_POST("mercenary post", BuildingType.TROOP_TRAINER, 25, Map.of(Resource.WOOD, 10)),
    ENGINEERS_GUILD("engineers guild", BuildingType.TROOP_TRAINER, 15, Map.of(Resource.WOOD, 10, Resource.GOLD, 100)),
    KILLING_PIT("killing pit", BuildingType.TRAP, -1000, Map.of(Resource.WOOD, 6)),
    INN("inn", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 20, Resource.GOLD, 100)),
    MILL("mill", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 20)),
    IRON_MINE("iron mine", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 20)),
    MARKETPLACE("marketplace", null, 15, Map.of(Resource.WOOD, 5)),
    OX_TETHER("ox tether", BuildingType.PRODUCT_MAKER, 10, Map.of(Resource.WOOD, 5)),
    PITCH_RIG("pitch rig", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 20)),
    QUARRY("quarry", BuildingType.QUARRY, 20, Map.of(Resource.WOOD, 20)),
    STOCKPILE("stockpile", BuildingType.STORAGE, 10, null),
    WOOD_CUTTER("wood cutter", BuildingType.PRODUCT_MAKER, 8, Map.of(Resource.WOOD, 3)),
    HOVEL("hovel", BuildingType.RESIDENCY, 10, Map.of(Resource.WOOD, 6)),
    CHURCH("church", null, 5, Map.of(Resource.GOLD, 250)),
    CATHEDRAL("cathedral", BuildingType.TROOP_TRAINER, 6, Map.of(Resource.GOLD, 1000)),
    ARMOURER("armourer", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.GOLD, 100, Resource.WOOD, 20)),
    BLACKSMITH("blacksmith", BuildingType.WEAPON_PRODUCTION, 30, Map.of(Resource.GOLD, 100, Resource.WOOD, 20)),
    FLETCHER("fletcher", BuildingType.WEAPON_PRODUCTION, 30, Map.of(Resource.GOLD, 100, Resource.WOOD, 20)),
    POLETURNER("poleturner", BuildingType.WEAPON_PRODUCTION, 35, Map.of(Resource.GOLD, 100, Resource.WOOD, 10)),
    OIL_SMELTER("oil smelter", BuildingType.OIL_SMELTER, 20, Map.of(Resource.IRON, 10, Resource.GOLD, 100)),
    PITCH_DITCH("pitch ditch", BuildingType.TRAP, -1000, null),
    CAGED_WAR_DOGS("caged war dogs", BuildingType.CAGED_WAR_DOGS, 10, Map.of(Resource.GOLD, 100, Resource.WOOD, 10)),
    SIEGE_TENT("siege tent", BuildingType.SIEGE_TENT, 8, null),
    STABLE("stable", BuildingType.STABLE, 20, Map.of(Resource.WOOD, 20, Resource.GOLD, 400)),
    APPLE_ORCHARD("apple orchard", BuildingType.PRODUCT_MAKER, 30, Map.of(Resource.WOOD, 5)),
    DAIRY_FARM("dairy farm", BuildingType.PRODUCT_MAKER, 35, Map.of(Resource.WOOD, 10)),
    HOPS_FARM("hops farm", BuildingType.PRODUCT_MAKER, 30, Map.of(Resource.WOOD, 15)),
    HUNTERS_HUT("hunters hut", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 5)),
    WHEAT_FARM("wheat farm", BuildingType.PRODUCT_MAKER, 25, Map.of(Resource.WOOD, 15)),
    BAKERY("bakery", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 10)),
    BREWERY("brewery", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 10)),
    GRANARY("granary", BuildingType.STORAGE, 25, Map.of(Resource.WOOD, 5)),
    WALL("wall", null, 20, null),
    DITCH("ditch", null, -1000, null);
    public enum BuildingType {
        PRODUCT_MAKER, GATE, STORAGE, TOWER, TROOP_TRAINER, QUARRY, SIEGE_TENT, STABLE, TRAP, WEAPON_PRODUCTION, OIL_SMELTER, CAGED_WAR_DOGS, RESIDENCY
    }
    private String name;
    private BuildingType buildingType;
    private int maxHitPoints;
    private Map<Resource, Integer> requiredResource;
    BuildingsDetails(String name, BuildingType buildingType, int maxHitPoints, Map<Resource, Integer> requiredResource){}

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
}
