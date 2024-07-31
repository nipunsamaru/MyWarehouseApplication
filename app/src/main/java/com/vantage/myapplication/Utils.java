package com.vantage.myapplication;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Utils {


    public static JSONArray getArray(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                String colName = cursor.getColumnName(i);
                if (colName != null) {
                    try {
                        switch (cursor.getType(i)) {
                            case Cursor.FIELD_TYPE_BLOB:
                                rowObject.put(colName, Arrays.toString(cursor.getBlob(i)));
                                break;
                            case Cursor.FIELD_TYPE_FLOAT:
                                rowObject.put(colName, cursor.getDouble(i));
                                break;
                            case Cursor.FIELD_TYPE_INTEGER:
                                rowObject.put(colName, cursor.getLong(i));
                                break;
                            case Cursor.FIELD_TYPE_NULL:
                                rowObject.put(colName, null);
                                break;
                            case Cursor.FIELD_TYPE_STRING:
                                rowObject.put(colName, cursor.getString(i));
                                break;
                        }
                    } catch (JSONException e) {
                        System.err.println(e);
                    }
                }
            }
            resultSet.put(rowObject);
            if (!cursor.moveToNext())
                break;
        }

        cursor.close();
        return resultSet;
    }
}
