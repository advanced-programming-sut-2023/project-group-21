package view;

import controller.MapController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.Cell;
import model.generalenums.Extras;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapViewGui extends Application implements Initializable {
    private double startDragX = 0, startDragY = 0;
    private int CELL_SIZE = 75;
    @FXML
    private Pane cellPane;
    @FXML
    private AnchorPane mainPane;
    private Cell[][] showingMap;
    private int currentX = 5, currentY = 5;
    public MapController mapController = new MapController();
    private boolean isStartDrag = false;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox objectBox;
    private int selectedX1 = -1, selectedX2 = -1, selectedY1 = -1, selectedY2 = -1;//-1 means there is no selected cell!

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent parent1 = FXMLLoader.load(MapController.class.getResource("/FXML/Map.fxml"));
            Scene mapScene = new Scene(parent1);
            stage.setScene(mapScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMap(Cell[][] myMap, int size) {
        showingMap = myMap;
        if (cellPane.getChildren().size() > 1)
            cellPane.getChildren().remove(0, cellPane.getChildren().size());
        for (int i1 = 0; i1 < myMap.length && i1 < (600 / CELL_SIZE); i1++)
            for (int i2 = 0; i2 < myMap.length && i2 < (600 / CELL_SIZE); i2++) {
                Label label = myMap[i1][i2].toLabel(CELL_SIZE * i1, CELL_SIZE * i2, CELL_SIZE);
                cellPane.getChildren().add(label);
            }
        if (600 % CELL_SIZE != 0) {
            int thing = 600 / CELL_SIZE;
            for (int i = 0; i < thing && i < myMap.length; i++) {
                Label label = myMap[thing][i].toLabel(thing * CELL_SIZE, i * CELL_SIZE, (600 - CELL_SIZE * (thing)), CELL_SIZE);
                cellPane.getChildren().add(label);
            }
            for (int i = 0; i < (600 / CELL_SIZE) && i < myMap.length; i++) {
                Label label = myMap[i][thing].toLabel(i * CELL_SIZE, thing * CELL_SIZE, CELL_SIZE, 600 - CELL_SIZE * thing);
                cellPane.getChildren().add(label);
            }
            Label label = myMap[thing][thing].toLabel(CELL_SIZE * thing,
                    CELL_SIZE * thing, 600 - CELL_SIZE * thing, 600 - CELL_SIZE * thing);
            cellPane.getChildren().add(label);
        }
    }

    private void selectCell(double x, double y, double x2, double y2) {
        if (Math.abs(x - x2) < 0.5 && Math.abs(y - y2) < 0.5) {
            int xInt = (int) (x / CELL_SIZE);
            int yInt = (int) (x / CELL_SIZE);
            selectedX1 = selectedX2 = (xInt + currentX);
            selectedY1 = selectedY2 = (yInt + currentY);
            return;
        }
        selectedX1 = (int) ((x / CELL_SIZE) + currentX - 1);
        selectedX2 = (int) ((x2 / CELL_SIZE) + currentX - 1);
        selectedY1 = (int) (y / CELL_SIZE + currentY - 1);
        selectedY2 = (int) (y2 / CELL_SIZE + currentY - 1);
    }

    private void moveMap(int x, int y) {
        System.out.println("(" + x + "," + y + ")");
    }

    public void save() {
        System.out.println(selectedX1 + " " + selectedX2 + " " + selectedY1 + " " + selectedY2);
    }

    public void back() {

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
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }

    public void goLeft() {
        if (currentX > 1)
            currentX--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }

    public void zoomIn() {
        if (CELL_SIZE <= 95)
            CELL_SIZE += 5;
        showMap(showingMap, 100);
    }

    public void zoomOut() {
        if (CELL_SIZE >= 80)
            CELL_SIZE -= 5;
        showMap(showingMap, 100);
    }

    public void goDown() {
        if (currentY > 1)
            currentY--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }

    public void goUp() {
        if (currentY < mapController.getMap().length - 8)
            currentY++;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }

    public void refreshMap() {

    }

    public void selectUnit() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initExtra();
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.S)
                    save();
                else if(keyEvent.getCode() == KeyCode.Q)
                    back();
                else if(keyEvent.getCode() == KeyCode.N)
                    System.out.println("next turn!");//for going to next turn
                else if(keyEvent.getCode() == KeyCode.G)
                    gotoXY();
                else if (keyEvent.getCode() == KeyCode.L)
                    goLeft();
                else if (keyEvent.getCode() == KeyCode.R)
                    goRight();
                else if (keyEvent.getCode() == KeyCode.U)
                    goUp();
                else if(keyEvent.getCode() == KeyCode.D)
                    goDown();
            }
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cellPane.setStyle("-fx-background-color: black;");
        mapController.initializeMap(400, true);
        showMap(mapController.showMapGui(currentX, currentY), 75);

        cellPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println(keyEvent.getCharacter());
            }
        });
        cellPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isStartDrag = true;
                startDragX = mouseEvent.getX();
                startDragY = mouseEvent.getY();
                int xShow = (int) (mouseEvent.getX() / CELL_SIZE);
                int yShow = (int) (mouseEvent.getY() / CELL_SIZE);
                System.out.println(xShow);
                System.out.println(yShow);
            }
        });

        cellPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isStartDrag) {
                    selectCell(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    return;
                }
                selectCell(startDragX, startDragY, mouseEvent.getX(), mouseEvent.getY());
                System.out.println(mouseEvent.getX() + " jjj");
                System.out.println(mouseEvent.getY() + " jjj");
                isStartDrag = false;
            }
        });
    }

    public void gotoXY() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("go to Cell");
        alert.setHeaderText(null);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getChildren().remove(0);
        TextInputDialog textField = new TextInputDialog();
        TextField textField1 = new TextField();
        textField1.setLayoutX(15);
        textField1.setLayoutY(15);
        textField1.setVisible(true);
        textField1.setPromptText("ali");
        alert.getDialogPane().getChildren().add(textField1);
        alert.showAndWait();
    }


    public void initExtra() {
        objectBox.getChildren().remove(0, objectBox.getChildren().size());
        ImageView imageView = new ImageView();
        Image image;
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);//modify
        for (int i = 0; i < Extras.values().length; i++) {
            File file = new File(Extras.values()[i].getImagePath());
            image = new Image("file:"+"/home/morteza/Desktop/program_ap/FinalProject/StrongHold2/src/main/java/view/shrub.jpeg");
            imageView = new ImageView(image);
            objectBox.getChildren().add(imageView);
        }
        System.out.println(objectBox.getChildren());
    }
}
