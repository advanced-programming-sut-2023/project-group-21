package model.human;

import model.Cell;
import model.Government;
import model.human.Enums.WorkerDetails;

public class Worker extends Person {
    private String state;
    private final WorkerDetails workerDetails;
    private Cell position;
    private Cell destination;
    private Cell firstCell,secondCell;
    private boolean isPatrolOn = false, isOnTower = false;
    private Worker enemy;
    private boolean hasLadder = false;
    public boolean getPatrol(){
        return this.isPatrolOn;
    }
    public Worker(WorkerDetails workerDetails, Government government, Cell position, Cell destination) {
        super(government);
        this.workerDetails = workerDetails;
        this.position = position;
        this.destination = destination;
        state = "standing";
        if(workerDetails == WorkerDetails.LADDERMAN)
            hasLadder = true;
        this.hitPoint = workerDetails.getMaxHitPoint();
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
        isPatrolOn = false;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPatrolMovement(Cell firstCell, Cell secondCell) {
        this.firstCell = firstCell;
        this.secondCell = secondCell;
        this.destination = secondCell;
        isPatrolOn = true;
    }

    public void doPetrolCheck(){//use before the move !
        if(!isPatrolOn)
            return;
        if(position == secondCell)
            destination = firstCell;
        if(position == firstCell)
            destination = secondCell;//check state
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

    public boolean isOnTower() {
        return isOnTower;
    }

    public void setOnTower(boolean onTower) {
        isOnTower = onTower;
    }

    public void transport(Cell cell){
        position.getPeople().remove(this);
        this.position = cell;
         if(!cell.getPeople().contains(this))
             cell.addPeople(this);
    }

    @Override
    public String toString() {
        return "name: "+workerDetails.getName()+" government: "+government.getLord().getUserName();
    }
}
