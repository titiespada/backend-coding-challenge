package com.engage.backendcodingchallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.engage.backendcodingchallenge.model.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

}
