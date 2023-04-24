package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.TroopTrainerDetails;
import model.human.Enums.WorkerDetails;
import model.human.Worker;

import java.util.ArrayList;

public class TroopTrainer extends Building {
    private TroopTrainerDetails troopTrainerDetails;
    private ArrayList<Worker> queue = new ArrayList<>();

    public TroopTrainer(Government government, Cell cell, TroopTrainerDetails troopTrainerDetails) {
        super(government, troopTrainerDetails.getBuildingsDetails(), cell);
        this.troopTrainerDetails = troopTrainerDetails;
    }

    public BuildingsDetails getBuildingsDetails() {
        return troopTrainerDetails.getBuildingsDetails();
    }


    public int getRate() {
        return troopTrainerDetails.getRate();
    }

    public TroopTrainerDetails getTroopTrainerDetails() {
        return troopTrainerDetails;
    }

    public void addToQueue(WorkerDetails workerDetails, int count, Cell cell) {
        for (int i = 0; i < count; i++) {
            queue.add(new Worker(workerDetails, cell, cell));
        }
    }
}
