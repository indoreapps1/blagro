package com.blagro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blagro.model.MyPojo;
import com.blagro.utilities.Contants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lalit on 7/25/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = Contants.DATABASE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MyOrderDataEntity");
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_MyOrder_TABLE = "CREATE TABLE MyOrderDataEntity(id INTEGER, product_name TEXT, product_unit TEXT, product_gst TEXT, category TEXT, Quantity REAL,gst TEXT)";
        db.execSQL(CREATE_MyOrder_TABLE);

    }

    //------------basket Order data----------------
    public boolean upsertBasketOrderData(MyPojo ob) {
        boolean done = false;
        MyPojo data = null;
        if (ob.getId() != 0) {
            data = getBasketOrderData(ob.getId());
            if (data == null) {
                done = insertBasketOrderData(ob);
            } else {
                done = updateBasketOrderData(ob);
            }
        }
        return done;
    }

    //GetAll Basket Order
    private void populateBasketOrderData(Cursor cursor, MyPojo ob) {
        ob.setId(cursor.getInt(0));
        ob.setName(cursor.getString(1));
        ob.setUnit(cursor.getString(2));
        ob.setGst(cursor.getString(3));
        ob.setCategory(cursor.getString(4));
        ob.setQuant(cursor.getInt(5));
        ob.setGst(cursor.getString(6));
    }

    //show  Basket Order list data
    public List<MyPojo> GetAllBasketOrderData() {
        ArrayList<MyPojo> list = new ArrayList<MyPojo>();
        String query = "Select * FROM MyOrderDataEntity ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                MyPojo ob = new MyPojo();
                populateBasketOrderData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //insert Basket Order data
    public boolean insertBasketOrderData(MyPojo ob) {
        ContentValues values = new ContentValues();
        populateBasketOrderValue(ob, values);
        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("MyOrderDataEntity", null, values);
        db.close();
        return i > 0;
    }

    //Basket Order data by id
    public MyPojo getBasketOrderData(int id) {
        String query = "Select * FROM MyOrderDataEntity WHERE id= " + id + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MyPojo data = new MyPojo();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateBasketOrderData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    private void populateBasketOrderValue(MyPojo ob, ContentValues values) {
        values.put("id", ob.getId());
        values.put("product_name", ob.getName());
        values.put("product_unit", ob.getUnit());
        values.put("product_gst", ob.getGst());
        values.put("category", ob.getCategory());
        values.put("Quantity", ob.getQuant());
        values.put("gst", ob.getGst());

    }

    //ProductId,ProductName,BasketId,StoreId,Quantity,Price,OrderTime  MyOrderDataEntity
    //update Basket Order data
    public boolean updateBasketOrderData(MyPojo ob) {
        ContentValues values = new ContentValues();
        populateBasketOrderValue(ob, values);

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("MyOrderDataEntity", values, "id = " + ob.getId() + "", null);
        db.close();
        return i > 0;
    }

    // delete Basket Order Data By Product Id ...........
    public boolean deleteBasketOrderDataById(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "id = " + id + " ";
        db.delete("MyOrderDataEntity", query, null);
        db.close();
        return result;
    }

    // delete all  Data
    public boolean deleteAllBasketOrderData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("MyOrderDataEntity", null, null);
        db.close();
        return result;
    }

    // get Basket OrderData
    public MyPojo getBasketOrderData() {

        String query = "Select * FROM MyOrderDataEntity";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MyPojo data = new MyPojo();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateBasketOrderData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

}
