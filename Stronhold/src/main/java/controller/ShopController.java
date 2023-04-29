package controller;

import model.Government;
import model.generalenums.Resource;
import view.message.ShopMessage;

import java.util.Map;


public class ShopController {
    Government currentGovernment;

    public ShopController(Government government){
        this.currentGovernment = government;
    }


    public ShopMessage buy(String name,int amount){
        Resource resource = Resource.getExtrasByName(name);
        if(resource == null)
            return ShopMessage.NOT_A_RESOURCE;
        if(amount<=0)
            return ShopMessage.INVALID_NUMBER;


        return null;
    }
    public ShopMessage sell(String name,int amount){
        Resource resource = Resource.getExtrasByName(name);
        if(resource == null)
            return ShopMessage.NOT_A_RESOURCE;
        if(amount<=0)
            return ShopMessage.INVALID_NUMBER;
        return null;
    }

    public String showDetails(){
        String temp = "";
        Map<Resource,Integer> resources=currentGovernment.getResources();
        Resource resource;
        Integer count;
        String name;
        int price;
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            resource= entry.getKey();
            count= entry.getValue();
            name = resource.getName();
            price = resource.getCostBuy();
            temp += ("recourse name:"+name+" price(buy): "+price+" price(sell): "+(price - 1)+" inventory: "+count);
        }
        return temp;
    }
}
