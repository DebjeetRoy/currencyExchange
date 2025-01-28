package com.test.currencyExchange.controller;

import com.test.currencyExchange.model.Bill;
import com.test.currencyExchange.service.BillCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class BillCalculationController {

    private final BillCalculationService billCalculationService;

    public BillCalculationController(BillCalculationService billCalculationService) {
        this.billCalculationService = billCalculationService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculateBill(@RequestBody Bill bill) {
        Map<String, Object> response = billCalculationService.calculatePayableAmount(bill);
        return ResponseEntity.ok(response);
    }
}
