package model.generalenums;

import model.building.Enums.BuildingsDetails;
import model.building.Storage;

public enum Resource {
    MEAT("meat", BuildingsDetails.GRANARY,5),
    APPLE("apple", BuildingsDetails.GRANARY,3),
    CHEESE("cheese", BuildingsDetails.GRANARY,3),
    BREAD("bread", BuildingsDetails.GRANARY,2),
    WHEAT("wheat", BuildingsDetails.STOCKPILE,2),
    FLOUR("flour", BuildingsDetails.STOCKPILE,3),
    HOPS("hops", BuildingsDetails.STOCKPILE,4),
    ALE("ale", BuildingsDetails.STOCKPILE,4),
    STONE("stone", BuildingsDetails.STOCKPILE,4),
    IRON("iron", BuildingsDetails.STOCKPILE,4),
    WOOD("wood", BuildingsDetails.STOCKPILE,6),
    PITCH("pitch", BuildingsDetails.STOCKPILE,8),
    GOLD("gold", null,1),
    BOW("bow", BuildingsDetails.ARMOURY,9),
    CROSSBOW("crossbow", BuildingsDetails.ARMOURY,12),
    SPEAR("spear", BuildingsDetails.ARMOURY,6),
    PIKE("pike", BuildingsDetails.ARMOURY,5),
    MACE("mace", BuildingsDetails.ARMOURY,4),
    SWORD("sword", BuildingsDetails.ARMOURY,2),
    LEATHER_ARMOR("leather armor", BuildingsDetails.ARMOURY,3),
    METAL_ARMOR("metal armor", BuildingsDetails.ARMOURY,2),
    HORSE("horse", null,15);
    private final String name;
    private final int costBuy;
    private final int costSell;
    private final BuildingsDetails resourceKeeper;

    Resource(String name, BuildingsDetails resourceKeeper,int cost) {
        this.name = name;
        this.resourceKeeper = resourceKeeper;
        this.costBuy = cost;
        this.costSell = costBuy - 1;
    }

    public String getName() {
        return name;
    }

    public BuildingsDetails getResourceKeeper() {
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
