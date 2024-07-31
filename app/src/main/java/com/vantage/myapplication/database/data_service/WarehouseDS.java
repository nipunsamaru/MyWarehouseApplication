package com.vantage.myapplication.database.data_service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.vantage.myapplication.database.DataBaseHelper;
import com.vantage.myapplication.database.table_entity.InvoiceCombined;
import com.vantage.myapplication.database.table_entity.WareHouseEntity;

import java.util.ArrayList;
import java.util.List;

public class WarehouseDS {
    DataBaseHelper dataBaseHelper;

    public WarehouseDS(Context context) {
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    public boolean createOrUpdate(WareHouseEntity object) {
        boolean action = false;
        SQLiteDatabase db = dataBaseHelper.getDB();
        db.beginTransaction();

        try {
            if (updateStockIfExists(object.getPriceUID(), object.getItemUID(), object.getStock())) {
                // Record updated successfully
            } else {
                // Insert new entry
                String insertSql = "INSERT INTO " + tableName
                        + " (" + col_PriceUID + ", " + col_ItemUID + ", " + col_Stock + ")"
                        + " VALUES (?, ?, ?)";
                SQLiteStatement insertStatement = db.compileStatement(insertSql);
                insertStatement.bindLong(1, object.getPriceUID());
                insertStatement.bindLong(2, object.getItemUID());
                insertStatement.bindDouble(3, object.getStock());
                insertStatement.execute();
            }

            db.setTransactionSuccessful();
            action = true;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            dataBaseHelper.getDB().endTransaction();
        }

        return action;
    }

    private boolean updateStockIfExists(long priceUID, long itemUID, double newStock) {
        SQLiteDatabase db = dataBaseHelper.getDB();
        String query = "SELECT " + col_Stock + " FROM " + tableName + " WHERE " + col_PriceUID + " = ? AND " + col_ItemUID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(priceUID), String.valueOf(itemUID)});

        boolean exists = cursor.moveToFirst();
        if (exists) {
            @SuppressLint("Range") double existingStock = cursor.getDouble(cursor.getColumnIndex(col_Stock));
            double updatedStock = existingStock + newStock;

            // Update existing entry with new stock value
            String updateSql = "UPDATE " + tableName
                    + " SET " + col_Stock + " = ?"
                    + " WHERE " + col_PriceUID + " = ? AND " + col_ItemUID + " = ?";
            SQLiteStatement updateStatement = db.compileStatement(updateSql);
            updateStatement.bindDouble(1, updatedStock);
            updateStatement.bindLong(2, priceUID);
            updateStatement.bindLong(3, itemUID);
            updateStatement.execute();
        }

        cursor.close();
        return exists;
    }

    public List<InvoiceCombined> combinedInvoiceData() {
        List<InvoiceCombined> combinedList = new ArrayList<>();
        String query = "SELECT item.UID AS itemUID, item.Name, price.UID AS priceUID, price.RPL, warehouse.Stock " +
                "FROM item " +
                "INNER JOIN price ON item.UID = price.itemUID " +
                "INNER JOIN warehouse ON price.UID = warehouse.PriceUID";
        try {
            Cursor cursor = dataBaseHelper.getDB().rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int itemUID = cursor.getInt(cursor.getColumnIndex("itemUID"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                    @SuppressLint("Range") int priceUID = cursor.getInt(cursor.getColumnIndex("priceUID"));
                    @SuppressLint("Range") double rpl = cursor.getDouble(cursor.getColumnIndex("RPL"));
                    @SuppressLint("Range") double stock = cursor.getDouble(cursor.getColumnIndex("Stock"));
                    combinedList.add(new InvoiceCombined(itemUID, priceUID, name, rpl, stock));
                } while (cursor.moveToNext());
            }

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return combinedList;
    }

    public static String tableName = "Warehouse";

    public static String col_UID = "UID";
    public static String col_PriceUID = "PriceUID";
    public static String col_ItemUID = "ItemUID";
    public static String col_Stock = "Stock";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_PriceUID + " integer ," +
            col_ItemUID + " integer ," +
            col_Stock + " double " +
            ");";
}
