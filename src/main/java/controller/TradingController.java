package controller;

import model.Game;
import model.TradeRequest;
import model.generalenums.Resource;
import view.TradingMenu;
import view.message.TradeMenuMessage;

public class TradingController {

    public TradeMenuMessage checkDonate(TradeRequest tradeRequest){
        if(tradeRequest.getSender().getResourceAmount(tradeRequest.getResource())< tradeRequest.getAmount())
            return TradeMenuMessage.NOT_EXISTENCE_IN_SELLER;
        // when accepted
        //        int coinChange = tradeRequest.getAmount() * tradeRequest.getPrice();
//        tradeRequest.getSender().getResources().replace(Resource.GOLD, tradeRequest.getSender().getResourceAmount(Resource.GOLD) + coinChange);
        tradeRequest.getSender().getResources().replace(tradeRequest.getResource(),
                    tradeRequest.getSender().getResourceAmount(tradeRequest.getResource())- tradeRequest.getAmount());
        Game.addTradeRequest(tradeRequest);
        return TradeMenuMessage.SUCCESS;
    }

    public TradeMenuMessage checkRequest(TradeRequest tradeRequest){
        int coinChange = tradeRequest.getAmount() * tradeRequest.getPrice();
        if(tradeRequest.getSender().getGold()<coinChange)
            return TradeMenuMessage.NOT_ENOUGH_MONEY;
        if(tradeRequest.getSender().leftStorage(tradeRequest.getResource())<tradeRequest.getAmount())
            return TradeMenuMessage.CAPACITY;
        tradeRequest.getSender().reduceResources(Resource.GOLD,coinChange);
        Game.addTradeRequest(tradeRequest);
        return TradeMenuMessage.SUCCESS;
    }

    public TradeMenuMessage accept(TradeRequest tradeRequest) {
        int coinChange = tradeRequest.getAmount() * tradeRequest.getPrice();
        if(tradeRequest.isBuyRequest()){
            if(tradeRequest.getReceiver().getResourceAmount(tradeRequest.getResource())<tradeRequest.getAmount())
                return TradeMenuMessage.NOT_EXISTENCE_IN_SELLER;
            tradeRequest.getSender().addToResource(tradeRequest.getResource(),tradeRequest.getAmount());
            tradeRequest.getReceiver().reduceResources(tradeRequest.getResource(),tradeRequest.getAmount());
            tradeRequest.getReceiver().addToResource(Resource.GOLD,coinChange);
            tradeRequest.accepted=true;
            tradeRequest.isDone=true;
            return TradeMenuMessage.SUCCESS;
        }else {
            if(tradeRequest.getReceiver().getGold()<coinChange)
                return TradeMenuMessage.NOT_ENOUGH_MONEY;
            tradeRequest.getSender().addToResource(Resource.GOLD,coinChange);
            tradeRequest.getReceiver().addToResource(tradeRequest.getResource(),tradeRequest.getAmount());
            tradeRequest.getReceiver().reduceResources(Resource.GOLD,coinChange);
            //consider storage
            tradeRequest.accepted=true;
            tradeRequest.isDone=true;
            return TradeMenuMessage.SUCCESS;
        }
    }

    public TradeMenuMessage reject(TradeRequest tradeRequest) {
        int coinChange = tradeRequest.getAmount() * tradeRequest.getPrice();
        if(tradeRequest.isBuyRequest()){
            tradeRequest.getSender().addToResource(Resource.GOLD,coinChange);
            tradeRequest.accepted=false;
            tradeRequest.isDone=true;
            return TradeMenuMessage.SUCCESS;
        }else {
            tradeRequest.getSender().addToResource(tradeRequest.getResource(),tradeRequest.getAmount());
            tradeRequest.accepted=false;
            tradeRequest.isDone=true;
            return TradeMenuMessage.SUCCESS;
        }
    }



//    public TradeMenuMessage trade(String resourceName, int amount, int price, String message,Government government) {
//        Government currentGovernment=government;
//        Resource resource=Resource.getResourceByName(resourceName);
//        if((resource==null)||resource==Resource.GOLD)
//            return TradeMenuMessage.INVALID_RESOURCE_TYPE;
//        if(price<0)
//            return TradeMenuMessage.INVALID_PRICE;
//        if(amount<=0)
//            return TradeMenuMessage.INVALID_AMOUNT;
//        if(currentGovernment.getResourceAmount(resource)==-1)
//            return TradeMenuMessage.NOT_EXISTENCE_IN_SELLER;
//        if(currentGovernment.getResourceAmount(resource)<amount)
//            return TradeMenuMessage.NOT_ENOUGH_AMOUNT;
//        Trade createdTrade=new Trade(currentGovernment,resource,price,amount,message);
//        currentGovernment.removeResource(resource,amount);
//        currentGovernment.addTrade(createdTrade);
//        Game.addTrade(createdTrade);
//        return TradeMenuMessage.SUCCESS;
//    }
//
//    public static TradeMenuMessage accept(int id, String message, Government government) {
//        if(Game.getTradeById(id)==null) {
//            return TradeMenuMessage.INVALID_ID;
//        }
//        Trade trade=Game.getTradeById(id);
//        Government buyer=government;
//        Government seller=Game.getTradeById(id).getSeller();
//        if(seller.getGold()<trade.getCost()* trade.getAmount())
//            return TradeMenuMessage.CAN_NOT_AFFORD;
//        if(seller.leftStorage(trade.getResource())< trade.getAmount())
//            return TradeMenuMessage.CAPACITY;
//        buyer.buySuccessfully(Game.getTradeById(id));
//        seller.sellSuccessfully(Game.getTradeById(id));
//        Game.getTradeById(id).isDone();
//        Game.removeTrade(Game.getTradeById(id));
//        return TradeMenuMessage.SUCCESS;
//    }
}
