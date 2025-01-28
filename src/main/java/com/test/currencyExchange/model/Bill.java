package com.test.currencyExchange.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Bill {
    private double totalAmount;
    private String originalCurrency;
    private String targetCurrency;
    private String userType;
    private int customerTenureYears;
    private String category;
    private List<Item> items;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getCustomerTenureYears() {
        return customerTenureYears;
    }

    public void setCustomerTenureYears(int customerTenureYears) {
        this.customerTenureYears = customerTenureYears;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
