package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.ProductMakerDetails;
import model.generalenums.Resource;

public class WeaponProduction extends ProductMaker {
    private Resource currentWeapon;
    public WeaponProduction(Government government, Cell cell, ProductMakerDetails productMakerDetails) {
        super(government, cell, productMakerDetails);
        currentWeapon = productMakerDetails.getProducts().get(0);
    }
}
