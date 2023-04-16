package model.human;

import model.human.Enums.MercenaryDetails;
import model.human.Enums.WorkerDetails;

public class Mercenary extends Worker {
    MercenaryDetails mercenaryDetails;

    public Mercenary(WorkerDetails workerDetails, MercenaryDetails mercenaryDetails) {
        super(workerDetails);
        this.mercenaryDetails = mercenaryDetails;
    }

    public WorkerDetails getWorkerDetails() {
        return mercenaryDetails.getWorkerDetails();
    }

    public int getCost() {
        return mercenaryDetails.getCost();
    }
}
