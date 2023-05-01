package controller;

import model.Game;
import model.Government;
import model.generalenums.Resource;
import model.Trade;
import view.message.TradeMenuMessage;

public class TradeController {
    public TradeMenuMessage trade(String resourceName, int amount, int price, String message,Government government) {
        Government currentGovernment=government;
        Resource resource=Resource.getResourceByName(resourceName);
        if((resource==null)||resource==Resource.GOLD)
            return TradeMenuMessage.INVALID_RESOURCE_TYPE;
        if(price<0)
            return TradeMenuMessage.INVALID_PRICE;
        if(amount<=0)
            return TradeMenuMessage.INVALID_AMOUNT;
        if(currentGovernment.getResourceAmount(resource)==-1)
            return TradeMenuMessage.NOT_EXISTENCE_IN_SELLER;
        if(currentGovernment.getResourceAmount(resource)<amount)
            return TradeMenuMessage.NOT_ENOUGH_AMOUNT;
        Trade createdTrade=new Trade(currentGovernment,resource,price,amount,message);
        currentGovernment.removeResource(resource,amount);
        currentGovernment.addTrade(createdTrade);
        Game.addTrade(createdTrade);
        return TradeMenuMessage.SUCCESS;
    }

    public static TradeMenuMessage accept(int id, String message, Government government) {
        if(Game.getTradeById(id)==null) {
            return TradeMenuMessage.INVALID_ID;
        }
        Trade trade=Game.getTradeById(id);
        Government buyer=government;
        Government seller=Game.getTradeById(id).getSeller();
        if(seller.getGold()<trade.getCost()* trade.getAmount())
            return TradeMenuMessage.CAN_NOT_AFFORD;
        if(seller.calculateLeftStorageCapacity(trade.getResource())< trade.getAmount())
            return TradeMenuMessage.CAPACITY;
        buyer.buySuccessfully(Game.getTradeById(id));
        seller.sellSuccessfully(Game.getTradeById(id));
        Game.getTradeById(id).isDone();
        Game.removeTrade(Game.getTradeById(id));
        return TradeMenuMessage.SUCCESS;
    }
}
