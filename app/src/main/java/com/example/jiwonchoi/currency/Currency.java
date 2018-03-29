package com.example.jiwonchoi.currency;

/**
 * Created by jiwonchoi on 2018. 3. 22..
 */

public class Currency {

    private String name;
    private double rate;

    public Currency(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }
}
