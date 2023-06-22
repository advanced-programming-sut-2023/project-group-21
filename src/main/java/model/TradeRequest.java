package model;

import model.generalenums.Resource;

public class TradeRequest {
    private boolean isDone;
    private boolean isBuyRequest;
    private Government sender;
    private Government receiver;
    private int amount;
    private int price;
    private Resource resource;

    public TradeRequest(boolean isBuyRequest, Government sender, Government receiver, int amount, int price, Resource resource) {
        this.isBuyRequest = isBuyRequest;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.price = price;
        this.resource = resource;
    }

    public void finish() {
        isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean isBuyRequest() {
        return isBuyRequest;
    }

    public Government getSender() {
        return sender;
    }

    public Government getReceiver() {
        return receiver;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public Resource getResource() {
        return resource;
    }
}
