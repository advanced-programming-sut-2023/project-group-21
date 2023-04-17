package model.human;

import model.human.Enums.MercenaryDetails;
import model.human.Enums.WorkerDetails;

public class Assassin extends Mercenary {
    private boolean isHidden = false;

    public Assassin(WorkerDetails workerDetails, MercenaryDetails mercenaryDetails) {
        super(workerDetails, mercenaryDetails);
    }

    public void expose(boolean hidden) {
        isHidden = hidden;
    }
}
