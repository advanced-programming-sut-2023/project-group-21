package view;

import controller.FileController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileController.start();
        StartingMenu.main(args);
    }
}