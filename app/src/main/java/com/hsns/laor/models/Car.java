package com.hsns.laor.models;

/**
 * Created by otdom on 12/15/15.
 */
public class Car {
    private String name;
    private String number;
    public Car(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
