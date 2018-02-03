package com.engage.backendcodingchallenge.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.engage.backendcodingchallenge.model.Expense;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RestClientTest
public class ExpenseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void whenFindAll_thenReturnExpenses() {
        // given
        Expense expense1 = new Expense(null, new Date(), BigDecimal.valueOf(12.45), "Dinner");
        Expense expense2 = new Expense(null, new Date(), BigDecimal.valueOf(5.99), "Pharmacy");
        entityManager.persist(expense1);
        entityManager.persist(expense2);
        entityManager.flush();

        // when
        List<Expense> found = new ArrayList<>();
        expenseRepository.findAll().forEach(found::add);

        // then
        assertEquals(Integer.valueOf(1), found.get(0).getId());
        assertEquals(BigDecimal.valueOf(12.45), found.get(0).getValue());
        assertEquals(expense1.getReason(), found.get(0).getReason());

        assertEquals(Integer.valueOf(2), found.get(1).getId());
        assertEquals(BigDecimal.valueOf(5.99), found.get(1).getValue());
        assertEquals(expense2.getReason(), found.get(1).getReason());
    }

    @Test
    public void whenFindById_thenReturnExpense() {
        // given
        Expense expense1 = new Expense(null, new Date(), BigDecimal.valueOf(12.45), "Dinner");
        entityManager.persist(expense1);
        entityManager.flush();

        // when
        List<Expense> foundList = new ArrayList<>();
        expenseRepository.findAll().forEach(foundList::add);
        Expense found = expenseRepository.findOne(1);

        // then
        assertEquals(Integer.valueOf(1), found.getId());
        assertEquals(BigDecimal.valueOf(12.45), found.getValue());
        assertEquals(expense1.getReason(), found.getReason());
    }

    @Test
    public void whenSave_thenExpensePersisted() {
        // given
        Expense expense1 = new Expense(null, new Date(), BigDecimal.valueOf(12.45), "Dinner");

        // when
        Expense save = expenseRepository.save(expense1);

        // then
        assertEquals(Integer.valueOf(1), save.getId());
        assertEquals(BigDecimal.valueOf(12.45), save.getValue());
        assertEquals(expense1.getReason(), save.getReason());
    }

}
