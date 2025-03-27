package com.test.currencyExchange.model;


public class User {
    private String userId;
    private String userName;
    private String userType;

    public User(String userId, String userName, String userType) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
