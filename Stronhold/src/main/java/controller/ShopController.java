package controller;

import model.Government;
import model.generalenums.Resource;
import view.message.ShopMessage;

import java.util.Map;


public class ShopController {
    Government currentGovernment;
    Map<Resource, Integer> resources;

    public ShopController(Government government) {
        this.currentGovernment = government;
        resources = currentGovernment.getResources();
    }


    public ShopMessage buy(String name, int amount) {
        Resource resource = Resource.getResourceByName(name);
        if (resource == null || resource == Resource.GOLD)
            return ShopMessage.NOT_A_RESOURCE;
        if (amount <= 0)
            return ShopMessage.INVALID_NUMBER;
        if (amount >= currentGovernment.leftStorage(resource))
            return ShopMessage.NO_CAPACITY;
        if ((amount * resource.getCostBuy()) > resources.get(Resource.GOLD))
            return ShopMessage.NOT_ENOUGH_MONEY;
        if (resources.containsKey(resource))
            resources.replace(resource, resources.get(resource) + amount);
        else
            resources.put(resource, amount);
        int coinChange = amount * resource.getCostBuy();
        resources.replace(Resource.GOLD, resources.get(Resource.GOLD) - coinChange);
        return ShopMessage.SUCCESS;
    }

    public ShopMessage sell(String name, int amount) {
        Resource resource = Resource.getResourceByName(name);
        if (resource == null || resource == Resource.GOLD)
            return ShopMessage.NOT_A_RESOURCE;
        if (amount <= 0)
            return ShopMessage.INVALID_NUMBER;
        if (amount > (resources.get(resource)))
            return ShopMessage.NOT_ENOUGH;
        resources.replace(resource, resources.get(resource) - amount);
        if (resources.get(resource) == 0)
            resources.remove(resource);
        int coinChange = amount * resource.getCostSell();
        resources.replace(Resource.GOLD, resources.get(Resource.GOLD) + coinChange);
        // it needs repair
        return ShopMessage.SUCCESS;
    }

    public String showDetails() {
        String temp = "";
        Resource resource;
        Integer count;
        String name;
        int price;
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            resource = entry.getKey();
            count = entry.getValue();
            name = resource.getName();
            price = resource.getCostBuy();
            temp += ("recourse name:" + name + " price(buy): " + price + " price(sell): " + (price - 1)
                    + " inventory: " + count + "\n");
        }
        return temp;
    }
}
