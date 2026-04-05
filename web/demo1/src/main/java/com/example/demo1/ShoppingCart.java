package com.example.demo1;

import java.util.HashMap;

public class ShoppingCart {
    public HashMap<String, Integer> shoppingcart = new HashMap<String, Integer>();
    public void addItem(String item, String quantity) {
        if(shoppingcart.containsKey(item))
            shoppingcart.put(item, shoppingcart.get(item) + Integer.parseInt(quantity));
        else
            shoppingcart.put(item, Integer.parseInt(quantity));
    }
}
