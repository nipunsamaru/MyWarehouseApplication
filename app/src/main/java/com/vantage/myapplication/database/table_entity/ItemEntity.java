package com.vantage.myapplication.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class ItemEntity{

	@SerializedName("UID")
	private int uID;

	@SerializedName("Name")
	private String name;

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

	public String toString(){
		return name;
	}
}