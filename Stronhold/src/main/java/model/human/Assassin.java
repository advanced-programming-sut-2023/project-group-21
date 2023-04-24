package model.human;

import model.human.Enums.WorkerDetails;

public class Assassin extends Worker {
    private boolean isHidden = false;

    public Assassin(WorkerDetails workerDetails) {
        super(workerDetails);
    }

    public void expose(boolean hidden) {
        isHidden = hidden;
    }
}
