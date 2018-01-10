package com.engage.backendcodingchallenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.engage.backendcodingchallenge.model.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

}
