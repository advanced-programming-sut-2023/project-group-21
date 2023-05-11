package model.building;

import model.Cell;
import model.Government;
import model.generalenums.Resource;
import model.building.Enums.BuildingsDetails;
import model.building.Enums.ProductMakerDetails;

import java.util.List;

public class ProductMaker extends Building {
    ProductMakerDetails productMakerDetails;
    Resource currentProduction;

    public ProductMaker(Government government, Cell cell, ProductMakerDetails productMakerDetails) {
        super(government, productMakerDetails.getBuildingsDetails(), cell);
        this.productMakerDetails = productMakerDetails;
        currentProduction = productMakerDetails.getProducts().get(0);
    }

    public BuildingsDetails getBuildingsDetails() {
        return productMakerDetails.getBuildingsDetails();
    }

    public List<Resource> getProducts() {
        return productMakerDetails.getProducts();
    }

    public Resource getConsumingProduct() {
        return productMakerDetails.getConsumption();
    }

    public int getRate() {
        return productMakerDetails.getRate();
    }
}
