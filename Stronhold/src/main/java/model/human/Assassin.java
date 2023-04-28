package model.human;

import model.Cell;
import model.human.Enums.WorkerDetails;

public class Assassin extends Worker {
    private boolean isHidden = false;

    public Assassin(WorkerDetails workerDetails, Cell position, Cell destination) {
        super(workerDetails, position, destination);
    }

    public void expose(boolean hidden) {
        isHidden = hidden;
    }
}
