package com.vantage.myapplication.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class WareHouseEntity{

	@SerializedName("UID")
	private int uID;

	@SerializedName("PriceUID")
	private int priceUID;

	@SerializedName("ItemUID")
	private int itemUID;

	@SerializedName("Stock")
	private Double Stock;

	public void setUID(int uID){
		this.uID = uID;
	}

	public int getUID(){
		return uID;
	}

	public void setPriceUID(int priceUID){
		this.priceUID = priceUID;
	}

	public int getPriceUID(){
		return priceUID;
	}

	public void setItemUID(int itemUID){
		this.itemUID = itemUID;
	}

	public int getItemUID(){
		return itemUID;
	}

	public void setStock(Double Stock){
		this.Stock = Stock;
	}

	public Double getStock(){
		return Stock;
	}

	@Override
 	public String toString(){
		return 
			"WareHouseEntity{" + 
			"uID = '" + uID + '\'' + 
			",priceUID = '" + priceUID + '\'' + 
			",itemUID = '" + itemUID + '\'' + 
			",Stock = '" + Stock + '\'' +
			"}";
		}
}