package ServerConnection;

import model.Government;
import model.building.Enums.BuildingsDetails;

import java.io.Serializable;

public class DropBuilding implements Serializable {
    private final BuildingsDetails buildingsDetails;
    private final int xPosition, yPosition;
    private String username;

    public DropBuilding(BuildingsDetails buildingsDetails, int xPosition, int yPosition) {
        this.buildingsDetails = buildingsDetails;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
