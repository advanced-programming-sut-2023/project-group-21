package controller;

import model.building.Enums.BuildingsDetails;
import model.human.Worker;

public class Command {
    Worker worker;
    BuildingsDetails buildingsDetails;
    String name;
    int x, y;

    public Command(String name, BuildingsDetails buildingsDetails, int x, int y) {
        this.name = name;
        this.buildingsDetails = buildingsDetails;
        this.x = x;
        this.y = y;
    }
}
