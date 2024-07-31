package com.vantage.myapplication.database.data_service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.google.gson.Gson;
import com.vantage.myapplication.Utils;
import com.vantage.myapplication.database.DataBaseHelper;
import com.vantage.myapplication.database.table_entity.Combined;
import com.vantage.myapplication.database.table_entity.ItemEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ItemDS {

    DataBaseHelper dataBaseHelper;

    public ItemDS(Context context) { dataBaseHelper =DataBaseHelper.getInstance(context);}

    public boolean createOrUpdate(ItemEntity object){
        boolean action = false;

        try {
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_UID + ","
                    + col_Name + ")"
                    + " values (?,?)";

            dataBaseHelper.getDB().beginTransaction();
            SQLiteStatement statement = dataBaseHelper.getDB().compileStatement(sql);
            insertStatement(statement, object);
            dataBaseHelper.getDB().setTransactionSuccessful();
            action = true;
        }catch (Exception e){
            System.err.println(e);
        }finally {
            dataBaseHelper.getDB().endTransaction();
        }
        return action;
    }

    private void  insertStatement(SQLiteStatement statement, ItemEntity object){
        statement.bindLong(1,object.getUID());
        statement.bindString(2,object.getName());
        statement.execute();
    }


    public List<Combined> getCombinedData() {
        List<Combined> combinedDataList = new ArrayList<>();
        String query = "SELECT item.UID AS itemUID, item.Name, price.UID AS priceUID, price.RPL FROM item " +
                "INNER JOIN price ON item.UID = price.itemUID";
        try {
            Cursor cursor = dataBaseHelper.getDB().rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int itemUID = cursor.getInt(cursor.getColumnIndex("itemUID"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                    @SuppressLint("Range") int priceUID = cursor.getInt(cursor.getColumnIndex("priceUID"));
                    @SuppressLint("Range") double rpl = cursor.getDouble(cursor.getColumnIndex("RPL"));
                    combinedDataList.add(new Combined(itemUID, priceUID, name, rpl));
                } while (cursor.moveToNext());
            }

            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return combinedDataList;
    }

    public  static String tableName = "Item";

    public  static  String col_UID = "UID";

    public static  String col_Name = "Name";

    public static  String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_Name + " text " +
            ");";
}
