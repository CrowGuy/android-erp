package com.example.user.myapplication.data.model;

public class User {

    public static final String TAG = User.class.getSimpleName();
    public static final String TABLE = "User";
    // Labels Table Columns names
    public static final String KEY_Account = "Account";
    public static final String KEY_Password = "Password";
    public static final String KEY_Priority = "Priority";

    private String account;
    private String password;
    private String priority;

    // Constructors
    public User() {

    }

    public User(String account) {
        this.account = account;
    }

    public User(String account, String password, String priority) {
        this.account = account;
        this.password = password;
        this.priority = priority;
    }

    // Setter
    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Getter

    public String getAccount() {
        return this.account;
    }

    public String getPassword() {
        return this.password;

    }

    public String getPriority() {

        return this.priority;
    }
}
