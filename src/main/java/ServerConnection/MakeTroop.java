package ServerConnection;

import model.human.Enums.WorkerDetails;

import java.io.Serializable;

public class MakeTroop implements Serializable {
    private final WorkerDetails workerDetails;
    private final int xPosition, yPosition;
    private String username;

    public MakeTroop(WorkerDetails workerDetails, int xPosition, int yPosition) {
        this.workerDetails = workerDetails;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public WorkerDetails getWorkerDetails() {
        return workerDetails;
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
