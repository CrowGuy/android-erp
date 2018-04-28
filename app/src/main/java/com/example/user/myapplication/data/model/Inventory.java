package com.example.user.myapplication.data.model;

public class Inventory {

    public static final String TAG = Inventory.class.getSimpleName();
    public static final String TABLE = "Inventory";
    // Labels Table Columns names
    public static final String KEY_InventoryId = "INV_NO";
    public static final String KEY_ClientId = "CL_NO";
    public static final String KEY_ProductId = "P_NO";
    public static final String KEY_Color = "COLOR";
    public static final String KEY_Number = "NUM";
    public static final String KEY_Date = "DATE";

    private String inventory_id;
    private String client_id;
    private String product_id;
    private String _color;
    private String inventory_num;
    private String edit_date;

    public Inventory() {

    }

    public Inventory(String inventory_id, String client_id, String product_id, String _color, String edit_date) {
        this.inventory_id = inventory_id;
        this.client_id = client_id;
        this.product_id = product_id;
        this._color = _color;
        this.edit_date = edit_date;
    }

    public Inventory(String inventory_id, String client_id, String product_id, String _color, String inventory_num, String edit_date) {
        this.inventory_id = inventory_id;
        this.client_id = client_id;
        this.product_id = product_id;
        this._color = _color;
        this.inventory_num = inventory_num;
        this.edit_date = edit_date;
    }

    // Setter
    public void setInventoryId(String inventory_id) {
        this.inventory_id = inventory_id;
    }

    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id;
    }

    public void set_Color(String _color) {
        this._color = _color;
    }

    public void setNum(String inventory_num) { this.inventory_num = inventory_num; }

    public void setEditDate(String edit_date) {
        this.edit_date = edit_date;
    }

    // Getter
    public String getInventoryId() {
        return this.inventory_id;
    }

    public String getProductId() {
        return this.product_id;
    }

    public String getClientId() {
        return this.client_id;
    }

    public String get_Color() {
        return this._color;
    }

    public String getInventory_num() { return this.inventory_num; }

    public String getEditDate() {
        return this.edit_date;
    }
}
