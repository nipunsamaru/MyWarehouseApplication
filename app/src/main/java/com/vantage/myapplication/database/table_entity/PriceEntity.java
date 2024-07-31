package com.vantage.myapplication.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class PriceEntity{

	@SerializedName("UID")
	private int uID;

	@SerializedName("MRP")
	private double mRP;

	@SerializedName("RPL")
	private double rPL;

	@SerializedName("ItemUID")
	private int itemUID;

	public void setUID(int uID){
		this.uID = uID;
	}

	public int getUID(){
		return uID;
	}

	public void setMRP(double mRP){
		this.mRP = mRP;
	}

	public double getMRP(){
		return mRP;
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

	@Override
 	public String toString(){
		return 
			"PriceEntity{" + 
			"uID = '" + uID + '\'' + 
			",mRP = '" + mRP + '\'' + 
			",rPL = '" + rPL + '\'' +
			",itemUID = '" + itemUID + '\'' +
			"}";
		}
}