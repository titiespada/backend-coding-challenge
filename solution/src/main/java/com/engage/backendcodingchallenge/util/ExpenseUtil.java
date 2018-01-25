package com.engage.backendcodingchallenge.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.engage.backendcodingchallenge.dto.ExpenseDto;
import com.engage.backendcodingchallenge.model.Expense;

public class ExpenseUtil {
    
    private ExpenseUtil() { }

    public static Expense createModel(ExpenseDto dto) {
        Expense model = new Expense();
        model.setId(dto.getId());  
        model.setDate(dto.getDate());
        model.setValue(dto.getGbpValue());
        model.setReason(dto.getReason());
        return model;
    }

    public static ExpenseDto createDto(Expense model) {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(model.getId());
        dto.setDate(model.getDate());
        dto.setValue(model.getValue().toString());
        dto.setReason(model.getReason());
        dto.setGbpValue(model.getValue());
        dto.setVat(calculateVat(model.getValue()));
        return dto;
    }
    
    public static BigDecimal calculateVat(BigDecimal amount) {
        return amount.subtract(amount.divide(BigDecimal.valueOf(1.2), 2, RoundingMode.HALF_UP));
    }
    
}
