package com.vantage.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.vantage.myapplication.database.data_service.DiscountDS;
import com.vantage.myapplication.database.data_service.InvoiceDS;
import com.vantage.myapplication.database.data_service.InvoiceItemDS;
import com.vantage.myapplication.database.data_service.ItemDS;
import com.vantage.myapplication.database.data_service.PriceDS;
import com.vantage.myapplication.database.data_service.WarehouseDS;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int dataBaseVersion = 1;
    private static final String dataBaseName = "myDataBase";

    private static DataBaseHelper instance;
    private static SQLiteDatabase database;

    public DataBaseHelper(@Nullable Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
    }

    public static synchronized DataBaseHelper getInstance(Context context){
        if(instance==null){
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    public synchronized SQLiteDatabase getDB() {
        if(database == null || !database.isOpen()){
            database = instance.getWritableDatabase();
            database.disableWriteAheadLogging();
        }
        return database;
    }

    @Override
    public  void onCreate(SQLiteDatabase db){

        try {
            db.execSQL(ItemDS.create);
        }catch (Exception e){
            System.err.println(e);
        }
        try {
            db.execSQL(PriceDS.create);
        }catch (Exception e){
            System.err.println(e);
        }
        try {
            db.execSQL(DiscountDS.create);
        }catch (Exception e){
            System.err.println(e);
        }
        try {
            db.execSQL(WarehouseDS.create);
        }catch (Exception e){
            System.err.println(e);
        }
        try {
            db.execSQL(InvoiceDS.create);
        }catch (Exception e){
            System.err.println(e);
        }
        try {
            db.execSQL(InvoiceItemDS.create);
        }catch (Exception e){
            System.err.println(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + InvoiceDS.tableName);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + InvoiceItemDS.tableName);
        onCreate(db);

    }

}
