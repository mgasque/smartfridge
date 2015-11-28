package com.group2.smartfridge.smartfridge.database;

/**
 * Created by aude on 28/11/15.
 */
public class Product {

    private int id;
    private String productname;
    private int quantity;

    public Product() {

    }

    public Product(int id, String productname, int quantity) {
        this.id = id;
        this.productname = productname;
        this.quantity = quantity;
    }

    public Product(String productname, int quantity) {
        this.productname = productname;
        this.quantity = quantity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
