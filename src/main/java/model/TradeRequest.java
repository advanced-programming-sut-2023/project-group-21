package model;

import ServerConnection.User;
import controller.TradingController;
import model.generalenums.Resource;
import view.message.TradeMenuMessage;

public class TradeRequest {
    public boolean isDone;
    private boolean isBuyRequest;
    private Government sender;
    private Government receiver;
    private int amount;
    private int price;
    private Resource resource;
    public boolean accepted;
    private String status;

    public TradeRequest(boolean isBuyRequest, Government sender, Government receiver, int amount, int price, Resource resource) {
        this.isBuyRequest = isBuyRequest;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.price = price;
        this.resource = resource;
    }

    public String  getStatus(){
        if(!isDone())
            return "unchecked";
        else if(isAccepted())
            return "accepted";
        else
            return "rejected";
    }

    public TradeMenuMessage accept() {
        accepted=true;
        isDone = true;
        TradingController tradingController=new TradingController();
        TradeMenuMessage message= tradingController.accept(this);
        return message;
    }

    public TradeMenuMessage reject(){
        TradingController tradingController=new TradingController();
        TradeMenuMessage message= tradingController.reject(this);
        return message;
    }

    public boolean isAccepted(){ return accepted;}
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

    public static class Messenger {
        private final User user;
        public Messenger(User user){
            this.user = user;
        }
    }
}
