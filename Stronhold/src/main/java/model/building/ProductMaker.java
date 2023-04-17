package model.building;

import model.Cell;
import model.Government;
import model.Resource;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ProductMakerDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductMaker extends Building {
    ProductMakerDetails productMakerDetails;

    public BuildingsDetails getBuildingsDetails() {
        return productMakerDetails.getBuildingsDetails();
    }

    public List<Resource> getProducts() {
        return productMakerDetails.getProducts();
    }

    public ProductMaker(Government government, BuildingsDetails buildingsDetails, int hitPoint, Cell cell, HashMap<Resource, Integer> cost, ProductMakerDetails productMakerDetails) {
        super(government, buildingsDetails, hitPoint, cell, cost);
        this.productMakerDetails = productMakerDetails;
    }

    public int getRate() {
        return productMakerDetails.getRate();
    }
}
