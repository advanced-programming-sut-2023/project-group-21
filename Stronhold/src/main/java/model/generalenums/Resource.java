package model.generalenums;

import model.building.Enums.BuildingsDetails;
import model.building.Storage;

public enum Resource {
    MEAT("meat", "granary",5),
    APPLE("apple", "granary",3),
    CHEESE("cheese", "granary",3),
    BREAD("bread", "granary",2),
    WHEAT("wheat", "stockpile",2),
    FLOUR("flour", "stockpile",3),
    HOPS("hops", "stockpile",4),
    ALE("ale", "stockpile",4),
    STONE("stone", "stockpile",4),
    IRON("iron", "stockpile",4),
    WOOD("wood", "stockpile",6),
    PITCH("pitch", "stockpile",8),
    GOLD("gold", null,1),
    BOW("bow", "armoury",9),
    CROSSBOW("crossbow", "armoury",12),
    SPEAR("spear", "armoury",6),
    PIKE("pike", "armoury",5),
    MACE("mace", "armoury",4),
    SWORD("sword", "armoury",2),
    LEATHER_ARMOR("leather armor", "armoury",3),
    METAL_ARMOR("metal armor", "armoury",2),
    HORSE("horse", "stable",15);
    private final String name, keeper;
    private final int costBuy;
    private final int costSell;

    Resource(String name, String keeper,int cost) {
        this.name = name;
        this.keeper = keeper;
        this.costBuy = cost;
        this.costSell = costBuy - 1;
    }

    public String getName() {
        return name;
    }

    public BuildingsDetails getResourceKeeper() {
        return BuildingsDetails.getBuildingDetailsByName(keeper);
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
