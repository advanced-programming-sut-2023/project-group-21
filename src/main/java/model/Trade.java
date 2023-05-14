package model;

import model.generalenums.Resource;

public class Trade {
    private Government seller;
    private Resource resource;
    private int cost, amount, id;
    private static int idFirst = 1000;
    private String comment;
    private String buyerMessage;
    private Boolean finished;
    public Trade(Government seller, Resource resource, int cost, int amount, String comment){
        this.seller = seller;
        this.resource = resource;
        this.cost = cost;
        this.amount = amount;
        this.comment = comment;
        this.id = idFirst;
        this.finished=false;
        idFirst++;
    }
    public Government getSeller() {
        return seller;
    }

    public Resource getResource() {
        return resource;
    }

    public int getCost() {
        return cost;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String toString(){
        return "id: "+id+" government: "+seller.getLord().getUserName()+" resource: "+ resource.name() + " price: " + cost + " comment: " + comment;
    }

    public String showTradeOptions() {
        return null;
    }

    public void isDone() {
        finished=true;
    }
    public void addBuyerMessage(String message){
        buyerMessage=message;
    }
}