package com.vantage.myapplication.database.data_service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.vantage.myapplication.database.DataBaseHelper;
import com.vantage.myapplication.database.table_entity.InvoiceItemEntity;

public class InvoiceItemDS {

    DataBaseHelper dataBaseHelper;

    public InvoiceItemDS(Context context) {
        dataBaseHelper = DataBaseHelper.getInstance(context);
        dataBaseHelper.getWritableDatabase();
    }

    public boolean createOrUpdate(InvoiceItemEntity object) {
        boolean action = false;
        SQLiteDatabase db = dataBaseHelper.getDB();
        db.beginTransaction();

        try {
            // Create or update the invoice item
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_PriceUID + ","
                    + col_InvoiceUID + ","
                    + col_ItemUID + ","
                    + col_Qty + ","
                    + col_Type + ")"
                    + " values (?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            insertStatement(statement, object);

            // Decrease the quantity in the Warehouse table
            decreaseStock(object.getPriceUID(), object.getItemUID(), object.getQty());

            db.setTransactionSuccessful();
            action = true;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            db.endTransaction();
        }

        return action;
    }

    private void insertStatement(SQLiteStatement statement, InvoiceItemEntity object) {
        statement.bindLong(1, object.getPriceUID());
        statement.bindLong(2, object.getInvoiceUID());
        statement.bindLong(3, object.getItemUID());
        statement.bindDouble(4, object.getQty());
        statement.bindLong(5, object.getType());
        statement.execute();
    }

    private void decreaseStock(long priceUID, long itemUID, double qty) {
        SQLiteDatabase db = dataBaseHelper.getDB();
        String query = "SELECT " + WarehouseDS.col_Stock + " FROM " + WarehouseDS.tableName +
                " WHERE " + WarehouseDS.col_PriceUID + " = ? AND " + WarehouseDS.col_ItemUID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(priceUID), String.valueOf(itemUID)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") double existingStock = cursor.getDouble(cursor.getColumnIndex(WarehouseDS.col_Stock));
            double updatedStock = existingStock - qty;

            // Update stock value in Warehouse table
            String updateSql = "UPDATE " + WarehouseDS.tableName
                    + " SET " + WarehouseDS.col_Stock + " = ?"
                    + " WHERE " + WarehouseDS.col_PriceUID + " = ? AND " + WarehouseDS.col_ItemUID + " = ?";
            SQLiteStatement updateStatement = db.compileStatement(updateSql);
            updateStatement.bindDouble(1, updatedStock);
            updateStatement.bindLong(2, priceUID);
            updateStatement.bindLong(3, itemUID);
            updateStatement.execute();
        }

        cursor.close();
    }

    public static String tableName = "InvoiceItem";
    public static String col_UID = "UID";
    public static String col_PriceUID = "PriceUID";
    public static String col_InvoiceUID = "InvoiceUID";
    public static String col_ItemUID = "ItemUID";
    public static String col_Qty = "Qty";
    public static String col_Type = "Type";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_PriceUID + " integer ," +
            col_InvoiceUID + " integer ," +
            col_ItemUID + " integer ," +
            col_Qty + " integer ," +
            col_Type + " integer " +
            ");";
}
