package com.example.user.myapplication.data.model;

public class Product {

    public static final String TAG = Product.class.getSimpleName();
    public static final String TABLE = "Product";
    // Labels Table Columns names
    public static final String KEY_ProductId = "P_NO";
    public static final String KEY_Name = "P_NAME";

    private String product_id;
    private String product_name;

    public Product() {

    }

    public Product(String product_id, String product_name) {
        this.product_id = product_id;
        this.product_name = product_name;
    }

    // Setter
    public void setProductId(String product_id) {
        this.product_id = product_id;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    // Getter
    public String getProductId() {
        return this.product_id;
    }

    public String getProductName() {
        return this.product_name;
    }
}
