package controller;

import model.Government;
import model.Trade;
import model.User;
import view.message.ShopMessage;

import java.util.ArrayList;

public class ShopController {
    Government currentGovernment;
    private ArrayList<Trade> trades = new ArrayList<>();
    public ShopController(Government government){
        this.currentGovernment = government;
    }


    public ShopMessage buy(String name,int amount){



        return null;
    }
    public ShopMessage sell(int id,int amount){
        return null;
    }

    public String showDetails(){
        return "";
    }
}
