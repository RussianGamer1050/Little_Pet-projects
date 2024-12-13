package com.crypto.domain;

/*
 * Class to represent cryptocurrency
 */

public class Crypto {
    
    public String name;
    public double priceUsd;
    public Trend trend = Trend.NULL;
    
    public enum Trend {
        UP, DOWN, NULL
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    // Override toString()
    @Override
    public String toString() {
        return name + ": " + priceUsd + " USD";
    }
    
}
