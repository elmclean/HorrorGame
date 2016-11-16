package com.example.elmclean.horrorgame;

import android.content.ClipData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elmclean on 10/19/2016.
 */
public class Inventory implements Serializable {
    ArrayList<String> inventory = new ArrayList<String>();

    public String getItemList() {
        return inventory.get(0);
    }

    public void addItem(String item) {
        inventory.add(item);
    }

    public Boolean searchInventory(String item) {
        if(inventory.contains(item)) {
            return true;
        } else {
            return false;
        }
    }
}