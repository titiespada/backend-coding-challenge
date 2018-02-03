package com.engage.backendcodingchallenge.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.engage.backendcodingchallenge.dto.ExpenseDto;
import com.engage.backendcodingchallenge.model.Expense;

public class ExpenseUtilTest {

    @Test
    public void whenCalculatingVatForNull_thenNothingIsReturned() {
        BigDecimal amount = null;
        BigDecimal vat = ExpenseUtil.calculateVat(amount);
        assertNull(vat);
    }

    @Test
    public void whenCalculatingVatForZero_thenNoVatValueIsReturned() {
        BigDecimal amount = BigDecimal.valueOf(0.0);
        BigDecimal vat = ExpenseUtil.calculateVat(amount);
        assertTrue(0.0 == vat.doubleValue());
    }

    @Test
    public void whenCalculatingVatForNumber_thenCorrectVatIsReturned() {
        BigDecimal amount = BigDecimal.valueOf(45.89);
        BigDecimal vat = ExpenseUtil.calculateVat(amount);
        assertEquals(BigDecimal.valueOf(7.65), vat);
    }

    @Test
    public void whenApplyingANullRate_thenNothingHappens() {
        BigDecimal expenseValue = BigDecimal.valueOf(45.89);
        Double rate = null;
        BigDecimal expenseNewValue = ExpenseUtil.applyRate(expenseValue, rate);
        assertEquals(expenseNewValue, expenseValue);
    }

    @Test
    public void whenApplyingANullExpense_thenReturnsNull() {
        BigDecimal expenseValue = null;
        Double rate = 0.5;
        BigDecimal expenseNewValue = ExpenseUtil.applyRate(expenseValue, rate);
        assertNull(expenseNewValue);
    }

    @Test
    public void whenApplyingAValidRate_thenReturnsCorrectNewValue() {
        BigDecimal expenseValue = BigDecimal.valueOf(45.89);
        Double rate = 0.5;
        BigDecimal expenseNewValue = ExpenseUtil.applyRate(expenseValue, rate);
        assertEquals(BigDecimal.valueOf(22.945), expenseNewValue);
    }

    @Test
    public void whenGivenANullModel_thenReturnEmptyDto() {
        ExpenseDto dto = ExpenseUtil.createDto(null);
        assertNull(dto);
    }

    @Test
    public void whenGivenAModel_thenReturnDto() throws ParseException {
        String str = "27/02/2017";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(str);

        Expense model = new Expense(Integer.valueOf(1), date, BigDecimal.valueOf(3.67), "Lunch");
        ExpenseDto dto = ExpenseUtil.createDto(model);

        assertNotNull(dto);
        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getDate(), dto.getDate());
        assertEquals(model.getValue(), dto.getValue());
        assertEquals(model.getReason(), dto.getReason());
        assertEquals(BigDecimal.valueOf(0.61), dto.getVat());
    }

    @Test
    public void whenGivenANullDto_thenReturnEmptyModel() {
        Expense model = ExpenseUtil.createModel(null);
        assertNull(model);
    }

    @Test
    public void whenGivenADto_thenReturnModel() throws ParseException {
        String str = "27/02/2017";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(str);

        ExpenseDto dto = new ExpenseDto();
        dto.setId(Integer.valueOf(1));
        dto.setDate(date);
        dto.setGbpValue(BigDecimal.valueOf(3.67));
        dto.setReason("Lunch");

        Expense model = ExpenseUtil.createModel(dto);

        assertNotNull(model);
        assertEquals(dto.getId(), model.getId());
        assertEquals(dto.getDate(), model.getDate());
        assertEquals(dto.getGbpValue(), model.getValue());
        assertEquals(dto.getReason(), model.getReason());
    }

}
