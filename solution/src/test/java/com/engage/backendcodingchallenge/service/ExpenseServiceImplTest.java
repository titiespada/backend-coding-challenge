package com.engage.backendcodingchallenge.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.engage.backendcodingchallenge.model.Expense;
import com.engage.backendcodingchallenge.repository.ExpenseRepository;

@RunWith(SpringRunner.class)
public class ExpenseServiceImplTest {

    @TestConfiguration
    static class ExpenseServiceImplTestContextConfiguration {

        @Bean
        public ExpenseService expenseService() {
            return new ExpenseServiceImpl();
        }
    }

    @Autowired
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    private static Expense expenseToSave = new Expense(null, new Date(), BigDecimal.valueOf(12.45), "Dinner");
    private static Expense expense1 = new Expense(1, new Date(), BigDecimal.valueOf(12.45), "Dinner");
    private static Expense expense2 = new Expense(2, new Date(), BigDecimal.valueOf(5.99), "Pharmacy");
    private static Expense expense3 = new Expense(3, new Date(), BigDecimal.valueOf(678.68), "House");

    @Before
    public void setUp() {
        Mockito.when(expenseRepository.findAll())
        .thenReturn(new ArrayList<>(Arrays.asList(expense1, expense2, expense3)));
        Mockito.when(expenseRepository.findOne(1))
        .thenReturn(expense1);
        Mockito.when(expenseRepository.save(expenseToSave))
        .thenReturn(expense1);
    }

    @Test
    public void whenValidId_thenExpenseShouldBeFound() {
        Integer id = Integer.valueOf(1);
        Expense found = expenseService.findById(id);

        assertEquals(id, found.getId());
    }

    @Test
    public void whenFindAll_thenAllExpenseShouldBeFound() {
        List<Expense> found = expenseService.findAll();

        assertEquals(3, found.size());
    }

    @Test
    public void whenSave_thenExpenseSavedShouldBeReturned() {
        Expense found = expenseService.save(expenseToSave);

        assertEquals(expenseToSave.getReason(), found.getReason());
        assertEquals(expenseToSave.getValue(), found.getValue());
    }

}
