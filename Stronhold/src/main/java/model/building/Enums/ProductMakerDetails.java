package model.building.Enums;

import model.generalenums.Resource;

import java.util.Arrays;
import java.util.List;

public enum ProductMakerDetails {
    INN(BuildingsDetails.INN, Arrays.asList(Resource.ALE), null, 2),
    MILL(BuildingsDetails.MILL, Arrays.asList(Resource.WHEAT), Arrays.asList(Resource.FLOUR), 3),
    IRON_MINE(BuildingsDetails.IRON_MINE, null, Arrays.asList(Resource.IRON), 3),
    WOOD_CUTTER(BuildingsDetails.WOOD_CUTTER, null, Arrays.asList(Resource.WOOD), 4),
    ARMOURER(BuildingsDetails.ARMOURER, Arrays.asList(Resource.IRON), Arrays.asList(Resource.METAL_ARMOR), 2),
    BLACKSMITH(BuildingsDetails.BLACKSMITH, Arrays.asList(Resource.IRON), Arrays.asList(Resource.SWORD, Resource.MACE), 2),
    FLETCHER(BuildingsDetails.FLETCHER, Arrays.asList(Resource.WOOD), Arrays.asList(Resource.BOW, Resource.CROSSBOW), 2),
    POLETURNER(BuildingsDetails.POLETURNER, Arrays.asList(Resource.WOOD), Arrays.asList(Resource.SPEAR, Resource.PIKE), 2),
    PITCH_RIG(BuildingsDetails.PITCH_RIG, null, Arrays.asList(Resource.PITCH), 3),
    APPLE_ORCHARD(BuildingsDetails.APPLE_ORCHARD, null, Arrays.asList(Resource.APPLE), 4),
    DAIRY_FARM(BuildingsDetails.DAIRY_FARM, null, Arrays.asList(Resource.LEATHER_ARMOR, Resource.CHEESE), 3),
    HOPS_FARM(BuildingsDetails.HOPS_FARM, null, Arrays.asList(Resource.HOPS), 3),
    HUNTERS_HUT(BuildingsDetails.HUNTERS_HUT, null, Arrays.asList(Resource.MEAT), 4),
    WHEAT_FARM(BuildingsDetails.WHEAT_FARM, null, Arrays.asList(Resource.WHEAT), 4),
    BAKERY(BuildingsDetails.BAKERY, Arrays.asList(Resource.FLOUR), Arrays.asList(Resource.BREAD), 2),
    BREWERY(BuildingsDetails.BREWERY, Arrays.asList(Resource.HOPS), Arrays.asList(Resource.ALE), 2);

    BuildingsDetails buildingsDetails;
    List<Resource> consumptions;
    List<Resource> products;
    int rate;
    ProductMakerDetails(BuildingsDetails buildingsDetails, List<Resource> consumptions, List<Resource> products, int rate){}

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public List<Resource> getProducts() {
        return products;
    }

    public int getRate() {
        return rate;
    }

    public static ProductMakerDetails getProductMakerDetailsByBuildingDetails(BuildingsDetails buildingsDetails) {
        for (ProductMakerDetails productMakerDetails: ProductMakerDetails.values())
            if (productMakerDetails.buildingsDetails.equals(buildingsDetails)) return productMakerDetails;
        return null;
    }
}
