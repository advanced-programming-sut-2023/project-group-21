package view;

import controller.FileController;
import model.User;
import view.PlayerMenu;
import view.StartMenu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import model.User;
import view.PlayerMenu;
import view.StartMenu;
import javafx.stage.Stage;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileController.start();
        StartingMenu.main(args);
    }
}