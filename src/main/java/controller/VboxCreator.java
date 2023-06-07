package controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.building.Enums.BuildingsDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VboxCreator {
    public static final Map<String, BuildingsDetails> CASTLE_BUILDINGS = new HashMap<>(Map.of(
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Armoury.jpg")).toExternalForm(),
            BuildingsDetails.ARMOURY,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Barracks.png")).toExternalForm(),
            BuildingsDetails.BARRACKS,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Caged War Dogs.png")).toExternalForm(),
            BuildingsDetails.CAGED_WAR_DOGS,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Draw Bridge.jpg")).toExternalForm(),
            BuildingsDetails.DRAWBRIDGE,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Killing Pit.png")).toExternalForm(),
            BuildingsDetails.KILLING_PIT,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Large GateHouse.png")).toExternalForm(),
            BuildingsDetails.BIG_STONE_GATEHOUSE,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Mercenary Post.png")).toExternalForm(),
            BuildingsDetails.MERCENARY_POST,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Pitch Ditch.png")).toExternalForm(),
            BuildingsDetails.PITCH_DITCH,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Small GateHouse.png")).toExternalForm(),
            BuildingsDetails.SMALL_STONE_GATEHOUSE,
            Objects.requireNonNull(VboxCreator.class.getResource("/Game Images/Castle Buildings/Turret.png")).toExternalForm(),
            BuildingsDetails.TURRET
            ));

    static {
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource("/Game Images/Castle Buildings/Square Tower.jpg").toExternalForm(),
                BuildingsDetails.SQUARE_TOWER
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource("/Game Images/Castle Buildings/Wall.png").toExternalForm(),
                BuildingsDetails.WALL
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource("/Game Images/Castle Buildings/Round Tower.jpg").toExternalForm(),
                BuildingsDetails.ROUND_TOWER
        );
        CASTLE_BUILDINGS.put(
                VboxCreator.class.getResource("/Game Images/Castle Buildings/Look Out Tower.png").toExternalForm(),
                BuildingsDetails.LOOKOUT_TOWER
        );
    }
    public static VBox getBuildingVbox(String name) {
        switch (name) {
            case "Castle" -> {
                return castleBuilding();
            }
        }
        return null;
    }

    private static VBox castleBuilding() {
        ImageView imageView;
        VBox vBox = new VBox();
        System.out.println(CASTLE_BUILDINGS.size());
        for (Map.Entry<String, BuildingsDetails> entry: CASTLE_BUILDINGS.entrySet()) {
            imageView = new ImageView(new Image(entry.getKey()));
            imageView.setFitHeight(70);
            imageView.setFitWidth(90);
            vBox.getChildren().add(new Label(null, imageView));
        }
        return vBox;
    }
}
