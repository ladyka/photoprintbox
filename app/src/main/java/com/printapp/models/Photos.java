package com.printapp.models;

import java.util.ArrayList;

public class Photos {

    public Double count;
    public ArrayList<Photo> items;
    @Override
    public String toString() {
        String s ="";
        for(Object o : items){
            s+= toString()+"\n";
        }
        return s;
    }
}
