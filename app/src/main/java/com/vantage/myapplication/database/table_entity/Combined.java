package com.vantage.myapplication.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class Combined {

    @SerializedName("UID")
    private int uID;

    @SerializedName("Name")
    private String name;

    @SerializedName("RPL")
    private double rPL;

    @SerializedName("ItemUID")
    private int itemUID;

    @SerializedName("PriceUID")
    private int priceUID;

    @SerializedName("Stock")
    private Double Stock;



    public Combined(int itemUID, int priceUID, String name, double rpl) {
        this.name = name;
        this.rPL = rpl;
        this.itemUID = itemUID;
        this.priceUID = priceUID;


    }


    public void setUID(int uID){
        this.uID = uID;
    }

    public int getUID(){
        return uID;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setRPL(double rPL){
        this.rPL = rPL;
    }

    public double getRPL(){
        return rPL;
    }

    public void setItemUID(int itemUID){
        this.itemUID = itemUID;
    }

    public int getItemUID(){
        return itemUID;
    }

    public void setPriceUID(int priceUID){
        this.priceUID = priceUID;
    }

    public int getPriceUID(){
        return priceUID;
    }

    public void setStock(Double Stock){
        this.Stock = Stock;
    }

    public Double getStock(){
        return Stock;
    }
}
