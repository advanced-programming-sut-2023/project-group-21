package model.human;

import model.Cell;
import model.human.Enums.EuropeanSoldiersDetails;
import model.human.Enums.WorkerDetails;

public class European extends Worker {
    EuropeanSoldiersDetails europeanSoldiersDetails;

    public European(WorkerDetails workerDetails, Cell position, Cell destination) {
        super(workerDetails, position, destination);
        this.europeanSoldiersDetails = EuropeanSoldiersDetails.getDetailsByWorkerDetails(workerDetails);
    }
}
