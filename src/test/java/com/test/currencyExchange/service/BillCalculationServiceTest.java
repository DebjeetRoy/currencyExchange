package com.test.currencyExchange.service;

import com.test.currencyExchange.model.Bill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootTest
public class BillCalculationServiceTest {

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private BillCalculationService billCalculationService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testCalculatePayableAmount() throws Exception {
        setPrivateField(currencyExchangeService, "apiKey", "api-key");

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setOriginalCurrency("USD");
        bill.setTargetCurrency("EUR");
        bill.setUserType("employee");
        bill.setCustomerTenureYears("3");
        bill.setCategory("electronics");

        Mockito.when(currencyExchangeService.getExchangeRate("USD", "EUR")).thenReturn(0.85);

        Map<String, Object> result = billCalculationService.calculatePayableAmount(bill);
        Assertions.assertEquals(110.5, result.get("payableAmount"));
    }

    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        // Use reflection to set private fields
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}

