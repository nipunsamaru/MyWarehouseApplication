package com.vantage.myapplication.database.data_service;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import com.vantage.myapplication.database.DataBaseHelper;
import com.vantage.myapplication.database.table_entity.InvoiceEntity;

public class InvoiceDS {
    DataBaseHelper dataBaseHelper;

    public InvoiceDS(Context context) {
        dataBaseHelper = DataBaseHelper.getInstance(context);
        dataBaseHelper.getWritableDatabase(); // Ensure database is created
    }

    public long createOrUpdate(InvoiceEntity object) {
        long uid = -1;

        try {
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_Qty + ","
                    + col_Date + ")"
                    + " values (?,?)";

            dataBaseHelper.getDB().beginTransaction();

            SQLiteStatement statement = dataBaseHelper.getDB().compileStatement(sql);
            uid = insertStatement(statement, object);
            dataBaseHelper.getDB().setTransactionSuccessful();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            dataBaseHelper.getDB().endTransaction();
        }

        return uid;
    }

    private long insertStatement(SQLiteStatement statement, InvoiceEntity object) {
        statement.bindDouble(1, object.getQty());
        statement.bindString(2, object.getDate());
        return statement.executeInsert();
    }

    public static String tableName = "Invoice";
    public static String col_UID = "UID";
    public static String col_Qty = "Qty";
    public static String col_Date = "Date";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_Qty + " integer ," +
            col_Date + " string " +
            ");";
}
