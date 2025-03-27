package com.test.currencyExchange.service;

import com.test.currencyExchange.constants.UserItemCategoryConstants;
import com.test.currencyExchange.model.Bill;
import com.test.currencyExchange.model.Item;
import com.test.currencyExchange.model.UserCategory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillCalculationService {

    private final CurrencyExchangeService currencyExchangeService;

    public BillCalculationService(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    public Map<String, Object> calculatePayableAmount(Bill bill) {
        // Apply discount rules
        double finalBill = calculateDiscount(bill);

        // Convert currency
        double exchangeRate = currencyExchangeService.getExchangeRate(bill.getOriginalCurrency(), bill.getTargetCurrency());
        double finalAmountInTargetCurrency = finalBill * exchangeRate;

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("originalAmount", bill.getTotalAmount());
        response.put("payableAmount", finalAmountInTargetCurrency);
        response.put("currency", bill.getTargetCurrency());

        return response;
    }

    private double calculateDiscount(Bill bill) {
        double discount;

        // Apply percentage-based discounts
        List<Item> items = bill.getItems();
        String userType = bill.getUser().getUserType();
        double newBill = 0;

        for(Item item : items){
            String itemCategory = item.getCategory();
            double discountPer = UserCategory.getDiscountPercentage(userType);
            if((!UserItemCategoryConstants.GROCERY.equals(itemCategory) && UserItemCategoryConstants.EMPLOYEE.equalsIgnoreCase(userType)) || (!UserItemCategoryConstants.GROCERY.equals(itemCategory) && UserItemCategoryConstants.AFFILIATE.equalsIgnoreCase(userType))){
                discount = item.getPrice() * discountPer;
                newBill += item.getPrice() - discount;
            } else if(UserItemCategoryConstants.CUSTOMER.equals(userType) || UserItemCategoryConstants.GROCERY.equals(itemCategory)){
                newBill += item.getPrice();
            }
            if(newBill > 100)
                newBill = newBill - (0.05 * newBill);
        }
        if(Integer.parseInt(bill.getTenure()) > 2 && newBill > 0 && UserItemCategoryConstants.CUSTOMER.equalsIgnoreCase(userType)){
            newBill = newBill - (newBill * 0.05);
        } else if(Integer.parseInt(bill.getTenure()) > 2 && newBill == 0 && UserItemCategoryConstants.CUSTOMER.equalsIgnoreCase(userType)){
            newBill = bill.getTotalAmount() - (bill.getTotalAmount() * 0.05);
        }
        return newBill;
    }
}

