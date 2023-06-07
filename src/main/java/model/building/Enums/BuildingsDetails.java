package model.building.Enums;

import model.generalenums.Resource;

import java.util.Map;

public enum BuildingsDetails {
    HOLD("hold", null, 100, null, 0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    SMALL_STONE_GATEHOUSE("small stone gatehouse", BuildingType.GATE, 35, null, 0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    BIG_STONE_GATEHOUSE("big stone gatehouse", BuildingType.GATE, 45, Map.of(Resource.STONE, 20), 0,
            "/Game Images/Castle Buildings/Large GateHouse.png"),
    DRAWBRIDGE("drawbridge", BuildingType.GATE, 35, Map.of(Resource.WOOD, 10), 0,
            "/Game Images/Castle Buildings/Draw Bridge.jpg"),
    LOOKOUT_TOWER("lookout tower", BuildingType.TOWER, 45, Map.of(Resource.STONE, 10),0,
            "/Game Images/Castle Buildings/Look Out Tower.png"),
    PERIMETER_TOWER("perimeter tower", BuildingType.TOWER, 40, Map.of(Resource.STONE, 10),0,
            "/Game Images/Castle Buildings/Perimeter Tower.jpg"),
    TURRET("turret", BuildingType.TOWER, 50, Map.of(Resource.STONE, 15),0,
            "/Game Images/Castle Buildings/Turret.png"),
    SQUARE_TOWER("square tower", BuildingType.TOWER, 40, Map.of(Resource.STONE, 35),0,
            "/Game Images/Castle Buildings/Square Tower.jpg"),
    ROUND_TOWER("round tower", BuildingType.TOWER, 45, Map.of(Resource.STONE, 40),0,
            "/Game Images/Castle Buildings/Round Tower.jpg"),
    ARMOURY("armoury", BuildingType.STORAGE, 20, Map.of(Resource.WOOD, 5),0,
            "/Game Images/Castle Buildings/Armoury.jpg"),
    BARRACKS("barracks", BuildingType.TROOP_TRAINER, 25, Map.of(Resource.STONE, 15),0,
            "/Game Images/Castle Buildings/Barracks.png"),
    MERCENARY_POST("mercenary post", BuildingType.TROOP_TRAINER, 25, Map.of(Resource.WOOD, 10),0,
            "/Game Images/Castle Buildings/Mercenary Post.png"),
    ENGINEERS_GUILD("engineers guild", BuildingType.TROOP_TRAINER, 15, Map.of(Resource.WOOD, 10, Resource.GOLD, 100),0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    TANNERS_GUILD("tanners guild", BuildingType.TROOP_TRAINER, 15, Map.of(Resource.WOOD, 5), 0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    KILLING_PIT("killing pit", BuildingType.TRAP, -1000, Map.of(Resource.WOOD, 6),0,
            "/Game Images/Castle Buildings/Killing Pit.png"),
    INN("inn", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 20, Resource.GOLD, 100), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    MILL("mill", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 20), 3,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    IRON_MINE("iron mine", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 20), 2,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    MARKETPLACE("marketplace", null, 15, Map.of(Resource.WOOD, 5), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    OX_TETHER("ox tether", BuildingType.OX_TETHER, 10, Map.of(Resource.WOOD, 5), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    PITCH_RIG("pitch rig", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 20), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    QUARRY("quarry", BuildingType.QUARRY, 20, Map.of(Resource.WOOD, 20), 3,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    STOCKPILE("stockpile", BuildingType.STORAGE, 10, null,0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    WOOD_CUTTER("wood cutter", BuildingType.PRODUCT_MAKER, 8, Map.of(Resource.WOOD, 3), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    HOVEL("hovel", BuildingType.RESIDENCY, 10, Map.of(Resource.WOOD, 6),0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    CHURCH("church", null, 5, Map.of(Resource.GOLD, 250),0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    CATHEDRAL("cathedral", BuildingType.TROOP_TRAINER, 6, Map.of(Resource.GOLD, 1000),0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    ARMOURER("armourer", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.GOLD, 100, Resource.WOOD, 20), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    BLACKSMITH("blacksmith", BuildingType.WEAPON_PRODUCTION, 30, Map.of(Resource.GOLD, 100, Resource.WOOD, 20), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    FLETCHER("fletcher", BuildingType.WEAPON_PRODUCTION, 30, Map.of(Resource.GOLD, 100, Resource.WOOD, 20), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    POLETURNER("poleturner", BuildingType.WEAPON_PRODUCTION, 35, Map.of(Resource.GOLD, 100, Resource.WOOD, 10), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    OIL_SMELTER("oil smelter", BuildingType.OIL_SMELTER, 20, Map.of(Resource.IRON, 10, Resource.GOLD, 100), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    PITCH_DITCH("pitch ditch", BuildingType.TRAP, -1000, null,0,
            "/Game Images/Castle Buildings/Pitch Ditch.png"),
    CAGED_WAR_DOGS("caged war dogs", BuildingType.CAGED_WAR_DOGS, 10, Map.of(Resource.GOLD, 100, Resource.WOOD, 10),0,
            "/Game Images/Castle Buildings/Caged War Dogs.png"),
    SIEGE_TENT("siege tent", BuildingType.SIEGE_TENT, 8, null,0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    STABLE("stable", BuildingType.STABLE, 20, Map.of(Resource.WOOD, 20, Resource.GOLD, 400),0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    APPLE_ORCHARD("apple orchard", BuildingType.PRODUCT_MAKER, 30, Map.of(Resource.WOOD, 5), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    DAIRY_FARM("dairy farm", BuildingType.PRODUCT_MAKER, 35, Map.of(Resource.WOOD, 10), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    HOPS_FARM("hops farm", BuildingType.PRODUCT_MAKER, 30, Map.of(Resource.WOOD, 15), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    HUNTERS_HUT("hunters hut", BuildingType.PRODUCT_MAKER, 15, Map.of(Resource.WOOD, 5), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    WHEAT_FARM("wheat farm", BuildingType.PRODUCT_MAKER, 25, Map.of(Resource.WOOD, 15), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    BAKERY("bakery", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 10), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    BREWERY("brewery", BuildingType.PRODUCT_MAKER, 20, Map.of(Resource.WOOD, 10), 1,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    GRANARY("granary", BuildingType.STORAGE, 25, Map.of(Resource.WOOD, 5),0,
            "/Game Images/Castle Buildings/Small GateHouse.png"),
    WALL("wall", null, 20, null,0,
            "/Game Images/Castle Buildings/Wall.png"),
    DITCH("ditch", null, -1000, null,0,
            "/Game Images/Castle Buildings/Small GateHouse.png");
    public enum BuildingType {
        PRODUCT_MAKER, GATE, STORAGE, TOWER, TROOP_TRAINER, QUARRY, SIEGE_TENT, STABLE, TRAP, WEAPON_PRODUCTION, OIL_SMELTER, CAGED_WAR_DOGS, RESIDENCY, OX_TETHER
    }
    private final String name, imagePath;
    private final BuildingType buildingType;
    private final int maxHitPoints, workersCount;
    private final Map<Resource, Integer> requiredResource;

    BuildingsDetails(String name, BuildingType buildingType, int maxHitPoints, Map<Resource, Integer> requiredResource, int workersCount, String imagePath) {
        this.name = name;
        this.buildingType = buildingType;
        this.maxHitPoints = maxHitPoints;
        this.workersCount = workersCount;
        this.requiredResource = requiredResource;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }
}
