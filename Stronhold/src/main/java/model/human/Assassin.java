package model.human;

import model.Cell;
import model.Government;
import model.human.Enums.WorkerDetails;

public class Assassin extends Worker {
    private boolean isHidden = false;

    public Assassin(WorkerDetails workerDetails, Government government, Cell position, Cell destination) {
        super(workerDetails, government, position, destination);
    }

    public void expose(boolean hidden) {
        isHidden = hidden;
    }
}
