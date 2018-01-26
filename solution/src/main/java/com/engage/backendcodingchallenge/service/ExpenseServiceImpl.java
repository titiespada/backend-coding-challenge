package com.engage.backendcodingchallenge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engage.backendcodingchallenge.exception.ExpenseIdNotFoundException;
import com.engage.backendcodingchallenge.model.Expense;
import com.engage.backendcodingchallenge.repository.ExpenseRepository;

@Service("expenseService")
@Transactional
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public List<Expense> findAll() {
		List<Expense> expenses = new ArrayList<>();
		expenseRepository.findAll().forEach(expenses::add);
		return expenses;
	}

	@Override
	public Expense findById(Integer id) {
		Expense expense = expenseRepository.findOne(id);
		if (expense == null) {
		    throw new ExpenseIdNotFoundException(String.format("Expense not found for id %s.", id));
		}
		return expense;
	}

	@Override
	public Expense save(Expense expense) {
		return expenseRepository.save(expense);
	}

}
