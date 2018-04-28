package com.example.user.myapplication.data.model;

public class Client {

    public static final String TAG = Client.class.getSimpleName();
    public static final String TABLE = "Client";
    // Labels Table Columns names
    public static final String KEY_ClientId = "CL_NO";
    public static final String KEY_Name = "CL_NAME";

    private String client_id;
    private String client_name;

    // Constructors
    public Client() {

    }

    public Client(String client_id, String client_name) {
        this.client_id = client_id;
        this.client_name = client_name;
    }

    // Setter
    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    public void setClientName(String client_name) {
        this.client_name = client_name;
    }

    // Getter

    public String getClientId() {
        return this.client_id;
    }

    public String getClientName() {
        return this.client_name;
    }
}
