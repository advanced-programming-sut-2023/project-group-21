package model.building.Enums;

import model.Resource;

import java.util.Arrays;
import java.util.List;

public enum ProductMakerDetails {
    PRODUCT_MAKER_DETAILS(BuildingsDetails.BUILDINGS_DETAILS, Arrays.asList(Resource.RESOURCE), 0);

    BuildingsDetails buildingsDetails;
    List<Resource> products;
    int rate;
    ProductMakerDetails(BuildingsDetails buildingsDetails, List<Resource> products, int rate){}

    public BuildingsDetails getBuildingsDetails() {
        return buildingsDetails;
    }

    public List<Resource> getProducts() {
        return products;
    }

    public int getRate() {
        return rate;
    }
}
