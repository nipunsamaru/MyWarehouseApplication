package com.vantage.myapplication.database.data_service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.vantage.myapplication.database.DataBaseHelper;
import com.vantage.myapplication.database.table_entity.DiscountEntity;

import java.util.ArrayList;
import java.util.List;

public class DiscountDS {

    DataBaseHelper dataBaseHelper;

    public DiscountDS(Context context) {
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    public boolean createOrUpdate(DiscountEntity object) {
        boolean action = false;

        try {
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_UID + ","
                    + col_UpperQty + ","
                    + col_BreakQty + ","
                    + col_DiscountQty + ")"
                    + " values (?,?,?,?)";

            dataBaseHelper.getDB().beginTransaction();
            SQLiteStatement statement = dataBaseHelper.getDB().compileStatement(sql);
            insertStatement(statement, object);
            dataBaseHelper.getDB().setTransactionSuccessful();
            action = true;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            dataBaseHelper.getDB().endTransaction();
        }
        return action;
    }

    private void insertStatement(SQLiteStatement statement, DiscountEntity object) {
        statement.bindLong(1, object.getUID());
        statement.bindLong(2, object.getUpperQty());
        statement.bindLong(3, object.getBreakQty());
        statement.bindLong(4, object.getDiscountQty());
        statement.execute();
    }

    @SuppressLint("Range")
    public List<DiscountEntity> getAllDiscounts() {
        List<DiscountEntity> discountList = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getDB();
        String query = "SELECT * FROM " + tableName;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DiscountEntity discount = new DiscountEntity();
                discount.setUID(cursor.getInt(cursor.getColumnIndex(col_UID)));
                discount.setUpperQty(cursor.getInt(cursor.getColumnIndex(col_UpperQty)));
                discount.setBreakQty(cursor.getInt(cursor.getColumnIndex(col_BreakQty)));
                discount.setDiscountQty(cursor.getInt(cursor.getColumnIndex(col_DiscountQty)));
                discountList.add(discount);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return discountList;
    }

    public static String tableName = "Discount";

    public static String col_UID = "UID";
    public static String col_UpperQty = "UpperQty";
    public static String col_BreakQty = "BreakQty";
    public static String col_DiscountQty = "DiscountQty";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_UpperQty + " integer ," +
            col_BreakQty + " integer ," +
            col_DiscountQty + " integer " +
            ");";
}
