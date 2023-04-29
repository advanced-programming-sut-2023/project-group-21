import controller.FileController;
import model.User;
import view.PlayerMenu;
import view.StartMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileController.start();
        User user = FileController.getStayedUser();
        if (user != null) {
            PlayerMenu playerMenu = new PlayerMenu(user);
            playerMenu.run(scanner);
        }
        StartMenu startMenu = new StartMenu();
        startMenu.run(scanner);

        FileController.finish();
    }
}