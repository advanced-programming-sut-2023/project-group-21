package model.generalenums;

import model.building.Enums.StorageDetails;
import model.building.Storage;

public enum Resource {
    MEAT("meat", StorageDetails.GRANARY),
    APPLE("apple", StorageDetails.GRANARY),
    CHEESE("cheese", StorageDetails.GRANARY),
    BREAD("bread", StorageDetails.GRANARY),
    WHEAT("wheat", StorageDetails.STOCKPILE),
    FLOUR("flour", StorageDetails.STOCKPILE),
    HOPS("hops", StorageDetails.STOCKPILE),
    ALE("ale", StorageDetails.STOCKPILE),
    STONE("stone", StorageDetails.STOCKPILE),
    IRON("iron", StorageDetails.STOCKPILE),
    WOOD("wood", StorageDetails.STOCKPILE),
    PITCH("pitch", StorageDetails.STOCKPILE),
    GOLD("gold", null),
    BOW("bow", StorageDetails.ARMOURY),
    CROSSBOW("crossbow", StorageDetails.ARMOURY),
    SPEAR("spear", StorageDetails.ARMOURY),
    PIKE("pike", StorageDetails.ARMOURY),
    MACE("mace", StorageDetails.ARMOURY),
    SWORD("sword", StorageDetails.ARMOURY),
    LEATHER_ARMOR("leather armor", StorageDetails.ARMOURY),
    METAL_ARMOR("metal armor", StorageDetails.ARMOURY),
    HORSE("horse", StorageDetails.STABLE);
    String name;
    StorageDetails resourceKeeper;

    Resource(String name, StorageDetails resourceKeeper) {
        this.name = name;
        this.resourceKeeper = resourceKeeper;
    }
}
