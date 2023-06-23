package controller;

import model.building.Enums.BuildingsDetails;
import model.human.Enums.WorkerDetails;
import model.machine.MachineDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VboxCreator {
    public static final Map<String, BuildingsDetails> CASTLE_BUILDINGS = new HashMap<>(Map.of(
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Armoury.jpg")).toExternalForm(),
            BuildingsDetails.ARMOURY,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Barracks.png")).toExternalForm(),
            BuildingsDetails.BARRACKS,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Caged War Dogs.png")).toExternalForm(),
            BuildingsDetails.CAGED_WAR_DOGS,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Draw Bridge.jpg")).toExternalForm(),
            BuildingsDetails.DRAWBRIDGE,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Killing Pit.png")).toExternalForm(),
            BuildingsDetails.KILLING_PIT,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Large GateHouse.png")).toExternalForm(),
            BuildingsDetails.BIG_STONE_GATEHOUSE,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Mercenary Post.png")).toExternalForm(),
            BuildingsDetails.MERCENARY_POST,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Pitch Ditch.png")).toExternalForm(),
            BuildingsDetails.PITCH_DITCH,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Small GateHouse.png")).toExternalForm(),
            BuildingsDetails.SMALL_STONE_GATEHOUSE,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Turret.png")).toExternalForm(),
            BuildingsDetails.TURRET
    )),
            FARM_BUILDINGS = new HashMap<>(Map.of(
                    VboxCreator.class.getResource(BuildingsDetails.APPLE_ORCHARD.getImagePath()).toExternalForm(),
                    BuildingsDetails.APPLE_ORCHARD,
                    VboxCreator.class.getResource(BuildingsDetails.DAIRY_FARM.getImagePath()).toExternalForm(),
                    BuildingsDetails.DAIRY_FARM,
                    VboxCreator.class.getResource(BuildingsDetails.HOPS_FARM.getImagePath()).toExternalForm(),
                    BuildingsDetails.HOPS_FARM,
                    VboxCreator.class.getResource(BuildingsDetails.HUNTERS_HUT.getImagePath()).toExternalForm(),
                    BuildingsDetails.HUNTERS_HUT,
                    VboxCreator.class.getResource(BuildingsDetails.WHEAT_FARM.getImagePath()).toExternalForm(),
                    BuildingsDetails.WHEAT_FARM
            )),
            FOOD_PROCESSING_BUILDINGS = new HashMap<>(Map.of(
                    VboxCreator.class.getResource(BuildingsDetails.BAKERY.getImagePath()).toExternalForm(),
                    BuildingsDetails.BAKERY,
                    VboxCreator.class.getResource(BuildingsDetails.BREWERY.getImagePath()).toExternalForm(),
                    BuildingsDetails.BREWERY,
                    VboxCreator.class.getResource(BuildingsDetails.GRANARY.getImagePath()).toExternalForm(),
                    BuildingsDetails.GRANARY,
                    VboxCreator.class.getResource(BuildingsDetails.INN.getImagePath()).toExternalForm(),
                    BuildingsDetails.INN,
                    VboxCreator.class.getResource(BuildingsDetails.MILL.getImagePath()).toExternalForm(),
                    BuildingsDetails.MILL
            )),
            INDUSTRY_BUILDINGS = new HashMap<>(Map.of(
                    VboxCreator.class.getResource(BuildingsDetails.IRON_MINE.getImagePath()).toExternalForm(),
                    BuildingsDetails.IRON_MINE,
                    VboxCreator.class.getResource(BuildingsDetails.OX_TETHER.getImagePath()).toExternalForm(),
                    BuildingsDetails.OX_TETHER,
                    VboxCreator.class.getResource(BuildingsDetails.PITCH_RIG.getImagePath()).toExternalForm(),
                    BuildingsDetails.PITCH_RIG,
                    VboxCreator.class.getResource(BuildingsDetails.QUARRY.getImagePath()).toExternalForm(),
                    BuildingsDetails.QUARRY,
                    VboxCreator.class.getResource(BuildingsDetails.STOCKPILE.getImagePath()).toExternalForm(),
                    BuildingsDetails.STOCKPILE,
                    VboxCreator.class.getResource(BuildingsDetails.WOOD_CUTTER.getImagePath()).toExternalForm(),
                    BuildingsDetails.WOOD_CUTTER
            )),
            TOWN_BUILDING = new HashMap<>(Map.of(
                    VboxCreator.class.getResource(BuildingsDetails.CATHEDRAL.getImagePath()).toExternalForm(),
                    BuildingsDetails.CATHEDRAL,
                    VboxCreator.class.getResource(BuildingsDetails.CHURCH.getImagePath()).toExternalForm(),
                    BuildingsDetails.CHURCH,
                    VboxCreator.class.getResource(BuildingsDetails.HOVEL.getImagePath()).toExternalForm(),
                    BuildingsDetails.HOVEL
            )),
            WEAPONS_BUILDINGS = new HashMap<>(Map.of(
                    VboxCreator.class.getResource(BuildingsDetails.ARMOURER.getImagePath()).toExternalForm(),
                    BuildingsDetails.ARMOURER,
                    VboxCreator.class.getResource(BuildingsDetails.BLACKSMITH.getImagePath()).toExternalForm(),
                    BuildingsDetails.BLACKSMITH,
                    VboxCreator.class.getResource(BuildingsDetails.FLETCHER.getImagePath()).toExternalForm(),
                    BuildingsDetails.FLETCHER,
                    VboxCreator.class.getResource(BuildingsDetails.POLETURNER.getImagePath()).toExternalForm(),
                    BuildingsDetails.POLETURNER
            ));


    public static Map<String, MachineDetails> MACHINES = new HashMap<>(Map.of(
            VboxCreator.class.getResource(MachineDetails.BATTERING_RAM.getImagePath()).toExternalForm(),
            MachineDetails.BATTERING_RAM,
            VboxCreator.class.getResource(MachineDetails.CATAPULT.getImagePath()).toExternalForm(),
            MachineDetails.CATAPULT,
            VboxCreator.class.getResource(MachineDetails.FIRE_BALLISTA.getImagePath()).toExternalForm(),
            MachineDetails.FIRE_BALLISTA,
            VboxCreator.class.getResource(MachineDetails.PORTABLE_SHIELD.getImagePath()).toExternalForm(),
            MachineDetails.PORTABLE_SHIELD,
            VboxCreator.class.getResource(MachineDetails.SIEGE_TOWER.getImagePath()).toExternalForm(),
            MachineDetails.SIEGE_TOWER,
            VboxCreator.class.getResource(MachineDetails.TREBUCHET.getImagePath()).toExternalForm(),
            MachineDetails.TREBUCHET
    ));

    public static Map<String, WorkerDetails> EUROPEAN_SOLDIERS = new HashMap<>(Map.of(
            VboxCreator.class.getResource(WorkerDetails.ARCHER.getImagePath()).toExternalForm(),
            WorkerDetails.ARCHER,
            VboxCreator.class.getResource(WorkerDetails.CROSSBOWMAN.getImagePath()).toExternalForm(),
            WorkerDetails.CROSSBOWMAN,
            VboxCreator.class.getResource(WorkerDetails.SPEARMAN.getImagePath()).toExternalForm(),
            WorkerDetails.SPEARMAN,
            VboxCreator.class.getResource(WorkerDetails.PIKEMAN.getImagePath()).toExternalForm(),
            WorkerDetails.PIKEMAN,
            VboxCreator.class.getResource(WorkerDetails.MACEMAN.getImagePath()).toExternalForm(),
            WorkerDetails.MACEMAN,
            VboxCreator.class.getResource(WorkerDetails.SWORDSMAN.getImagePath()).toExternalForm(),
            WorkerDetails.SWORDSMAN,
            VboxCreator.class.getResource(WorkerDetails.KNIGHT.getImagePath()).toExternalForm(),
            WorkerDetails.KNIGHT
    )),
            ARABIAN_SOLDIERS = new HashMap<>(Map.of(
                    VboxCreator.class.getResource(WorkerDetails.ARABIAN_BOW.getImagePath()).toExternalForm(),
                    WorkerDetails.ARABIAN_BOW,
                    VboxCreator.class.getResource(WorkerDetails.SLAVE.getImagePath()).toExternalForm(),
                    WorkerDetails.SLAVE,
                    VboxCreator.class.getResource(WorkerDetails.SLINGER.getImagePath()).toExternalForm(),
                    WorkerDetails.SLINGER,
                    VboxCreator.class.getResource(WorkerDetails.ASSASSIN.getImagePath()).toExternalForm(),
                    WorkerDetails.ASSASSIN,
                    VboxCreator.class.getResource(WorkerDetails.HORSE_ARCHER.getImagePath()).toExternalForm(),
                    WorkerDetails.HORSE_ARCHER,
                    VboxCreator.class.getResource(WorkerDetails.ARABIAN_SWORDSMAN.getImagePath()).toExternalForm(),
                    WorkerDetails.ARABIAN_SWORDSMAN,
                    VboxCreator.class.getResource(WorkerDetails.FIRE_THROWER.getImagePath()).toExternalForm(),
                    WorkerDetails.FIRE_THROWER
            )),
            ENGINEERS_GUILD = new HashMap<>(Map.of(
                    VboxCreator.class.getResource(WorkerDetails.ENGINEER.getImagePath()).toExternalForm(),
                    WorkerDetails.ENGINEER,
                    VboxCreator.class.getResource(WorkerDetails.LADDERMAN.getImagePath()).toExternalForm(),
                    WorkerDetails.LADDERMAN
            ));


    static {
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Square Tower.jpg").toExternalForm(),
                BuildingsDetails.SQUARE_TOWER
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Wall.png").toExternalForm(),
                BuildingsDetails.WALL
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource("/Game Images/Buildings/Castle Buildings/Round Tower.jpg").toExternalForm(),
                BuildingsDetails.ROUND_TOWER
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource(BuildingsDetails.LOOKOUT_TOWER.getImagePath()).toExternalForm(),
                BuildingsDetails.LOOKOUT_TOWER
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource(BuildingsDetails.ENGINEERS_GUILD.getImagePath()).toExternalForm(),
                BuildingsDetails.ENGINEERS_GUILD
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource(BuildingsDetails.OIL_SMELTER.getImagePath()).toExternalForm(),
                BuildingsDetails.OIL_SMELTER
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource(BuildingsDetails.STABLE.getImagePath()).toExternalForm(),
                BuildingsDetails.STABLE
        );
    }
}
