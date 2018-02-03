package com.engage.backendcodingchallenge.service;

import java.util.List;

import com.engage.backendcodingchallenge.model.Expense;

public interface ExpenseService {

    List<Expense> findAll();
    Expense findById(Integer id);
    Expense save(Expense expense);

}
