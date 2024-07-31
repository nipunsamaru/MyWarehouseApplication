package com.vantage.myapplication.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class InvoiceEntity {

	@SerializedName("UID")
	private int uID;

	@SerializedName("Qty")
	private Double qty;

	@SerializedName("Date")
	private String date;

	public void setUID(int uID){
		this.uID = uID;
	}

	public int getUID(){
		return uID;
	}

	public void setQty(Double qty){
		this.qty = qty;
	}

	public double getQty(){
		return qty;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	@Override
 	public String toString(){
		return 
			"Invoice{" + 
			"uID = '" + uID + '\'' + 
			",qty = '" + qty + '\'' + 
			",date = '" + date + '\'' + 
			"}";
		}
}