import view.StartMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StartMenu startMenu = new StartMenu();
        Scanner scanner = new Scanner(System.in);
        startMenu.run(scanner);
    }
}
