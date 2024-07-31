package com.vantage.myapplication.database.table_entity;

public class InvoiceCombined {
    private int itemUID;
    private int priceUID;
    private String name;
    private double rpl;
    private double stock;


    public InvoiceCombined(int itemUID, int priceUID, String name, double rpl, double stock) {
        this.itemUID = itemUID;
        this.priceUID = priceUID;
        this.name = name;
        this.rpl = rpl;
        this.stock = stock;
    }


    public int getItemUID() {
        return itemUID;
    }

    public int getPriceUID() {
        return priceUID;
    }

    public String getName() {
        return name;
    }

    public double getRPL() {
        return rpl;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}
