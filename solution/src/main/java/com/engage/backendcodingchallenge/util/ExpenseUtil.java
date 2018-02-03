package com.engage.backendcodingchallenge.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.engage.backendcodingchallenge.dto.ExpenseDto;
import com.engage.backendcodingchallenge.model.Expense;

public class ExpenseUtil {

    private ExpenseUtil() { }

    public static Expense createModel(ExpenseDto dto) {
        if (dto == null) {
            return null;
        }
        Expense model = new Expense();
        model.setId(dto.getId());  
        model.setDate(dto.getDate());
        model.setValue(dto.getGbpValue());
        model.setReason(dto.getReason());
        return model;
    }

    public static ExpenseDto createDto(Expense model) {
        if (model == null) {
            return null;
        }
        ExpenseDto dto = new ExpenseDto();
        dto.setId(model.getId());
        dto.setDate(model.getDate());
        dto.setValue(model.getValue());
        dto.setReason(model.getReason());
        dto.setGbpValue(model.getValue());
        dto.setVat(calculateVat(model.getValue()));
        return dto;
    }

    public static BigDecimal calculateVat(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        return amount.subtract(amount.divide(BigDecimal.valueOf(1.2), 2, RoundingMode.HALF_UP));
    }
    
    public static BigDecimal applyRate(BigDecimal expenseValue, Double rate) {
        if (expenseValue == null) {
            return null;
        }
        if (rate == null) {
            return expenseValue;
        }
        return expenseValue.multiply(BigDecimal.valueOf(rate));
    }

}
