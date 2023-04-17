import view.StartMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StartMenu startMenu=new StartMenu();
        startMenu.run(scanner);
    }
}