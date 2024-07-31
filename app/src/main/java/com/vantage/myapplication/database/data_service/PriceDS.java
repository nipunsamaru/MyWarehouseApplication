package com.vantage.myapplication.database.data_service;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import com.google.gson.Gson;
import com.vantage.myapplication.Utils;
import com.vantage.myapplication.database.DataBaseHelper;
import com.vantage.myapplication.database.table_entity.ItemEntity;
import com.vantage.myapplication.database.table_entity.PriceEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PriceDS {

    DataBaseHelper dataBaseHelper;

    public PriceDS(Context context) { dataBaseHelper =DataBaseHelper.getInstance(context);}

    public boolean createOrUpdate(PriceEntity object){
        boolean action = false;

        try {
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_UID + ","
                    + col_MRP + ","
                    + col_RPL + ","
                    + col_ItemUID + ")"
                    + " values (?,?,?,?)";

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

    private void  insertStatement(SQLiteStatement statement, PriceEntity object){
        statement.bindLong(1,object.getUID());
        statement.bindDouble(2,object.getMRP());
        statement.bindDouble(3,object.getRPL());
        statement.bindLong(4,object.getItemUID());
        statement.execute();
    }


    public List<PriceEntity> getRPL() throws JSONException {
        List<PriceEntity> list = new ArrayList<>();
        String sql = "select " + col_ItemUID + " from " + tableName;
        dataBaseHelper.getDB().beginTransaction();
        JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
        for (int i = 0; i < array.length(); i++) {
            list.add(new Gson().fromJson(array.getJSONObject(i).toString(), PriceEntity.class));
        }
        dataBaseHelper.getDB().endTransaction();
        return list;
    }

    public  static String tableName = "Price";

    public  static  String col_UID = "UID";

    public static  String col_MRP = "MRP";

    public static  String col_RPL = "RPL";

    public static String col_ItemUID = "ItemUID";

    public static  String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_MRP + " double ," +
            col_RPL + " double  ," +
            col_ItemUID + " integer  " +
            ");";

}
