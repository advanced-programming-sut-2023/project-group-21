package model.building;

import model.Cell;
import model.Government;
import model.building.Enums.ProductMakerDetails;
import model.generalenums.Resource;
import model.human.Person;

import java.util.ArrayList;

public class WeaponProduction extends ProductMaker {
    private Resource currentWeapon;
    public WeaponProduction(Government government, Cell cell, ProductMakerDetails productMakerDetails, ArrayList<Person> workers) {
        super(government, cell, productMakerDetails, workers);
        currentWeapon = productMakerDetails.getProducts().get(0);
    }

    public Resource getCurrentWeapon() {
        return currentWeapon;
    }
}
