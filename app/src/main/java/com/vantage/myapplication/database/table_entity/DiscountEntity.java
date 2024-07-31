package com.vantage.myapplication.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class DiscountEntity{

	@SerializedName("UID")
	private int uID;

	@SerializedName("BreakQty")
	private int breakQty;

	@SerializedName("UpperQty")
	private int upperQty;

	@SerializedName("DiscountQty")
	private int discountQty;

	public void setUID(int uID){
		this.uID = uID;
	}

	public int getUID(){
		return uID;
	}

	public void setBreakQty(int breakQty){
		this.breakQty = breakQty;
	}

	public int getBreakQty(){
		return breakQty;
	}

	public void setUpperQty(int upperQty){
		this.upperQty = upperQty;
	}

	public int getUpperQty(){
		return upperQty;
	}

	public void setDiscountQty(int discountQty){
		this.discountQty = discountQty;
	}

	public int getDiscountQty(){
		return discountQty;
	}

	@Override
 	public String toString(){
		return 
			"DiscountEntity{" + 
			"uID = '" + uID + '\'' + 
			",breakQty = '" + breakQty + '\'' + 
			",upperQty = '" + upperQty + '\'' + 
			",discountQty = '" + discountQty + '\'' + 
			"}";
		}
}