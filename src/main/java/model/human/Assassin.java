package model.human;

import ServerConnection.Cell;
import model.Government;
import model.human.Enums.WorkerDetails;

public class Assassin extends Worker {
    private boolean isHidden = true;

    public Assassin(WorkerDetails workerDetails, Government government, Cell position, Cell destination) {
        super(workerDetails, government, position, destination);
    }

    public void expose() {
        isHidden = false;
    }

    public boolean isHidden() {
        return isHidden;
    }
}
