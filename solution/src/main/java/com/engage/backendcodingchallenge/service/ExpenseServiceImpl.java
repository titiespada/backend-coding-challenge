package com.engage.backendcodingchallenge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engage.backendcodingchallenge.model.Expense;
import com.engage.backendcodingchallenge.repository.ExpenseRepository;

@Service("expenseService")
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public List<Expense> findAll() {
		List<Expense> expenses = new ArrayList<>();
		expenseRepository.findAll().forEach(e -> expenses.add(e));
		return expenses;
	}

	@Override
	public Expense findById(Integer id) {
		return expenseRepository.findOne(id);
	}

	@Override
	public Expense save(Expense expense) {
		return expenseRepository.save(expense);
	}

}