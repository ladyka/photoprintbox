package com.printapp.models;

import java.util.ArrayList;

public class Groups {

    public double count;
    public ArrayList<Group> items;

    @Override
    public String toString() {
        String s ="";
        for(Object o : items){
            s+= toString()+"\n";
        }
        return s;
    }
}
