package com.test.currencyExchange.model;

import com.test.currencyExchange.constants.UserItemCategoryConstants;

import java.util.HashMap;
import java.util.Map;

public class UserCategory {
    private static final Map<String, Double> CATEGORY_DISCOUNTS = new HashMap<>();

    static {
        CATEGORY_DISCOUNTS.put(UserItemCategoryConstants.EMPLOYEE, 0.3);  // 30% discount
        CATEGORY_DISCOUNTS.put(UserItemCategoryConstants.AFFILIATE, 0.1); // 10% discount
        CATEGORY_DISCOUNTS.put(UserItemCategoryConstants.LOYAL_CUSTOMER, 0.05); // 5% discount
    }

    public static double getDiscountPercentage(String categoryName) {
        return CATEGORY_DISCOUNTS.getOrDefault(categoryName, 0.0);
    }
}
