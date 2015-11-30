package com.group2.smartfridge.smartfridge.database;

/**
 * Created by aude on 28/11/15.
 */
public class Product {

    private int id;
    private String productname;
    private String floorName;
    private float quantity;

    public Product() {

    }

    public Product(int id, String productname, float quantity, String floorName) {
        this.id = id;
        this.productname = productname;
        this.quantity = quantity;
        this.floorName = floorName;
    }

    public Product(String productname, float quantity, String floorName) {
        this.productname = productname;
        this.quantity = quantity;
        this.floorName = floorName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public void setProductName(String productname) {
        this.productname = productname;
    }

    public String getProductName() {
        return this.productname;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getQuantity() {
        return this.quantity;
    }
}
