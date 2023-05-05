package model.generalenums;

import model.building.Enums.StorageDetails;
import model.building.Storage;

public enum Resource {
    MEAT("meat", StorageDetails.GRANARY,5),
    APPLE("apple", StorageDetails.GRANARY,3),
    CHEESE("cheese", StorageDetails.GRANARY,3),
    BREAD("bread", StorageDetails.GRANARY,2),
    WHEAT("wheat", StorageDetails.STOCKPILE,2),
    FLOUR("flour", StorageDetails.STOCKPILE,3),
    HOPS("hops", StorageDetails.STOCKPILE,4),
    ALE("ale", StorageDetails.STOCKPILE,4),
    STONE("stone", StorageDetails.STOCKPILE,4),
    IRON("iron", StorageDetails.STOCKPILE,4),
    WOOD("wood", StorageDetails.STOCKPILE,6),
    PITCH("pitch", StorageDetails.STOCKPILE,8),
    GOLD("gold", null,1),
    BOW("bow", StorageDetails.ARMOURY,9),
    CROSSBOW("crossbow", StorageDetails.ARMOURY,12),
    SPEAR("spear", StorageDetails.ARMOURY,6),
    PIKE("pike", StorageDetails.ARMOURY,5),
    MACE("mace", StorageDetails.ARMOURY,4),
    SWORD("sword", StorageDetails.ARMOURY,2),
    LEATHER_ARMOR("leather armor", StorageDetails.ARMOURY,3),
    METAL_ARMOR("metal armor", StorageDetails.ARMOURY,2),
    HORSE("horse", null,15);
    private final String name;
    private final int costBuy;
    private final int costSell;
    private final StorageDetails resourceKeeper;

    Resource(String name, StorageDetails resourceKeeper,int cost) {
        this.name = name;
        this.resourceKeeper = resourceKeeper;
        this.costBuy = cost;
        this.costSell = costBuy - 1;
    }

    public String getName() {
        return name;
    }

    public StorageDetails getResourceKeeper() {
        return resourceKeeper;
    }

    public int getCostBuy() {
        return costBuy;
    }
    public static Resource getResourceByName(String name) {
        for (Resource resource: Resource.values()) {
            if (resource.getName().equals(name)) return resource;
        }
        return null;
    }

    public int getCostSell(){
        return costSell;
    }
}
