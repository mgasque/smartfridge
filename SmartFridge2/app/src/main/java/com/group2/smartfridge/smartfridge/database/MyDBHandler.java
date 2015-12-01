package com.group2.smartfridge.smartfridge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.group2.smartfridge.smartfridge.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aude on 28/11/15.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_FLOOR = "floorName";
    public static final String COLUMN_UNITY = "unity";
    public static final String COLUMN_IMG = "img";


    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME
                + " TEXT," + COLUMN_QUANTITY + " INTEGER," + COLUMN_FLOOR + " TEXT," + COLUMN_UNITY + " TEXT," + COLUMN_IMG + " TEXT NOT NULL" + ")";
        Log.d("log_tag", CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }



    public void addProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_FLOOR, product.getFloorName());
        values.put(COLUMN_UNITY, product.getUnity());
        //Example of image byte to store
        values.put(COLUMN_IMG, product.getImg());


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }


    public List<Product> findProductByFloor(String floorName) {
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_FLOOR + " =  \"" + floorName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        List<Product> myList = new ArrayList<Product>();

        while (cursor.moveToNext()) {
            Product product = new Product();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Float.parseFloat(cursor.getString(2)));
            product.setFloorName(cursor.getString(3));
            product.setUnity(cursor.getString(4));
            product.setImg(cursor.getBlob(5));
            myList.add(product);

        }
        cursor.close();
        db.close();
        return myList;
    }

    public void incrementQuantity(String productname) {

        String query = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_QUANTITY + " = " + COLUMN_QUANTITY + " + 1 " + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);

    }

    public void decrementQuantity(String productname) {

        String query = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_QUANTITY + " = " + COLUMN_QUANTITY + " - 1 " + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);

    }

    public void setQuantityInDB(String productname, float quantity) {
        String query = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_QUANTITY + " = " + quantity + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);
    }

    public void setUnityInDB(String productname, String unity) {
        String query = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_UNITY + " = \"" + unity + "\"" + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);
    }

    public boolean deleteProduct(String productname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(product.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public void fillDb(Context context) {

        addProduct(new Product("steak", 3, "floor2" , "U" , convertImg(context, R.drawable.steack)));
        addProduct(new Product("biere", 2, "door" , "U" , convertImg(context, R.drawable.biere)));
        addProduct(new Product("broccoli", 1, "vegetable" , "Kg" , convertImg(context, R.drawable.broccoli)));
        addProduct(new Product("carotte", 6, "vegetable" , "U" , convertImg(context, R.drawable.carrot)));
        addProduct(new Product("camembert", 60, "floor1" , "%" , convertImg(context, R.drawable.camembert)));
        addProduct(new Product("cheese", 40, "floor1" , "%" , convertImg(context, R.drawable.cheese)));
        addProduct(new Product("coca", 80, "door" , "%" , convertImg(context, R.drawable.coca)));
        addProduct(new Product("corn", 1, "vegetable" , "Kg" , convertImg(context, R.drawable.corn)));
        addProduct(new Product("cote_porc", 3, "floor2" , "U" , convertImg(context, R.drawable.cote_porc)));
        addProduct(new Product("cream", Float.parseFloat("0.6"), "floor2" , "L" , convertImg(context, R.drawable.cream)));
        addProduct(new Product("eggs", 4, "door" , "U" , convertImg(context, R.drawable.eggs)));
        addProduct(new Product("jambon", 4, "floor2" , "U" , convertImg(context, R.drawable.jambon)));
        addProduct(new Product("jus_orange", Float.parseFloat("0.8"), "door" , "L" , convertImg(context, R.drawable.jus_orange)));
        addProduct(new Product("ketchup", 70, "door" , "%" , convertImg(context, R.drawable.ketchup)));
        addProduct(new Product("lait", Float.parseFloat("0.5"), "door" , "L" , convertImg(context, R.drawable.lait)));
        addProduct(new Product("yahourt", 5, "floor1", "U", convertImg(context, R.drawable.yogurt)));
        addProduct(new Product("tomate",2,"vegetable","kg",convertImg(context,R.drawable.tomato)));
        addProduct(new Product("saucisse",6,"floor2","U",convertImg(context,R.drawable.saucisse)));
        addProduct(new Product("salade",1,"vegetable","U",convertImg(context,R.drawable.salad)));
        addProduct(new Product("poulet",1,"floor2","U",convertImg(context,R.drawable.poulet)));
        addProduct(new Product("pomme de terre", 6, "vegetable", "U", convertImg(context, R.drawable.potato)));
        addProduct(new Product("pomme", 5, "vegetable", "U", convertImg(context, R.drawable.pomme)));
        addProduct(new Product("poivron",2,"vegetable","kg",convertImg(context,R.drawable.pepper)));
        addProduct(new Product("oignon",6,"vegetable","U",convertImg(context,R.drawable.onion)));
    }

    public static byte[] convertImg(Context context, int idDrawable){
        Drawable mDrawable = ContextCompat.getDrawable(context, idDrawable);
        Bitmap img = ((BitmapDrawable) mDrawable).getBitmap();
        // Then convert to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
