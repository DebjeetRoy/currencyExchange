package com.test.currencyExchange.service;

import com.test.currencyExchange.model.Bill;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BillCalculationService {

    private final CurrencyExchangeService currencyExchangeService;

    public BillCalculationService(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    public Map<String, Object> calculatePayableAmount(Bill bill) {
        // Apply discount rules
        double discount = calculateDiscount(bill);
        double discountedAmount = bill.getTotalAmount() - discount;

        // Convert currency
        double exchangeRate = currencyExchangeService.getExchangeRate(bill.getOriginalCurrency(), bill.getTargetCurrency());
        double convertedAmount = discountedAmount * exchangeRate;

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("originalAmount", bill.getTotalAmount());
        response.put("discount", discount);
        response.put("payableAmount", convertedAmount);
        response.put("currency", bill.getTargetCurrency());

        return response;
    }

    private double calculateDiscount(Bill bill) {
        double discount = 0.0;

        // Apply percentage-based discounts
        if (!"groceries".equalsIgnoreCase(bill.getCategory())) {
            if ("employee".equalsIgnoreCase(bill.getUserType())) {
                discount = bill.getTotalAmount() * 0.30;
            } else if ("affiliate".equalsIgnoreCase(bill.getUserType())) {
                discount = bill.getTotalAmount() * 0.10;
            } else if (bill.getCustomerTenureYears() > 2) {
                discount = bill.getTotalAmount() * 0.05;
            }
        }

        // Add fixed discount for every $100
        discount += (int) (bill.getTotalAmount() / 100) * 5;

        return discount;
    }
}

