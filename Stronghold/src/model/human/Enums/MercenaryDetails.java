package model.human.Enums;

public enum MercenaryDetails {
    MERCENARY_DETAILS(WorkerDetails.WORKER_DETAILS, 0);
    WorkerDetails workerDetails;
    int cost;
    MercenaryDetails(WorkerDetails workerDetails, int cost){}

    public WorkerDetails getWorkerDetails() {
        return workerDetails;
    }

    public int getCost() {
        return cost;
    }
}
