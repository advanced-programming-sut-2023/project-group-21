package model.human;

import model.Cell;
import model.Government;
import model.human.Enums.WorkerDetails;

public class Worker extends Person {
    private String state;
    private final WorkerDetails workerDetails;
    private Cell position;
    private Cell destination;
    private boolean isPatrolOn = false;
    private int patrolX1, patrolY1, patrolX2, patrolY2;
    private Worker enemy;
    private boolean hasLadder = false;

    public Worker(WorkerDetails workerDetails, Government government, Cell position, Cell destination) {
        super(government);
        this.workerDetails = workerDetails;
        this.position = position;
        this.destination = destination;
        state = "standing";
        if(workerDetails == WorkerDetails.LADDERMAN)
            hasLadder = true;
    }

    public String getName() {
        return workerDetails.getName();
    }

    public int getMaxHitPoint() {
        return workerDetails.getMaxHitPoint();
    }

    public int getDamage() {
        return workerDetails.getDamage();
    }

    public int getDefense() {
        return workerDetails.getDefense();
    }

    public int getSpeed() {
        return workerDetails.getSpeed();
    }

    public int getRange() {
        return workerDetails.getRange();
    }

    public Cell getPosition() {
        return position;
    }

    public Cell getDestination() {
        return destination;
    }

    public void setDestination(Cell destination) {
        this.destination = destination;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPatrolMovement(int x1, int y1, int x2, int y2) {
        patrolX1 = x1;
        patrolX2 = x2;
        patrolY1 = y1;
        patrolY2 = y2;
        isPatrolOn = true;
    }

    public void setPatrolOn(boolean patrolOn) {
        isPatrolOn = patrolOn;
    }

    public void setEnemy(Worker enemy) {
        this.enemy = enemy;
    }

    public Worker getEnemy() {
        return enemy;
    }

    public String getState() {
        return state;
    }

    @Override
    public void delete() {
        super.delete();
        position.deletePerson(this);
    }

    public void putLadder(){
        this.delete();
    }

    public void getDamaged(int amount){
        hitPoint-=amount;
    }

    public WorkerDetails getWorkerDetails(){
        return workerDetails;
    }
}
