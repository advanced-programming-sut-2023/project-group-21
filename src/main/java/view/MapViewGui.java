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
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapViewGui extends Application implements Initializable {
    private double startDragX = 0, startDragY = 0;
    private int CELL_SIZE = 75;
    @FXML
    private Pane cellPane;
    private Cell[][] showingMap;
    private int currentX = 5, currentY = 5;
    public MapController mapController = new MapController();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent parent1 = FXMLLoader.load(MapController.class.getResource("/FXMLs/Map.fxml"));
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
        for (int i1 = 0; i1 < myMap.length && i1 <= 600 / CELL_SIZE; i1++)
            for (int i2 = 0; i2 < myMap.length && i2 <= 600 / CELL_SIZE; i2++) {
                Label label = myMap[i1][i2].toLabel(CELL_SIZE * i1, CELL_SIZE * i2, CELL_SIZE);
                int finalI = i1;
                int finalI1 = i2;
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectCell(finalI, finalI1);
                    }
                });
                cellPane.getChildren().add(label);
            }
    }

    private void selectCell(int x, int y) {
        System.out.println(x + "    " + y);
    }

    private void moveMap(int x, int y) {
        System.out.println("(" + x + "," + y + ")");
    }

    public void save() {

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
        cellPane.setStyle("-fx-background-color: black;");
        mapController.initializeMap(400, true);
        showMap(mapController.showMapGui(currentX, currentY), 75);
        cellPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startDragX = mouseEvent.getX();
                int xShow = (int) (mouseEvent.getX() / CELL_SIZE);
                int yShow = (int) (mouseEvent.getY() / CELL_SIZE);
                System.out.println(xShow);
                System.out.println(yShow);
            }
        });

        cellPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(mouseEvent);
                System.out.println(mouseEvent.getSceneX() + " jjj");
                System.out.println(mouseEvent.getSceneY() + " jjj");
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
//        dialogPane.getChildren().add(textField);
        alert.getDialogPane().getChildren().add(textField1);
        System.out.println(dialogPane.getChildren());
        alert.showAndWait();
    }
}
