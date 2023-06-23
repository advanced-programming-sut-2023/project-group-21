package view;

import controller.GameController;
import controller.MapController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Cell;
import model.building.Enums.BuildingsDetails;
import model.generalenums.Extras;
import model.generalenums.GroundTexture;
import view.message.MapMessages;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MapViewGui extends Application implements Initializable {
    private final static String pathCssFile = "file:" + (new File("").getAbsolutePath()) +
            "/src/main/resources/CSS/Texture.css";
    private String[] textureItem;
    private static Stage mainWindows;
    private final Label errorLabel = new Label("");
    private double startDragX = 0, startDragY = 0;
    private int CELL_SIZE = 75;
    @FXML
    private Pane cellPane;
    @FXML
    private AnchorPane mainPane;
    private Cell[][] showingMap;
    private int currentX = 5, currentY = 5;
    public MapController mapController;
    public static MapController mapControllerStatic;
    private boolean isStartDrag = false;
    private static MainMenu staticMainMenu;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox objectBox;
    private double startMoveX = 0, startMoveY = 0;
    private int selectedX1 = -1, selectedX2 = -1, selectedY1 = -1, selectedY2 = -1;//-1 means there is no selected cell!
    private boolean isDraggedExtra = false;
    private Extras selectedExtra;
    private long currentTimeZoom = System.currentTimeMillis();//set current time for zoom
    private long currentTimeHover = currentTimeZoom;//to make delay hover!
    private BuildingsDetails selectedBuildingDetails;
    private Stage mainStage;
    //    Alert alert = new Alert(Alert.AlertType.ERROR);
    private MainMenu mainMenu;
    private GameController gameController;
    private static GameController staticGameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        staticGameController = gameController;
    }

    public static void setStaticGameController(GameController gameController) {
        MapViewGui.staticGameController = gameController;
    }


    public void setMainMenu(MainMenu mainMenu) {
        staticMainMenu = mainMenu;
        this.mainMenu = mainMenu;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        if (mapControllerStatic == null) {
            mapController = new MapController();
            mapControllerStatic = mapController;
            mainWindows = stage;
        } else
            mapController = mapControllerStatic;
        if (gameController == null)
            gameController = staticGameController;
        this.mainStage = stage;
        try {
            Parent parent1 = FXMLLoader.load(MapController.class.getResource("/FXML/Map.fxml"));
            Scene mapScene = new Scene(parent1);
            stage.setScene(mapScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMap(Cell[][] myMap) {
        showingMap = myMap;
        if (cellPane.getChildren().size() > 1)
            cellPane.getChildren().remove(0, cellPane.getChildren().size());
        for (int i1 = 0; i1 < myMap.length && i1 < (600 / CELL_SIZE); i1++)
            for (int i2 = 0; i2 < myMap.length && i2 < (600 / CELL_SIZE); i2++) {
                Label label = myMap[i1][i2].toLabel(CELL_SIZE * i1, CELL_SIZE * i2, CELL_SIZE);
                int finalI = i1;
                if ((currentX + i1 - 1) >= selectedX1 && (currentX + i1 - 1) <= selectedX2 &&
                        (currentY + i2 - 1) >= selectedY1 && (currentY + i2 - 1) <= selectedY2) {
                    label.setOpacity(0.4);
                }
                int finalI1 = i2;
                label.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent contextMenuEvent) {
                        isDraggedExtra = false;
                        if ((currentX + finalI - 1) <= selectedX2 && (currentX + finalI - 1) >= selectedX1 &&
                                (currentY + finalI1 - 1) <= selectedY2 && (currentY + finalI1 - 1) >= selectedY1)
                            setTexture(selectedX1, selectedY1, selectedX2, selectedY2);
                        else
                            setTexture(currentX + finalI - 1, currentY + finalI1 - 1,
                                    currentX + finalI - 1, currentY + finalI1 - 1);

                    }
                });
                label.setTooltip(new Tooltip(mapController.showDetails(currentX + i1 - 1,currentY + i2 -1)));

                cellPane.getChildren().add(label);
            }
        if (600 % CELL_SIZE != 0) {
            int thing = 600 / CELL_SIZE;
            for (int i = 0; i < thing && i < myMap.length; i++) {
                Label label = myMap[thing][i].toLabel(thing * CELL_SIZE, i * CELL_SIZE,
                        (600 - CELL_SIZE * (thing)), CELL_SIZE);
                cellPane.getChildren().add(label);
            }
            for (int i = 0; i < (600 / CELL_SIZE) && i < myMap.length; i++) {
                Label label = myMap[i][thing].toLabel(i * CELL_SIZE, thing * CELL_SIZE, CELL_SIZE,
                        600 - CELL_SIZE * thing);
                cellPane.getChildren().add(label);
            }
            Label label = myMap[thing][thing].toLabel(CELL_SIZE * thing,
                    CELL_SIZE * thing, 600 - CELL_SIZE * thing, 600 - CELL_SIZE * thing);
            cellPane.getChildren().add(label);
        }
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
        mapControllerStatic = mapController;
    }

    private void selectCell(double x, double y, double x2, double y2) {
        if (Math.abs(x - x2) < 0.5 && Math.abs(y - y2) < 0.5) {
            int xInt = (int) (x / CELL_SIZE);
            int yInt = (int) (y / CELL_SIZE);
            selectedX1 = selectedX2 = (xInt + currentX - 1);
            selectedY1 = selectedY2 = (yInt + currentY - 1);
            showMap(showingMap);
//            System.out.println("test!");
            return;
        }
        selectedX1 = (int) ((x / CELL_SIZE) + currentX - 1);
        selectedX2 = (int) ((x2 / CELL_SIZE) + currentX - 1);
        selectedY1 = (int) (y / CELL_SIZE + currentY - 1);
        selectedY2 = (int) (y2 / CELL_SIZE + currentY - 1);
        int maxX = Math.max(selectedX2, selectedX1);
        int minX = Math.min(selectedX2, selectedX1);
        int maxY = Math.max(selectedY2, selectedY1);
        int minY = Math.min(selectedY1, selectedY2);
        selectedX1 = minX;
        selectedY1 = minY;
        selectedX2 = maxX;
        selectedY2 = maxY;
        showMap(showingMap);
    }


    public void save() {
        mapController.saveMap();
    }

    public void back() {
        if (staticMainMenu != null)
            staticMainMenu.start(StartingMenu.mainStage);
        else {
            mainMenu = new MainMenu();
            mainMenu.setMapController(mapControllerStatic);
            mainMenu.start(StartingMenu.mainStage);
        }
    }

    public void resetFields() {
        if (cellPane.getChildren().size() > 1)
            cellPane.getChildren().remove(0, cellPane.getChildren().size() - 1);
    }

    public void goRight() {
        if (currentX < mapController.getMap().length - 8)
            currentX++;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
    }

    public void goLeft() {
        if (currentX > 1)
            currentX--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
    }

    public void zoomIn() {
        if (System.currentTimeMillis() - currentTimeZoom < 700)
            return;
        if (CELL_SIZE <= 95)
            CELL_SIZE += 5;
        if (CELL_SIZE == 85)
            CELL_SIZE = 90;
        currentTimeZoom = System.currentTimeMillis();
        showMap(showingMap);
    }

    public void zoomOut() {
        if (System.currentTimeMillis() - currentTimeZoom < 700)
            return;
        if (CELL_SIZE >= 80)
            CELL_SIZE -= 5;
        if (CELL_SIZE == 85)
            CELL_SIZE = 80;
        currentTimeZoom = System.currentTimeMillis();
        showMap(showingMap);
    }

    public void goDown() {
        if (currentY > 1)
            currentY--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
    }

    public void goUp() {
        if (currentY < mapController.getMap().length - 8)
            currentY++;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
    }


    private void raiseError(MapMessages messages) {//raise error
        // repaired!
        errorLabel.setText(messages.toString());
        if (!mainPane.getChildren().contains(errorLabel))
            mainPane.getChildren().add(errorLabel);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.relocate(900,200);
        if (gameController == null)
            gameController = staticGameController;
        if (mapControllerStatic == null) {
            if (MainMenu.staticMapController != null) {
            }
            else {
                MainMenu.staticMapController = new MapController();
            }
            mapController = MainMenu.staticMapController;
            mapControllerStatic = mapController;
        } else
            mapController = mapControllerStatic;
        textureItem = new String[GroundTexture.values().length];
        for (int i = 0; i < textureItem.length; i++)
            textureItem[i] = GroundTexture.values()[i].getName();
        initExtra();
        cellPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                if (scrollEvent.getDeltaY()<1 && scrollEvent.getDeltaY()>-1)
                    return;
                if (scrollEvent.getDeltaY()>0)
                    zoomIn();
                else
                    zoomOut();
            }
        });
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {// for handling shortcuts!
                if (keyEvent.getCode() == KeyCode.S)
                    save();
                else if (keyEvent.getCode() == KeyCode.Q)
                    back();
                else if (keyEvent.getCode() == KeyCode.N)
                    System.out.println("next turn!");//for going to next turn
                else if (keyEvent.getCode() == KeyCode.G)
                    gotoXY();
                else if (keyEvent.getCode() == KeyCode.L)
                    goLeft();
                else if (keyEvent.getCode() == KeyCode.R)
                    goRight();
                else if (keyEvent.getCode() == KeyCode.U)
                    goUp();
                else if (keyEvent.getCode() == KeyCode.D)
                    goDown();
            }
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        if (!mapController.isInitState())
            mapController.loadMapNormal();
        showMap(mapController.showMapGui(currentX, currentY));

        cellPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton() == MouseButton.MIDDLE || mouseEvent.getButton() == MouseButton.SECONDARY) {
                    if (mouseEvent.getButton() == MouseButton.MIDDLE){
                        startMoveX = mouseEvent.getX();
                        startMoveY = mouseEvent.getY();
                    }
                    return;
                }
                isStartDrag = true;
                startDragX = mouseEvent.getX();
                startDragY = mouseEvent.getY();
            }
        });

        cellPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.MIDDLE || mouseEvent.getButton() == MouseButton.SECONDARY) {
                    if (mouseEvent.getButton() == MouseButton.MIDDLE){
                        double deltaX = mouseEvent.getX() - startMoveX;
                        double deltaY = mouseEvent.getY() - startMoveY;
                        if (deltaX >  CELL_SIZE)
                            goLeft();
                        if (deltaX < -1 * CELL_SIZE)
                            goRight();
                        if (deltaY > CELL_SIZE)
                            goDown();
                        if (deltaY < -1 * CELL_SIZE)
                            goUp();
                    }
                    return;
                }
                if (isDraggedExtra) {
                    isDraggedExtra = false;
                    MapMessages messages = mapController.setExtra(currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                            currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1, selectedExtra);
                    if (messages != MapMessages.SUCCESS) {
                        raiseError(messages);
                    }else {
                        raiseError(MapMessages.NULL_MESSAGE);
                    }
                    showMap(showingMap);
                    return;
                }
                if (!isStartDrag) {
                    selectCell(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    return;
                }
                selectCell(startDragX, startDragY, mouseEvent.getX(), mouseEvent.getY());
                isStartDrag = false;
            }
        });
        mainPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isDraggedExtra = false;
                isStartDrag = false;
            }
        });
    }

    public void gotoXY() {
        Stage goStage = new Stage();
        Pane thisPane = new Pane();
        Scene scene = new Scene(thisPane,320,200);
//        scene.getStylesheets().add(pathCssFile);
        goStage.setScene(scene);
        Spinner<Integer> xSpinner = new Spinner<>();
        Spinner<Integer> ySpinner = new Spinner<>();
        xSpinner.setMaxWidth(70);ySpinner.setMaxWidth(70);
//        xSpinner.setEditable(false);ySpinner.setEditable(false);
        SpinnerValueFactory<Integer> XFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                mapController.getMap().length, mapController.getMap().length / 2);
        SpinnerValueFactory<Integer> YFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                mapController.getMap().length, mapController.getMap()[0].length / 2);
        xSpinner.setValueFactory(XFactory);
        ySpinner.setValueFactory(YFactory);
        Button go = new Button("go");
        go.relocate(100, 120);
        xSpinner.relocate(50, 30);
        ySpinner.relocate(200, 30);
        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentX = xSpinner.getValue() - 1;
                currentY = ySpinner.getValue() - 1;
                showMap(mapController.showMapGui(xSpinner.getValue() - 1, ySpinner.getValue() - 1));
                goStage.close();
            }
        });
        thisPane.getChildren().addAll(go, xSpinner, ySpinner);
        goStage.show();
    }

    public void initExtra() {
        objectBox.getChildren().remove(0, objectBox.getChildren().size());
        ImageView imageView;
        Image image;
        Label label;
        String basePath = new File("").getAbsolutePath();
        for (int i = 0; i < Extras.values().length; i++) {
            image = new Image("file:" + basePath + "/src/main/resources/ExtraImage/" + Extras.values()[i].getImagePath());
            imageView = new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(70);
            int finalI = i;
            label = new Label(null, imageView);
            label.setStyle("-fx-border-color: black;");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println(finalI);
                    selectedExtra = Extras.values()[finalI];
                    isDraggedExtra = true;
                }
            });
            objectBox.getChildren().add(label);
        }
    }


    private void setTexture(int x1, int y1, int x2, int y2) {//use function this way: x1 <= x2 && y1 <= y2
        Label label = new Label("");
        ChoiceBox<String> textureBox = new ChoiceBox<>();
        textureBox.getItems().addAll(textureItem);
        textureBox.setValue(textureItem[0]);
        textureBox.relocate(130, 70);
        Stage stage = new Stage();
        stage.initOwner(mainStage);
        stage.setResizable(false);
        stage.setTitle("texture");
        Pane pane = new Pane();
        Scene myScene = new Scene(pane);
        myScene.getStylesheets().add(pathCssFile);
        stage.setScene(myScene);
        Button clearButton = new Button("clear");
        clearButton.relocate(200, 100);
        pane.getChildren().add(clearButton);
        pane.getChildren().add(textureBox);
        clearButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                for (int i1 = x1; i1 <= x2; i1++)
                    for (int i2 = y1; i2 <= y2; i2++)
                        mapController.clear(i1, i2);
                showMap(showingMap);
                stage.close();
            }
        });
        Button change = new Button("save");
        change.relocate(100, 100);
        pane.getChildren().add(change);
        change.setOnMouseClicked(mouseEvent -> {
            MapMessages messages;
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                messages = mapController.setTexture(x1, x2, y1, y2, textureBox.getValue());
                showMap(showingMap);
                if (messages == MapMessages.SUCCESS)
                    stage.close();
                else {
                    label.setText(messages.toString());
                    label.setAlignment(Pos.CENTER);
                    label.setLayoutX(130);
                    label.setLayoutY(10);
                    pane.getChildren().add(label);
                }
            }
        });
        stage.show();
    }

}
