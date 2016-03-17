package com.printapp.models;


import java.util.ArrayList;


public class Users {

    public Double count;
    public ArrayList<User> items;

    @Override
    public String toString() {
        String s ="";
        for(Object o : items){
            s+= toString()+"\n";
        }
        return s;
    }
}
