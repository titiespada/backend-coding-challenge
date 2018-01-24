package com.engage.backendcodingchallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.engage.backendcodingchallenge.model.Expense;

/**
 * Repository to do CRUD operations on expense table.
 * @author patriciaespada
 *
 */
@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

}
