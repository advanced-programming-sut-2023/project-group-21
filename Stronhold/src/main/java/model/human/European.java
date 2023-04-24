package model.human;

import model.human.Enums.EuropeanSoldiersDetails;
import model.human.Enums.WorkerDetails;

public class European extends Worker {
    EuropeanSoldiersDetails europeanSoldiersDetails;

    public European(WorkerDetails workerDetails, EuropeanSoldiersDetails europeanSoldiersDetails) {
        super(workerDetails);
        this.europeanSoldiersDetails = europeanSoldiersDetails;
    }
}
